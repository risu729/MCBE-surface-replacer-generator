import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Arrays;
import java.util.ArrayList;

class Main {
  
  static String[][] errArray;
  
  public static void main(String[] args){
    
    long time = System.currentTimeMillis();

    memory();
    
    //read Errors.txt in errArray
    
    try {
      String errorsFile = Files.readString(Path.of("resources","Errors.txt"));
      
      errArray = new String[2][];
      errArray[0] = errorsFile.split("\n");
      errArray[1] = new String[errArray[0].length];
      
      for (int i = 0; i < errArray[0].length; i++) {
        String[] split = errArray[0][i].split(":", 2);
        errArray[0][i] = split[0];
        errArray[1][i] = split[1];
      }
      
    } catch (IOException err) {
      error(999);
    }
    
    //load Settings.txt in String[]
    
    String settingsFile;

    try {
      settingsFile = Files.readString(Path.of("Settings.txt"));
    } catch (IOException err) {
      settingsFile = null;
      error(101);
    }

    final String[] settings = settingsFile.split("\n");
    
    //check the format of the file
    
    if (!(settings.length == 5 || settings.length == 6)) {
      error(102);
    }

    for (int i = 0; i < settings.length; i++) {
      if (settings[i].isEmpty()) {
        error(103);
      }
    }

    //check the format of 1st line, the function name
    
    if (!(settings[0].matches("^[\\w\\.\\-\\(\\)]+$"))) {
      error(111);
    }
    
    final String funcName = settings[0];
    
    //check the format of 2nd line, the side length

    if (!(isInteger(settings[1]))) {
      error(121);
    }

    if (Integer.parseInt(settings[1]) < 1) {
      error(121);
    }

    if (Integer.parseInt(settings[1]) %16 > 0) {
      error(121);
    }

    final int sideChunk = Integer.parseInt(settings[1]) /16;
    
    final int[] areaCoords = new int[4];
    //0=-x, 1=-z, 2=+x, 3=+z
    
    areaCoords[0] = -16 * (int)Math.floor(sideChunk / (double)2);
    areaCoords[1] = areaCoords[0];
    areaCoords[2] = -1 + 16 * (int)Math.ceil(sideChunk / (double)2);
    areaCoords[3] = areaCoords[2];

    //check the format of 3rd line, the y coord

    final int[] yCoords = new int[2];

    if (settings[2].matches("^-?\\d+$")) {
      if (!(isInteger(settings[2]))) {
        error(131);
      }

      yCoords[0] = Integer.parseInt(settings[2]);
      yCoords[1] = yCoords[0];

    } else if (settings[2].matches("^-?\\d+, ?-?\\d+$")) {
      String[] split = settings[2].split(", ?");

      for (String check : split) {
        if (!(isInteger(check))) {
          error(131);
        }
      }
      
      if (Integer.parseInt(split[0]) < Integer.parseInt(split[1])) {
        yCoords[0] = Integer.parseInt(split[0]);
        yCoords[1] = Integer.parseInt(split[1]);

      } else {
        yCoords[0] = Integer.parseInt(split[1]);
        yCoords[1] = Integer.parseInt(split[0]);
      }

    } else {
      error(133);
    }

    for (int i : yCoords) {
      if (i < -64 || i > 319) {
        error(132);
      }
    }
    
    //check the format of 4th line, the replacing block names

    if (!(settings[3].matches
          ("^[\\w&&[^A-Z]]+( (-1|\\d|1[0-5]))?(, ?[\\w&&[^A-Z]]+( (-1|\\d|1[0-5]))?)*$"))) {
      error(141);
    }

    String[] blockRepl = settings[3].split(", ?");

    for (int i = 0; i < blockRepl.length; i++) {
      if (blockRepl[i].matches("^\\w+$")) {
        blockRepl[i] += " -1";
      }
    }

    //check the format of 5th line, the filling block names

    if (!(settings[4].matches
          ("^[\\w&&[^A-Z]]+( (\\d|1[0-5]))?, ?[\\w&&[^A-Z]]+( (\\d|1[0-5]))?$"))) {
      error(151);
    }

    String[] blockFill  = settings[4].split(", ?");

    for (int i = 0; i < 2; i++) {
      if (blockFill[i].matches("^\\w+$")) {
        blockFill[i] += " 0";
      }
    }

    // delete duplicated elements
    HashSet<String> blockReplHS = 
      new HashSet<>(Arrays.asList(blockRepl));

    blockReplHS.addAll(Arrays.asList(blockFill));

    ArrayList<String> blockReplAL = new ArrayList<>(blockReplHS);
    blockRepl = blockReplAL.toArray(new String[blockReplAL.size()]);

    //check the format of 6th line, the tag

    final String tag;

    if (settings.length == 6) {
      if (settings[5].contains("\"")) {
        error(161);
      }
      
      if (settings[5].matches("^\\w+$")) {
        tag = settings[5];
      } else {
        tag = "\"" + settings[5] + "\"";
      }
    } else {
      tag = null;
    }
    
    //show variables (for test)
    display("Funtion name", funcName);
    display("Replacing area corners coordinates", areaCoords);
    display("Y coordinates", yCoords);
    display("Replacing blocks names", blockRepl);
    display("Filling blocks names", blockFill);
    display("Armor stands tag", tag);
    memory();

    //set beginning of commands

    final String execute;
    
    if (tag == null) {
      execute = "execute @e[type=armor_stand] ~~~ fill ";
    } else {
      execute = "execute @e[type=armor_stand, tag=" + tag + "] ~~~ fill ";
    }

    final int height = yCoords[1] - yCoords[0] +1;

    final int[][] unitLen = new int[2][2];
    //1st index: repl - fill0, fill0 - fill1
    //2nd index: x,z

    //calculate how many 8*8 areas can be replaced in one command
    unitLen[0][0] = 8 * (int)Math.floor(Math.sqrt (512 / height));
    //512 = 32768 / 8^2
    unitLen[0][1] = unitLen[0][0];

    //calculate how large area can be replaced in one command
    final int sect;
    if (height < 128) {
      sect = 1;
      unitLen[1][0] = 16;
      unitLen[1][1] = 16;
    } else if (height < 256) {
      sect = 2;
      unitLen[1][0] = 8;
      unitLen[1][1] = 16;
    } else {
      sect = 4;
      unitLen[1][0] = 8;
      unitLen[1][1] = 8;
    }

    //calculate lines
    int linesCalc =
      forCount(areaCoords[0], areaCoords[2], unitLen[0][0])
      * forCount(areaCoords[1], areaCoords[3], unitLen[0][1])
      * blockRepl.length;

    if (!(blockFill[0].equals(blockFill[1]))) {
      linesCalc += 2 * sect
      * forCount(areaCoords[0], areaCoords[2], 32)
      * forCount(areaCoords[1], areaCoords[3], 32);     
    }

    //generate StringBuilder
    StringBuilder[] commands = new StringBuilder[(int) Math.ceil(linesCalc / (double) 10000)];
    for (int i = 0; i < commands.length; i++) {
      commands[i] = new StringBuilder();
    }

    //generate commands
    int lines = 0;
    
    for (int x = areaCoords[0]; x < areaCoords[2]; x += unitLen[0][0]) {
      int x2 = Math.min(x + unitLen[0][0] -1, areaCoords[2]);
      for (int z = areaCoords[1]; z < areaCoords[3]; z += unitLen[0][1]) {
        int z2 = Math.min(z + unitLen[0][1] -1, areaCoords[3]);
        for (String rep : blockRepl) {
          commands[lines / 10000].append(
            execute + x +" "+ yCoords[0] +" "+ z +" "
            + x2 +" "+ yCoords[1] +" "+ z2 +" "
            + blockFill[0] + " replace " + rep + "\n");
          lines++;
        }
      }
    }

    if (!(blockFill[0].equals(blockFill[1]))) {
      for (int i = 0; i < 17; i += 16) {
        for (int x = areaCoords[0] + i; x < areaCoords[2]; x += 32) {
          for (int x2= x + unitLen[1][0] -1; x2 < x + 16; x2 += unitLen[1][0]) {
            for (int z = areaCoords[1] + i; z < areaCoords[3]; z += 32) {
              for (int z2= z + unitLen[1][1] -1; z2 < z + 16; z2 += unitLen[1][1]) {
                commands[lines / 10000].append(
                  execute + x + " " + yCoords[0] + " " + z + " "
                  + x2 + " " + yCoords[1] + " " + z2 + " "
                  + blockFill[1] + " replace " + blockFill[0] + "\n");
                lines++;
              }
            }
          }
        }
      }
    }

    //show datas (for test)
    display("Function lines calculated", linesCalc);
    display("Function lines", lines);

    //generate files

    //create directories
    Path pDir = Path.of("results", funcName);
    Path pDirTxt = Path.of(pDir.toString(), "txt");
    Path pDirMcf = Path.of(pDir.toString(), "mcfunction");

    try {
      Files.createDirectory(pDir);
      Files.createDirectory(pDirTxt);
      Files.createDirectory(pDirMcf);
    } catch (IOException err) {
      error(201);
    }

    //copy settings file
    try {
      Files.copy(Path.of("Settings.txt"), Path.of(pDir.toString(), "Settings.txt"));
    } catch (IOException err) {
      error(201);
    }
    
    //create function files        
    for (int i = 0; i < commands.length; i++) {
      String abc = "abcdefghijklmnopqrstuvwxyz";
        
      String fileName;
      if (commands.length == 1) {
        fileName = funcName;
      } else {
        fileName = funcName + "_" + abc.charAt(i);
      }
        
      commands[i].insert(0, "#" + fileName + "\n");
          
      Path pFileTxt = Path.of(pDirTxt.toString(), fileName + ".txt");
      Path pFileMcf = Path.of(pDirMcf.toString(), fileName + ".mcfunction");

      try {
        Files.createFile(pFileTxt);
        Files.writeString(pFileTxt, commands[i]);
        Files.copy(pFileTxt, pFileMcf);
      } catch (IOException err) {
        error(201);
      }
    }

    //show execution time
    time = System.currentTimeMillis() - time;
    
    String timeStr;
    if (time < 10000) {
      timeStr = time + "ms";
    } else {
      timeStr = time / (double)1000 + "s";
    }

    display("Execution time", timeStr);

    memory();

    System.out.println("The function files were generated.");
  } //The end of the main method
  
   
  static void error (int id) {
    
    String msg = "Unknown reason.";
    
    if (id == 999) {
      msg = "Errors.txt doesn't exist.";
      
    } else {
      for (int i = 0; i < errArray[0].length; i++) {
        if (Integer.parseInt(errArray[0][i]) == id) {
          msg = errArray[1][i];
          break;
        }
      }
    }
    
    System.out.println("Error. "+ msg + " (" + id +")");
    System.exit(0);
  }
  
  static boolean isInteger (String str) {
    try {
      int n = Integer.parseInt(str);
      return true;
    } catch (NumberFormatException err) {
      return false;
    }
  }

  static int forCount (int init, int cond, int change) {
    return (int) Math.ceil ((cond - init) / (double) change);
  }

  static void display (String msg, int n) {
    System.out.println(msg);
    System.out.println(n);
    System.out.println("----------");
  }

  static void display (String msg, int[] array) {
    System.out.println(msg);
    for (int n : array) {
      System.out.println(n);
    }
    System.out.println("----------");
  }

  static void display (String msg, String str) {
    System.out.println(msg);
    System.out.println(str);
    System.out.println("----------");
  }

  static void display (String msg, String[] array) {
    System.out.println(msg);
    for (String str : array) {
      System.out.println(str);
    }
    System.out.println("----------");
  }

  static void memory () {
    Runtime rt = Runtime.getRuntime();
    System.out.println("Used memory: "+ (rt.totalMemory() - rt.freeMemory()) /(double)1024 /(double)1024 + "MB");
    System.out.println("Free memory: "+ rt.freeMemory() /(double)1024 /(double)1024 + "MB");
    System.out.println("Total memory: "+ rt.totalMemory() /1024 / (double)1024 + "MB");
    System.out.println("Max memory: "+ rt.maxMemory() /1024 / (double)1024.0 + "MB");
    System.out.println("----------");
  }
  
}
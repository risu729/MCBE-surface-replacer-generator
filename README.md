# MCBE-surface-replacer-generator

for MCBE, made by Risu ([@risu_minecraft](https://twitter.com/risu_minecraft))<br>
<br>
This program generates functions to replace the world surface to show borders of chunks.<br>
<br>
Edit "Settings.txt" to change the settings.<br>
<br>
The function files are generated in "results" folder.<br>
<br>
You can use this generator online using [Replit](https://replit.com/@risumcbe/MCBE-surface-replacer-generator).<br>
In Replit, please fork and run the generator.<br>
<br>

------------------------------------------------------------

### How to write Settings.txt<br>

1. Write the function name.<br>
The name can only contain alphanumeric characters, periods, underscores, hyphens, and parentheses.<br>
If the commands in the function are more than 10000, the function is automatically divided and named (function name)_a, (function name)_b, and so on.<br>
```surfaceReplacer```<br>
<br>

2. Write the side length of the square area for replacing the surface.<br>
It must be a positive multiple of 16.<br>
```1024```<br>
<br>

3. Define the Y coordinates for replacing the surface.<br>
The coordinates must be separated by ",".<br>
If you would like to replace only one column, you may write only one value.<br>
```0,3```<br>
```3```<br>
<br>

4. Write all block names you would like to replace.<br>
Each block name must be separated by ",".<br>
You may, but don't need to write a block metadata value.<br>
If you don’t write it, all metadata of the block name are replaced.<br>
The block metadata value must be an integer between -1 and 15.<br>
```dirt, grass, bedrock, air, wool, concrete 0, concrete 15```<br>
<br>

5. Write 2 block names you would like to fill. Each block name must be separated by ",".<br>
You may, but don’t need to write a metadata value.<br>
If you don’t write it, the state is automatically set as 0.<br>
The block metadata value must be an integer between 0 and 15.<br>
These blocks are also replaced.<br>
```stained_glass, stained_glass 8```<br>
<br>

6. Write a tag to specify armor stands executing the functions.<br>
If it's needed, the string is automatically quoted.<br>
You can use all characters, except a double quote.<br>
If you don't write it, all armor stands to execute the functions.<br>
```replacer```<br>
```Surface Replacer 1```<br>

------------------------------------------------------------
### How to use the functions <br>

1. Summon an armor stand at the southwestern corner of the center chunk you would like to replace the surface. <br>

2. If you set a tag, add the tag to it. <br>

3. Execute the all functions in repeating command blocks. <br>

4. Load chunks by flying over. <br>

------------------------------------------------------------
### Copyright <br>
This program is released under MIT License, see [LICENSE](https://github.com/risu-minecraft/MCBE-surface-replacer-generator/blob/main/LICENSE).

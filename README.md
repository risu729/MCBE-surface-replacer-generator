# MCBE-surface-replacer-generator

for MCBE, made by Risu ([@risu_minecraft](https://twitter.com/risu_minecraft))<br>
<br>
This program generates functions replacing the world surface to show borders of chunks.<br>
<br>
Edit "Settings.txt" to change the settings.<br>
<br>
The function files are generated in "results" folder.<br>
<br>
You can use this generator online using [Replit](https://replit.com/@risumcbe/MCBE-surface-replacer-generator).<br>
In Replit, please fork and run the generator.<br>
<br>
This is the link for GitHub.<br>
https://github.com/risu-minecraft/MCBE-surface-replacer-generator
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

------------------------------------------------------------

# MCBE-surface-replacer-generator

for MCBE (Minecraft統合版) by りす([@risu_minecraft](https://twitter.com/risu_minecraft))
<br> 
このプログラムは、ワールドの地面を置き換えチャンク境界を示すfunctionファイルを生成します。<br>
<br>
設定を変更するには「Settings.txt」を編集してください。<br>
<br>
functionファイルは「results」フォルダに生成されます。<br>
<br>
このプログラムは、[Replit](https://replit.com/@risumcbe/MCBE-surface-replacer-generator)で、オンラインで使用することができます。<br>
Replitでは、プログラムをフォークして実行してください。<br>
<br> 
GitHubのリンクです。<br> 
https://github.com/risu-minecraft/MCBE-surface-replacer-generator 
<br> 

------------------------------------------------------------ 

### Settings.txtの書き方<br>。

1. function名を書きます。<br>。
英数字、ピリオド、アンダースコア、ハイフン、カッコのみを使用できます。<br>。
function内のコマンドが10000個以上の場合は、自動的に関数が分割され、(function名)_a,(function名)_b...となります。<br>
````surfaceReplacer```<br>
<br> 

2. 地面を置き換える正方形の範囲の1辺の長さを指定します。<br>
16の正の倍数である必要があります。<br>
````1024```<br>
<br>

3. 地面を置き換えるY座標を指定します。<br>
それぞれの座標は「,」で区切ってください。<br>
もし、単一のY座標だけを置き換えたい場合は、値を1個だけ書いても構いません。<br>
```0,3```<br>
```3```<br> 
<br>

4. 置き換え元のブロックを指定します。<br>
各ブロック名は「,」で区切ってください。<br>
ブロックのデータ値は指定しても構いませんが、その必要はありません。<br>
指定しなかったまたは-1とした場合は、そのブロック名のすべてのメタデータが置き換えられます。
ブロックのメタデータの値は -1 から 15 までの整数でなければなりません。
``dirt, grass, bedrock, air, wool, concrete 0, concrete 15``<br> <br> 
<br> 

5. 埋めたいブロック名を2つ書いてください。それぞれのブロック名は「,」で区切らなければなりません。<br>。
<br> メタデータの値を書いてもいいですが、書かなくてもいいです。
書き込まないと、自動的に状態が0になります。<br>。
ブロックのメタデータの値は、0から15までの整数でなければなりません。
また、これらのブロックは置き換えられます。<br> 
``stained_glass, stained_glass 8```<br>。
<br> 

6. <br> 6. 関数を実行するアーマースタンドを指定するタグを書きます。
必要であれば、文字列は自動的にクォートされます。
二重引用符以外のすべての文字が使用できます。<br>。
書き込まなければ、すべてのアーマースタンドが機能を実行することになります。
<br> ``replacer``<br> 
<br> ``表面リプレーサー1``<br> 

------------------------------------------------------------ 
### functionの使い方 <br>

1. 地面を置き換える範囲の中心のチャンクの南西の角に防具立てを置く。<br>

2. タグを設定した場合は、そのタグを防具立てに追加します。<br>

3. リピートモードのコマンドブロックで、すべてのfunctionを実行する。<br>

4. 飛行するなどして全てのチャンクをロードする。<br>

------------------------------------------------------------ 
### Copyright <br>
このプログラムはMITライセンスで公開されています。[LICENSE-ja](https://github.com/risu-minecraft/MCBE-surface-replacer-generator/blob/main/LICENSE-ja)を参照してください。

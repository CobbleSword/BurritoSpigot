BurritoSpigot Server
======

Compilation
-----------
To compile BurritoSpigot, you will need JDK 8 or higher, maven, an internet connection, and git.

Clone this repo and run `./burrito jar` from bash in the server folder.
The output file will be in target/BurritoSpigot.jar.

Contributing
------------
BurritoSpigot uses CraftBukkit's patch system.
To setup a development enviroment, run `./burrito setup`.
This will download and decompile Minecraft, and then apply our patches.
Once you're done making changes, run `./burrito rb` to rebuild the patches.
Also, make sure to include `// BurritoSpigot` comments to indicate modified NMS sources/non-burritospigot files.

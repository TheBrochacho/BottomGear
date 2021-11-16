# Welcome to Bottom Gear Mates

Bottom Gear is a mod that gives the player another way to control mob spawns around them. Once a player's gear score reaches a configurable threshold for a given dimension, mobs will no longer spawn around that player.

A player's total gear score is calculated by taking the sum of their individual equipment's gear scores.
Equipment refers to any equipable item that can be placed into an armor/tinker's/baubles/traveller's gear slot or any weapon on your hotbar.

Use the `bottom stick` in game while in creative mode to print a list of all equipment items in the players inventory to the Minecraft console. Copy-Paste that output into the `GearScores.txt` file located in `config/Bottom Gear`. The default gear score assigned is `1`.

How the generated output is formatted:
```
#unlocalized item name
(Forge Unique Item Name)@(Damage Value)=(Gear Score)
```
The gear score value can be changed to whatever you want. 

Crouch + Use the `bottom stick` in game while in creative mode to print a list of all registered dimensions to the Minecraft console. Copy-Paste that output into the `DimScores.txt` file located in `config/Bottom Gear`. The default difficulty assigned to each dimension is `Integer.MAX_VALUE`.

How the generated output is formatted:
```
#Dimension name
(Dimension ID)=(Dimension Difficulty)
```
The dimension difficuly can be changed to whatever you want.

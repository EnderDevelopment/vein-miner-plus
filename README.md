# Minecraft Vein Miner Plugin

VeinMiner+Book O'Decay is a Minecraft plugin for Paper servers that provides configurable vein mining, block category selection, debris decay, a guide book menu, and death chests. It supports ores, trees, grass, gravel, stone, and dark stone categories, with per-player settings stored between restarts.

## Install

1. Build the plugin with Java 21 and Maven:
   ```bash
   mvn package
   ```
2. Copy the generated `target/plugin.jar` file to your Paper server's `plugins` folder.
3. Start or restart the server.
4. Use `/veinbook` in game to receive the configuration book.

Requirements:

- Paper Minecraft server 1.21.11 or a compatible Paper 1.21 server.
- Java 21.
- Maven and Java 21 are required only for building from source.

## Use

1. Run `/veinbook` to receive the Vein Miner Guide.
2. Right-click the guide to open the Vein Miner Blocks menu.
3. Click a category to enable or disable vein mining for that category.
4. Break a supported block while its category is enabled. Connected blocks in the same category are mined automatically, up to 128 blocks.
5. Configure the debris chance with:
   ```text
   /veindecay <category> <percent>
   ```
   Valid categories are `ores`, `trees`, `grass`, `gravel`, `stone`, and `dark_stone`. The percentage must be from 0 to 100.
6. When decay affects a connected block, that block is replaced with air and gravel is dropped instead of the normal block drops.
7. When a player dies without keep inventory enabled, the death drops are placed into a chest at the death location. If the location is occupied, the plugin attempts to place the chest one block higher.

The default enabled categories are ores and trees. Grass, gravel, stone, and dark stone are disabled by default. Player category and decay settings are saved in the plugin data folder.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/860c5991-26f2-4311-af69-907bec824e03?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=860c5991-26f2-4311-af69-907bec824e03

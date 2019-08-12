# Version 1.4

- Added "PluginBase" dependency. The plugin now uses that API for a bunch of stuff, it will still function the same but some parts of the plugins code have been moved to the new API and are much better written now
- Fixed bug that caused wands not to have a cooldown

# Version 1.3

- Added slime wand
- Added config file allowing the user control over some wand spawn settings
- Added permission ```wands.give```. Now only players that have this permission or are operators can recieve wands using commands
- Added ```/wands help``` command that will show a list of all commands
- Fixed coloring of pumpkin wand
- Fixed bug that caused rocket wand not to work in some situations

# Version 1.2

- Updated wand descriptions
- Wands will now spawn in different rarity types (i.e. common, uncommon, ...) These will not effect the spawn rate of the wand but will rather function as a visual indicator of its power
- Changed from a gunpowder based system to a cooldown based system. All wands will no be usable without the consumption of items. However they will need some time to recharge
- Fixed bug that caused ice wand to not work in caves
- Fixed ```/wands give <name>``` command to work with an improved way of spawning wands
- Removed ```/wands free``` command

# Version 1.1

- Added cloud, trickery and pumpkin wand
- Added particle effects to all wands
- Added ```/wands free``` command (OP only) that makes the use of wands stop taking gunpowder as fuel. This can be toggled
- Made summoners wand less likely to spawn wolfs inside of blocks
- Increased the price of fireball wand (1 -> 2)
- Fixed bug that caused wands to spawn incorrectly
- Fixed bug that caused wands to not work in caves

# Version 1.0

- Added a [Bukkit Page](https://dev.bukkit.org/projects/simple-wands)
- Official release on Bukkit Dev

# Version 0.2

- Added sound effects for all wands
- Added summoners, lightning, rocket and craftsman wands

# Version 0.1

- Made wands use different amounts of gunpowder on action
- Made wands drop after killing witches
- Added fireball, ice, earth and teleport wands
- Initial release (not publicly shared yet)

# FastCore

FastCore is a Bukkit/Spigot plugin for Minecraft servers that provides essential core functionalities and features for server management. It offers a wide range of commands, data management, listeners, and utilities to enhance the server administration experience.

## Features

- **Player Management:** FastCore provides various commands and data management systems for managing players on the server, such as setting ranks, warning players, kicking, muting, banning, and blacklisting.

- **Punishment System:** The plugin includes a comprehensive punishment system with support for warnings, mutes, temporary mutes, bans, temporary bans, and blacklists. Punishments can be issued, revoked, and tracked through the plugin.

- **Grant System:** FastCore offers a grant system that allows server administrators to give temporary permissions or ranks to players. Grants can be managed, revoked, and tracked through the plugin.

- **Staff Tools:** The plugin includes a set of commands and tools for staff members, such as staff chat, admin chat, vanish mode, mod mode, freeze mode, teleportation, gamemode switching, healing, feeding, and more.

- **Economy System:** FastCore provides basic economy functionalities, including balance management, bounties, payments, and an economy command for staff members to manage server economy settings.

- **Permissions and Ranks:** The plugin integrates with the Bukkit permissions system and allows administrators to define ranks with custom prefixes, colors, display names, and permissions.

- **Tags:** FastCore supports custom tags that can be assigned to players. Tags can be granted, revoked, and managed through the plugin.

- **World Separation:** The plugin allows for separating worlds on the server, each with its own set of configuration options.

- **Server Management:** FastCore includes commands for server management, such as server listing, player listing, server chat management, broadcast, settings, server teleportation, and more.

## Getting Started

To use FastCore on your Bukkit/Spigot Minecraft server, follow these steps:

1. Download the latest version of the plugin from the releases section.
2. Place the downloaded JAR file into the `plugins` folder of your server.
3. Start or reload your server.
4. Configure the plugin by modifying the `config.yml` file located in the `plugins/FastCore` folder.
5. Customize ranks, tags, and other settings based on your server's requirements.
6. Restart or reload your server to apply the changes.

## Commands and Permissions

FastCore provides the following commands:

- `/setrank` - Sets the rank of a player.
- `/warn` - Warns a player.
- `/kick` - Kicks a player from the server.
- `/mute` - Mutes a player.
- `/unmute` - Unmutes a player.
- `/tempmute` - Temporarily mutes a player.
- `/ban` - Bans a player from the server.
- `/unban` - Unbans a player.
- `/tempban` - Temporarily bans a player.
- `/blacklist` - Blacklists a player from the server.
- `/unblacklist` - Unblacklists a player.
- `/history` - Shows the punishment history of a player.
- `/clearhistory` - Clears the punishment history of a player.
- `/grant` - Grants a permission or rank to a player.
- `/grants` - Shows the grants of a player.
- `/ungrant` - Revokes a grant from a player.
- `/cleargranthistory` - Clears the grant history of a player.
- `/staffchat` - Sends a message in staff chat.
- `/adminchat` - Sends a message in admin chat.
- `/managementchat` - Sends a message in management chat.
- `/vanish` - Toggles vanish mode.
- `/togglestaffalerts` - Toggles staff alerts.
- `/gamemode` - Changes the gamemode of a player.
- `/heal` - Heals a player.
- `/feed` - Feeds a player.
- `/clear` - Clears the inventory of a player.
- `/clearchat` - Clears the chat.
- `/mutechat` - Mutes the chat.
- `/fly` - Toggles fly mode.
- `/teleport` - Teleports a player.
- `/teleporthere` - Teleports a player to another player.
- `/teleportall` - Teleports all players to a location.
- `/modmode` - Toggles mod mode.
- `/freeze` - Freezes or unfreezes a player.
- `/report` - Reports a player.
- `/buildmode` - Toggles build mode.
- `/togglemessages` - Toggles receiving messages.
- `/alts` - Shows the alternate accounts of a player.
- `/onlineplayers` - Shows the list of online players.
- `/invsee` - Opens the inventory of a player.
- `/rank` - Shows the rank of a player.
- `/chatcolor` - Sets the chat color of a player.
- `/broadcast` - Sends a broadcast message.
- `/settings` - Shows or modifies plugin settings.
- `/speed` - Sets the speed of a player.
- `/sudo` - Executes a command as another player.
- `/balance` - Shows the balance of a player.
- `/bounty` - Places a bounty on a player.
- `/unbounty` - Removes a bounty from a player.
- `/pay` - Sends money to another player.
- `/economy` - Manages the server economy.
- `/stats` - Shows the stats of a player.
- `/resetstats` - Resets the stats of a player.
- `/togglescoreboard` - Toggles the scoreboard display.
- `/enderchest` - Opens the ender chest of a player.
- `/rewarn` - Reissues a warning to a player.
- `/remute` - Reissues a mute to a player.
- `/retempmute` - Reissues a temporary mute to a player.
- `/reban` - Reissues a ban to a player.
- `/retempban` - Reissues a temporary ban to a player.
- `/reblacklist` - Reissues a blacklist to a player.
- `/servermanager` - Manages servers on the network.
- `/ping` - Checks the ping of a player.
- `/reports` - Lists all open reports.
- `/clearreports` - Clears all open reports.
- `/seen` - Shows the last login information of a player.
- `/rankdisguise` - Disguises as a specific rank.
- `/nickname` - Sets the nickname of a player.
- `/realname` - Shows the real name of a player.
- `/tags` - Lists all available tags.
- `/tag` - Assigns a tag to a player.
- `/user` - Manages users and their permissions.
- `/powertool` - Sets a powertool command for a player.
- `/find` - Finds a player on the network.
- `/server` - Shows information about the current server.
- `/send` - Sends a player to another server.
- `/glist` - Lists the servers on the network.
- `/hub` - Sends a player to the hub server.
- `/spawn` - Teleports

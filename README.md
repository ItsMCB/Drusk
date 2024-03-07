# Drusk ✨
> The module-based core feature suite for Spigot/Paper/etc. servers.

*This project is a work-in-progress and isn't fully documented.*

# Features ⚡





## General
### Teleportation
Teleport to different places.
`/top` - Teleport to the safest top block relative to your location.
`/back` - Teleport to a previous location.
`/tpm` - Teleport to the middle of a block.
`/tpto <username>` - Create a teleport request to another player
`/tphere <username>` - Teleport another player to you

#### Spawn
- Set or visit the initial area on the server.
- Permission: `N/A`
- `/spawn` - Visit spawn area.
- `/spawn set` - Set the spawn area.

### Messaging
- Allow players to send messages to each other.
- Usage: `/msg [online user] [message]`
- Permission: `drusk.msg`
- For moderation purposes, messages will also be sent to players with the permission `drusk.mod`.

### Notify
- Plays a sound when players enter the game or send a message in chat.
- Usage: Grant the permission node to hear the sound.
- Permission: `drusk.msg.notify`

### Text
- Virtual books for players to read.  Files can be added in the `texts` directory.
- Permission: `drusk.book.open`
- Usage: `/opentext [file name]`

### Build Ideas
- Generate a random build idea.
- Permission: `drusk.whattobuild`
- Usage: `/whattobuild`

### Skin / Costumes
- Have you player skin display as another. Currently lasts for your current server session only.
- Permission: `drusk.skin`
- `/skin set [valid username]` - Dress as the current skin of a valid Minecraft: Java Edition player.
- `/skin copy` - Dress as the skin of a player entity you're looking at. Works with NPCs too!





## Admin
### Status
- View information.
- Permission: `drusk.admin`
- `/status player <username>` - Information about an online player.
- `/status <server | storage | jvm | players>`

### Game Mode
- Set a different game mode.
- Usage: `/gamemode <username> [survival | creative | adventure | spectator] [username]`
  - `/gms [username]` - Switch game mode to survival.
  - `/gmc [username]` - Switch game mode to creative.
  - `/gma [username]` - Switch game mode to adventure.
  - `/gmsp [username]` - Switch game mode to spectator.

### Flyspeed
- Set flight speed.
- Permission: `drusk.flyspeed`
- Usage: `/flyspeed [0.0-10.0] [username]`

### Smite
- Summon lighting upon a player.
- Usage: `/smite <username> [amount of times]`

### Inventory
- Manage a player's inventory.
- `/inventory open <username>`
- `/inventory clear <username>`

### Item
- Display or edit the item in your hand.
- `/item info`
- `/item rename [new name]`
- `/item model-data <integer>`
- `/item lore set [index number] <text>`
- `/item lore remove [index number]`

### Special Items
- Get normally unobtainable or hard-to-get items.
- Permission: `drusk.specialitems`
- Usage: `/specialitems`

## Proxy
### Connect
- Request to connect to another server on the proxy.
- Usage: `/connect [registered server name]` - Asks proxy to connect player to that server.
- Troubleshooting: Plugin channel messages MUST be enabled for this feature to work properly.

## Extra
### Logger
- Log information to Discord.
- Configure in the `config.yml`

### WorldEdit Menu
- Pre-made WorldEdit commands for ease of use.
- Permission: `drusk.wm`
- Usage: `/wm`

### Armor Stand Editor
- Punch an armor stand while crouching to access an editor GUI.

### Colors
- View legacy Minecraft color codes

### Say
- Say something as yourself in chat through a command.
- Permission: `N/A`
- Usage: `/say <message>`

### Info
View useful information about the server, players, etc.

Usage:
`/info server` - Displays server information
`/info player [playername]` - Displays information about an online player.






# 📝 Planned Features
- Radio - play .nbs files through an in-game chest menu using the NoteBlockAPI

# ✋ Help
Please join the [Libre Galaxy](https://discord.gg/86qJJHtDgT) Discord server.

# ⚖️ License
[GNU General Public License v3.0](LICENSE)

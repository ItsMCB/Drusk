# Drusk
> A suite of useful core features for Spigot/Paper/etc. servers.

*This project is a work-in-progress and isn't fully documented.*

# ⚡ Current Features

## General

### Book
Virtual books for players to read.
Adds a workaround to display text content in a chest menu for Bedrock players (Geyser does not currently support virtual books).
Files can be added in the `texts` directory. 

Permission: `drusk.book.open`
Usage: `/openbook [file name]`

### Messaging
Allow players to send messages to each other.
Usage: `/msg [online user] [message]`
Permission: `drusk.msg`

For moderation purposes, messages will also be sent to players with the permission `drusk.mod`.

### Notify
Plays a sound when players enter the game or send a message in chat.
Usage: Grant the permission node to hear the sound.
Permission: `drusk.msg.notify`

### Skin / Costumes
Have you player skin display as another. Currently lasts for your current server session only.

Usage:
`/skin set [valid username]` - Dress as the current skin of a valid Minecraft: Java Edition player.
`/skin copy` - Dress as the skin of a player entity you're looking at. Works with NPCs too!

### Info
View useful information about the server, players, etc.

Usage:
`/info server` - Displays server information
`/info player [playername]` - Displays information about an online player.

### Connect
Command to request that a player be sent to another server on your proxy.

Usage:
`/connect [registered server name]` - Asks proxy to connect player to that server.

Troubleshooting:
Plugin channel messages MUST be enabled for this feature to work properly.

### Double Jump
...

### NPC Tool
...

### Spawn
...

### Special Events
...

### Drusk
Used to debug and reload the config.

Usage:
`/drusk reload` - Reloads the Drusk configuration file and features.

# 📝 Planned Features
- Radio - play .nbs files through an in-game chest menu using the NoteBlockAPI

# ✋ Help
Please join the [Libre Galaxy](https://discord.gg/86qJJHtDgT) Discord server.

# ⚖️ License
Drusk is currently licensed under the [GNU General Public License v3.0](LICENSE)

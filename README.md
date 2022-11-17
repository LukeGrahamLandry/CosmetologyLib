# CosmetologyLib

A library to help Minecraft mods add custom cosmetics. 
A unified interface for equipping cosmetics supplied by a variety of rendering libraries. 
Provides tools for managing which cosmetics each player has permission to equip. 

### Features

- track slots for different cosmetics
- lock and unlock access to cosmetics
- packets for syncing with server saved data
- widgets for displaying cosmetics in guis
- load remote cosmetics to allow changes without requiring players to update your mod
- most features work when shadowed (just register my event handlers manually)
- cosmetic rendering
    - [Geckolib](https://github.com/bernie-g/geckolib): add an armor model layer to the player 

> The Minecraft EULA/CUG says don't integrate with NFTs or sell cosmetics that look like capes, so... don't do that.

### Tutorials

- TODO: Creating Armor Models with Geckolib in Block Bench
- TODO: Creating Player Animations with Geckolib in Block Bench
- TODO: Adding Patron Only Cosmetics To Your Mod or Modpack

### Development Sponsors

- Daejangnamu (MCBG Cosmetics)
- [Adam](https://www.curseforge.com/members/adam98991/projects) (MC Eternal 2)

## Roadmap 

PRs for these or anything else you think would be cool are always welcome!

- download resource packs from a url
    - load cosmetic definitions from json
    - manifest file with hashes for efficient downloading
- port to multi-loader & later MC versions 
- tutorial videos on making the models/animations and putting them in a resource pack in the right format
- api improvements
    - split DataStoreImpl into parts
    - .mod package event listeners so it doesn't need to be shadowed
    - automatically unequip and unfavourite when relocked
- developer documentation
- automatically release source jar artifacts so people can keep comments
- let cosmetics register a render callback for the button widget 
- might be funny to detect the word cape in id/name/desc and do something weird to prohibit breaking eula

### Cosmetic Render Types

- geo model animations (support different situations like idle, walk, attack, jump, swim)
- geckolib animations as emotes
- sounds synced with animations
- ambient particles
- vanilla Modded Entity model format
- [CustomPlayerModels](https://github.com/tom5454/CustomPlayerModels/wiki/API-documentation#set-model-041) model format
- spoofing skins for vanilla clients
    - integrates well with cpm for modded clients
- little pets that follow you around
- replacing the player completely with different models
    - good for doing brain-dead "Minecraft But..." mods quickly 

### Data Sources

- patreon/kofi/github sponsers
    - load keys from datapack so other mods/modpacks can easily use
    - how to get patreon data securely
    - each creator can be its own DataStore instance but select cosmetics from same gui
- sql database
- [tebex.io](https://github.com/tebexio/Tebex-Forge)
- spigot plugin permissions DataSource

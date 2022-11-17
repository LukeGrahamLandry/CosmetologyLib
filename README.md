# CosmetologyLib

A library to help Minecraft mods add custom cosmetics. 
A unified interface for equipping cosmetics supplied by a variety of rendering libraries. 
Provides tools for managing which cosmetics each player has permission to equip. 

### Features

- render additional geckolib models on the player
- track slots for different cosmetics
- lock and unlock access to cosmetics
- packets for syncing with server saved data
- widgets for displaying cosmetics in guis

### Notes

- Since I want the library to work when shadowed, it can't rely on getting loaded as a mod. That means no mixins and no forge events.
- The Minecraft EULA/CUG says don't sell cosmetics that look like capes, so... don't do that.
- Mojang is probably cracking down on NFTs soon, so... don't do that.

### Tutorials

- TODO: Creating Armor Models with Geckolib in Block Bench
- TODO: Creating Player Animations with Geckolib in Block Bench
- TODO: Adding Patron Only Cosmetics To Your Mod or Modpack

### Roadmap 

- geckolib animations as emotes
- sounds synced with animations
- replacing the player completely with different models
  - good for doing brain-dead "Minecraft But..." mods quickly
- download resource packs from a url
  - load cosmetic definitions from json
  - manifest file with hashes for efficient downloading
- support ambient particles
- support vanilla Modded Entity model format
- support [CustomPlayerModels](https://github.com/tom5454/CustomPlayerModels/wiki/API-documentation#set-model-041) model format
- support spoofing skins for vanilla clients
  - integrates well with cpm for modded clients
- patreon/kofi/github sponsers DataStore
  - load keys from datapack so other mods/modpacks can easily use
  - how to get patreon data securely
  each creator can be its own DataStore instance but select cosmetics from same gui
- sql db DataStore
- [tebex.io](https://github.com/tebexio/Tebex-Forge) DataStore
- spigot plugin permissions DataSource
- port to multi-loader & later MC versions 
- tutorial videos on making the models/animations and putting the in a resource pack in the right format
- api improvements
  - split DataStoreImpl into parts
  - .mod package event listeners so it doesn't need to be shadowed
  - automatically unequip and unfavourite when relocked
- developer documentation
- automatically release source jar artifacts so people can keep comments

PRs for these or anything else you think would be cool are always welcome!

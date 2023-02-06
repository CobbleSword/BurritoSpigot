# 🌯 `BurritoSpigot` 🌯
BurritoSpigot is a fork of TacoSpigot 1.8.8 that offers several enhancements to performance as well as bug fixes. while offer extra APIs and support for plugins

Supports 300 Players @ 20 TPS (Given all your plugins are programmed well)

## Features
Since it's been a few years since 1.8.9 release, it has fallen behind in a few optimizations to the infrastructure to replies on, these changes just brings it up to speed
* Java 8+ support this brings all the performance improvements of the more later java versions
* Updated netty 4.0.23.Final => 4.1.75.Final
* Stops classic book exploit 
* Improve max chat length to 512
* Backport NamespacedKey to improve newer plugin compact

## Commons Plugins

* [ViaVersion](https://www.spigotmc.org/resources/viaversion.19254/history) Support 1.9 all the way up to 1.19 and onward on a 1.8.9 server
* [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/) An api common used by developers to edit packets
* [Vault](https://www.spigotmc.org/resources/vault.34315/) Allows your plugins to share currencies
* [Citizens](https://www.spigotmc.org/resources/citizens.13811/) Allows you to spawn NPC of players and entities
* [EssentialsX](https://essentialsx.net/downloads.html) a mix of common commands

# Download
1.8.8: Download [BurritoSpigot.jar](https://github.com/CobbleSword/BurritoSpigot/blob/downloads/jars/BurritoSpigot.jar?raw=true)

1.8.9: See GitHub Actions

# Compilation
See the [server directory's README.md](BurritoSpigot-Server/README.md#Compilation) for instructions to compile BurritoSpigot.

## How to modify knockback
Edit the `KnockbackSpigot.json` then restart the server

## Knockback Config / `KnockbackSpigot.json`
```json
{
  "knockbackFriction": 2.0,
  "knockbackHorizontal": 0.35,
  "knockbackVertical": 0.35,
  "knockbackVerticalLimit": 0.4,
  "knockbackExtraHorizontal": 0.425,
  "knockbackExtraVertical": 0.085
}
```

## Discord / Support
[Join Discord group](https://discord.gg/SBTEbSx)

## Special thanks
* CraftBukkit, [Spigot](https://spigotmc.org) , [Paper](https://github.com/PaperMC/Paper) , [TacoSpigot](https://github.com/TacoSpigot/TacoSpigot) and Kohi

## How to build
We have to use a patching system do to minecrafts copyright, so it's a little bit tricky
* `cd BurritoSpigot-Server`
* `./burrito dev`
* Wait till all the files have be patched
* Open the parent project in IntelliJ
* Done

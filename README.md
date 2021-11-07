# `BurritoSpigot`
BurritoSpigot is a fork of TacoSpigot 1.8.9 that offers several enhancements to performance as well as bug fixes. while offer extra APIs and support for plugins

Supports 300 Players @ 20 TPS (Given all your plugins are programmed well)

## Features
Since it's been a few years since 1.8.9 release, it has fallen behind in a few optimizations to the infrastructure to replies on, these changes just brings it up to speed
* Java 8+ support (WIP) this brings all the performance improvements of the more later java versions 
* Updated netty 4.0.23.Final => 4.1.68.Final
* Stops classic book exploit 

## Commons Plugins

* [ViaVersion](https://www.spigotmc.org/resources/viaversion.19254/history?__cf_chl_jschl_tk__=pmd_r4LeY2TmaRwR8_QpQZPjhUbYlsNfCtUx_bhfKx8jgYM-1635647788-0-gqNtZGzNAjujcnBszQnR) Support 1.9 all the way up to 1.17 and onward on a 1.8 server
* [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/) An api common used by developers to edit packets
* [Vault](https://www.spigotmc.org/resources/vault.34315/) Allows your plugins to share currencies
* [citizens](https://www.spigotmc.org/resources/citizens.13811/) Allows you to spawn NPC of players and entities
* [essentialsx](https://essentialsx.net/downloads.html) a mix of common commands

## Download
[Download BurritoSpigot.jar](https://nightly.link/CobbleSword/BurritoSpigot/workflows/build/main/BurritoSpigot.zip)


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
* Paper, Taco and Kohi

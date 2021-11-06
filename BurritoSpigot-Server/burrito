#!/usr/bin/env bash

# exit immediately if a command exits with a non-zero status
set -e

# get base dir regardless of execution location
while [ -h "$SOURCE" ]; do # resolve $SOURCE until the file is no longer a symlink
    DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
    SOURCE="$(readlink "$SOURCE")"
    [[ "$SOURCE" != /* ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done
SOURCE=$([[ "$SOURCE" = /* ]] && echo "$SOURCE" || echo "$PWD/${SOURCE#./}")
basedir=$(dirname "$SOURCE")/BurritoSpigot-Server

git submodule update --init
case "$1" in
    "rb" | "rbp" | "rebuild")
    (
        set -e
        cd "$basedir"
        scripts/makePatches.sh work/1.8.8
    )
    ;;
    "p" | "patch" | "apply")
    (
        set -e
        cd "$basedir"
        scripts/applyPatches.sh "$basedir" work/1.8.8
    )
    ;;
    "b" | "bu" | "build")
    (
        cd "$basedir"
        scripts/remap.sh "$basedir"
        scripts/decompile.sh "$basedir"
        scripts/applyPatches.sh "$basedir" work/1.8.8
        mvn clean install --file "$basedir"/../pom.xml
    )
    ;;
    "map" | "remap")
    (
        cd "$basedir"
        scripts/remap.sh "$basedir"
    )
    ;;
    "d" | "decompile")
    (
        cd "$basedir"
        scripts/decompile.sh "$basedir"
    )
    ;;
    "dev" | "setup")
    (
        cd "$basedir"
        scripts/remap.sh "$basedir"
        scripts/decompile.sh "$basedir"
        scripts/applyPatches.sh "$basedir" work/1.8.8
    )
    ;;
    *)
        echo "BurritoSpigot build tool command."
        echo ""
        echo " Commands:"
        echo "  * map, remap       | Downloads and deobfuscates Minecraft."
        echo "  * d, decompile     | Decompiles the Minecraft server jar."
        echo "  * rb, rbp rebuild  | Rebuild our patches."
        echo "  * p, apply, patch  | Apply the BurritoSpigot patches."
        echo "  * b, bu, build     | Downloads and decompiles Minecraft, and applies our patches, and builds the server."
        echo "  * setup, dev       | Downloads and decompiles Minecraft, and applies our patches"
    ;;
esac

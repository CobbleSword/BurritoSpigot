#!/usr/bin/env bash

(
set -e
PS1="$"
basedir="$(cd "$1" && pwd -P)"
if [ -z "$2" ]
then
    echo "Please run this script again with the clean decompile sources as an argument. In most cases this will be work/1.8.8"
    exit
fi

nms="$basedir/$2/net/minecraft/server"
cb="$basedir/src/main/java/net/minecraft/server"
#clean up and rebuild
rm -rf "$cb"
mkdir -p "$cb"
for file in $(/bin/ls "$basedir/nms-patches")
do
    patchFile="$basedir/nms-patches/$file"
    file="$(echo $file | cut -d. -f1).java"

    echo "Patching $file < $patchFile"
    sed -i 's/\r//' "$nms/$file" > /dev/null

    cp "$nms/$file" "$cb/$file"
    patch -d "$basedir/src/main/java/" "net/minecraft/server/$file" < "$patchFile"
done
)

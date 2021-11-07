#!/usr/bin/env bash

(
set -e
PS1="$"
basedir="$(cd "$1" && pwd -P)"
workdir=$basedir/work
builddatadir=$workdir/BuildData
minecraftversion=$(cat $builddatadir/info.json | grep minecraftVersion | cut -d '"' -f 4)
minecrafthash=$(cat $builddatadir/info.json | grep minecraftHash | cut -d '"' -f 4)
accesstransforms=$builddatadir/mappings/$(cat $builddatadir/info.json | grep accessTransforms | cut -d '"' -f 4)
classmappings=$builddatadir/mappings/$(cat $builddatadir/info.json | grep classMappings | cut -d '"' -f 4)
membermappings=$builddatadir/mappings/$(cat $builddatadir/info.json | grep memberMappings | cut -d '"' -f 4)
packagemappings=$builddatadir/mappings/$(cat $builddatadir/info.json | grep packageMappings | cut -d '"' -f 4)
jarpath=$workdir/$minecraftversion/$minecraftversion

echo "Downloading unmapped vanilla jar..."
if [ ! -f  "$jarpath.jar" ]; then
    mkdir -p "$workdir/$minecraftversion"
    curl -s -o "$jarpath.jar" "https://s3.amazonaws.com/Minecraft.Download/versions/$minecraftversion/minecraft_server.$minecraftversion.jar"
    if [ "$?" != "0" ]; then
        echo "Failed to download the vanilla server jar. Check connectivity or try again later."
        exit 1
    fi
fi

# OS X doesn't have md5sum, just md5 -r
if [[ "$OSTYPE" == "darwin"* ]]; then
   shopt -s expand_aliases
   alias md5sum='md5 -r'
   echo "Using an alias for md5sum on OS X"
fi

checksum=$(md5sum "$jarpath.jar" | cut -d ' ' -f 1)
if [ "$checksum" != "$minecrafthash" ]; then
    echo "The MD5 checksum of the downloaded server jar does not match the $builddatadir hash."
    exit 1
fi

echo "Applying class mappings..."
if [ ! -f "$jarpath-cl.jar" ]; then
    java -jar $builddatadir/bin/SpecialSource-2.jar map -i "$jarpath.jar" -m "$classmappings" -o "$jarpath-cl.jar" 1>/dev/null
    if [ "$?" != "0" ]; then
        echo "Failed to apply class mappings."
        exit 1
    fi
fi

echo "Applying member mappings..."
if [ ! -f "$jarpath-m.jar" ]; then
    java -jar $builddatadir/bin/SpecialSource-2.jar map -i "$jarpath-cl.jar" -m "$membermappings" -o "$jarpath-m.jar" 1>/dev/null
    if [ "$?" != "0" ]; then
        echo "Failed to apply member mappings."
        exit 1
    fi
fi

echo "Creating remapped jar..."
if [ ! -f "$jarpath-mapped.jar" ]; then
    java -jar $builddatadir/bin/SpecialSource.jar --kill-lvt -i "$jarpath-m.jar" --access-transformer "$accesstransforms" -m "$packagemappings" -o "$jarpath-mapped.jar" 1>/dev/null
    if [ "$?" != "0" ]; then
        echo "Failed to create remapped jar."
        exit 1
    fi
fi

echo "Installing remapped jar..."
mvn install:install-file -q -Dfile="$jarpath-mapped.jar" -Dpackaging=jar -DgroupId=net.minecraft -DartifactId=server -Dversion="$minecraftversion-SNAPSHOT"
if [ "$?" != "0" ]; then
    echo "Failed to install remapped jar."
    exit 1
fi
)

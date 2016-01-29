#!/bin/sh

WDIR=`dirname $0`
JAVA=`which java`
JAR="../target/TileMapVisualizer-1.0.0-SNAPSHOT.jar"

$JAVA -jar "$WDIR/$JAR" $*


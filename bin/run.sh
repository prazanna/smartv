#!/bin/sh
HOME_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
LIB_DIR=$HOME_DIR/lib
CLASSPATH=$(JARS=("$LIB_DIR"/*.jar); IFS=:; echo "${JARS[*]}")
CLASSPATH=$HOME_DIR/classes:$CLASSPATH
echo $CLASSPATH
java -Dsmart.tv.home=$HOME_DIR -cp "$CLASSPATH" com.synergy.smartv.source.SmartVRunner ${1+"$@"}
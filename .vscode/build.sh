#!/bin/bash

declare -r TRUE=0
declare -r FALSE=1

function exist() {
    which $1 >/dev/null && return $TRUE || return $FALSE
}

# Constants
workspaceDir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"/..
javac=$jdk6_64/bin/javac
javah=$jdk6_64/bin/javah
jar=$jdk6_64/bin/jar

# Configuration
dllName=CmdUtils
binDir=$workspaceDir/bin
srcDir=$workspaceDir/src
nativeDir=$workspaceDir/src/native

#Removable of old files
echo Removing old files
if [ -f $workspaceDir/MyLibrary.jar ]; then rm $workspaceDir/MyLibrary.jar; fi
if [ -d $binDir ]; then rm $binDir -r; fi
if [ -f $nativeDir/*.h ]; then rm $nativeDir/*.h; fi

#Recreating tree
echo Recreating directory tree
if ! [ -d $binDir ]; then mkdir $binDir; fi

#Compiling java files
echo Compiling java files
find $srcDir -name *.java -type f > $workspaceDir/.vscode/javaFiles.txt
$javac -d $binDir -sourcepath $srcDir @$workspaceDir/.vscode/javaFiles.txt

#Creating C header base on compiled java files
echo Creating C headers
$javah -jni -d $nativeDir -classpath $binDir @$workspaceDir/.vscode/nativeClassFiles.txt

#Building shared libraries for win32 and win64
if exist gcc32; then
    echo Building win32 dll
    gcc32 -I$jdk6_32/include -I$jdk6_32/include/win32 -I$nativeDir -shared -o $binDir/$dllName'32.dll' $nativeDir/$dllName'.c'
else
    echo "Missing gcc32 (Symbolic link to i686-mingw32-gcc-win32)"
fi

if exist gcc64; then
    echo Building win64 dll
    gcc64 -I$jdk6_64/include -I$jdk6_64/include/win32 -I$nativeDir -shared -o $binDir/$dllName'64.dll' $nativeDir/$dllName'.c'
else
    echo "Missing gcc64 (Symbolic link to x64-w64-mingw32-gcc-win32)"
fi

#Generating jar with all builded files
echo Generating jar
$jar -cfe $workspaceDir/MyLibrary.jar sk.pa3kc.mylibrary.Universal -C $binDir .

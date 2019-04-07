@echo off
setlocal

REM Constants
set workspaceDir=%~dp0..
set gcc32=C:\cygwin\bin\i686-w64-mingw32-gcc.exe
set gcc64=C:\cygwin\bin\x86_64-w64-mingw32-gcc.exe
set javac="%jdk6_64%\bin\javac.exe"
set javah="%jdk6_64%\bin\javah.exe"
set jar="%jdk6_64%\bin\jar.exe"

REM Configuration
set dllName=CmdUtils
set binDir=%workspaceDir%\bin
set srcDir=%workspaceDir%\src
set nativeDir=%workspaceDir%\src\native

echo Removing old files
IF EXIST %workspaceDir%\MyLibrary.jar (del %workspaceDir%\MyLibrary.jar /q)
IF EXIST %binDir% (rd %binDir% /s /q)
IF EXIST %nativeDir%\*.h (del %nativeDir%\*.h /q)

echo Recreating directory tree
if NOT EXIST %binDir% (mkdir %binDir%)

echo Compiling java files
dir /s /B %srcDir%\*.java > %workspaceDir%\.vscode\javaFiles.txt
%javac% -d %binDir% -sourcepath srcDir @%workspaceDir%\.vscode\javaFiles.txt

echo Creating C headers
%javah% -jni -d %nativeDir% -classpath %binDir% @%workspaceDir%\.vscode\nativeClassFiles.txt

echo Building 32-bit dll
%gcc32% -I"%jdk6_32%\include" -I"%jdk6_32%\include\win32" -I%nativeDir% -shared -o %binDir%\%dllName%32.dll %nativeDir%\%dllName%.c
echo Building 64-bit dll
%gcc64% -I"%jdk6_64%\include" -I"%jdk6_64%\include\win32" -I%nativeDir% -shared -o %binDir%\%dllName%64.dll %nativeDir%\%dllName%.c

echo Generating jar
%jar% -cfve %workspaceDir%\MyLibrary.jar sk.pa3kc.mylibrary.Universal -C %binDir% .

endlocal

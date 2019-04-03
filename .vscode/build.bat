@echo off
setlocal

set workspaceDir=%~dp0..
set gcc32=C:\cygwin\bin\i686-w64-mingw32-gcc.exe
set gcc64=C:\cygwin\bin\x86_64-w64-mingw32-gcc.exe
set javac="%jdk6_64%\bin\javac.exe"
set javah="%jdk6_64%\bin\javah.exe"
set jar="%jdk6_64%\bin\jar.exe"

echo Checking directory tree
IF NOT EXIST %workspaceDir%\bin (mkdir %workspaceDir%\bin)
IF NOT EXIST %workspaceDir%\src (mkdir %workspaceDir%\src)
IF NOT EXIST %workspaceDir%\src\native (mkdir %workspaceDir%\src\native)

echo Compiling java files
dir /s /B %workspaceDir%\src\*.java > %workspaceDir%\.vscode\javaFiles.txt
%javac% -d %workspaceDir%\bin -sourcepath %workspaceDir%\src @%workspaceDir%\.vscode\javaFiles.txt

echo Creating C headers
%javah% -jni -d %workspaceDir%\src\native -classpath %workspaceDir%\bin @%workspaceDir%\.vscode\nativeClassFiles.txt

echo Building 32-bit dll
%gcc32% -I"%jdk6_32%\include" -I"%jdk6_32%\include\win32" -I%workspaceDir%\src\native -shared -o %workspaceDir%\bin\CmdColor32.dll %workspaceDir%\src\native\CmdColor.c
echo Building 64-bit dll
%gcc64% -I"%jdk6_64%\include" -I"%jdk6_64%\include\win32" -I%workspaceDir%\src\native -shared -o %workspaceDir%\bin\CmdColor64.dll %workspaceDir%\src\native\CmdColor.c

echo Generating jar
%jar% -cfve %workspaceDir%\MyLibrary.jar sk.pa3kc.mylibrary.Universal -C %workspaceDir%\bin .

endlocal

@ECHO OFF
SETLOCAL

REM Constants
SET workspaceDir=%~dp0..
SET gcc32=C:\cygwin\bin\i686-w64-mingw32-gcc.exe
SET gcc64=C:\cygwin\bin\x86_64-w64-mingw32-gcc.exe
SET javac="%jdk6_64%\bin\javac.exe"
SET javah="%jdk6_64%\bin\javah.exe"
SET jar="%jdk6_64%\bin\jar.exe"
SET tempFile=%TEMP%\javaFiles.txt

REM Configuration
SET dllName=CmdUtils
SET binDir=%workspaceDir%\bin
SET srcDir=%workspaceDir%\src
SET nativeDir=%workspaceDir%\src\native

REM Output jars
SET outputJar=%workspaceDir%\MyLibrary.jar
SET sourcesJar=%workspaceDir%\MyLibrary-sources.jar

ECHO Removing old files
IF EXIST %outputJar% (DEL %outputJar% /q)
IF EXIST %sourcesJar% (DEL %sourcesJar% /q)
IF EXIST %binDir% (RD %binDir% /s /q)
IF EXIST %nativeDir%\*.h (DEL %nativeDir%\*.h /q)

ECHO Recreating directory tree
IF NOT EXIST %binDir% (MKDIR %binDir%)

ECHO Compiling java files
DIR /s /B %srcDir%\*.java > %tempFile%
%javac% -d %binDir% -sourcepath %srcDir% @%tempFile%

ECHO Creating C headers
%javah% -jni -d %nativeDir% -classpath %binDir% @%workspaceDir%\.vscode\nativeClassFiles.txt

ECHO Building 32-bit dll
%gcc32% -I"%jdk6_32%\include" -I"%jdk6_32%\include\win32" -I%nativeDir% -shared -o %binDir%\%dllName%32.dll %nativeDir%\%dllName%.c
ECHO Building 64-bit dll
%gcc64% -I"%jdk6_64%\include" -I"%jdk6_64%\include\win32" -I%nativeDir% -shared -o %binDir%\%dllName%64.dll %nativeDir%\%dllName%.c

ECHO Generating jar
%jar% -cfe %outputJar% sk.pa3kc.mylibrary.Universal -C %binDir% .
%jar% -cf %sourcesJar% -C %srcDir% sk

ENDLOCAL

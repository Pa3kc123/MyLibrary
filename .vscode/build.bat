@ECHO OFF
SETLOCAL

REM Constants
SET workspaceDir=%~dp0..
SET gcc32=C:\cygwin\bin\i686-w64-mingw32-gcc.exe
SET gcc64=C:\cygwin\bin\x86_64-w64-mingw32-gcc.exe
SET javac="%jdk6_64%\bin\javac.exe"
SET javah="%jdk6_64%\bin\javah.exe"
SET jar="%jdk6_64%\bin\jar.exe"

REM Configuration
SET dllName=CmdUtils
SET binDir=%workspaceDir%\bin
SET srcDir=%workspaceDir%\src
SET nativeDir=%workspaceDir%\src\native

ECHO Removing old files
IF EXIST %workspaceDir%\MyLibrary.jar (DEL %workspaceDir%\MyLibrary.jar /q)
IF EXIST %binDir% (RD %binDir% /s /q)
IF EXIST %nativeDir%\*.h (DEL %nativeDir%\*.h /q)

ECHO Recreating directory tree
IF NOT EXIST %binDir% (MKDIR %binDir%)

ECHO Compiling java files
DIR /s /B %srcDir%\*.java > %workspaceDir%\.vscode\javaFiles.txt
%javac% -d %binDir% -sourcepath srcDir @%workspaceDir%\.vscode\javaFiles.txt

ECHO Creating C headers
%javah% -jni -d %nativeDir% -classpath %binDir% @%workspaceDir%\.vscode\nativeClassFiles.txt

ECHO Building 32-bit dll
%gcc32% -I"%jdk6_32%\include" -I"%jdk6_32%\include\win32" -I%nativeDir% -shared -o %binDir%\%dllName%32.dll %nativeDir%\%dllName%.c
ECHO Building 64-bit dll
%gcc64% -I"%jdk6_64%\include" -I"%jdk6_64%\include\win32" -I%nativeDir% -shared -o %binDir%\%dllName%64.dll %nativeDir%\%dllName%.c

ECHO Generating jar
%jar% -cfe %workspaceDir%\MyLibrary.jar sk.pa3kc.mylibrary.Universal -C %binDir% .

ENDLOCAL

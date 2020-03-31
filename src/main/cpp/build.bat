@ECHO OFF
SETLOCAL

REM Constants
SET workspaceDir=%~dp0..\..\..
SET gcc32=C:\tools\cygwin\bin\i686-w64-mingw32-gcc.exe
SET gcc64=C:\tools\cygwin\bin\x86_64-w64-mingw32-gcc.exe
SET jdk6_64=C:\Program Files\Java\jdk1.6.0_45
SET jdk6_32=C:\Program Files (x86)\Java\jdk1.6.0_45
SET javah="%jdk6_64%\bin\javah.exe"

SET dllName=CmdUtils
SET binDir=%workspaceDir%\target\classes
SET outDir=%workspaceDir%\src\main\resources
SET srcDir=%workspaceDir%\src\main\java
SET nativeDir=%workspaceDir%\src\main\cpp

IF NOT EXIST %gcc64% (
    ECHO %gcc64% - no file found
    GOTO :EOF
)

IF NOT EXIST %gcc32% (
    ECHO %gcc32% - no file found
    GOTO :EOF
)

IF NOT EXIST %javah% (
    ECHO %javah% - no file found
    GOTO :EOF
)

ECHO Removing old files
IF EXIST %nativeDir%\*.h (DEL %nativeDir%\*.h /q)

ECHO Creating C headers
%javah% -jni -d %nativeDir% -classpath %binDir% @.\nativeClassFiles.txt

ECHO Building 32-bit dll
%gcc32% -I"%jdk6_32%\include" -I"%jdk6_32%\include\win32" -I%nativeDir% -shared -o %outDir%\%dllName%32.dll %nativeDir%\%dllName%.c
ECHO Building 64-bit dll
%gcc64% -I"%jdk6_64%\include" -I"%jdk6_64%\include\win32" -I%nativeDir% -shared -o %outDir%\%dllName%64.dll %nativeDir%\%dllName%.c

ENDLOCAL

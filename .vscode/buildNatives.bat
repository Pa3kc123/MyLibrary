@echo off
setlocal

set gcc32=C:\cygwin\bin\i686-w64-mingw32-gcc.exe
set gcc64=C:\cygwin\bin\x86_64-w64-mingw32-gcc.exe

echo Building 32-bit dll
%gcc32% -I"%jdk32%\include" -I"%jdk32%\include\win32" -I%1 -shared -o %232.dll %3.c
echo Building 64-bit dll
%gcc64% -I"%jdk64%\include" -I"%jdk64%\include\win32" -I%1 -shared -o %264.dll %3.c

endlocal

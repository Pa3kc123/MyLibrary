#linux
LINUXCC=gcc
LINUX_JDK_HOME=/usr/lib/jvm/java-8-openjdk-amd64

#win32
WIN32CC=i686-w64-mingw32-gcc
WIN32_JDK_HOME=/usr/lib/jvm/java-8-openjdk-amd64

#win64
WIN64CC=x86_64-w64-mingw32-gcc
WIN64_JDK_HOME=/usr/lib/jvm/java-8-openjdk-amd64

#macos
MACOS=gcc
MACOS_JDK_HOME=/usr/lib/jvm/java-8-openjdk-amd64

#global
FLAGS=-Wall -Werror -lm
TARGET=../resources

linux:
	$(LINUXCC) -fPIC $(FLAGS) -I. -I$(LINUX_JDK_HOME)/include -I$(LINUX_JDK_HOME)/include/linux -shared -o $(TARGET)/FloatMath.so FloatMath.c

win32:
	$(WIN32CC) $(FLAGS) -I. -I$(WIN32_JDK_HOME)/include -I$(WIN32_JDK_HOME)/include/windows -shared -o $(TARGET)/FloatMath32.dll FloatMath.c

win64:
	$(WIN64CC) $(FLAGS) -I. -I$(WIN64_JDK_HOME)/include -I$(WIN64_JDK_HOME)/include/windows -shared -o $(TARGET)/FloatMath64.dll FloatMath.c

macos:
	$(MACOSCC) $(FLAGS) -I. -I(MACOS_JDK_HOME)/include -I(MACOS_JDK_HOME)/include/darwin -dynamiclib -o $(TARGET)/FloatMath.dylib FloatMath.c

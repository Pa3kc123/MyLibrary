#include <stdio.h>
#include <windows.h>
#include <jni.h>
#include "sk_pa3kc_mylibrary_util_CmdColor.h"

#ifndef ENABLE_VIRTUAL_TERMINAL_PROCESSING
#define ENABLE_VIRTUAL_TERMINAL_PROCESSING 0x0004
#endif

JNIEXPORT void JNICALL Java_sk_pa3kc_mylibrary_util_CmdColor_init(JNIEnv *env, jclass obj)
{
    printf("Enabling virtual terminal processing ... ");
	HANDLE handle = GetStdHandle(STD_OUTPUT_HANDLE);
	DWORD dwMode = 0;
	GetConsoleMode(handle, &dwMode);
	dwMode |= ENABLE_VIRTUAL_TERMINAL_PROCESSING;
	SetConsoleMode(handle, dwMode);
    printf("DONE\n");
}
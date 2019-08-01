#include <stdio.h>
#include <windows.h>
#include <jni.h>
#include "sk_pa3kc_mylibrary_cmd_CmdUtils.h"

#ifndef ENABLE_VIRTUAL_TERMINAL_PROCESSING
#define ENABLE_VIRTUAL_TERMINAL_PROCESSING 0x0004
#endif

JNIEXPORT void JNICALL Java_sk_pa3kc_mylibrary_cmd_CmdUtils_init(JNIEnv *env, jclass obj)
{
	HANDLE handle = GetStdHandle(STD_OUTPUT_HANDLE);
	DWORD dwMode = 0;
	GetConsoleMode(handle, &dwMode);
	dwMode |= ENABLE_VIRTUAL_TERMINAL_PROCESSING;
	SetConsoleMode(handle, dwMode);
}

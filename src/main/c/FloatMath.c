#include <jni.h>
#include <math.h>

JNIEXPORT jfloat JNICALL Java_sk_pa3kc_mylibrary_util_FloatMath_sqrt(JNIEnv * env, jclass cls, jfloat a) {
    return sqrtf(a);
}

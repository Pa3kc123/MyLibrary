#include <jni.h>
#include <math.h>

JNIEXPORT jfloat JNICALL Java_sk_pa3kc_mylibrary_util_FloatMath_sqrt(JNIEnv * env, jclass cls, jfloat a) {
//     long i;
// 	float x2, y;
// 	const float threehalfs = 1.5F;

// 	x2 = a * 0.5F;
// 	y  = a;
// 	i  = * ( long * ) &y;                       // evil floating point bit level hacking
// 	i  = 0x5f3759df - ( i >> 1 );               // what the fuck?
// 	y  = * ( float * ) &i;
// 	y  = y * ( threehalfs - ( x2 * y * y ) );   // 1st iteration
// //	y  = y * ( threehalfs - ( x2 * y * y ) );   // 2nd iteration, this can be removed

// 	return y;
    return sqrtf(a); // This looks like casted sqrt func :(
}

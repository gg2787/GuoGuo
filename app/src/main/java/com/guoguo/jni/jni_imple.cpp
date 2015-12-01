#include <jni.h>
#include "com_guoguo_jni_JniDemo.h"

JNIEXPORT jint JNICALL Java_com_guoguo_jni_JniDemo_sayHello
  (JNIEnv *, jobject) {
  return 100;
  }
//
// Created by user on 7/30/2022.
//
#include <jni.h>


JNIEXPORT jstring JNICALL
Java_digikraft_project_digikraftbikestation_services_api_NetworkModule_getBaseURL(JNIEnv *env,jobject thiz) {
    return (*env)->NewStringUTF(env, "https://www.poznan.pl/mim/plan/");
}
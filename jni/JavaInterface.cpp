#include <jni.h>
#include <android/log.h>

#include <stdlib.h>
#include <stdio.h>
#include <string>

#include "libs/libmobi/src/save_epub.h"

#define LG_TAG "EbookConv"
#define LOGD(format, args...)  __android_log_print(ANDROID_LOG_DEBUG, LG_TAG, format, ##args);
#define LOGE(format, args...)  __android_log_print(ANDROID_LOG_ERROR, LG_TAG, format, ##args);

static std::string fromJstring(JNIEnv *env, jstring js) {
	const char *_js = env->GetStringUTFChars(js, NULL);
	std::string outStr(_js, env->GetStringUTFLength(js));
	env->ReleaseStringUTFChars(js, _js);
	return outStr;
}

extern "C"
JNIEXPORT int JNICALL Java_com_ebookconvlibrary_ConvLib_mobiToEpubNative(
	JNIEnv *env, jclass cls,
	jstring mobiFileName, jstring epubFileName)
{
	std::string fnameMobi = fromJstring(env, mobiFileName);
	std::string fnameEpub = fromJstring(env, epubFileName);
	return convertMobiToEpub(fnameMobi.c_str(), fnameEpub.c_str(), NULL, false);
}

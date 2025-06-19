#include <jni.h>
#include <string>
#include <memory>
#include "TelemetryManager.h"

std::unique_ptr<TelemetryManager> telemetryManager;


extern "C" JNIEXPORT jstring JNICALL
Java_utec_pi3_siembrahora_MainActivity_stringFromJNI(JNIEnv* env, jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C" {

// Notice ALL function names now end with "Native" to match NativeBridge.java

JNIEXPORT void JNICALL
Java_utec_pi3_siembrahora_NativeBridge_initTelemetryNative(JNIEnv *env, jclass clazz, jstring filePath) {
    const char *path = env->GetStringUTFChars(filePath, nullptr);
    telemetryManager = std::make_unique<TelemetryManager>(std::string(path));
    env->ReleaseStringUTFChars(filePath, path);
}

JNIEXPORT void JNICALL
Java_utec_pi3_siembrahora_NativeBridge_logSessionStartNative(JNIEnv *env, jclass clazz) {
    if (telemetryManager) telemetryManager->logSessionStart();
}

JNIEXPORT void JNICALL
Java_utec_pi3_siembrahora_NativeBridge_logSessionEndNative(JNIEnv *env, jclass clazz, jlong duration) {
    if (telemetryManager) telemetryManager->logSessionEnd(duration);
}

JNIEXPORT void JNICALL
Java_utec_pi3_siembrahora_NativeBridge_logCareActionNative(JNIEnv *env, jclass clazz, jstring plant_id, jstring action_type, jint adherence_seconds) {
    if (telemetryManager) {
        const char *plantIdStr = env->GetStringUTFChars(plant_id, nullptr);
        const char *actionTypeStr = env->GetStringUTFChars(action_type, nullptr);
        telemetryManager->logCareAction(plantIdStr, actionTypeStr, adherence_seconds);
        env->ReleaseStringUTFChars(plant_id, plantIdStr);
        env->ReleaseStringUTFChars(action_type, actionTypeStr);
    }
}

JNIEXPORT void JNICALL
Java_utec_pi3_siembrahora_NativeBridge_logFeatureInteractionNative(JNIEnv *env, jclass clazz, jstring feature_id) {
    if (telemetryManager) {
        const char *featureIdStr = env->GetStringUTFChars(feature_id, nullptr);
        telemetryManager->logFeatureInteraction(featureIdStr);
        env->ReleaseStringUTFChars(feature_id, featureIdStr);
    }
}

JNIEXPORT void JNICALL
Java_utec_pi3_siembrahora_NativeBridge_logAppPerformanceNative(JNIEnv *env, jclass clazz, jstring metric_name, jfloat value) {
    if (telemetryManager) {
        const char *metricNameStr = env->GetStringUTFChars(metric_name, nullptr);
        telemetryManager->logAppPerformance(metricNameStr, value);
        env->ReleaseStringUTFChars(metric_name, metricNameStr);
    }
}

JNIEXPORT void JNICALL
Java_utec_pi3_siembrahora_NativeBridge_logScreenViewNative(JNIEnv *env, jclass clazz, jstring screen_name) {
    if (telemetryManager) {
        const char *screenNameStr = env->GetStringUTFChars(screen_name, nullptr);
        telemetryManager->logScreenView(screenNameStr);
        env->ReleaseStringUTFChars(screen_name, screenNameStr);
    }
}

} // extern "C"
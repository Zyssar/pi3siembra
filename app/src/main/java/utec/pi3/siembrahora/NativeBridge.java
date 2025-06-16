package utec.pi3.siembrahora;

import android.content.Context;
import android.util.Log; // Make sure this is imported
import java.io.File;

public final class NativeBridge {

    private static final String TAG = "NativeBridge"; // A tag for logging

    private NativeBridge() {}

    static {
        try {
            System.loadLibrary("siembrahora");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Failed to load native library 'siembrahora'", e);
        }
    }

    // --- PUBLIC, SAFE-TO-CALL METHODS ---

    public static void initialize(Context context) {
        try {
            File telemetryFile = new File(context.getFilesDir(), "telemetry.csv");
            initTelemetryNative(telemetryFile.getAbsolutePath());
        } catch (Exception e) {
            Log.e(TAG, "An unexpected error occurred during telemetry initialization.", e);
        }
    }

    public static void logSessionStart() {
        try {
            logSessionStartNative();
        } catch (Exception e) {
            Log.e(TAG, "Failed to log session start.", e);
        }
    }

    public static void logSessionEnd(long durationSeconds) {
        try {
            logSessionEndNative(durationSeconds);
        } catch (Exception e) {
            Log.e(TAG, "Failed to log session end.", e);
        }
    }

    public static void logCareAction(String plantId, String actionType, int adherenceSeconds) {
        try {
            logCareActionNative(plantId, actionType, adherenceSeconds);
        } catch (Exception e) {
            Log.e(TAG, "Failed to log care action.", e);
        }
    }

    public static void logFeatureInteraction(String featureId) {
        try {
            logFeatureInteractionNative(featureId);
        } catch (Exception e) {
            Log.e(TAG, "Failed to log feature interaction.", e);
        }
    }

    public static void logAppPerformance(String metricName, float value) {
        try {
            logAppPerformanceNative(metricName, value);
        } catch (Exception e) {
            Log.e(TAG, "Failed to log app performance.", e);
        }
    }

    public static void logScreenView(String screenName) {
        try {
            logScreenViewNative(screenName);
        } catch (Exception e) {
            Log.e(TAG, "Failed to log screen view: " + screenName, e);
        }
    }


    // --- PRIVATE NATIVE METHOD DECLARATIONS ---
    // These are the actual links to the C++ functions.

    private static native void initTelemetryNative(String filePath);
    private static native void logSessionStartNative();
    private static native void logSessionEndNative(long durationSeconds);
    private static native void logCareActionNative(String plantId, String actionType, int adherenceSeconds);
    private static native void logFeatureInteractionNative(String featureId);
    private static native void logAppPerformanceNative(String metricName, float value);
    private static native void logScreenViewNative(String screenName);
}
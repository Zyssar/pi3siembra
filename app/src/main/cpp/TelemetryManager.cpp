#include "TelemetryManager.h"
#include <fstream>
#include <iostream>
#include <ctime>
#include <iomanip>
#include <sstream>
#include <chrono>
#include <android/log.h>
#define APPNAME "SiembraHoraTelemetry"
// --- Constructor ---

TelemetryManager::TelemetryManager(const std::string& csvFilePath) {
    // Store the provided file path so other functions can use it.
    this->telemetryCsvPath = csvFilePath;

    // Check if the CSV file already exists to see if a header is needed.
    // We open for reading first to check.
    std::ifstream fileCheck(this->telemetryCsvPath);
    if (!fileCheck.good()) {
        // The file doesn't exist or is not readable. Let's create it and write the header.
        std::ofstream newFile(this->telemetryCsvPath);
        if (newFile.is_open()) {
            newFile << "timestamp,event_type,param1,param2,param3\n";
            newFile.close();
        }
    }
    // If the file already exists, we don't need to do anything.
}

// --- TIER 1: CORE METRICS IMPLEMENTATION ---

void TelemetryManager::logSessionStart() {
    // Call the core append function with the specific event type and empty parameters.
    appendToCsv("session_start", "", "", "");
}

void TelemetryManager::logSessionEnd(long durationSeconds) {
    // Convert the duration into a string to be stored in the CSV's first parameter column.
    appendToCsv("session_end", std::to_string(durationSeconds), "", "");
}

void TelemetryManager::logCareAction(const std::string& plantId, const std::string& actionType, int adherenceSeconds) {
    // Fill all three parameter columns with the details of the care action.
    appendToCsv("care_action", plantId, actionType, std::to_string(adherenceSeconds));
}

// --- TIER 2: DEEPER INSIGHTS IMPLEMENTATION ---

void TelemetryManager::logFeatureInteraction(const std::string& featureId) {
    // Log the specific UI feature that the user interacted with.
    appendToCsv("feature_interaction", featureId, "", "");
}

// --- PRIVATE HELPER FUNCTIONS ---

std::string TelemetryManager::getCurrentTimestamp() {
    // Use the <chrono> library to get the current system time.
    auto now = std::chrono::system_clock::now();

    // Convert it to a time_t type, which is compatible with C-style time functions.
    auto in_time_t = std::chrono::system_clock::to_time_t(now);

    std::stringstream ss;
    // std::gmtime converts the time to UTC (Coordinated Universal Time), represented by 'Z'.
    // std::put_time formats the time into the specified ISO 8601 string format.
    ss << std::put_time(std::gmtime(&in_time_t), "%Y-%m-%dT%H:%M:%SZ");

    return ss.str();
}

void TelemetryManager::appendToCsv(const std::string& eventType, const std::string& col1, const std::string& col2, const std::string& col3) {
    // The file stream is now declared in its own scope.
    std::ofstream csvFile(this->telemetryCsvPath, std::ios::out | std::ios::app);

    if (!csvFile.is_open()) {
        // Use Android's logging system instead of std::cerr for better integration.
        __android_log_print(ANDROID_LOG_ERROR, APPNAME, "Error: Could not open telemetry file for writing: %s", this->telemetryCsvPath.c_str());
        return;
    }

    // Write the line and then explicitly check for write errors.
    csvFile << getCurrentTimestamp() << ","
            << eventType << ","
            << col1 << ","
            << col2 << ","
            << col3 << "\n";

    csvFile.flush(); // Ensure data is written to the file system immediately.

    if (csvFile.fail()) {
        __android_log_print(ANDROID_LOG_ERROR, APPNAME, "Error: Failed to write to telemetry file.");
    }

    // The file is closed automatically when csvFile goes out of scope here.
    // This is handled by the object's destructor (RAII principle).
}


// --- TIER 3: ADVANCED METRICS IMPLEMENTATION ---

void TelemetryManager::logAppPerformance(const std::string& metricName, float value) {
    // This logs a key-value pair for performance tracking.
    // We use the first two parameter columns.
    appendToCsv("app_performance", metricName, std::to_string(value), "");
}

void TelemetryManager::logScreenView(const std::string& screenName) {
    // This logs which screen the user navigated to, helping you map user flow.
    appendToCsv("screen_view", screenName, "", "");
}

//
// Created by neriou on 6/16/25.
//

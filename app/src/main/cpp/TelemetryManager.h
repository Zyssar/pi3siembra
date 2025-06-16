#ifndef TELEMETRY_MANAGER_H
#define TELEMETRY_MANAGER_H

#include <string>
#include <chrono>

class TelemetryManager {
public:
    // ... (constructor and Tier 1 & 2 functions remain the same) ...
    TelemetryManager(const std::string& csvFilePath);
    void logSessionStart();
    void logSessionEnd(long durationSeconds);
    void logCareAction(const std::string& plantId, const std::string& actionType, int adherenceSeconds);
    void logFeatureInteraction(const std::string& featureId);


    // --- TIER 3: ADVANCED METRICS (For future optimization) ---

    /**
     * @brief Logs a specific performance metric.
     * Example: logAppPerformance("startup_time_ms", 1250);
     * @param metricName The name of the metric being measured (e.g., "startup_time_ms", "memory_usage_mb").
     * @param value The value of the metric.
     */
    void logAppPerformance(const std::string& metricName, float value);

    /**
     * @brief Logs when a user navigates to a new screen, tracking user flow.
     * Example: logScreenView("PlantDetailsScreen");
     * @param screenName A unique name for the screen the user is now viewing.
     */
    void logScreenView(const std::string& screenName);


private:
    // ... (private members remain the same) ...
    std::string telemetryCsvPath;
    void appendToCsv(const std::string& eventType, const std::string& col1, const std::string& col2, const std::string& col3);
    std::string getCurrentTimestamp();
};

#endif // TELEMETRY_MANAGER_H
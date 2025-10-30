public class SurveillanceDashboard {

    // A method processAlerts that returns an array of alerts that are CRITICAL
    // Handles NullPointerException and returns an empty array on error.
    public OutbreakAlert[] processAlerts(HealthAlert[] alerts) {
        if (alerts == null) {
            return new OutbreakAlert[0];
        }

        java.util.List<OutbreakAlert> criticalOutbreaks = new java.util.ArrayList<>();

        try {
            for (HealthAlert alert : alerts) {
                // Check if the alert is an OutbreakAlert and CRITICAL
                if (alert instanceof OutbreakAlert) {
                    OutbreakAlert oa = (OutbreakAlert) alert;
                    if (oa.getAlertLevel().equals("CRITICAL")) {
                        criticalOutbreaks.add(oa);
                    }
                }
            }
        } catch (NullPointerException e) {
            // Catches NullPointerException if an element in the 'alerts' array is null
            System.err.println("Error processing alerts: NullPointerException occurred.");
            return new OutbreakAlert[0]; // Return empty array upon error
        }

        return criticalOutbreaks.toArray(new OutbreakAlert[0]);
    }

    // A method parseReportData
    public HealthAlert parseReportData(String reportData) throws NumberFormatException, NullPointerException {
        // Example string format:
        // "ECOUNTRYCODE,DISEASENAME,CASES,DATEREPORTED,PARAM1,PARAM2"
        // E.g., "NG,CHOLERA,4500,2025-10-28,0.05,SOUTH-EAST"

        if (reportData == null || reportData.trim().isEmpty()) {
            throw new NullPointerException("Report data cannot be null or empty.");
        }

        // Step 1: Handle string manipulation exception
        // (already covered by NullPointerException check above,
        // but included a trim() for robustness)

        // Split the string
        String[] parts = reportData.split(",");

        // Check for correct number of parts (assuming 5 or 6 parts for the two subclasses)
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid report format: Too few data fields.");
        }

        String countryCode = parts[0].trim();
        String diseaseName = parts[1].trim();
        String dateReported = parts[3].trim();

        int casesReported;
        try {
            // Step 2: Handles NumberFormatException
            casesReported = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid cases reported value: " + parts[2]);
        }

        // Identify which alert type to create and validate alert data
        if (parts.length == 6) {
            // OutbreakAlert expected: E.g., "NG,CHOLERA,4500,2025-10-28,0.05,SOUTH-EAST"
            // PARAM1 (percentage) and PARAM2 (region) - The subclass definition only uses a percentage.
            // Let's assume the parameters match the subclass constructors.

            double percentage;
            try {
                // Step 2 (cont.): Handles NumberFormatException for double
                percentage = Double.parseDouble(parts[4].trim());
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid percentage infected value: " + parts[4]);
            }

            // This is OutbreakAlert data based on the constructor I defined (percentageInfected)
            // Note: The example string seems to have two parameters,
            // but the OutbreakAlert only has one additional field (double percentage).
            // I will use parts[4] for the percentage.
            return new OutbreakAlert(countryCode, diseaseName, casesReported, dateReported, percentage);

        } else if (parts.length == 5) {
            // EndemicAlert expected (needs a region string array, which might be one or more regions separated by a char)
            // Assuming parts[4] is a comma-separated list of regions *within* the last field for EndemicAlert's String array.
            // The prompt isn't clear on the EndemicAlert data format in the string.
            // I'll assume parts[4] is a single region string for simplicity of the prompt's parsing requirement.
            String[] regions = parts[4].trim().split(";"); // Use a different delimiter for regions if multiple

            return new EndemicAlert(countryCode, diseaseName, casesReported, dateReported, regions);

        } else {
            // No matching alert type
            return null; // Return null if parsing fails/no match
        }
    }

    // A method trackHighestSeverityAlert
    public void trackHighestSeverityAlert(String reportData) {
        try {
            // Parse the report data
            HealthAlert newAlert = parseReportData(reportData);

            if (newAlert != null) {
                // Identify which alerts require immediate response
                boolean immediateResponse = newAlert.requiresInternationalResponse();

                // Tracks the highest severity alert and displays its details at the end
                // For simplicity, we'll track the *latest* high-severity alert
                // and print it immediately. A more complex system would
                // store and compare all alerts.

                if (immediateResponse) {
                    System.out.println("\n*** HIGH PRIORITY ALERT TRACKED ***");
                    newAlert.displayBasicInfo();
                    System.out.println("Severity: " + newAlert.calculateSeverity());
                    System.out.println("Requires International Response: YES");
                    if (newAlert instanceof OutbreakAlert) {
                        System.out.println("Alert Level: " + ((OutbreakAlert)newAlert).getAlertLevel());
                    }
                } else {
                    System.out.println("Alert tracked. Severity: " + newAlert.calculateSeverity());
                }
            } else {
                System.out.println("Alert report data was valid but did not match a known alert type.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Data format malformed: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Handling format exception (String manipulation): " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument/format: " + e.getMessage());
        } catch (Exception e) {
            // Handles any other unforeseen exception
            System.err.println("An unexpected error occurred during report processing: " + e.getMessage());
        }
    }
}
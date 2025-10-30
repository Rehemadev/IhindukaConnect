// src/Main.java
public class Main {
    public static void main(String[] args) {

        // 4. Create an array of at least 6 HealthAlert objects (mix of both subclasses)
        // 2 OutbreakAlerts, 2 EndemicAlerts
        // Make sure to include one alert that requires international response.
        String[] regions1 = {"A", "B"};
        String[] regions2 = {"R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10"}; // length 10

        HealthAlert[] healthAlerts = {
                // OutbreakAlert 1 (LOW severity) - Severity: 1 + 20*0.05 = 2.0
                new OutbreakAlert("UG", "Flu", 100, "2025-10-25", 0.05),
                // OutbreakAlert 2 (CRITICAL - severity >= 80) - Severity: 10 + 20*4 = 90
                new OutbreakAlert("DRC", "Ebola", 1000, "2025-10-26", 4.0),
                // EndemicAlert 1 (LOW severity) - Severity: 2 + 100*2 = 202 (International: YES)
                new EndemicAlert("NG", "Malaria", 200, "2025-10-27", regions1),
                // EndemicAlert 2 (HIGH severity) - Severity: 5 + 100*10 = 1005 (International: YES)
                new EndemicAlert("SA", "TB", 500, "2025-10-28", regions2),
                // OutbreakAlert 3 (HIGH severity - severity >= 50) - Severity: 50 + 20*1 = 70
                new OutbreakAlert("ET", "Cholera", 5000, "2025-10-29", 1.0),
                // EndemicAlert 3 (LOW severity) - Severity: 1 + 100*1 = 101 (International: NO)
                new EndemicAlert("TZ", "Typhoid", 100, "2025-10-29", new String[]{"Zanzibar"}),
                // 4. At least one element to test exception handling
                null // Null element for NullPointerException test
        };

        SurveillanceDashboard dashboard = new SurveillanceDashboard();

        // 4. Demonstrate polymorphism (different countries and diseases)
        System.out.println("\n--- Demonstrating Polymorphism and International Response Check ---");
        for (HealthAlert alert : healthAlerts) {
            if (alert != null) {
                alert.displayBasicInfo(); // Concrete method in base class
                System.out.println("Calculated Severity: " + alert.calculateSeverity()); // Abstract method overridden

                // Polymorphism in action: different subclass logic for int'l response
                if (alert.requiresInternationalResponse()) {
                    System.out.println("**Requires International Response: YES**\n");
                } else {
                    System.out.println("Requires International Response: NO\n");
                }
            }
        }

        // 4. Include one alert that requires international response (already included)

        // 4. Include at least one null element to test exception handling (already included)

        // 4. Test the parseReportData method with at least 3 sample strings
        // (1 valid, 1 invalid format, 1 invalid numeric value)

        System.out.println("\n--- Testing parseReportData and trackHighestSeverityAlert (Exception Handling) ---");

        String validReport = "NG,CHOLERA,8500,2025-10-28,0.5"; // Valid: EndemicAlert (5 parts)
        String invalidFormatReport = "ZA|Flu|1000|2025-10-28|0.1"; // Invalid format (wrong delimiter)
        String invalidNumericReport = "KEN,Measles,THREE_HUNDRED,2025-10-28,0.01"; // Invalid numeric value (CASES)

        // 1. Valid Test
        System.out.println("Testing VALID report string:");
        dashboard.trackHighestSeverityAlert(validReport);

        // 2. Invalid Format Test (Exception handling for format)
        System.out.println("\nTesting INVALID FORMAT report string:");
        dashboard.trackHighestSeverityAlert(invalidFormatReport);

        // 3. Invalid Numeric Test (Exception handling for NumberFormatException)
        System.out.println("\nTesting INVALID NUMERIC report string:");
        dashboard.trackHighestSeverityAlert(invalidNumericReport);

        // 4. Null test (Exception handling for NullPointerException)
        System.out.println("\nTesting NULL report string:");
        dashboard.trackHighestSeverityAlert(null);


        // 4. Call processAlerts to generate the surveillance dashboard
        System.out.println("\n--- Generating Critical Alert Dashboard ---");
        OutbreakAlert[] criticalAlerts = dashboard.processAlerts(healthAlerts);

        if (criticalAlerts.length > 0) {
            System.out.println("Found " + criticalAlerts.length + " CRITICAL Outbreak Alerts:");
            for (OutbreakAlert ca : criticalAlerts) {
                ca.displayBasicInfo();
                System.out.println("Alert Level: " + ca.getAlertLevel());
            }
        } else {
            System.out.println("No CRITICAL Outbreak Alerts found.");
        }

        // 4. Demonstrate polymorphism again (casting to subclass type is not the main point of polymorphism,
        // but calling getAlertLevel/getRegionsAffected shows subclass specific behavior)
        System.out.println("\n--- Subclass Specific Information ---");
        for (HealthAlert alert : healthAlerts) {
            if (alert instanceof OutbreakAlert) {
                OutbreakAlert oa = (OutbreakAlert) alert;
                System.out.println(oa.diseaseName + " has Alert Level: " + oa.getAlertLevel());
            } else if (alert instanceof EndemicAlert) {
                EndemicAlert ea = (EndemicAlert) alert;
                System.out.println(ea.diseaseName + " affects " + ea.getRegionsAffected().length + " regions.");
            }
        }
    }
}

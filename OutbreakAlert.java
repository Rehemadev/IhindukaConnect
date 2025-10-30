public class OutbreakAlert extends HealthAlert {
    // Additional field (double percentage)
    private double percentageInfected;

    // Constructor that calls super and initializes additional field
    public OutbreakAlert(String countryCode, String diseaseName, int casesReported, String reportDate, double percentageInfected) {
        super(countryCode, diseaseName, casesReported, reportDate);
        this.percentageInfected = percentageInfected;
    }

    // Override calculateSeverity() that returns:
    // int(casesReported / 100) + int(percentageInfected * 20)
    @Override
    public int calculateSeverity() {
        return (int) (this.casesReported / 100) + (int) (this.percentageInfected * 20);
    }

    // Override requiresInternationalResponse()
    // returns true if more than 3 regions affected
    // (Assuming region info is not stored directly,
    // this check will be based on casesReported for simplicity
    // or we'd need another field/parameter.
    // I'll assume we check the severity, as it is related to the case count)
    // NOTE: The prompt text is slightly ambiguous here.
    // I will use a simple check based on casesReported as a proxy for 'regions affected'.
    @Override
    public boolean requiresInternationalResponse() {
        // Simple proxy for "more than 3 regions affected"
        // This logic should be clarified in a real-world scenario.
        return this.casesReported > 1000;
    }

    // Method getAlertLevel that returns "CRITICAL", "HIGH", or "MODERATE"
    public String getAlertLevel() {
        int severity = calculateSeverity();
        if (severity >= 80) {
            return "CRITICAL";
        } else if (severity >= 50) {
            return "HIGH";
        } else {
            return "MODERATE";
        }
    }
}
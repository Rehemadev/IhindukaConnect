public class EndemicAlert extends HealthAlert {
    // Additional fields (String array)
    private String[] affectedRegions;

    // Constructor that calls super and initializes affectedRegions
    public EndemicAlert(String countryCode, String diseaseName, int casesReported, String reportDate, String[] affectedRegions) {
        super(countryCode, diseaseName, casesReported, reportDate);
        this.affectedRegions = affectedRegions;
    }

    // Override calculateSeverity() that returns
    // (casesReported / 100) + (100 * affectedRegions.length)
    @Override
    public int calculateSeverity() {
        return (this.casesReported / 100) + (100 * affectedRegions.length);
    }

    // Override requiresInternationalResponse()
    // returns true if affectedRegions.length >= 10
    @Override
    public boolean requiresInternationalResponse() {
        return affectedRegions.length >= 10;
    }

    // Method getRegionsAffected() that returns affectedRegions
    public String[] getRegionsAffected() {
        return affectedRegions;
    }
}
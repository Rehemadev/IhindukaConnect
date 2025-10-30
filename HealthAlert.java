public abstract class HealthAlert {
    // Protected fields
    protected String countryCode;
    protected String diseaseName;
    protected int casesReported;
    protected String reportDate; // Added based on subclasses requirements, though not explicitly in fields list for HealthAlert

    // Constructor to initialize fields
    public HealthAlert(String countryCode, String diseaseName, int casesReported, String reportDate) {
        this.countryCode = countryCode;
        this.diseaseName = diseaseName;
        this.casesReported = casesReported;
        this.reportDate = reportDate;
    }

    // Abstract method to return an integer severity score
    public abstract int calculateSeverity();

    // Concrete method to print basic info
    public void displayBasicInfo() {
        System.out.println("--- Health Alert Info ---");
        System.out.println("Country: " + countryCode);
        System.out.println("Disease: " + diseaseName);
        System.out.println("Cases Reported: " + casesReported);
        System.out.println("Report Date: " + reportDate);
    }

    // Concrete method that returns true if severity score is above 70
    public boolean requiresInternationalResponse() {
        return calculateSeverity() > 70;
    }

    // Getter for casesReported (needed for EndemicAlert's calculation)
    public int getCasesReported() {
        return casesReported;
    }

    // Getter for severity (needed for EndemicAlert's override)
    public int getSeverity() {
        return calculateSeverity();
    }
}
public class SoilAnalysis {
    private String farmerId;
    private String districtName;
    private double nitrogenLevel;
    private double phosphorusLevel;
    private double potassiumLevel;
    private String cropType;

    // Constructor
    public SoilAnalysis(String farmerId, String districtName, double nitrogenLevel,
                        double phosphorusLevel, double potassiumLevel, String cropType) {
        this.farmerId = farmerId;
        this.districtName = districtName;
        this.nitrogenLevel = nitrogenLevel;
        this.phosphorusLevel = phosphorusLevel;
        this.potassiumLevel = potassiumLevel;
        this.cropType = cropType;
    }

    // Getters
    public String getFarmerId() { return farmerId; }
    public String getDistrictName() { return districtName; }
    public double getNitrogenLevel() { return nitrogenLevel; }
    public double getPhosphorusLevel() { return phosphorusLevel; }
    public double getPotassiumLevel() { return potassiumLevel; }
    public String getCropType() { return cropType; }

    // Check if nutrient levels are balanced
    public boolean isBalanced() {
        return nitrogenLevel >= 20 && nitrogenLevel <= 100 &&
                phosphorusLevel >= 20 && phosphorusLevel <= 100 &&
                potassiumLevel >= 20 && potassiumLevel <= 100;
    }

    // Calculate fertilizer recommendation
    public String calculateFertilizerNeeded() {
        if (nitrogenLevel <= 0 || phosphorusLevel <= 0 || potassiumLevel <= 0) {
            throw new IllegalArgumentException("Invalid nutrient reading");
        }

        StringBuilder message = new StringBuilder();

        // Check for excess nutrients
        if (nitrogenLevel > 100 || phosphorusLevel > 100 || potassiumLevel > 100) {
            message.append("EXCESS - Reduce ");
            if (nitrogenLevel > 100) message.append("Nitrogen ");
            if (phosphorusLevel > 100) message.append("Phosphorus ");
            if (potassiumLevel > 100) message.append("Potassium ");
            message.append("application");
            return message.toString();
        }

        // Check for deficiency
        if (nitrogenLevel < 20 || phosphorusLevel < 20 || potassiumLevel < 20) {
            message.append("DEFICIENT - High application needed for ");
            if (nitrogenLevel < 20) message.append("Nitrogen ");
            if (phosphorusLevel < 20) message.append("Phosphorus ");
            if (potassiumLevel < 20) message.append("Potassium ");
            return message.toString();
        }

        // If balanced
        return "OPTIMAL - Maintenance fertilizer only";
    }
}


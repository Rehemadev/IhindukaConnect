public class FertilizerAdvisorySystem {

    public void processSamples(SoilAnalysis[] samples) {
        int balancedCount = 0;
        int deficientCount = 0;

        for (SoilAnalysis sample : samples) {
            try {
                String recommendation = sample.calculateFertilizerNeeded();
                System.out.println("--------------------------------------");
                System.out.println("Farmer ID: " + sample.getFarmerId());
                System.out.println("District: " + sample.getDistrictName());
                System.out.println("Crop Type: " + sample.getCropType());
                System.out.println("Fertilizer Recommendation: " + recommendation);

                if (sample.isBalanced()) balancedCount++;
                else if (recommendation.contains("DEFICIENT")) deficientCount++;

            } catch (IllegalArgumentException e) {
                System.out.println("Error for Farmer ID " + sample.getFarmerId() + ": " + e.getMessage());
            }
        }

        System.out.println("\n========= SUMMARY =========");
        System.out.println("Total Balanced Samples: " + balancedCount);
        System.out.println("Total Deficient Samples: " + deficientCount);
    }

    public static void main(String[] args) {
        SoilAnalysis[] samples = {
                new SoilAnalysis("F001", "Kirehe", 50, 60, 80, "Maize"),   // Balanced
                new SoilAnalysis("F002", "Bugesera", 10, 55, 70, "Rice"),   // Deficient
                new SoilAnalysis("F003", "Nyagatare", 110, 90, 95, "Beans"), // Excess
                new SoilAnalysis("F004", "Gatsibo", -5, 40, 50, "Cassava"),  // Invalid
                new SoilAnalysis("F005", "Rwamagana", 25, 15, 18, "Wheat")   // Deficient
        };

        FertilizerAdvisorySystem system = new FertilizerAdvisorySystem();
        system.processSamples(samples);
    }
}


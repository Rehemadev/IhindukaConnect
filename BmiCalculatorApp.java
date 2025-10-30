import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BmiCalculatorApp extends JFrame implements ActionListener {
    // Declare components
    private JTextField weightField, heightField;
    private JLabel bmiResultLabel, categoryLabel;
    private JButton calculateButton;

    public BmiCalculatorApp() {
        // 1. Setup the Frame
        setTitle("BMI Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Simple vertical layout

        // 2. Initialize Components
        weightField = new JTextField(10);
        heightField = new JTextField(10);
        calculateButton = new JButton("Calculate BMI");
        bmiResultLabel = new JLabel("BMI: ");
        categoryLabel = new JLabel("Category: ");

        // 3. Add Components to Frame
        add(new JLabel("Weight (kg):"));
        add(weightField);
        add(new JLabel("Height (meters):"));
        add(heightField);
        add(calculateButton);
        add(bmiResultLabel);
        add(categoryLabel);

        // 4. Attach Listener
        calculateButton.addActionListener(this);

        // Finalize
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            calculateBMI();
        }
    }

    private void calculateBMI() {
        try {
            // Get inputs and convert to double
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());

            // Check for non-positive values
            if (weight <= 0 || height <= 0) {
                JOptionPane.showMessageDialog(this, "Weight and Height must be positive values.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calculate BMI
            double bmi = weight / (height * height);

            // Format BMI to one decimal place for display
            String formattedBMI = String.format("%.1f", bmi);

            // Determine Category and Feedback
            String category;
            String feedback;

            if (bmi < 18.5) {
                category = "Underweight";
                feedback = "You are in the Underweight range. You may need to gain weight.";
            } else if (bmi <= 24.9) {
                category = "Normal weight";
                feedback = "You are in the Normal weight range. Great job!";
            } else if (bmi <= 29.9) {
                category = "Overweight";
                feedback = "You are Overweight. Try to maintain a healthy diet and exercise.";
            } else { // BMI >= 30.0
                category = "Obesity";
                feedback = "You are Obese, which may increase your risk of health problems. Consider consulting a healthcare provider.";
            }

            // Update GUI Labels
            bmiResultLabel.setText("BMI: " + formattedBMI);
            categoryLabel.setText("Category: " + category + " | Feedback: " + feedback);

        } catch (NumberFormatException ex) {
            // Handle invalid input (non-numeric)
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Weight and Height.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        // Run the application on the Event Dispatch Thread (EDT) for safety
        SwingUtilities.invokeLater(() -> new BmiCalculatorApp());
    }
}
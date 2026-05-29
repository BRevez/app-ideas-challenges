import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseConverter {

    public static void main(String[] args) throws Exception {
        
        // WINDOW
        JFrame frame = new JFrame("Base Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout(10, 10));

        // WRAPPER
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // DROPDOWN / LABEL
        JLabel labelCombo = new JLabel("Select a Base:");
        labelCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        String[] bases = {"Binary", "Octal", "Decimal", "Hexadecimal"};
        JComboBox<String> comboBase = new JComboBox<>(bases);
        comboBase.setAlignmentX(Component.LEFT_ALIGNMENT);

        // INPUT / LABEL
        JLabel labelValue = new JLabel("Enter Value (Max 8 digits):");
        labelValue.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField fieldValue = new JTextField();
        fieldValue.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        fieldValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        // BUTTON
        JButton btnConvert = new JButton("Convert");
        btnConvert.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnConvert.setBackground(new Color(70, 130, 180));
        btnConvert.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        btnConvert.setForeground(Color.WHITE);

        // ADD TO THE WRAPPER
        topPanel.add(labelCombo);
        topPanel.add(comboBase);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(labelValue);
        topPanel.add(fieldValue);
        topPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        topPanel.add(btnConvert);

        // TEXT AREA
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13)); 
        outputArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Results & Calculations"));

        // ADD TO THE FRAME
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // ACTION
        btnConvert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBaseName = (String) comboBase.getSelectedItem();
                String base = "";
                switch (selectedBaseName) {
                    case "Binary": base = "B"; break;
                    case "Octal": base = "O"; break;
                    case "Decimal": base = "D"; break;
                    case "Hexadecimal": base = "H"; break;
                }

                String input = fieldValue.getText().trim();
                outputArea.setText("");

                System.out.println("\n--- NEW CONVERSION REQUEST ---");
                System.out.println("Initial Base: " + selectedBaseName);
                System.out.println("Input Value: " + input);

                if (!ConverterUtil.isValidForBase(input, base)) {
                    String rangeMsg = getRangeMessage(base);
                    String errorText = "INVALID INPUT!\n" +
                                     "The value '" + input + "' is not valid for " + selectedBaseName + ".\n" +
                                     "Allowed Range: " + rangeMsg + "\n" +
                                     "Max length: 8 digits.";
                    
                    outputArea.setForeground(Color.RED);
                    outputArea.setText(errorText);
                    
                    System.err.println("\nERROR: " + errorText.replace("\n", " "));
                    return;
                }

                outputArea.setForeground(Color.BLACK);
                String resultText = processConversionsToString(input, base);
                
                outputArea.setText(resultText);
                outputArea.setCaretPosition(0);

                System.out.println(resultText);
            }
        });

        frame.setVisible(true);
    }

    private static String getRangeMessage(String base) {
        switch (base) {
            case "B": return "[0-1]";
            case "O": return "[0-7]";
            case "D": return "[0-9]";
            case "H": return "[0-9, A-F]";
            default: return "";
        }
    }

    public static String processConversionsToString(String input, String base) {
        StringBuilder sb = new StringBuilder();
        int radixIn = ConverterUtil.getRadix(base);
        long decimalValue = ConverterUtil.parseToDecimal(input, radixIn); 
        
        String[] allBases = {"D", "B", "O", "H"};

        sb.append("=========================================\n");
        sb.append("     CONVERSION RESULTS & CALCULATION    \n");
        sb.append("=========================================\n");

        for (String targetBase : allBases) {
            if (targetBase.equalsIgnoreCase(base)) continue; 

            int radixOut = ConverterUtil.getRadix(targetBase);
            String result = ConverterUtil.decimalToBase(decimalValue, radixOut);

            sb.append("\nBASE ").append(targetBase.toUpperCase()).append(" (").append(radixOut).append(")\n");

            if (radixOut == 10) {
                sb.append("Formula: ");
                int maxPower = input.length() - 1;
                for (int i = 0; i < input.length(); i++) {
                    char c = input.toUpperCase().charAt(i);
                    int digitValue = (c >= '0' && c <= '9') ? (c - '0') : (c - 'A' + 10);
                    sb.append(digitValue).append(" * ").append(radixIn).append("^").append(maxPower - i);
                    if (i < input.length() - 1) sb.append(" + ");
                }
                sb.append("\n");
            } else {
                sb.append("Division of ").append(decimalValue).append(" by ").append(radixOut).append(":\n");
                long tempDec = decimalValue;
                if (tempDec == 0) {
                    sb.append(tempDec).append(" / ").append(radixOut).append(" = 0\n");
                } else {
                    while (tempDec > 0) {
                        long quotient = tempDec / radixOut;
                        int remainder = (int) (tempDec % radixOut);
                        char digitChar = (remainder < 10) ? (char) (remainder + '0') : (char) (remainder - 10 + 'A');
                        sb.append(tempDec).append(" / ").append(radixOut).append(" = ").append(quotient)
                          .append("  --->  ").append(remainder).append(" (Digit: '").append(digitChar).append("')\n");
                        tempDec = quotient;
                    }
                }
            }
            sb.append("FINAL RESULT: ").append(input).append(" (Base ").append(radixIn).append(") = ")
              .append(result).append(" (Base ").append(radixOut).append(")\n");
            sb.append("-----------------------------------------\n");
        }
        return sb.toString();
    }

}
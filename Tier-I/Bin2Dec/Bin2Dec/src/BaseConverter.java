import java.util.Scanner;

public class BaseConverter {
    
    public static void main(String[] args) {
        String input, base, option;
        Scanner sc = new Scanner(System.in);
        Boolean isRunning = true;

        while (isRunning) {
            do {
                base = "";
                displayBases();
                System.out.print("\nSelect the Initial base: ");
                base = sc.nextLine();
            } while (!isValidBase(base));

            do {
                input = "";
                System.out.print("\nEnter the value (Max 8 digits): ");
                input = sc.nextLine();
            } while (!isValidForBase(input, base));

            processAllConversions(input, base);

            do {
                option = "";
                System.out.print("\nTry Again? (Y/N): ");
                option = sc.nextLine();
            } while (!isValidOption(option));
            
            if (option.equalsIgnoreCase("N")) {
                isRunning = false;
                sc.close();
            }
        }
    }

    public static void processAllConversions(String input, String base) {
        int radixIn = getRadix(base);
        long decimalValue = parseToDecimal(input, radixIn); 
        
        String[] allBases = {"D", "B", "O", "H"};

        System.out.println("\n=========================================");
        System.out.println("   CONVERSION RESULTS & CALCULATION");
        System.out.println("=========================================");

        for (String targetBase : allBases) {
            
            if (targetBase.equalsIgnoreCase(base)) {
                continue; 
            }

            int radixOut = getRadix(targetBase);
            String result = decimalToBase(decimalValue, radixOut);

            System.out.println("\nBASE " + targetBase.toUpperCase() + " (" + radixOut + ")");

            if (radixOut == 10) {
                System.out.print("Formula: ");
                int maxPower = input.length() - 1;
                for (int i = 0; i < input.length(); i++) {
                    char c = input.toUpperCase().charAt(i);
                    int digitValue = (c >= '0' && c <= '9') ? (c - '0') : (c - 'A' + 10);
                    
                    System.out.print(digitValue + " * " + radixIn + "^" + (maxPower - i));
                    if (i < input.length() - 1) System.out.print(" + ");
                }
                System.out.println();
            } 
            else {
                System.out.println("Division of " + decimalValue + " by " + radixOut + ":");
                long tempDec = decimalValue;
                
                if (tempDec == 0) {
                    System.out.println(tempDec + " / " + radixOut + " = 0");
                } else {
                    while (tempDec > 0) {
                        long quotient = tempDec / radixOut;
                        int remainder = (int) (tempDec % radixOut);
                        char digitChar = (remainder < 10) ? (char) (remainder + '0') : (char) (remainder - 10 + 'A');

                        System.out.println(tempDec + " / " + radixOut + " = " + quotient + 
                                           "  --->  " + remainder + " (Digit: '" + digitChar + "')");
                        tempDec = quotient;
                    }
                }
            }
            
            System.out.println("FINAL RESULT: " + input + " (Base " + radixIn + ") = " + result + " (Base " + radixOut + ")");
            System.out.println("-----------------------------------------");
        }
    }

    public static long parseToDecimal(String input, int base) {
        long result = 0;
        int power = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.toUpperCase().charAt(i);
            int digitValue;

            digitValue = (c >= '0' && c <= '9') ? (c - '0') : (c - 'A' + 10);
            
            result += (long) (digitValue * Math.pow(base, power));
            power++; 
        }

        return result;
    }

    public static String decimalToBase(long decimalValue, int base) {
        if (decimalValue == 0) return "0";

        StringBuilder result = new StringBuilder();

        while (decimalValue > 0) {
            int remainder = (int) (decimalValue % base); 
            char digitChar;

            digitChar = (remainder < 10) ? (char) (remainder + '0') : (char) (remainder - 10 + 'A');

            result.append(digitChar);
            decimalValue = decimalValue / base;
        }

        return result.reverse().toString();
    }

    private static int getRadix(String base) {
        switch (base.toUpperCase()) {
            case "B": return 2;
            case "O": return 8;
            case "D": return 10;
            case "H": return 16;
            default: return 10;
        }
    }

    public static void displayBases() {
        System.out.println("\n***********************************");
        System.out.println("****                          ****");
        System.out.println("****        B - Binary        ****");
        System.out.println("****        O - Octal         ****");
        System.out.println("****        D - Decimal       ****");
        System.out.println("****        H - Hexadecimal   ****");
        System.out.println("****                          ****");
        System.out.println("***********************************");
    }

    public static boolean isValidBase(String base) {
        if (!base.toUpperCase().matches("[BODH]")) {
            System.out.println("\nInvalid Base!\nBase: B, O, D, H.\n");
            return false;
        }
        return true;
    }

    public static boolean isValidOption(String option) {
        if (!option.toUpperCase().matches("[NY]")) {
            System.out.println("\nInvalid Option!\nOptions: Y (Yes), N (No).\n");
            return false;
        }
        return true;
    }

    public static boolean isValidForBase(String input, String base) {
        String regex;
        String rangeMsg;

        switch (base.toUpperCase()) {
            case "B":
                regex = "[01]{1,8}";
                rangeMsg = "[0-1]";
                break;
            case "O":
                regex = "[0-7]{1,8}";
                rangeMsg = "[0-7]";
                break;
            case "D":
                regex = "[0-9]{1,8}";
                rangeMsg = "[0-9]";
                break;
            case "H":
                regex = "(?i)[0-9A-F]{1,8}";
                rangeMsg = "[0-9, A-F]";
                break;
            default:
                System.out.println("\nInvalid Base!\nBases: B, O, D, H.\n");
                return false;
        }

        if (!input.matches(regex)) {
            System.out.println("\nInvalid Input!\nRange: " + rangeMsg + ".\nMax length: 8 digits.\n");
            return false;
        }
        
        return true;
    }

}
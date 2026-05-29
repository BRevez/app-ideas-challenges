public class ConverterUtil {

    public static long parseToDecimal(String input, int base) {
        long result = 0;
        int power = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            char c = input.toUpperCase().charAt(i);
            int digitValue = (c >= '0' && c <= '9') ? (c - '0') : (c - 'A' + 10);
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
            char digitChar = (remainder < 10) ? (char) (remainder + '0') : (char) (remainder - 10 + 'A');
            result.append(digitChar);
            decimalValue = decimalValue / base;
        }
        return result.reverse().toString();
    }

    public static int getRadix(String base) {
        switch (base.toUpperCase()) {
            case "B": return 2;
            case "O": return 8;
            case "D": return 10;
            case "H": return 16;
            default: return 10;
        }
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

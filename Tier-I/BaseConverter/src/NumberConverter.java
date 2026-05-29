public class NumberConverter {

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

}

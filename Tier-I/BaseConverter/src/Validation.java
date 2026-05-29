public class Validation {

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

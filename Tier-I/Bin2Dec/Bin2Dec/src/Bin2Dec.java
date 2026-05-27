import java.util.Scanner;

public class Bin2Dec {
    public static void main(String[] args) throws Exception {

        String inputNum = "";
        int result = 0;
        Scanner sc = new Scanner(System.in);
        
        while(inputNum == "") {    
            System.out.print("Enter a Binary Number: ");
            inputNum = sc.nextLine();
            if(!inputNum.matches("[01]+")) {
                System.out.println("\n" + "Invalid Binary Number!" + "\n" + "Binary range is 0 and 1." + "\n" );
                inputNum = "";
            }
        }

        sc.close();

        for(int i = 0; i < inputNum.length(); i++){
            int num = inputNum.charAt(i) - '0'; 
            int power = inputNum.length() - 1 - i; 
            result += (num * Math.pow(2, power));            
        }
        
        System.out.println("The Decimal number is " + result);

    }
}

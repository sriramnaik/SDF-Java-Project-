package arbitraryarithmetic;

public class commonMethod {
    public static int compare(String num1, String num2){
        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);
        if (num1.length() != num2.length()) return num1.length() - num2.length();
        return num1.compareTo(num2);
    }
    public static String removeLeadingZeros(String number){
        int i = 0;
        while(i< number.length()-1 && number.charAt(i) == '0') i++;
        return number.substring(i);
    }
}

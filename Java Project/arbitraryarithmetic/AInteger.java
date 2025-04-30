package arbitraryarithmetic;

public class AInteger {
    protected String number;
    public AInteger(){
    this.number = "0";
    }
    public AInteger(String number){
        this.number = number;
    }

    public AInteger(AInteger other){
        this.number = other.number;
    }

    public AInteger parse(String number){
        return new AInteger(number);
    }

    public String getString(){
        return this.number;
    }
    @Override
    public String toString(){
        return this.number;
    }

    public AInteger add(AInteger other){
        String num1 = this.number;
        String num2 = other.number;
        boolean isNegative1 = num1.charAt(0) == '-';
        boolean isNegative2 = num2.charAt(0) == '-';
        if (isNegative1) num1 = num1.substring(1);
        if (isNegative2) num2 = num2.substring(1);

        if (isNegative1 && !isNegative2) {
            return other.sub(new AInteger(num1)); 
        } else if (!isNegative1 && isNegative2) {
            return this.sub(new AInteger(num2));  
        }
        num1 = commonMethod.removeLeadingZeros(num1);
        num2 = commonMethod.removeLeadingZeros(num2);
        
        int len1 = num1.length();
        int len2 = num2.length();
        StringBuilder result = new StringBuilder();
        int i = len1-1;
        int j = len2-1;
        int carry = 0;
        while(i>= 0 || j >= 0 || carry > 0){
            int digit1 = (i>= 0) ? num1.charAt(i)-'0' : 0;
            int digit2 = (j>= 0) ? num2.charAt(j)-'0' : 0;
            int sum = digit1 + digit2 + carry;
            result.append(sum%10);
            carry = sum / 10;
            i--;
            j--;
        }
        // System.out.println(result);
        if (isNegative1 && isNegative2) result.append('-');
        result = new StringBuilder(commonMethod.removeLeadingZeros(result.reverse().toString()));

        return new AInteger(new String(result.toString()));
    }
    public AInteger sub(AInteger other) {
        String num1 = this.number;
        String num2 = other.number;
    
        boolean isNegative1 = num1.charAt(0) == '-';
        boolean isNegative2 = num2.charAt(0) == '-';

        if (isNegative1) num1 = num1.substring(1);
        if (isNegative2) num2 = num2.substring(1);
    
        if (!isNegative1 && isNegative2) {
            return this.add(new AInteger(num2));
        }

        else if (isNegative1 && !isNegative2) {
            AInteger sum = new AInteger(num1).add(new AInteger(num2));
            return new AInteger("-" + sum.number);
        }
        
        else if (isNegative1 && isNegative2) {
            return new AInteger(num2).sub(new AInteger(num1));
        }
    
        boolean negative = false;
    
        if (num1.length() < num2.length() || (num1.length() == num2.length() && num1.compareTo(num2) < 0)) {
            String temp = num1;
            num1 = num2;
            num2 = temp;
            negative = true;
        }
    
        int len1 = num1.length();
        int len2 = num2.length();
        int[] output = new int[len1];
        int carry = 0;
        int i = len1 - 1;
        int j = len2 - 1;
        int k = 0;
    
        while (i >= 0 || j >= 0) {
            int digit1 = (i >= 0) ? num1.charAt(i) - '0' : 0;
            digit1 += carry;
            int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0;
    
            if (digit1 < digit2) {
                digit1 += 10;
                carry = -1;
            } else {
                carry = 0;
            }
    
            output[k++] = digit1 - digit2;
            i--;
            j--;
        }
    
        while (k > 1 && output[k - 1] == 0) {
            k--;
        }
    
        char[] string = new char[k + (negative ? 1 : 0)];
        int n = 0;
    
        if (negative) {
            string[0] = '-';
            n = 1;
        }
    
        for (int m = k - 1; m >= 0; m--, n++) {
            string[n] = (char)(output[m] + '0');
        }
    
        return new AInteger(new String(string));
    }
}
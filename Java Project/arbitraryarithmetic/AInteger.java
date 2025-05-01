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
        
        String finalAnswer = (commonMethod.removeLeadingZeros(result.reverse().toString()));
        if (finalAnswer.equals("0")) return new AInteger("0");
        if (isNegative1 && isNegative2) finalAnswer = "-" + finalAnswer;

        return new AInteger(finalAnswer);
    }

    public AInteger sub(AInteger other) {
        String num1 = this.number;
        String num2 = other.number;
        
        num1 = commonMethod.removeLeadingZeros(num1);
        num2 = commonMethod.removeLeadingZeros(num2);

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
        
        String resultStr = new String(string);
        if (resultStr.equals("-0") || resultStr.equals("0")) {
        return new AInteger("0");
        }

        return new AInteger(resultStr);
    }

    public AInteger mul(AInteger other){
        String num1 = this.number;
        String num2 = other.number;
        boolean negative = false;
        if (num1.equals("0") || num2.equals("0")) return new AInteger();

        
        if (num1.charAt(0) == '-') {
            negative = !negative;
            num1 = num1.substring(1);
        }
        if (num2.charAt(0) == '-') {
            negative = !negative;
            num2 = num2.substring(1);
        }
        
        num1 = commonMethod.removeLeadingZeros(num1);
        num2 = commonMethod.removeLeadingZeros(num2);

        String output = "0";
        int len1 = num1.length();
        int len2 = num2.length();
        int i = len1-1;

        while(i>= 0){
            int[] sub = new int[len2+1];
            int carry = 0;
            int k=0;
            for(int j=len2-1;j >= 0;j--){
                int sum = (num1.charAt(i)-'0')*(num2.charAt(j)-'0') + carry;
                sub[k++]= sum%10;
                carry = sum/10;
            }
            if(carry != 0){
                sub[k++] = carry;
            }
            
            char[] string = new char[k];
            for(int m = k-1,n=0; m>=0;m--,n++){
                string[n] = (char)(sub[m]+'0');
            }
            String Str = new String(string);
            for(int m =(len1-1-i);m>0;m--){
                Str+="0";
            }
            AInteger a = new AInteger(output);
            output = a.add(new AInteger(Str)).number;
            i--;
        }
        String finalStr =  negative ? "-" + output: output;
        return new AInteger(finalStr);
    }

    public AInteger div(AInteger other) {
        String dividend = this.number;
        String divisor = other.number;
    
        if (divisor.equals("0") || divisor.equals("-0")) {
            return new AInteger("");
        }

        boolean negative = false;
        if (dividend.charAt(0) == '-') {
            negative = !negative;
            dividend = dividend.substring(1);
        }
        if (divisor.charAt(0) == '-') {
            negative = !negative;
            divisor = divisor.substring(1);
        }
    
        dividend = commonMethod.removeLeadingZeros(dividend);
        divisor = commonMethod.removeLeadingZeros(divisor);
    
        StringBuilder result = new StringBuilder();
        String current = "";
    
        for (int i = 0; i < dividend.length(); i++) {
            current += dividend.charAt(i);
            int j = 0;
            while (j < current.length() - 1 && current.charAt(j) == '0') j++;
            current = current.substring(j);
    
            int count = 0;
            while (commonMethod.compare(current, divisor) >= 0) {
                AInteger a = new AInteger(current);
                current = a.sub(new AInteger(divisor)).number;
                count++;
            }
    
            result.append(count);
        }

        String quotient = result.toString();
        if(quotient.isEmpty()) quotient = "0";
        else {
            int n = 0;
            while (n < quotient.length() - 1 && quotient.charAt(n) == '0') n++;
            quotient = quotient.substring(n);
        }
    
        if (quotient.equals("0")) negative = false; 
    
        return new AInteger(negative ? "-" + quotient : quotient);
    }

    public AInteger mod(AInteger other){
        String dividend = this.number;
        String divisor = other.number;
    
        if (divisor.equals("0") || divisor.equals("-0")) {
            return new AInteger("");
        }
    
        boolean negative_num1 = false;
        boolean negative_num2 = false;
        if (dividend.charAt(0) == '-') {
            negative_num1 = !negative_num1;
            dividend = dividend.substring(1);
        }
        if (divisor.charAt(0) == '-') {
            negative_num2 = !negative_num2;
            divisor = divisor.substring(1);
        }

        dividend = commonMethod.removeLeadingZeros(dividend);
        divisor = commonMethod.removeLeadingZeros(divisor);
        String current = "";
    
        for (int i = 0; i < dividend.length(); i++) {
            current += dividend.charAt(i);
            int j = 0;
            while (j < current.length() - 1 && current.charAt(j) == '0') j++;
            current = current.substring(j);

            while (commonMethod.compare(current, divisor) >= 0) {
                AInteger a = new AInteger(current);
                current = a.sub(new AInteger(divisor)).number;
            }
        }
        if(negative_num1 && !current.equals("0")) current =  "-" + current;
        return new AInteger(current);
    }

}
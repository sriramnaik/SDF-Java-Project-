package arbitraryarithmetic;

public class AFloat {
    protected String number;
    public AFloat(){
        this.number = "0.0";
    }

    public AFloat(String number){
        this.number = number;
    }

    public AFloat(AFloat other){
        this.number = other.number;
    }

    public AFloat parse(String number){
        return new AFloat(number);
    }

    public String getString(){
        return this.number;
    }
    
    @Override
    public String toString(){
        return this.number;
    }

    public AFloat add(AFloat other) {
        String num1 = this.number;
        String num2 = other.number;
        boolean neg2 = num2.startsWith("-");
        boolean neg1 = num1.startsWith("-");

        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";

        if (neg1) num1 = num1.substring(1);
        if (neg2) num2 = num2.substring(1);

        if (num1.endsWith(".")) num1 += "0";
        if (num2.endsWith(".")) num2 += "0";


        if (neg1 && !neg2) {
            return other.sub(new AFloat(num1));  
        } else if (!neg1 && neg2) {
            return this.sub(new AFloat(num2));   
        }

        if (num1.startsWith(".")) num1 = "0" + num1;
        if (num2.startsWith(".")) num2 = "0" + num2;
    
        int int1 = num1.indexOf(".");
        int int2 = num2.indexOf(".");
        int dec1 = num1.length() - int1 - 1;
        int dec2 = num2.length() - int2 - 1;

        while (dec1 < dec2) {
            num1 += "0";
            dec1++;
        }
        while (dec2 < dec1) {
            num2 += "0";
            dec2++;
        }
    
        while (int1 < int2) {
            num1 = "0" + num1;
            int1++;
        }
        while (int2 < int1) {
            num2 = "0" + num2;
            int2++;
        }
    
        num1 = num1.replace(".", "");
        num2 = num2.replace(".", "");
    
        StringBuilder result = new StringBuilder();
        int carry = 0;
    
        for (int i = num1.length() - 1; i >= 0; i--) {
            int d1 = num1.charAt(i) - '0';
            int d2 = num2.charAt(i) - '0';
            int sum = d1 + d2 + carry;
            result.append((char) ((sum % 10) + '0'));
            carry = sum / 10;
        }
    
        if (carry > 0) {
            result.append((char) (carry + '0'));
        }
    
        result.reverse();
    
        int decimalPos = Math.max(dec1, dec2);
        if (decimalPos > 0) {
            result.insert(result.length() - decimalPos, ".");
        }
    
        String resStr = result.toString();
        while (resStr.length() > 1 && resStr.charAt(0) == '0' && resStr.charAt(1) != '.') {
            resStr = resStr.substring(1);
        }
    
        if (resStr.contains(".")) {
            while (resStr.endsWith("0")) resStr = resStr.substring(0, resStr.length() - 1);
            if (resStr.endsWith(".")) resStr = resStr.substring(0, resStr.length() - 1);
        }
    
        return new AFloat(resStr);
    }
    
    public AFloat sub(AFloat other) {
        String num1 = this.number;
        String num2 = other.number;
        boolean neg1 = num1.startsWith("-");
        boolean neg2 = num2.startsWith("-");
        boolean negative = false;

        if (neg1) num1 = num1.substring(1);
        if (neg2) num2 = num2.substring(1);
        if (num1.startsWith(".")) num1 = "0" + num1;
        if (num2.startsWith(".")) num2 = "0" + num2;
        if (num1.endsWith(".")) num1 += "0";
        if (num2.endsWith(".")) num2 += "0";


        if (neg1 && !neg2) {
            return new AFloat("-" + new AFloat(num1).add(new AFloat(num2)).number);
        } else if (!neg1 && neg2) {
            return this.add(new AFloat(num2)); 
        } else if (neg1 && neg2) {
            return new AFloat(num2).sub(new AFloat(num1)); 
        }

        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";
    
        int dec1 = num1.length() - 1 - num1.indexOf('.');
        int dec2 = num2.length() - 1 - num2.indexOf('.');
        while (dec1 < dec2) {
            num1 += "0";
            dec1++;
        }
        while (dec2 < dec1) {
            num2 += "0";
            dec2++;
        }
    
        int int1 = num1.indexOf('.');
        int int2 = num2.indexOf('.');
        while (int1 < int2) {
            num1 = "0" + num1;
            int1++;
        }
        while (int2 < int1) {
            num2 = "0" + num2;
            int2++;
        }
    
        String num1Comp = num1.replace(".", "");
        String num2Comp = num2.replace(".", "");
        if (num1Comp.compareTo(num2Comp) < 0) {
            negative = true;
            String temp = num1;
            num1 = num2;
            num2 = temp;
        }
    
        int len = num1.length();
        int carry = 0;
        StringBuilder result = new StringBuilder();
    
        for (int i = len - 1; i >= 0; i--) {
            char c1 = num1.charAt(i);
            char c2 = num2.charAt(i);
            if (c1 == '.') {
                result.append('.');
                continue;
            }
    
            int digit1 = (c1 - '0') + carry;
            int digit2 = c2 - '0';
    
            if (digit1 < digit2) {
                digit1 += 10;
                carry = -1;
            } else {
                carry = 0;
            }
    
            result.append((char) ((digit1 - digit2) + '0'));
        }
    
        if (negative) result.append('-');
    
        String output = result.reverse().toString();
    
        int start = 0;
        while (start < output.length() - 1 && output.charAt(start) == '0' && output.charAt(start + 1) != '.') {
            start++;
        }
        output = output.substring(start);
    
        if (output.contains(".")) {
            while (output.endsWith("0")) output = output.substring(0, output.length() - 1);
            if (output.endsWith(".")) output = output.substring(0, output.length() - 1);
        }
        
        if (output.isEmpty()) output = "0";
        if (negative && !output.equals("0")) output = "-" + output;
        return new AFloat(output);
    }
    
    public AFloat mul(AFloat other){
        String num1 = this.number;
        String num2 = other.number;
        boolean negative = false;

        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";

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

        if (num1.equals("0") || num2.equals("0")) return new AFloat();
        int dec1 = num1.length()-1-num1.indexOf('.');
        int dec2 = num2.length()-1-num2.indexOf('.');
        int totalDec = dec1+dec2;
        num1 = num1.replace(".", "");
        num2 = num2.replace(".", "");

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

        while (output.length() <= totalDec) output = "0" + output;
        String result = output.substring(0, output.length() - totalDec) + "." + output.substring(output.length() - totalDec);

        while (result.endsWith("0")) result = result.substring(0, result.length() - 1);
        if (result.endsWith(".")) result = result.substring(0, result.length() - 1);

        result = negative ? "-" + result : result;
        if (result.startsWith(".")) result = "0" + result;
        if (result.isEmpty()) result = "0";
        if (result.equals("0")) negative = false;

        return new AFloat(result);   
}
    
    public AFloat div(AFloat other) {
        String dividend = this.number;
        String divisor = other.number;
    
        boolean negative = false;
        if (dividend.startsWith("-")) {
            negative = !negative;
            dividend = dividend.substring(1);
        }
        if (divisor.startsWith("-")) {
            negative = !negative;
            divisor = divisor.substring(1);
        }
    
        int first_decimal = dividend.indexOf('.');
        int second_decimal = divisor.indexOf('.');
    
        int decimal_num1 = (first_decimal == -1) ? 0 : (dividend.length() - first_decimal - 1);
        int decimal_num2 = (second_decimal == -1) ? 0 : (divisor.length() - second_decimal - 1);
    
        if (first_decimal != -1) {
            dividend = dividend.substring(0, first_decimal) + dividend.substring(first_decimal + 1);
        }
        if (second_decimal != -1) {
            divisor = divisor.substring(0, second_decimal) + divisor.substring(second_decimal + 1);
        }
    
        dividend = commonMethod.removeLeadingZeros(dividend);
        divisor = commonMethod.removeLeadingZeros(divisor);
    
        if (divisor.equals("0")) throw new ArithmeticException("Division by zero");
    
        int shift = decimal_num2 - decimal_num1;
    
        StringBuilder result = new StringBuilder();
        String current = "";
    
        for (int i = 0; i < dividend.length(); i++) {
            current += dividend.charAt(i);
            current = commonMethod.removeLeadingZeros(current);
            if (commonMethod.compare(current, divisor) < 0) {
                result.append(result.length() == 0 ? "0" : "0");
                continue;
            }
            int count = 0;
            while (commonMethod.compare(current, divisor) >= 0) {
                current = new AFloat(current).sub(new AFloat(divisor)).number;
                count++;
            }
            result.append(count);
        }
    
        result.append('.');
        int precision = 1000;
        while (precision > 0) {
            current += "0";
            current = commonMethod.removeLeadingZeros(current);
            if (commonMethod.compare(current, divisor) < 0) {
                result.append('0');
            } else {
                int count = 0;
                while (commonMethod.compare(current, divisor) >= 0) {
                    current = new AFloat(current).sub(new AFloat(divisor)).number;
                    count++;
                }
                result.append(count);
            }
            precision--;
        }
    
        int decimal_index = result.indexOf(".");
        result.deleteCharAt(decimal_index);
    
        int new_index = decimal_index + shift;
    
        if (new_index <= 0) {
            while (new_index < 0) {
                result.insert(0, '0');
                new_index++;
            }
            result.insert(0, "0.");
        } else {
            while (result.length() <= new_index) {
                result.append('0');
            }
            result.insert(new_index, '.');
        }
    
        String finalResult = result.toString();
        if (finalResult.contains(".")) {
            finalResult = finalResult.replaceAll("0+$", "");
            if (finalResult.endsWith(".")) {
                finalResult = finalResult.substring(0, finalResult.length() - 1);
            }
        }
        finalResult = commonMethod.removeLeadingZeros(finalResult);
        if (negative && !finalResult.equals("0")) {
            finalResult = "-" + finalResult;
        }
    
        return new AFloat(finalResult);
    }
    
    public AFloat mod(AFloat other) {
        String divisor = other.number;
    
        if (divisor.equals("0") || divisor.equals("-0")) {
            return new AFloat(""); 
        }
    
        AFloat quotient = this.div(other);
        System.out.println(quotient.number);

        String[] parts = quotient.number.split("\\.");
        String intPart = parts[0]; 
        // System.out.println(intPart);
    
        if (quotient.number.charAt(0) == '-' && parts.length > 1 && !parts[1].matches("0*")) {
            intPart = new AInteger(intPart).sub(new AInteger("1")).number; 
        }

        AFloat product = other.mul(new AFloat(intPart));
        System.out.println(product.number+" "+ other.number);

        AFloat remainder = this.sub(product);
        System.out.println(remainder.number);
        return remainder;
    }  
}

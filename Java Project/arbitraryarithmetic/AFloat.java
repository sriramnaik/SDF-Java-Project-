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

        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";
    
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
}

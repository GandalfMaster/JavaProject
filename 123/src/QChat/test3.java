package QChat;

import java.math.BigDecimal;

public class test3 {
    public static void main(String[] args) {
        BigDecimal a = BigDecimal.valueOf(0.03);
        BigDecimal b = BigDecimal.valueOf(0.02);
        BigDecimal c = a.add(b);
        double result = c.doubleValue();
        System.out.println(result);
    }
}

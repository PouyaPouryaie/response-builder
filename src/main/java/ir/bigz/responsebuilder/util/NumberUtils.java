package ir.bigz.responsebuilder.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class NumberUtils {
    static Map baseStrings = new HashMap();

    static {
        baseStrings.put(0, "صفر");
        baseStrings.put(1, "يک");
        baseStrings.put(2, "دو");
        baseStrings.put(3, "سه");
        baseStrings.put(4, "چهار");
        baseStrings.put(5, "پنج");
        baseStrings.put(6, "شش");
        baseStrings.put(7, "هفت");
        baseStrings.put(8, "هشت");
        baseStrings.put(9, "نه");
        baseStrings.put(10, "ده");
        baseStrings.put(11, "يازده");
        baseStrings.put(12, "دوازده");
        baseStrings.put(13, "سيزده");
        baseStrings.put(14, "چهارده");
        baseStrings.put(15, "پانزده");
        baseStrings.put(16, "شانزده");
        baseStrings.put(17, "هفده");
        baseStrings.put(18, "هيجده");
        baseStrings.put(19, "نوزده");
        baseStrings.put(20, "بيست");
        baseStrings.put(30, "سي");
        baseStrings.put(40, "چهل");
        baseStrings.put(50, "پنجاه");
        baseStrings.put(60, "شصت");
        baseStrings.put(70, "هفتاد");
        baseStrings.put(80, "هشتاد");
        baseStrings.put(90, "نود");
        baseStrings.put(100, "یکصد");
        baseStrings.put(200, "دويست");
        baseStrings.put(300, "سيصد");
        baseStrings.put(400, "چهارصد");
        baseStrings.put(500, "پانصد");
        baseStrings.put(600, "ششصد");
        baseStrings.put(700, "هفتصد");
        baseStrings.put(800, "هشتصد");
        baseStrings.put(900, "نهصد");
        baseStrings.put(1000, "هزار");
        baseStrings.put(1000000, "ميليون");
        baseStrings.put(1000000000, "ميليارد");
        baseStrings.put(1000000000000L, " بیلیون ");
        baseStrings.put(1000000000000000L, "ميليون ميليارد");
        baseStrings.put(1000000000000000000L, "ميليارد ميليارد");
    }

    public static long getX(long x) {
        long c = 0;
        while ((x = x / 1000) > 0)
            c++;
        c = (long) Math.pow(1000, c);
        return c;
    }

    public static StringBuffer getAsString(long n) {
        if (n >= 1000) {
            String s = (String) baseStrings.get(n);
            if (s != null) {
                StringBuffer sb = new StringBuffer();
                sb.append("یک ");
                sb.append(s);
                return sb;
            }
        }
        return getAsStringInternal(n);
    }

    private static StringBuffer getAsStringInternal(long n) {
        StringBuffer sb = new StringBuffer();
        String s = (String) baseStrings.get(n);
        if (s != null) {
            sb.append(s);
            return sb;
        }
        if (n < 0) {
            sb.append("-");
            return sb.append(getAsStringInternal(-n));
        } else if (n < 1000) {
            long n3 = (n < 100) ? 10 : 100;
            long n1 = n % n3;
            long n2 = n - n1;
            sb.append(getAsStringInternal(n2));
            if (n1 > 0) {
                sb.append(" و ").append(getAsStringInternal(n1));
            }
        } else {
            long n3 = getX(n);
            long n1 = n % n3;
            long n2 = n / n3;
            sb.append(getAsStringInternal(n2)).append(' ').append(getAsStringInternal(n3));
            if (n1 > 0) {
                sb.append(" و ").append(getAsStringInternal(n1));
            }
        }
        return sb;
    }

    public static void main(String[] args) {
        System.out.println(getAsString(100));
        System.out.println(getAsString(100000));
        System.out.println(getAsString(136890));
        System.out.println(getAsString(1000));
        System.out.println(getAsString(1020));
        System.out.println(getAsString(1121));
    }

    public static Long longValue(Object number) {
        if (CommonUtils.isNull(number))
            return null;
        else if (number instanceof Number)
            return ((Number) number).longValue();
        else
            try {
                return Long.valueOf(number.toString().trim());
            } catch (NumberFormatException e) {
                return null;
            }
    }

    public static Long longValueScalar(Object number) {
        Long value = longValue(number);
        if(CommonUtils.isNotNull(value)){
            return value;
        }else{
            return 0L;
        }
    }


    public static Integer integerValue(Object number) {
        if (CommonUtils.isNull(number))
            return null;
        else if (number instanceof Number)
            return ((Number) number).intValue();
        else
            try {
                return Integer.valueOf(number.toString().trim());
            } catch (NumberFormatException e) {
                return null;
            }
    }

    public static Integer integerValueScalar(Object number) {
        Integer value = integerValue(number);
        if(CommonUtils.isNotNull(value)){
            return value;
        }else{
            return 0;
        }
    }

    public static Double doubleValue(Object number) {
        if (CommonUtils.isNull(number))
            return null;
        else if (number instanceof Number)
            return ((Number) number).doubleValue();
        else
            try {
                return Double.valueOf(number.toString().trim());
            } catch (NumberFormatException e) {
                return null;
            }
    }

    public static Double doubleValueScalar(Object number) {
        Double value = doubleValue(number);
        if(CommonUtils.isNotNull(value)){
            return value;
        }else{
            return 0.0;
        }
    }

    public static Float floatValue(Object number) {
        if (CommonUtils.isNull(number))
            return null;
        else if (number instanceof Number)
            return ((Number) number).floatValue();
        else
            try {
                return Float.valueOf(number.toString().trim());
            } catch (NumberFormatException e) {
                return null;
            }
    }

    public static Float floatValueScalar(Object number) {
        Float value = floatValue(number);
        if(CommonUtils.isNotNull(value)){
            return value;
        }else{
            return 0.0F;
        }
    }

    public static BigDecimal bigDecimalValue(Object number) {
        if (CommonUtils.isNull(number))
            return null;
        else if (number instanceof BigDecimal)
            return ((BigDecimal) number);
        else if (number instanceof Long)
            return BigDecimal.valueOf((Long) number);
        else if (number instanceof Double)
            return BigDecimal.valueOf((Double) number);
        else
            try {
                return new BigDecimal(number.toString().trim());
            } catch (Exception e) {
                return null;
            }
    }
}

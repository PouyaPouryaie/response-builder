package ir.bigz.responsebuilder.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class CommonUtils {

    public static String changeTerm(String term) {

        char[][] replace = new char[][]{
                {'\'', ' '},
                {'"', ' '},
                {' ', '%'},
//          {(char)55977, (char)55683},//kaf
//          {(char)56204, (char)55690},//ye
                {(char) 1705, (char) 1603},//kaf
                {(char) 1740, (char) 1610},//ye
                {'٠', '0'},
                {'١', '1'},
                {'٢', '2'},
                {'٣', '3'},
                {'٤', '4'},
                {'٥', '5'},
                {'٦', '6'},
                {'٧', '7'},
                {'٨', '8'},
                {'٩', '9'},
        };
        for (int i = 0; i < replace.length; i++) {
            char[] chars = replace[i];
            term = term.replace(chars[0], chars[1]);
        }
        return term;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static final String normalizeString(String s) {
        return s.replace('ی', 'ي').replace('ک', 'ك');
    }

    public static final boolean isNull(String str) {
        return str == null || "".equals(str) || "null".equals(str) || str.trim().isEmpty();
    }

    public static final boolean isNull(Object obj) {
        if (obj == null)
            return true;
        return isNull(obj.toString());
    }

    public static final boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * mansour: Checks if two strings are the same
     */
    public static final boolean isEqual(String toCompareStr, String constStr) {
        return (!isNull(toCompareStr) && !isNull(constStr) &&
                normalizeString(constStr).equals(normalizeString(toCompareStr)));
    }

    public static final boolean isEqual(Object obj, String constStr) {
        if (obj == null)
            return false;
        String str = obj.toString();
        return (isEqual(str, constStr));
    }

    public static final boolean isEqual(Object obj1, Object obj2) {
        return (isNull(obj1) && isNull(obj2)) || (obj1 != null && obj1.equals(obj2));
    }

    public static final String getString(Object object) {
        if (object != null) {
            return object.toString();
        } else {
            return "";
        }
    }

    public static final String getStringValue(Object columnValue) {
        if (isNull(columnValue)) {
            return "";
        } else {

            return columnValue.toString();
        }
    }

    public static Boolean getBooleanValue(Object object){
        if(CommonUtils.isNotNull(object)){
            if(object instanceof Number){
                Integer intResult = NumberUtils.integerValue(object);
                return Objects.equals(intResult, 1);
            }
            else if(object instanceof String){
                String stringResult = getStringValue(object);
                return Boolean.valueOf(stringResult);
            }
        }

        return false;
    }

    public static String reverseDate(String date) {
        try {
            StringTokenizer st = new StringTokenizer(date, "/");
            String part1 = st.nextToken();
            String part2 = st.nextToken();
            String part3 = st.nextToken();
            return part3 + "/" + part2 + "/" + part1;
        } catch (Exception e) {
            return date;
        }
    }

    public static final String reverseCodes(String code) {
        String[] parts = new String[50];
        int partsIndex = 0;
        StringBuffer newPart = new StringBuffer();
        for (int i = 0; i < code.length(); i++) {
            String newChar = code.substring(i, i + 1);
            if (!"-".equals(newChar) && !"/".equals(newChar) && !"_".equals(newChar) &&
                    !".".equals(newChar)) {
                if (i == code.length() - 1) {
                    newPart.append(newChar);
                    parts[partsIndex] = newPart.toString();
                    partsIndex++;
                } else {
                    newPart.append(newChar);
                }
            } else {
                parts[partsIndex] = newPart.toString();
                partsIndex++;
                newPart = new StringBuffer();
                parts[partsIndex] = newChar;
                partsIndex++;
            }
        }
        StringBuffer reverseCode = new StringBuffer();
        for (int i = partsIndex; i > 0; i--) {
            reverseCode.append(parts[i - 1]);
        }
        return reverseCode.toString();
    }

    public static final String reverseAccountNumber(String accountNumber) {
        try {
            StringTokenizer st = new StringTokenizer(accountNumber, "-");
            ArrayList parts = new ArrayList();
            while (st.hasMoreTokens()) {
                parts.add(st.nextToken());
            }
            String reversedAccountNumber = "";
            for (int i = parts.size() - 1; i >= 0; i--) {
                reversedAccountNumber += parts.get(i);
                if (i > 0) {
                    reversedAccountNumber += "-";
                }
            }
            return reversedAccountNumber;
        } catch (Exception e) {
            return accountNumber;
        }
    }

    public static final List<Thread> getThreadsFor(Runnable myRunnable) throws Exception {
        Method getThreads = Thread.class.getDeclaredMethod("getThreads");
        Field target = Thread.class.getDeclaredField("target");
        target.setAccessible(true);
        getThreads.setAccessible(true);
        Thread[] threads = (Thread[]) getThreads.invoke(null);
        List<Thread> result = new ArrayList<Thread>();
        for (Thread thread : threads) {
            Object runnable = target.get(thread);
            if (runnable == myRunnable)
                result.add(thread);
        }
        return result;
    }

//    public static final String encryptForXmlSequence(String text, String myEncryptionKey) {
//
//        try {
//            String myEncryptionScheme = "DESede";
//            byte[] arrayBytes = myEncryptionKey.getBytes(StandardCharsets.UTF_8);
//            DESedeKeySpec ks = new DESedeKeySpec(arrayBytes);
//            SecretKeyFactory skf = SecretKeyFactory.getInstance(myEncryptionScheme);
//            Cipher cipher = Cipher.getInstance(myEncryptionScheme);
//            SecretKey key = skf.generateSecret(ks);
//
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
//            byte[] encryptedText = cipher.doFinal(plainText);
//            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(encryptedText));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static final String byteArrayToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getCommaSeparatedForQuery(List<Map<String, Object>> list, String fieldName) {
        if (list != null && !list.isEmpty()) {
            StringBuffer numbers = new StringBuffer();
            for (Map<String, Object> number : list) {
                numbers.append(number.get(fieldName)).append(" , ");
            }
            numbers.delete(numbers.length() - 3, numbers.length());
            return numbers.toString();
        }
        return null;
    }

    public static String fixFarsiNumbers(String s) {
        s = s.replace((char) (1776), (char) (48));              // 0
        s = s.replace((char) (1777), (char) (49));              // 1
        s = s.replace((char) (1778), (char) (50));              // 2
        s = s.replace((char) (1779), (char) (51));              // 3
        s = s.replace((char) (1780), (char) (52));              // 4
        s = s.replace((char) (1781), (char) (53));              // 5
        s = s.replace((char) (1782), (char) (54));              // 6
        s = s.replace((char) (1783), (char) (55));              // 7
        s = s.replace((char) (1784), (char) (56));              // 8
        s = s.replace((char) (1785), (char) (57));              // 9
        return s;
    }
}

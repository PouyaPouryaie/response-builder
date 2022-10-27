package ir.bigz.responsebuilder.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ir.bigz.responsebuilder.util.BaseDateUtils;
import ir.bigz.responsebuilder.util.CommonUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Component("ValidationUtilsImpl")
public class ValidationUtilsImpl implements ValidationUtils {

    private final Pattern Email_Pattern = Pattern.compile("^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@" +
            "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$");
    private final Pattern Mobile_Pattern =  Pattern.compile("^(09|989)\\d{9}$");
    private final Pattern ShabaNumber_Pattern =  Pattern.compile("^(IR)\\d{24}$");

    @Override
    public boolean isEmailValid(String email) {
        return Email_Pattern.matcher(email).matches();
    }

    @Override
    public boolean isGenderValid(String gender, List<String> allowed) {
        return allowed.contains(gender.toLowerCase());
    }

    @Override
    public boolean isNationalCodeValid(String nationalCode) {
        boolean isValid = false;
        if (nationalCode != null && nationalCode.length() == 10) {
            if (StringUtils.isNumeric(nationalCode)) {
                AtomicInteger index = new AtomicInteger();
                int controllerConditionNumber = nationalCode.substring(0, 9).chars()
                        .map(i -> Integer.valueOf(Character.toString((char) i)) * (10 - index.getAndIncrement())).sum() % 11;
                int controllerNumber = Integer.valueOf(nationalCode.substring(9));
                isValid = (controllerConditionNumber < 2) ?
                        controllerConditionNumber == controllerNumber :
                        controllerNumber == (11 - controllerConditionNumber);
            }
        }
        return isValid;
    }

    @Override
    public boolean isMobileValid(String mobile) {
        return Mobile_Pattern.matcher(mobile).matches();
    }

    @Override
    public boolean isPersianDateFormatValid(String date) {
        return BaseDateUtils.isValidJalaliDate(date);
    }

    @Override
    public boolean isNumberValid(String number) {
        return StringUtils.isNumeric(number);
    }

    @Override
    public boolean isFieldValid(Object value) {
        return CommonUtils.isNotNull(value);
    }

    @Override
    public boolean isShebaNumberValid(String shebaNumber) {
        boolean matches = ShabaNumber_Pattern.matcher(shebaNumber).matches();
        if(matches){
            String firstString = shebaNumber.substring(0, 4);
            String secondString = shebaNumber.substring(4);
            String newString = secondString.concat(firstString);
            char[] values = firstString.substring(0,2).toCharArray();
            String[] numberValues = new String[2];
            int index = 65; //index of A
            int baseIndex = 10; //index of letter in algorithm
            for(int i=0; i < values.length; i++){
                int number = values[i];
                int range = number - 65;
                Integer newNumber = baseIndex + range;
                newString = newString.replaceAll(String.valueOf(values[i]), newNumber.toString());
            }
            BigDecimal numberValue = new BigDecimal(newString);
            BigDecimal result = numberValue.remainder(new BigDecimal(97));
            return result.intValue() == 1;
        }
        return false;
    }
}

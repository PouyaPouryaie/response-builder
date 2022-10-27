package ir.bigz.responsebuilder.validation;

public enum ValidationType {

    EMAIL ("پست الکترونیکی اشتباه وارد شده است", new String[]{}),
    MOBILE ("شماره همراه وارد شده اشتباه است", new String[]{}),
    GENDER ("جنسیت صحیح نمی باشد", new String[]{"male", "female"}),
    NATIONAL_CODE ("کد ملی صحیح نمی باشد", new String[]{}),
    DATE ("قلم داده ای وارد نشده است و یا فرمت آن اشتباه است", new String[]{}),
    FIELD_NOT_NULL ("قلم داده ای نمی تواند خالی باشد", new String[]{}),
    NUMBER ("قلم داده ای باید عددی باشد", new String[]{}),
    SHEBA_NUMBER("شماره شبا به درستی وارد نشده است", new String[]{});

    private final String failedMessage;
    private final String[] defaultValue;

    ValidationType(String failedMessage, String[] defaultValue) {
        this.failedMessage = failedMessage;
        this.defaultValue = defaultValue;
    }

    public String getFailedMessage() {
        return failedMessage;
    }

    public String[] getDefaultValue() {
        return defaultValue;
    }
}

package ir.bigz.responsebuilder.util;

import ir.bigz.responsebuilder.validation.BusinessValidator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;

public class BigZUtils {

    public static void validateAndThrowException(BusinessValidator... validators) {
        Arrays.stream(validators).forEachOrdered(BusinessValidator::validateAndThrowException);
    }

    public static Locale fetchLocale(LocaleResolver localeResolver, String defaultLocale) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null)
            return Locale.forLanguageTag(defaultLocale);
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        var locale = localeResolver.resolveLocale(request);
        return CommonUtils.isNull(locale.toString()) ? Locale.forLanguageTag(defaultLocale) : locale;
    }
}

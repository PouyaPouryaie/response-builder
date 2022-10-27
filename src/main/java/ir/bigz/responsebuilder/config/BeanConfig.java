package ir.bigz.responsebuilder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Locale;

@Configuration
public class BeanConfig {

    @Bean
    public LocaleResolver localeResolver() {
        return new LocaleResolver() {
            final Locale defaultLocale = Locale.forLanguageTag("fa");

            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                Locale locale = null;
                locale = (Locale) request.getAttribute("locale");
                locale = locale == null ? request.getHeader("locale") != null ?
                        Locale.forLanguageTag(request.getHeader("locale")) : null : locale;
                locale = locale == null ? (Locale) request.getSession().getAttribute("locale") : locale;
                return locale != null ? locale : defaultLocale;
            }

            @Override
            public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
                throw new UnsupportedOperationException(
                        "Cannot change HTTP accept header - use a different locale resolution strategy");
            }
        };
    }

    @Bean("messageSource")
    public ResourceBundleMessageSource messageSource(
            @Value("${spring.messages.base-names}") String messageBaseNames
    ) {
        String[] split = Arrays.stream(messageBaseNames.split(",")).map(String::trim).toArray(String[]::new);
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(split);
        return messageSource;
    }

    @Bean("errorSource")
    public ResourceBundleMessageSource errorSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("errors");
        /*messageSource.setDefaultLocale(Locale.forLanguageTag("fa"));*/
        return messageSource;
    }
}

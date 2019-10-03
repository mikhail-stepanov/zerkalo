package face.io.common.i18n;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageWrapper {

    private static MessageSource messageSource;

    public static void setSource(MessageSource messageSource){
        MessageWrapper.messageSource = messageSource;
    }

    public static String message(String code, Object[] args){
        return messageSource.getMessage(code, args, new Locale("ru"));
    }
}

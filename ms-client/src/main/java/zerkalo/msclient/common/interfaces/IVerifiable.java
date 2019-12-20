package zerkalo.msclient.common.interfaces;

import org.springframework.validation.Errors;

public interface IVerifiable {

    void verify(Errors errors);
}

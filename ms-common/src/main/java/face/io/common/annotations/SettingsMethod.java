package face.io.common.annotations;

import face.io.common.enums.SettingsMethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value= ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface SettingsMethod {

    SettingsMethodType methodType();
}

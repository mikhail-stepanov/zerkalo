package com.tochka.ms.client.validation.util;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class ValidationMap {
    public static final Map<Class<?>, Function<Object, Boolean>> types = new HashMap<>();

    static {
        types.put(UUID.class, ValidationMap::uuidValidate);
        types.put(Integer.class, ValidationMap::digitValidate);
        types.put(Base64.class, ValidationMap::base64Validate);

    }

    public static boolean uuidValidate(Object object) {
        try {
            UUID.fromString(object.toString());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static boolean digitValidate(Object object) {
        try {
            Integer.parseInt(object.toString());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static boolean base64Validate(Object object){
        return object.toString().matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$");
    }
}

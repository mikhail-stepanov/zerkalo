package social.tochka.common.services;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ModelExtensions {

//    public static <TFrom, TTo> TTo map(Class<TFrom> from, Class<TTo> to, TFrom model){
//        TTo result = BeanUtils.instantiateClass(to);
//        return map(from, to ,model, result);
//    }
//
//    public static <TFrom, TTo> TTo map(Class<TFrom> from, Class<TTo> to, TFrom model, TTo result){
//        ReflectionUtils.doWithFields(to, targetField ->{
//            Field sourceField = ReflectionUtils.findField(from, targetField.getName());
//            if(sourceField == null)
//                return;
//
//            ReflectionUtils.makeAccessible(targetField);
//            ReflectionUtils.makeAccessible(sourceField);
//
//            if (targetField.getType().equals(sourceField.getType()) || (sourceField.get(model) instanceof UUID && targetField.getType().equals(UUID.class)) || targetField.getType().equals(Object.class)){
//                targetField.set(result, sourceField.get(model));
//            }
//
//        });
//        return result;
//    }
//
//    public static <TTo> TTo map(CayenneDataObject from, Class<TTo> to){
//
//        TTo result = BeanUtils.instantiateClass(to);
//        return map(from, to, result);
//    }
//
//    public static <TTo> TTo map(CayenneDataObject from, Class<TTo> to, TTo result){
//
//        ReflectionUtils.doWithFields(to, targetField ->{
//            Object o = from.readProperty(targetField.getName());
//            if(o == null)
//                return;
//
//            ReflectionUtils.makeAccessible(targetField);
//            targetField.set(result, o);
//        });
//        return result;
//    }
//
//    public static <TFrom, TTo> TTo map(Class<TTo> to, TFrom model){
//        TTo result = BeanUtils.instantiateClass(to);
//        ReflectionUtils.doWithFields(to, targetField ->{
//            Field sourceField = ReflectionUtils.findField(model.getClass(), targetField.getName());
//            if(sourceField == null)
//                return;
//
//            ReflectionUtils.makeAccessible(targetField);
//            ReflectionUtils.makeAccessible(sourceField);
//
//            if (targetField.getType().equals(sourceField.getType()) || (sourceField.get(model) instanceof UUID && targetField.getType().equals(UUID.class)) || targetField.getType().equals(Object.class)){
//                targetField.set(result, sourceField.get(model));
//            }
//
//        });
//        return result;
//    }

    public static <T> T fromMap(Class<T> modelClass, LinkedHashMap<String, Object> properties){
        T modelInstance = null;

        try{
            modelInstance = modelClass.newInstance();
            return fromMap(modelClass, properties, modelInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelInstance;
    }

    public static <T> T fromMap(Class<T> modelClass, LinkedHashMap<String, Object> properties, T modelInstance){

        try{
            modelInstance = modelClass.newInstance();
            if (properties == null)
                return modelInstance;

            for(Map.Entry<String, Object> entry : properties.entrySet()){
                try {
                    Field field = ReflectionUtils.findField(modelClass, entry.getKey());
                    if (field == null || entry.getValue() == null)
                        continue;

                    if (field.getName().equalsIgnoreCase("createdDate")
                            || field.getName().equalsIgnoreCase("deletedDate")
                            || field.getName().equalsIgnoreCase("modifiedDate")){
                        continue;
                    }

                    ReflectionUtils.makeAccessible(field);

                    Object val = entry.getValue();
                    if (field.getName().equalsIgnoreCase("id")){
                        if (field.getType().equals(UUID.class)){
                            val = UUID.fromString(String.valueOf(val));
                        }else if(modelClass.getSuperclass().getName().contains("UpdatableEntityBase")){
                            val = UUID.fromString(String.valueOf(val));
                        }else{
                            val = Integer.parseInt(String.valueOf(val));
                        }
                    }else{
                        if (field.getType().equals(UUID.class)){
                            val = UUID.fromString(String.valueOf(val));
                        }
                    }
                    if (field.getType().equals(Integer.class) && val instanceof String){
                        ReflectionUtils.setField(field, modelInstance, Integer.parseInt((String)val));
                    }else{
                        ReflectionUtils.setField(field, modelInstance, val);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelInstance;
    }

    public static <T> LinkedHashMap<String, Object> toMap(Class<T> modelClass,  T model){
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        try {
            ReflectionUtils.doWithFields(modelClass, sourceField ->{
                ReflectionUtils.makeAccessible(sourceField);
                result.put(sourceField.getName(), sourceField.get(model));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

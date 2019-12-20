package zerkalo.common.util;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.map.EntityResolver;
import org.apache.cayenne.map.ObjEntity;

public class EntityHelper {

    public static ObjEntity resolveEntity(CayenneDataObject model){
        EntityResolver entityResolver = model.getObjectContext().getEntityResolver();
        return entityResolver.getObjEntity(model.getObjectId().getEntityName());
    }
}

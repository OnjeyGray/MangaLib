package com.epam.mangalib.database.jsql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLDAO<E extends SQLObject> {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();
    public <E extends SQLObject> List<E> mapListToObjectList(List<Map<String, Object>> mapList) {
        List<E> objectList = new ArrayList<>();
        Class<E> parameter = (Class<E>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        for (Map map : mapList) {
            try {
                objectList.add(parameter.getConstructor(Map.class).newInstance(map));
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                ROOT_LOGGER.error(e);
            }
        }
        return objectList;
    }

    public E mapListToObject(List<Map<String, Object>> mapList) {
        List<E> objectList = mapListToObjectList(mapList);
        if(objectList.isEmpty()) {
            return null;
        }
        return objectList.get(0);
    }
}

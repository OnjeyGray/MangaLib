package com.epam.mangalib.database.jsql;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SQLObject {
    protected Map<String, Object> objectMap = new HashMap<>();

    public SQLObject() {}

    public SQLObject(Map<String, Object> objectMap) {
        this.objectMap = objectMap;
    }

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, Object> objectMap) {
        this.objectMap = objectMap;
    }

    public Object get(String variable) {
        return objectMap.get(variable);
    }

    public void set(String variable, Object value) {
        objectMap.put(variable, value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectMap);
    }

   @Override
    public boolean equals(Object o) {
       if (this == o) return true;
       if (o == null || getClass() != o.getClass()) return false;
        SQLObject sqlObject = (SQLObject) o;
        for (String string : objectMap.keySet()) {
            if (!objectMap.get(string).equals(sqlObject.objectMap.get(string))) {
                return false;
            }
        }
        return true;
    }
}

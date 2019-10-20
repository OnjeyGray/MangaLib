package com.epam.mangalib.database.jsql;

import com.epam.mangalib.database.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLCall {
    private ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private StringBuilder queryString = new StringBuilder();
    private List<Object> valueList = new ArrayList<>();
    private List<Map<String, Object>> resultList = new ArrayList<>();

    private List<Map<String, Object>> execute() throws SQLException {
        Connection connection = CONNECTION_POOL.retrieve();
        Map<String, Object> resultMap = new HashMap<>();
        try(CallableStatement callableStatement = connection.prepareCall(String.valueOf(queryString))) {
            for (int i = 0; i < valueList.size(); i++) {
                callableStatement.setObject(i + 1, valueList.get(i));
            }
            ResultSet resultSet = callableStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                resultMap.put(resultSetMetaData.getColumnName(i), null);
            }
            while (resultSet.next()) {
                for (String columnName : resultMap.keySet()) {
                    resultMap.put(columnName, resultSet.getObject(columnName));
                }
                resultList.add((Map<String, Object>) ((HashMap<String, Object>) resultMap).clone());
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return resultList;
    }

    public class From {
        public List<Map<String, Object>> executeCall() throws SQLException {
            return execute();
        }
    }

    public class Select {
        public Select as(String columnName) {
            queryString.append("as " + columnName + " ");
            return new Select();
        }

        public From from(String table) {
            queryString.append("from " + table + " ");
            return new From();
        }
    }

    public Select select(String...column) {
        queryString.append("select ");
        for (int i = 0; i < column.length; i++) {
            queryString.append(column[i] + (i + 1 == column.length ? " " : ", "));
        }
        return new Select();
    }
}

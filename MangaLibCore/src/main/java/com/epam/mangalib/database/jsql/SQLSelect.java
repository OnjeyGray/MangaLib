package com.epam.mangalib.database.jsql;

import com.epam.mangalib.database.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLSelect {
    private ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private StringBuilder queryString = new StringBuilder();
    private List<Object> valueList = new ArrayList<>();
    private List<Map<String, Object>> resultList = new ArrayList<>();

    private List<Map<String, Object>> execute() throws SQLException {
        Connection connection = CONNECTION_POOL.retrieve();
        Map<String, Object> resultMap = new HashMap<>();
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(queryString))) {
            for (int i = 0; i < valueList.size(); i++) {
                preparedStatement.setObject(i + 1, valueList.get(i));
            }
            resultSet = preparedStatement.executeQuery();
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
            resultSet.close();
            CONNECTION_POOL.putBack(connection);
        }
        return resultList;
    }

    public class Order {
        public Order desc() {
            queryString.append("desc ");
            return new Order();
        }

        public List<Map<String, Object>> executeSelect() throws SQLException {
            return execute();
        }

        public SQLSelect executeInner() {
            SQLSelect sqlSelect = new SQLSelect();
            sqlSelect.queryString = queryString;
            sqlSelect.valueList = valueList;
            return sqlSelect;
        }
    }

    public class AndOr {
        public Where and(String variable) {
            queryString.append("and " + variable + " ");
            return new Where();
        }

        public Where or(String variable) {
            queryString.append("or " + variable + " ");
            return new Where();
        }

        public List<Map<String, Object>> executeSelect() throws SQLException {
            return execute();
        }

        public SQLSelect executeInner() {
            SQLSelect sqlSelect = new SQLSelect();
            sqlSelect.queryString = queryString;
            sqlSelect.valueList = valueList;
            return sqlSelect;
        }

        public Order orderBy(String variable) {
            queryString.append("order by " + variable + " ");
            return new Order();
        }

        public AndOr groupBy(String variable) {
            queryString.append("group by ( " + variable + " ) ");
            return new AndOr();
        }
    }

    public class Where {
        public AndOr like(Object value) {
            queryString.append("like ? ");
            valueList.add(value);
            return new AndOr();
        }

        public AndOr eq(Object value) {
            queryString.append("= ? ");
            valueList.add(value);
            return new AndOr();
        }

        public AndOr more(Object value) {
            queryString.append("> ? ");
            valueList.add(value);
            return new AndOr();
        }

        public AndOr less(Object value) {
            queryString.append("< ? ");
            valueList.add(value);
            return new AndOr();
        }

        public AndOr moreOrEq(Object value) {
            queryString.append(">= ? ");
            valueList.add(value);
            return new AndOr();
        }

        public AndOr lessOrEq(Object value) {
            queryString.append("<= ? ");
            valueList.add(value);
            return new AndOr();
        }

        public AndOr in(SQLSelect sqlSelect) {
            queryString.append("in( " + sqlSelect.queryString + " ) ");
            valueList.addAll(sqlSelect.valueList);
            return new AndOr();
        }
    }

    public class Join {
        public From using(String variable) {
            queryString.append("using(" + variable + ") ");
            return new From();
        }

        public List<Map<String, Object>> executeSelect() throws SQLException {
            return execute();
        }

        public SQLSelect executeInner() {
            SQLSelect sqlSelect = new SQLSelect();
            sqlSelect.queryString = queryString;
            sqlSelect.valueList = valueList;
            return sqlSelect;
        }
    }

    public class From {
        public Join join(String table) {
            queryString.append("join " + table + " ");
            return new Join();
        }

        public Join leftJoin(String table) {
            queryString.append("left join " + table + " ");
            return new Join();
        }

        public Join rightJoin(String table) {
            queryString.append("right join " + table + " ");
            return new Join();
        }

        public Join innerJoin(String table) {
            queryString.append("inner join " + table + " ");
            return new Join();
        }

        public Where where(String variable) {
            queryString.append("where " + variable + " ");
            return new Where();
        }

        public List<Map<String, Object>> executeSelect() throws SQLException {
            return execute();
        }

        public SQLSelect executeInner() {
            SQLSelect sqlSelect = new SQLSelect();
            sqlSelect.queryString = queryString;
            sqlSelect.valueList = valueList;
            return sqlSelect;
        }
    }

    public class Select {
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

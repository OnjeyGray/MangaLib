package com.epam.mangalib.database.jsql;

import com.epam.mangalib.database.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLInsert {
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private StringBuilder queryString = new StringBuilder();
    private List<Object> valueList = new ArrayList<>();

    public class Value {
        public void executeInsert() throws SQLException {
            Connection connection = CONNECTION_POOL.retrieve();
            try(PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(queryString))) {
                for (int i = 0; i < valueList.size(); i++) {
                    preparedStatement.setObject(i + 1, valueList.get(i));
                }
                preparedStatement.executeUpdate();
            } finally {
                CONNECTION_POOL.putBack(connection);
            }
        }
    }

    public class Variable {
        public Value values(Object...value) {
            queryString.append("values ( ");
            for (int i = 0; i < value.length; i++) {
                queryString.append(i + 1 == value.length ? "? " : "?, ");
            }
            queryString.append(") ");
            valueList.addAll(Arrays.asList(value));
            return new Value();
        }
    }

    public class Insert {
        public Variable variables(String...variable) {
            queryString.append("( ");
            for (int i = 0; i < variable.length; i++) {
                queryString.append(i + 1 == variable.length ? variable[i] + " " : variable[i] + ", ");
            }
            queryString.append(") ");
            return new Variable();
        }

    }

    public Insert insertInto(String table) {
        queryString.append("insert into " + table + " ");
        return new Insert();
    }
}

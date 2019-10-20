package com.epam.mangalib.database.jsql;

import com.epam.mangalib.database.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLDelete {
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private StringBuilder queryString = new StringBuilder();
    private List<Object> valueList = new ArrayList<>();

    private void execute() throws SQLException {
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

    public class AndOr {
        public Where and(String variable) {
            queryString.append("and " + variable + " ");
            return new Where();
        }

        public Where or(String variable) {
            queryString.append("or " + variable + " ");
            return new Where();
        }

        public void executeDelete() throws SQLException {
            execute();
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
    }

    public class Delete {
        public Where where(String variable) {
            queryString.append("where " + variable + " ");
            return new Where();
        }
    }

    public Delete deleteFrom(String table) {
        queryString.append("delete from " + table + " ");
        return new Delete();
    }
}

package com.epam.mangalib.database.jsql;

import com.epam.mangalib.database.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLAlter {
    private ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
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

    public class Eq {
        public void executeAlter() throws SQLException {
            execute();
        }

    }
    public class Param {
        public Eq eq(Object value) {
            queryString.append("= ? ");
            valueList.add(value);
            return new Eq();
        }
    }

    public class Alter {
        public Param param(String param) {
            queryString.append(param + " ");
            return new Param();
        }
    }

    public Alter alterTable(String table) {
        queryString.append("alter table " + table + " ");
        return new Alter();
    }
}

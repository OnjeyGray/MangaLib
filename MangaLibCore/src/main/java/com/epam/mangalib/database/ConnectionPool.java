package com.epam.mangalib.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    public static final String CONNECTION_POOL_URL = "url";
    public static final String CONNECTION_POOL_USER = "user";
    public static final String CONNECTION_POOL_PASSWORD = "password";
    public static final String CONNECTION_POOL_DRIVER = "driver";
    public static final String CONNECTION_POOL_INIT_CONNECTION_COUNT = "initConnectionCount";
    public static final String CONNECTION_POOL_BUNDLE = "connectionPool";

    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(CONNECTION_POOL_BUNDLE);
    private static final String URL = BUNDLE.getString(CONNECTION_POOL_URL);
    private static final String USER = BUNDLE.getString(CONNECTION_POOL_USER);
    private static final String PASSWORD = BUNDLE.getString(CONNECTION_POOL_PASSWORD);
    private static final String DRIVER = BUNDLE.getString(CONNECTION_POOL_DRIVER);
    private static final int INIT_CONNECTION_COUNT = Integer.parseInt(BUNDLE.getString(CONNECTION_POOL_INIT_CONNECTION_COUNT));
    private static final BlockingQueue<Connection> BLOCKING_QUEUE = new ArrayBlockingQueue<>(INIT_CONNECTION_COUNT);
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();

    private ConnectionPool() {
       init();
    }

    private void init() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            ROOT_LOGGER.error(e);
        }
        for (int i = 0; i < INIT_CONNECTION_COUNT; i++) {
            try {
                BLOCKING_QUEUE.put(DriverManager.getConnection(URL, USER, PASSWORD));
            } catch (InterruptedException | SQLException e) {
                ROOT_LOGGER.error(e);
            }
        }
    }

    public static ConnectionPool getInstance() {
        return CONNECTION_POOL;
    }

    public Connection retrieve() {
        Connection connection = null;
        try {
            connection = BLOCKING_QUEUE.take();
        } catch (InterruptedException e) {
            ROOT_LOGGER.error(e);
        }
        return connection;
    }

    public void putBack(Connection connection) {
        try {
            BLOCKING_QUEUE.put(connection);
        } catch (InterruptedException e) {
            ROOT_LOGGER.error(e);
        }
    }
}

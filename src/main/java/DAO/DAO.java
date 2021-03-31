package DAO;

import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

import org.postgresql.ds.PGConnectionPoolDataSource;


public abstract class DAO<E>{
    protected final String cav = "\'";
    private PGConnectionPoolDataSource poolDataSource;
    public DAO () throws SQLException {
        final Dotenv dotenv = Dotenv.load();
        poolDataSource = new PGConnectionPoolDataSource();
        poolDataSource.setDatabaseName(dotenv.get("DATABASE"));
        poolDataSource.setUser(dotenv.get("DATABASE_USER"));
        poolDataSource.setPassword(dotenv.get("DATABASE_PASS"));
        poolDataSource.setURL(dotenv.get("DATABASE_HOST"));

    }

    protected PGConnectionPoolDataSource getPoolConnection(){
        return poolDataSource;
    }
}

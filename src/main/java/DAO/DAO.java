package DAO;

import java.sql.*;

import org.postgresql.ds.PGConnectionPoolDataSource;


public abstract class DAO<E>{
    protected final String cav = "\'";
    private PGConnectionPoolDataSource poolDataSource;
    public DAO () throws SQLException {
        poolDataSource = new PGConnectionPoolDataSource();
        poolDataSource.setDatabaseName("d5l00o7h4h7tpe");
        poolDataSource.setUser("bnryzjotnofczg");
        poolDataSource.setPassword("1ca4d8086340c363f91b7a7a2d82db00a6299d2841f76557eaef52c77bcdd3c8");
        poolDataSource.setURL("jdbc:postgresql://ec2-54-145-102-149.compute-1.amazonaws.com");

    }

    protected PGConnectionPoolDataSource getPoolConnection(){
        return poolDataSource;
    }
}

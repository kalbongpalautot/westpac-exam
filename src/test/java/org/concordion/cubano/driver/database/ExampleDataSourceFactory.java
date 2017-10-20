package org.concordion.cubano.driver.database;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.FluentJdbcBuilder;
import org.codejargon.fluentjdbc.api.query.listen.AfterQueryListener;
import org.concordion.cubano.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.jdbc.pool.OracleDataSource;

public class ExampleDataSourceFactory {
    private static DataSource datasource = null;
    private static FluentJdbc fluentJDBC = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleDataSourceFactory.class);

    private ExampleDataSourceFactory() {
    }

    public static synchronized DataSource getOracleDataSource() throws SQLException {
        if (datasource == null) {
            OracleDataSource oracleDS = new OracleDataSource();
            oracleDS.setURL(AppConfig.getInstance().getDatabaseUrl());

            datasource = oracleDS;
        }

        return datasource;
    }

    public static synchronized FluentJdbc fluentJDBC() throws SQLException {
        if (fluentJDBC == null) {
            AfterQueryListener listener = execution -> {
                // TODO Also log parameters
                LOGGER.trace("Executed SQL: " + execution.sql());
            };

            fluentJDBC = new FluentJdbcBuilder()
                    .connectionProvider(getOracleDataSource())
                    .afterQueryListener(listener)
                    .build();
        }

        return fluentJDBC;
    }
}

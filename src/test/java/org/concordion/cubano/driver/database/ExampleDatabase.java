package org.concordion.cubano.driver.database;

import java.sql.SQLException;
import java.util.Map;

import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.concordion.cubano.AppConfig;

public class ExampleDatabase {
    protected static final String SCHEMA = AppConfig.getInstance().getDatabaseSchema();

    private String setSchema(String schmema, String sql) {
        return sql.replace("${SCHEMA}", schmema);
    }

    public String getExampleQuery(String instanceid) throws SQLException {
        Map<String, Object> result = ExampleDataSourceFactory.fluentJDBC()
                .query()
                .select(setSchema(SCHEMA, "SELECT * from ${SCHEMA}.TASKHISTORY WHERE TASKSTATUS = 'COMPLETED' and instanceid = :instanceid"))
                .namedParam("instanceid", instanceid)
                .singleResult(Mappers.map());

        return result.get("INSTANCEID").toString();
    }
}

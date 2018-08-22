package com.feixichen.influxdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.InfluxDB;


public class InfluxDBTest {
	
	private String url = "";
    private String username = "";
    private String password = "";
    private String database = "";
    private String policyName = "";
    private String duration = "";
    private Integer replicationNum = 1;
    private InfluxDB influxDB = null;

    public InfluxDBTest(String url) {
        this.url = url;
    }

    /**
     * 设置数据库
     * */
    public InfluxDBTest setDatabase(String database, String policyName) {
        this.database = database;
        this.policyName = policyName;
        return this;
    }

    public InfluxDBTest setAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    /**
     * 创建保存策略
    **/
    public InfluxDBTest setRetentionPolicy(String duration, Integer replicationNum) {
        this.duration = duration;
        this.replicationNum = replicationNum;
        return this;
    }

    public InfluxDBTest build() {
        if (influxDB == null) {
            synchronized (this) {
                if (influxDB == null) {
                    influxDB = InfluxDBFactory.connect(url, username, password);
                }
            }
        }
        influxDB.createDatabase(database);
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                policyName, database, duration, replicationNum);
        this.query(command);
        return this;
    }

    /**
     * 插入数据
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields) {

		Builder builder = Point.measurement(measurement);
		builder.tag(tags);
		builder.fields(fields);
		
		influxDB.write(database, policyName , builder.build());
    }

    /**
     * 查询数据
     */
    public QueryResult query(String command) {
        return influxDB.query(new Query(command, database));
    }
}

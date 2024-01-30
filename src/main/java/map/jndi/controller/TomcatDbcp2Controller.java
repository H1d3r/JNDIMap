package map.jndi.controller;

import map.jndi.annotation.JNDIController;
import map.jndi.annotation.JNDIMapping;
import map.jndi.bean.DatabaseBean;

import javax.naming.Reference;
import javax.naming.StringRefAddr;

@JNDIController
@JNDIMapping("/TomcatDbcp2")
public class TomcatDbcp2Controller extends DatabaseController {
    public Object process(DatabaseBean databaseBean) {
        Reference ref = new Reference("javax.sql.DataSource", "org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory", null);
        ref.add(new StringRefAddr("driverClassName", databaseBean.getDriver()));
        ref.add(new StringRefAddr("url", databaseBean.getUrl()));
        ref.add(new StringRefAddr("initialSize", "1"));

        if (databaseBean.getSql() != null) {
            ref.add(new StringRefAddr("connectionInitSqls", databaseBean.getSql()));
        }

        return ref;
    }
}
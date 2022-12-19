package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/prep";
    private String dbName = "prep";
    private String userName = "root";
    private String password = "79856425";
    private String dialect = "org.hibernate.dialect.MySQLDialect";



    public SessionFactory connectHibernate() {

            Properties prop= new Properties();

            prop.setProperty("hibernate.connection.url", url);
            prop.setProperty("dialect", dialect);
            prop.setProperty("hibernate.connection.username", userName);
            prop.setProperty("hibernate.connection.password", password);
            prop.setProperty("hibernate.connection.driver_class", driver);

            SessionFactory sessionFactory = new Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
            return sessionFactory;
    }

    public Connection createConnection() {
//        Connection conn = null;
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(url + dbName, userName, password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return conn;
        return null;
    }
}

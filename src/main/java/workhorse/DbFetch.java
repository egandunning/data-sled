package workhorse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

public class DbFetch {
    
    public static char[] pass;

    public static void setPass(char[] password) {
        pass = password;
    }
    
    public static void clearPass() {
        for(int i = 0; i < pass.length; i++) {
            pass[i] = (char)(Math.random() / 256);
        }
        pass = null;
    }
    
    public static ResultSet fetch(String dbConnLoc, String query) throws Exception {
        
        Properties dbConn = FileIo.readToProps(dbConnLoc);
        
        String driverLoc = dbConn.getProperty("driverloc");
        String className = dbConn.getProperty("classname");
        String connString = dbConn.getProperty("connstring");
        
        if(driverLoc == null || className == null || connString == null) {
            throw new Exception("DB connection file needs these properties:\n"
                    + "driverloc=<location of JDBC driver>\n"
                    + "classname=<JDBC driver class name. For example: org.postgresql.Driver>\n"
                    + "connstring=<JDBC connection string. Password can be parameterized with $PASSWORD>");
        }
        
        if(connString.contains("$PASSWORD")) {
            if(pass == null) {
                throw new Exception("password has not been set...");
            }
            connString.replace("$PASSWORD", new String(pass));
        }
        
        
        Connection conn = DriverManager.getConnection(connString);
        
        return conn.prepareStatement(query).executeQuery();
    }
    
    
}

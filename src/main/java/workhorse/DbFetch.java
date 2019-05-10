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
        
        String className = dbConn.getProperty("classname");
        String username = dbConn.getProperty("username");
        String connString = dbConn.getProperty("connstring");
        
        if(username == null || connString == null) {
            throw new Exception("DB connection file needs these properties:\n"
                    + "classname=<JDBC driver class name. For example: org.postgresql.Driver>\n"
                    + "username=<database username>\n"
                    + "connstring=<JDBC connection string. Don't include username/password.>");
        }
        
        if(className != null) {
            Class.forName(className);
        }
        
        Connection conn = DriverManager.getConnection(connString, username, new String(pass));
        
        return conn.prepareStatement(query).executeQuery();
    }
    
    
}

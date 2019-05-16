package workhorse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

public class FileIo {

    public static final String NULL_FILE = "filename cannot be empty...";
    public static final String NULL_RESULT_SET = "cannot write empty query result...";
    public static final String NULL_DELIM_NEWLINE = "must specify a field delimiter and newline character";
    
    public static Properties readToProps(String filename) throws Exception {
        
        if(filename == null) {
            throw new Exception(NULL_FILE);
        }
        
        Properties props = new Properties();
        
        try(FileInputStream fis = new FileInputStream(filename)) {
            props.load(fis);
            fis.close();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return props;
    }
    
    public static String readToString(String filename) throws Exception {
        
        if(filename == null) {
            throw new Exception(NULL_FILE);
        }
        
        File f = new File(filename);
        
        StringBuilder sb = new StringBuilder();
        
        try(Scanner s = new Scanner(f)) {
            while(s.hasNextLine()) {
                sb.append(s.nextLine()).append('\n');
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        return sb.toString();
    }
    
    public static void writeToFile(File file, ResultSet rs) throws Exception {
        writeToFile(file, rs, "|", "\n", 200);
    }
    
    public static void writeToFile(File file, ResultSet rs, String delimiter, String newline, int fetchSize) throws Exception {
        
        if(file == null) {
            throw new Exception(NULL_FILE);
        }
        
        if(rs == null) {
            throw new Exception(NULL_RESULT_SET);
        }
        
        if(delimiter == null || newline == null) {
            throw new Exception(NULL_DELIM_NEWLINE);
        }
        
        switch(newline.toUpperCase()) {
        case "LF":
            newline = "\n";
            break;
        case "CRLF":
            newline = "\r\n";
            break;
        case "CR":
            newline = "\r";
            break;
        }
        
        FileWriter fw = new FileWriter(file);
        
        rs.setFetchSize(fetchSize);
        
        int colCount = rs.getMetaData().getColumnCount();
        
        String[] columnTypes = new String[colCount];
        
        
        
        try(BufferedWriter bw = new BufferedWriter(fw)) {
            
            for(int i = 0; i < colCount; i++) {
                
                columnTypes[i] = rs.getMetaData().getColumnTypeName(i+1);
                bw.write(rs.getMetaData().getColumnName(i+1));
                
                if(i == colCount - 1) {
                    bw.write(newline);
                } else {
                    bw.write(delimiter);
                }
            }
            
            while(rs.next()) {
                for(int i = 0; i < colCount; i++) {
                    
                    Object obj = rs.getObject(i+1);
                    
                    String value = (obj == null) ? "" : obj.toString();
                    
                    bw.write(value.toString());
                    
                    if(i == colCount - 1) {
                        bw.write(newline);
                    } else {
                        bw.write(delimiter);
                    }
                }
            }
            
            bw.close();
            rs.close();
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }
}

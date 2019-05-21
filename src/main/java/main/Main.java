package main;

import java.io.Console;
import java.io.File;

import javax.swing.UIManager;

import ui.MainWindow;
import workhorse.DbFetch;
import workhorse.FileIo;

public class Main {
    
    private static final String CONSOLE_ARG_MSG = "Incorrect usage. 3 arguments are required for command line usage:\n"
            + "connection filename: the path to the database connection configuration file\n"
            + "query flename: the path to the SQL query to run\n"
            + "copy to filename: the file to copy exported results to";
    private static final String CONSOLE_SUCCESS_MSG = "success";

    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        //start in console mode
        if(args.length >= 3) {
            System.out.println("starting in console mode");
            consoleMode(args[0], args[1], args[2]);
            return;
        }
        
        //Show main window
        MainWindow mw = MainWindow.getInstance();
        
        mw.setVisible(true);

    }
    
    private static void consoleMode(String connectionFilename, String queryFilename, String copyToFilename) {
        
        //check for null/missing values
        if(connectionFilename == null || connectionFilename.trim().length() == 0
                || queryFilename == null || queryFilename.trim().length() == 0
                || copyToFilename == null || copyToFilename.trim().length() == 0) {
            System.out.println(CONSOLE_ARG_MSG);
            return;
        }
        
        String query;
        
        try {
            query = FileIo.readToString(queryFilename);
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
        Console cns = System.console();
        if(cns == null) {
            System.out.println("unable to initialize console - can not read password");
            return;
        }
        
        System.out.println("Enter database password");
        char[] pass = cns.readPassword();
        System.out.println();
        
        DbFetch.pass = pass;
        
        try {
            
            File file = new File(copyToFilename);
            
            FileIo.writeToFile(file, DbFetch.fetch(connectionFilename, query));
            System.out.println("Query results copied: " + file.getAbsolutePath());
            System.out.println(CONSOLE_SUCCESS_MSG);
            
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
        
    }

}

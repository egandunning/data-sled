package main;

import java.io.Console;
import java.io.File;
import java.util.Locale;

import javax.swing.UIManager;

import ui.MainWindow;
import ui.Popup;
import workhorse.DbFetch;
import workhorse.FileIo;

public class Main {
    
    private static final String CONSOLE_ARG_MSG = "Incorrect usage. 3 arguments are required for command line usage:\n"
            + "connection filename: the path to the database connection configuration file\n"
            + "query flename: the path to the SQL query to run\n"
            + "copy to filename: the file to copy exported results to";
    private static final String CONSOLE_ERR_MSG = "aborted";

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
        
        //true if host os is windows
        boolean windows = System.getProperty("os.name").toLowerCase(Locale.ROOT).startsWith("windows");
        
        Runtime rt = Runtime.getRuntime();
        
        //Set exit status to failed
        try {
            if(windows) {
                rt.exec("set ds_exit_cd=1");
            } else {
                rt.exec("export ds_exit_cd=1");
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(CONSOLE_ERR_MSG);
        }
        
        //check for null/missing values
        if(connectionFilename == null || connectionFilename.trim().length() == 0
                || queryFilename == null || queryFilename.trim().length() == 0
                || copyToFilename == null || copyToFilename.trim().length() == 0) {
            System.out.println(CONSOLE_ARG_MSG);
            System.out.println(CONSOLE_ERR_MSG);
            return;
        }
        
        String query;
        
        try {
            query = FileIo.readToString(queryFilename);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(CONSOLE_ERR_MSG);
            return;
        }
        
        Console cns = System.console();
        if(cns == null) {
            System.out.println("unable to initialize console - can not read password");
            System.out.println(CONSOLE_ERR_MSG);
        }
        
        System.out.println("Enter database password");
        char[] pass = cns.readPassword();
        System.out.println();
        
        DbFetch.pass = pass;
        
        try {
            
            File file = new File(copyToFilename);
            
            FileIo.writeToFile(file, DbFetch.fetch(connectionFilename, query));
            System.out.println("Query results copied: " + file.getAbsolutePath());
            
        } catch(Exception e) {
            e.printStackTrace();
            Popup.error("Exception while executing query", e);
        }
        
    }

}

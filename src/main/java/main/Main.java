package main;

import javax.swing.UIManager;

import ui.MainWindow;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        String connectArg = (args.length == 1) ? args[0] : "";
        
        //Show main window
        MainWindow mw = MainWindow.getInstance();
        
        if(mw.getActionBar() != null) {
            mw.getActionBar().setDbConnLocField(connectArg);
        }
        
        mw.setVisible(true);

    }

    
    
}

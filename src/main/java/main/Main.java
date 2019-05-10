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
        
        //Show main window
        MainWindow mw = MainWindow.getInstance();
        
        mw.setVisible(true);

    }

    
    
}

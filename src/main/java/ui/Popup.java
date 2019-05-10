package ui;

import javax.swing.JOptionPane;

public class Popup {

    public static void error(String title, Exception e) {
        JOptionPane.showMessageDialog(MainWindow.getInstance(),
                e.getMessage(),
                title,
                JOptionPane.ERROR_MESSAGE);
    }
    
    public static void error(String title, String message) {
        JOptionPane.showMessageDialog(MainWindow.getInstance(),
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }
    
}

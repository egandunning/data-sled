    package ui;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Popup {

    public static void error(String title, Exception e) {
        
        JTextArea msg = new JTextArea(e.getMessage());
        JOptionPane op = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
        msg.setBackground(op.getBackground());
        JDialog dialog = op.createDialog(MainWindow.getInstance(), title);
        dialog.setVisible(true);
    }
    
    public static void error(String title, String message) {
        
        JTextArea msg = new JTextArea(message);
        JOptionPane op = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);
        msg.setBackground(op.getBackground());
        JDialog dialog = op.createDialog(MainWindow.getInstance(), title);
        dialog.setVisible(true);
        
    }
    
    public static void success(String title, String message) {
        
        JTextArea msg = new JTextArea(message);
        JOptionPane op = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE);
        msg.setBackground(op.getBackground());
        JDialog dialog = op.createDialog(MainWindow.getInstance(), title);
        dialog.setVisible(true);
    }
}

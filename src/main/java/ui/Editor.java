package ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Editor extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextArea textArea;
    
    public Editor() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        textArea = new JTextArea();
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        setLayout(new BorderLayout());
        
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    public String getText() {
        return textArea.getText();
    }
}

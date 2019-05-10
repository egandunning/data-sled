package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private static MainWindow mw;
    private Editor editor;
    private ActionBar actionBar;
    
    /**
     * Get the instance of MainWindow. This ensures that other classes
     * can access the same instance.
     * @return
     */
    public static MainWindow getInstance() {
        
        if(mw != null) {
            return mw;
        }
        
        mw = new MainWindow();
        return mw;
    }
 
    /**
     * Initialize the window:
     * 
     * Set title, default close operation, default size
     */
    private MainWindow() {
        
        editor = new Editor();
        actionBar = new ActionBar();
        
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        
        mainPane.add(actionBar, BorderLayout.NORTH);
        mainPane.add(editor, BorderLayout.CENTER);
        
        
        add(mainPane, BorderLayout.CENTER);
        
        setTitle("Data Sled");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(700, 800);
    }
    
    public ActionBar getActionBar() {
        
        if(actionBar == null) {
            System.out.println("attempting to retrieve null ActionBar object");
        }
        
        return actionBar;
    }
    
    public static String getEditorText() {
        return getInstance().editor.getText();
    }
}

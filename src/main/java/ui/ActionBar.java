package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import workhorse.DbFetch;
import workhorse.FileIo;

public class ActionBar extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final String DB_CONN_LOC_LABEL = "JDBC connection location:";
    private static final String DB_CONN_LOC_NULL_MSG = "\"" + DB_CONN_LOC_LABEL + "\" is a required field. Enter the path to a DB connection file needs these properties:\n" + 
            "driverloc=<location of JDBC driver>\n" + 
            "classname=<JDBC driver class name. For example: org.postgresql.Driver>\n" + 
            "connstring=<JDBC connection string. Password can be parameterized with $PASSWORD>\"";
    private static final String DATA_SET_NAME_LABEL = "Data set name:";
    private static final String COPY_LOC_LABEL = "Copy to:";
    private static final String GO_BUTTON_LABEL = "Query & Copy";
    
    private String fileExt = ".psv";
    
    private JTextField dbConnLoc;
    private JTextField dataSetName;
    private JTextField copyLoc;
    private JButton goButton;
    
    
    public ActionBar() {
        setLayout(new GridBagLayout());
        
        dbConnLoc = new JTextField();
        dataSetName = new JTextField();
        copyLoc = new JTextField();
        
        goButton = new JButton(GO_BUTTON_LABEL);
        goButton.addActionListener(e -> goButtonPressed());
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        GridBagConstraints gbc2 = new GridBagConstraints();
        
        gbc1.gridx = 1;
        gbc1.gridy = 1;
        gbc1.weightx = 0;
        gbc1.ipadx = 2;
        gbc1.anchor = GridBagConstraints.EAST;
        
        gbc2.gridx = 2;
        gbc2.gridy = 1;
        gbc2.weightx = 1;
        gbc2.ipadx = 2;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        
        add(new Label(DB_CONN_LOC_LABEL, Label.RIGHT), gbc1);
        add(dbConnLoc, gbc2);
        gbc1.gridy++;
        gbc2.gridy++;
        add(new Label(DATA_SET_NAME_LABEL, Label.RIGHT), gbc1);
        add(dataSetName, gbc2);
        gbc1.gridy++;
        gbc2.gridy++;
        add(new Label(COPY_LOC_LABEL, Label.RIGHT), gbc1);
        add(copyLoc, gbc2);
        gbc1.gridx++;
        gbc2.gridy++;
        add(goButton, gbc2);
    }
    
    private void goButtonPressed() {

        String dbConnLocText = dbConnLoc.getText();
        String dataSetNameText = dataSetName.getText();
        String copyLocText = copyLoc.getText();
        String query = MainWindow.getEditorText();
        
        if(dbConnLocText == null || dbConnLocText.trim().length() == 0) {
            Popup.error("DB connection file not specified", DB_CONN_LOC_NULL_MSG);
            return;
        }
        
        if(dataSetNameText == null || dataSetNameText.trim().length() == 0) {
            dataSetNameText = "run_" + System.currentTimeMillis();
        }
        
        File dir;
        
        if(copyLocText == null || copyLocText.trim().length() == 0) {
            dir = new File("data");    
        } else {
            dir = new File(copyLocText);
        }
        
        try {
            
            if(!dir.exists()) {
                dir.mkdirs();
            }
            
            File file = new File(dir, dataSetNameText + fileExt);
            
            FileIo.writeToFile(file.getAbsolutePath(), DbFetch.fetch(dbConnLoc.getText(), query));
        } catch(Exception e) {
            e.printStackTrace();
            Popup.error("Exception while executing query", e);
        }
    }
    
    public String getDataSetName() {
        return dataSetName.getText();
    }
    
    public String getCopyLoc() {
        return copyLoc.getText();
    }
    
}

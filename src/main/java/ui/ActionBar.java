package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
    private static final String PASS_LABEL = "Database password:";
    private static final String GO_BUTTON_LABEL = "Query & Copy";
    
    private String fileExt = ".psv";
    
    private JTextField dbConnLocField;
    private JTextField dataSetNameField;
    private JTextField copyLocField;
    private JPasswordField passField;
    private JButton goButton;
    
    
    public ActionBar() {
        setLayout(new GridBagLayout());
        
        dbConnLocField = new JTextField();
        dataSetNameField = new JTextField();
        copyLocField = new JTextField();
        passField = new JPasswordField();
        
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
        
        add(new Label(DB_CONN_LOC_LABEL), gbc1);
        add(dbConnLocField, gbc2);
        gbc1.gridy++;
        gbc2.gridy++;
        add(new Label(DATA_SET_NAME_LABEL), gbc1);
        add(dataSetNameField, gbc2);
        gbc1.gridy++;
        gbc2.gridy++;
        add(new Label(COPY_LOC_LABEL), gbc1);
        add(copyLocField, gbc2);
        gbc1.gridy++;
        gbc2.gridy++;
        add(new Label(PASS_LABEL), gbc1);
        add(passField, gbc2);
        gbc1.gridy++;
        gbc2.gridy++;
        add(goButton, gbc2);
    }
    
    private void goButtonPressed() {
        
        String dbConnLocText = dbConnLocField.getText();
        String dataSetNameText = dataSetNameField.getText();
        String copyLocText = copyLocField.getText();
        String query = MainWindow.getEditorText();
        DbFetch.setPass(passField.getPassword());
        
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
            
            FileIo.writeToFile(file, DbFetch.fetch(dbConnLocField.getText(), query));
            Popup.success("Query results copied", file.getAbsolutePath());
            
        } catch(Exception e) {
            e.printStackTrace();
            Popup.error("Exception while executing query", e);
        }
 
    }
    
    public void setDbConnLocField(String connLocation) {
        
        if(dbConnLocField == null) {
            System.out.println("error - attempted to set db connection location before component was initialized");
            return;
        }
        
        dbConnLocField.setText(connLocation);
    }
    
    public String getDataSetName() {
        return dataSetNameField.getText();
    }
    
    public String getCopyLoc() {
        return copyLocField.getText();
    }
    
}

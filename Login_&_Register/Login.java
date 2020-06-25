package Login_Register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import DatabaseConnection.ConnectDB;
import StudentData.Student;
import OrganizerData.Organizer;
import AdminData.Admin;

class LoginForm extends JFrame implements ActionListener {  
    private JPanel container = (JPanel) getContentPane();
    private JLabel title = new JLabel("EXTRA CURRICULAR EVENTS MANAGEMENT SYSTEM");
    private JLabel nameLabel = new JLabel("NAME");
    private JLabel idLabel = new JLabel("ID");
    private JTextField nameTextField = new JTextField();
    private JTextField idTextField = new JTextField();
    private JButton loginButton = new JButton("LOGIN");
    private JLabel signUpLabel = new JLabel("Don't have an account? ");
    private JButton signUpButton = new JButton("Sign Up");
    private JLabel error = new JLabel("No connection.Try again after connecting.");
    private Connection conn;
    
    LoginForm() {
        try { 
            setLayoutManager();
            this.conn = new ConnectDB().getConnection(); 
            setLocationAndSize();
            addComponentsToContainer();
            addActionEvent();
        }
        catch(Exception e){ 
            JOptionPane.showMessageDialog(this, "Error connecting to database.\nPlease make sure the server is running and connected to the right database.");
            showError();
        }
        
    }
    
    public void showError(){
        error.setBounds(40,130,300,30);
        container.add(error);
    }
    
    public void setLayoutManager() {
        container.setLayout(null);
        container.setBackground(Color.CYAN);
    }

    public void setLocationAndSize() {
        title.setBounds(2,10,310,20);
        nameLabel.setBounds(50, 60, 50, 20);
        nameTextField.setBounds(100, 60, 150, 20);
        idLabel.setBounds(50, 120, 50, 20);
        idTextField.setBounds(100, 120, 150, 20);
        loginButton.setBounds(100, 180, 100, 20);
        signUpLabel.setBounds(20,240,150,20);
        signUpButton.setBounds(180,240,100,20);
    }

    public void addComponentsToContainer() {
        container.add(title);
        container.add(nameLabel);
        container.add(idLabel);
        container.add(nameTextField);
        container.add(idTextField);
        container.add(loginButton);
        container.add(signUpLabel);
        container.add(signUpButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        signUpButton.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String nameText;
            String idText;
            int idNum;
            nameText = nameTextField.getText();
            idText = idTextField.getText();
            idNum = Integer.parseInt(idText);
            String[] params = {idText, nameText};
            if(nameText.length() == 0 || idText.length() == 0 ){
                JOptionPane.showMessageDialog(this, "Fields cannot be blank!");
            }else{
                String query = "select designation from people where id=? and name=?";
                try{ 
                    PreparedStatement stmt = conn.prepareStatement(query); 
                    stmt.setInt(1,idNum);
                    stmt.setString(2,nameText);
                    ResultSet rs = stmt.executeQuery();
                    int designation = 0;
                    while(rs.next()) designation = rs.getInt("designation");
                    if(designation == 1){ Student.main(params); this.dispose();}
                    else if(designation == 2){ Organizer.main(params);this.dispose(); }
                    else if(designation == 3){ Admin.main(params);this.dispose(); }
                    else{
                        JOptionPane.showMessageDialog(this, "Invalid ID and Name!");
                    }
                    
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "Invalid ID and Name!");
                }
            }
        }
        
        if(e.getSource() == signUpButton){
            this.dispose();
            SignUp.main(new String[0]);
        }
    }
}

public class Login {
    public static void main(String[]args) {
        LoginForm frame= new LoginForm();
        frame.setTitle("Login");
        frame.setBounds(0, 0, 310, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
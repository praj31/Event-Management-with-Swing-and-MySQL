package Login_Register;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import DatabaseConnection.ConnectDB;

class SignUpForm extends JFrame implements ActionListener { 
    private JPanel container = (JPanel)getContentPane();
    private JLabel title = new JLabel("EXTRA CURRICULAR EVENTS MANAGEMENT SYSTEM");
    private JLabel nameLabel = new JLabel("NAME");
    private JLabel idLabel = new JLabel("ID");
    private JTextField nameTextField = new JTextField();
    private JTextField idTextField = new JTextField();
    private JButton signUpButton = new JButton("Sign Up");
    private Connection conn;
    SignUpForm() {
        this.conn = new ConnectDB().getConnection();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
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
        signUpButton.setBounds(100, 180, 100, 20);
    }

    public void addComponentsToContainer() {
        container.add(title);
        container.add(nameLabel);
        container.add(idLabel);
        container.add(nameTextField);
        container.add(idTextField);
        container.add(signUpButton);
    }

    public void addActionEvent() {
        signUpButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String query = "insert into people values(?,?,1)";
            try{
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1,Integer.parseInt(idTextField.getText()));
                stmt.setString(2,nameTextField.getText());
                int count = stmt.executeUpdate();
                if(count != 0){
                    JOptionPane.showMessageDialog(this, "Sign Up Successful!");
                    this.dispose();
                    Login.main(new String[0]);
                }
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error in Sign up!");
            }
        }
    }

}

public class SignUp {
    public static void main(String[]args) {
        SignUpForm frame = new SignUpForm();
        frame.setTitle("Signup");
        frame.setBounds(0, 0, 310, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
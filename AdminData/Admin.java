package AdminData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Login_Register.Login;

class AdminDashBoard extends JFrame implements ActionListener { 
    private JPanel container = (JPanel)getContentPane();
    private JLabel title;
    private JButton manageEvents = new JButton("Manage Events");
    private JButton promoteStudent = new JButton("Promote Student");
    private JButton demoteStudent = new JButton("Demote Student");
    private JButton logout = new JButton("Logout");
    private int id;
    AdminDashBoard(int id, String name) {
        title = new JLabel("Welcome "+name.toUpperCase()); 
        this.id = id;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        title.setBounds(10,10,180,30);
        manageEvents.setBounds(50,100,300,30);
        promoteStudent.setBounds(50,150,300,30);
        demoteStudent.setBounds(50,200,300,30);
        logout.setBounds(150,300,100,30);
    }

    public void addComponentsToContainer() {
        container.add(title);
        container.add(manageEvents);
        container.add(promoteStudent);
        container.add(demoteStudent);
        container.add(logout);
        container.setBackground(Color.CYAN);
    }

    public void addActionEvent() {
        manageEvents.addActionListener(this);
        promoteStudent.addActionListener(this);
        demoteStudent.addActionListener(this);
        logout.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == manageEvents){
            ManageEvents.main(new String[0]);    
        }
        if(e.getSource() == promoteStudent){
            PromoteStudent.main(new String[0]);
        }
        if(e.getSource() == demoteStudent){
            DemoteStudent.main(new String[0]);
        }
        if(e.getSource() == logout){
            try{ ManageEvents.close(); }
            catch(Exception ex){}
            try{ PromoteStudent.close(); }
            catch(Exception ex){}
            try{ DemoteStudent.close(); }
            catch(Exception ex){}
            this.dispose();
            Login.main(new String[0]);
        }
    }  
    
}

public class Admin{
    public static void main(String[]args){
        AdminDashBoard frame = new AdminDashBoard(Integer.parseInt(args[0]),args[1]);
        frame.setTitle("Extra-Curricular Events Management System");
        frame.setBounds(0, 0, 400, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
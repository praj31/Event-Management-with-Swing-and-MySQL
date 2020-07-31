package StudentData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Login_Register.Login;

class StudentDashBoard extends JFrame implements ActionListener { 
    private JPanel container = (JPanel)getContentPane();
    private JLabel title;
    private JButton upcomingEvents = new JButton("Upcoming Events");
    private JButton bookedEvents = new JButton("Booked Events");
    private JButton logout = new JButton("Logout");
    private int id;
    
    StudentDashBoard(int id, String name) {
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
        upcomingEvents.setBounds(50,100,300,30);
        bookedEvents.setBounds(50,150,300,30);
        logout.setBounds(150,300,100,30);
    }

    public void addComponentsToContainer() {
        container.add(title);
        container.add(upcomingEvents);
        container.add(bookedEvents);
        container.add(logout);
        container.setBackground(Color.CYAN);
    }

    public void addActionEvent() {
        upcomingEvents.addActionListener(this);
        bookedEvents.addActionListener(this);
        logout.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String param[] = { String.valueOf(id) };
        if(e.getSource() == upcomingEvents){
            UpcomingEvents.main(param);
        }
        if(e.getSource() == bookedEvents){
            BookedEvents.main(param);
        }
        if(e.getSource() == logout){
            try{ UpcomingEvents.close(); }
            catch(Exception ex){}
            try{ BookedEvents.close(); }
            catch(Exception ex){}
            this.dispose();
            Login.main(new String[0]);
        }
    }
}

public class Student{
    public static void main(String[]args){
        StudentDashBoard frame = new StudentDashBoard(Integer.parseInt(args[0]),args[1]);
        frame.setTitle("Extra-Curricular Events Management System");
        frame.setBounds(0, 0, 400, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
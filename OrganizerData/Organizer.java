package OrganizerData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Login_Register.Login;

class OrganizerDashBoard extends JFrame implements ActionListener { 
    private JPanel container = (JPanel)getContentPane();
    private JLabel title;
    private JButton addEvent = new JButton("Add Event");
    private JButton myEvents = new JButton("My Events");
    private JButton logout = new JButton("Logout");
    private int id;
    OrganizerDashBoard(int id, String name) {
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
        addEvent.setBounds(50,100,300,30);
        myEvents.setBounds(50,150,300,30);
        logout.setBounds(150,300,100,30);
    }

    public void addComponentsToContainer() {
        container.add(title);
        container.add(addEvent);
        container.add(myEvents);
        container.add(logout);
        container.setBackground(Color.CYAN);
    }

    public void addActionEvent() {
        addEvent.addActionListener(this);
        myEvents.addActionListener(this);
        logout.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String param[] = { String.valueOf(this.id) };
        if(e.getSource() == myEvents){
            MyEvents.main(param);
        }
        if(e.getSource() == addEvent){
            AddEvent.main(param);
        }
        if(e.getSource() == logout){
            this.dispose();
            Login.main(new String[0]);
        }
    }
}

public class Organizer{
    public static void main(String[]args){
        OrganizerDashBoard frame = new OrganizerDashBoard(Integer.parseInt(args[0]),args[1]);
        frame.setTitle("Extra-Curricular Events Management System");
        frame.setBounds(0, 0, 400, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
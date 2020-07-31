package StudentData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import DatabaseConnection.ConnectDB;

class BookedEventsPane extends JFrame implements ActionListener{
    private JPanel container = (JPanel) getContentPane();
    private JPanel eventList = new JPanel();
    private JPanel viewEvent = new JPanel();
    private JLabel title1 = new JLabel("Participated In");
    private JLabel title2 = new JLabel("View Event Details");
    private JLabel enterIdLabel = new JLabel("Enter EventID to view the event's details: ");
    private JTextField eventIdField = new JTextField();
    private JButton view = new JButton("View");
    private JButton reset = new JButton("Reset");
    private JButton exit = new JButton("Exit");
    private JButton cancel = new JButton("Cancel Booking");
    private Container eventDetails = new Container();
    private JSplitPane splitPane;
    private JScrollPane scrollPane;
    private int panelHeight = 500;
    private int itemHeight = 30;
    private int evidReceived=0;
    private Connection conn;
    private int id;
    BookedEventsPane(int id){
        this.conn = new ConnectDB().getConnection();
        this.id = id;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        getMyEvents();
    }
    
    public void setLayoutManager(){
        eventList.setLayout(null);
        viewEvent.setLayout(null);
        eventDetails.setLayout(null);
    }
    
    public void setLocationAndSize(){
        eventList.setPreferredSize(new Dimension(250,panelHeight));
        viewEvent.setPreferredSize(new Dimension(550,600));
        title2.setBounds(200,5,250,20);
        enterIdLabel.setBounds(10,30,400,20);
        eventIdField.setBounds(10,50,250,20);
        view.setBounds(270,50,80,20);
        reset.setBounds(360,50,80,20);
        eventDetails.setBounds(10,10,500,400);
        exit.setBounds(450,550,80,20);
    }
    
    public void addActionEvent(){
        view.addActionListener(this);
        reset.addActionListener(this);
        cancel.addActionListener(this);
        exit.addActionListener(this);
    }
    
    public void addComponentsToContainer(){
        eventList.add(title1);
        viewEvent.add(title2);
        viewEvent.add(enterIdLabel);
        viewEvent.add(eventIdField);
        viewEvent.add(view);
        viewEvent.add(reset);
        viewEvent.add(exit);
        scrollPane = new JScrollPane(eventList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(320,500));
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, scrollPane, viewEvent);
        splitPane.setDividerLocation(0.32);
        splitPane.setResizeWeight(0.32);
        container.add(splitPane);
        eventList.setBackground(Color.CYAN);
        viewEvent.setBackground(Color.CYAN);
    }

    public void getMyEvents(){
        title1.setBounds(80,5,320,20);
        eventList.add(title1);
        String query = "select evid from bookedBy where id=?";
        try{ 
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                getEvent(rs.getInt("evid"));
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Occurred.");
        }
    }
    
    public void getEvent(int evid){
        String query = "select evid, eventTitle from events where evid=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,evid);
            ResultSet rs = stmt.executeQuery();
            String eventToList = "";
            while(rs.next()){
                eventToList = String.valueOf(rs.getInt("evid")) + " - " + rs.getString("eventTitle");
                putEventToList(eventToList);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error occurred in fetching event from database");
            putEventToList(" -- Error --");
        }
    }
    
    public void putEventToList(String eventToPut){
        JLabel item = new JLabel(eventToPut);
        item.setBounds(10,itemHeight,310,20);
        itemHeight+=20;
        if(itemHeight > panelHeight){
            panelHeight+=20;
            eventList.setPreferredSize(new Dimension(320,panelHeight));
            eventList.revalidate();
        }
        eventList.add(item);
        eventList.repaint();
        container.repaint();
    }
    
    public void getEventDetails(int evidReceived){
        eventDetails.removeAll();
        viewEvent.repaint();
        String query = "select eventTitle, eventDescription, eventType, eventTypeData, repeating, eventDate, eventTime, numParticipants from events where evid=?";
        String eventTitle="",eventDescription="",eventType="",eventTypeData="",eventDate="",eventTime="", repeating="", numParticipants="";
        this.evidReceived = evidReceived;
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,evidReceived);
            ResultSet rs = stmt.executeQuery();
            if(rs.next() == false){
                JOptionPane.showMessageDialog(this, "Invalid EventID. No such event.");
                this.evidReceived = 0;
            }else{
                do{
                    eventTitle = rs.getString("eventTitle");
                    eventDescription = rs.getString("eventDescription");
                    eventType = rs.getString("eventType");
                    eventTypeData = rs.getString("eventTypeData");
                    repeating = rs.getString("repeating");
                    eventDate = rs.getDate("eventDate").toString();
                    eventTime = rs.getTime("eventTime").toString();
                    numParticipants = String.valueOf(rs.getInt("numParticipants"));
                }while(rs.next());
                JLabel eTitle = new JLabel("Event Title: "+eventTitle);
                eTitle.setBounds(5,60,500,20);
                JLabel eDescription = new JLabel("Event Description: ");
                eDescription.setBounds(5,80,110,20);
                JTextArea description = new JTextArea(3,350);
                description.setEditable(false);
                description.setLineWrap(true);
                description.setText(eventDescription);
                JScrollPane descriptionContainer = new JScrollPane(description, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                descriptionContainer.setBounds(120,80,350,50);
                JLabel eType = new JLabel("Event Type: "+ ((eventType.equals("O"))?"Online":"Physical"));
                eType.setBounds(5,140,500,20);
                JLabel eTypeData = new JLabel("At: "+eventTypeData);
                eTypeData.setBounds(5,160,500,20);
                JLabel eRepeat = new JLabel("Repeat: "+repeating);
                eRepeat.setBounds(5,180,500,20);
                JLabel eDate = new JLabel("Event Date: "+eventDate);
                eDate.setBounds(5,200,500,20);
                JLabel eTime = new JLabel("Event Time: "+eventTime+ "(24-Hour Format)");
                eTime.setBounds(5,220,500,20);
                JLabel eParticipants = new JLabel("No. of participants: "+numParticipants);
                eParticipants.setBounds(5,240,500,20);
                cancel.setBounds(5,300,300,20);
                eventDetails.add(eTitle);
                eventDetails.add(eDescription);
                eventDetails.add(descriptionContainer);
                eventDetails.add(eType);
                eventDetails.add(eTypeData);
                eventDetails.add(eRepeat);
                eventDetails.add(eDate);
                eventDetails.add(eTime);
                eventDetails.add(eParticipants);
                eventDetails.add(cancel);
                viewEvent.add(eventDetails);
                viewEvent.repaint();
                descriptionContainer.validate();
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error in fetching event data.");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view){
            if(eventIdField.getText().length() == 0){
                JOptionPane.showMessageDialog(this, "Field cannot be blank!");
            }
            else getEventDetails(Integer.parseInt(eventIdField.getText()));
        }
        if(e.getSource() == reset){
            eventIdField.setText("");
            eventDetails.removeAll();
            viewEvent.repaint();
        }
        if(e.getSource() == cancel){
            String query = "delete from bookedBy where id=? and evid=?";
            try{ 
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1,id);
                stmt.setInt(2,evidReceived);
                int count = stmt.executeUpdate();
                if(count!=0){
                    JOptionPane.showMessageDialog(this, "Event Booking Deleted Successfully!");
                }
                else JOptionPane.showMessageDialog(this, "Error in deleting booking. Have you participated?");
                eventList.removeAll();
                eventIdField.setText("");
                eventDetails.removeAll();
                itemHeight=30;
                evidReceived = 0;
                viewEvent.repaint();
                eventList.repaint();
                getMyEvents();
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error in deleting booking.");
            }
        }
        if(e.getSource() == exit) this.dispose();
    } 
    
}

public class BookedEvents{
    static BookedEventsPane frame;
    public static void main(String[]args){
        frame = new BookedEventsPane(Integer.parseInt(args[0]));
        frame.setTitle("Extra-Curricular Events Management System");
        frame.setBounds(0, 0, 800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void close(){ frame.dispose(); }
}
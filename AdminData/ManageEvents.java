package AdminData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import DatabaseConnection.ConnectDB;

class ManageEventsPane extends JFrame implements ActionListener{
    private JPanel container = (JPanel) getContentPane();
    private JPanel eventList = new JPanel();
    private JPanel viewEvent = new JPanel();
    private JLabel title1 = new JLabel("Event List");
    private JLabel title2 = new JLabel("View Event Details");
    private JLabel enterIdLabel = new JLabel("Enter EventID to view the event's details: ");
    private JTextField eventIdField = new JTextField();
    private JButton view = new JButton("View");
    private JButton reset = new JButton("Reset");
    private JButton cancel = new JButton("Cancel Event");
    private JButton delete  = new JButton("Delete Booking");
    private JTextField removeBookingOf = new JTextField();
    private JButton exit = new JButton("Exit");
    private Container eventDetails = new Container();
    private JScrollPane participantsDetailsContainer;
    private JTextArea participantsList = new JTextArea(5,250);
    private JSplitPane splitPane;
    private JScrollPane scrollPane;
    private int panelHeight = 500;
    private int itemHeight = 30;
    private int evidReceived=0;
    private int idToDelete = 0;
    private Connection conn;

    ManageEventsPane(){
        this.conn = new ConnectDB().getConnection();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        getEvents();
    }
    
    
    public void setLayoutManager() {
        eventList.setLayout(null);
        viewEvent.setLayout(null);
        eventDetails.setLayout(null);
    }

    public void setLocationAndSize() {
        eventList.setPreferredSize(new Dimension(250,panelHeight));
        viewEvent.setPreferredSize(new Dimension(550,600));
        title1.setBounds(100,5,320,20);
        title2.setBounds(200,5,250,20);
        enterIdLabel.setBounds(10,30,400,20);
        eventIdField.setBounds(10,50,250,20);
        view.setBounds(270,50,80,20);
        reset.setBounds(360,50,80,20);
        eventDetails.setBounds(10,10,500,550);
        exit.setBounds(450,550,80,20);
        eventList.setBackground(Color.CYAN);
        viewEvent.setBackground(Color.CYAN);
    }

    public void addComponentsToContainer() {
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
    }

    public void addActionEvent() {
        view.addActionListener(this);
        reset.addActionListener(this);
        cancel.addActionListener(this);
        exit.addActionListener(this);
        delete.addActionListener(this);
    }

    public void getEvents(){
        String query = "select evid, eventTitle from events";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
                JLabel participantsLabel = new JLabel("Participants List:");
                participantsLabel.setBounds(5,260,500,20);
                participantsList.setLineWrap(true);
                participantsList.setEditable(false);
                participantsDetailsContainer = new JScrollPane(participantsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                participantsDetailsContainer.setBounds(5,280,250,120);
                JLabel deleteParticipants = new JLabel("Enter student ID to remove his/her booking for this event: ");
                deleteParticipants.setBounds(5,400,350,20);
                removeBookingOf.setBounds(340,400,100,20);
                delete.setBounds(5,420,200,20);
                cancel.setBounds(5,500,300,20);
                eventDetails.add(eTitle);
                eventDetails.add(eDescription);
                eventDetails.add(descriptionContainer);
                eventDetails.add(eType);
                eventDetails.add(eTypeData);
                eventDetails.add(eRepeat);
                eventDetails.add(eDate);
                eventDetails.add(eTime);
                eventDetails.add(eParticipants);
                eventDetails.add(participantsLabel);
                eventDetails.add(participantsDetailsContainer);
                eventDetails.add(deleteParticipants);
                eventDetails.add(removeBookingOf);
                eventDetails.add(cancel);
                eventDetails.add(delete);
                viewEvent.add(eventDetails);
                fetchParticipantsList();
                viewEvent.repaint();
                descriptionContainer.validate();
                participantsDetailsContainer.validate();
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error in fetching event data.");
        }
    }

    public void fetchParticipantsList(){
        participantsList.setText("");
        String fetchParticipants1 = "select id from bookedBy where evid=?";
        String fetchParticipants2 = "select id,name from people where id=?";
        try{
            PreparedStatement stmt1= conn.prepareStatement(fetchParticipants1);
            stmt1.setInt(1,evidReceived);
            ResultSet rse1 = stmt1.executeQuery();
            while(rse1.next()){
                PreparedStatement stmt2 = conn.prepareStatement(fetchParticipants2);
                stmt2.setInt(1,rse1.getInt("id"));
                ResultSet rse2 = stmt2.executeQuery();
                while(rse2.next()){
                    participantsList.append(rse2.getInt("id")+" - "+rse2.getString("name")+"\n");
                    participantsList.repaint();
                }
            }
        }
        catch(SQLException e){}
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
            String query1 = "delete from organizedBy where evid=?";
            String query2 = "delete from events where evid=?";
            String query3 = "delete from bookedBy where evid=?";
            try{ 
                PreparedStatement stmt1 = conn.prepareStatement(query1);
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                PreparedStatement stmt3 = conn.prepareStatement(query3);
                stmt1.setInt(1,evidReceived);
                stmt2.setInt(1,evidReceived);
                stmt3.setInt(1,evidReceived);
                int count1 = stmt1.executeUpdate();
                if(count1!=0){
                    int count2 = stmt2.executeUpdate();
                    stmt3.executeUpdate();
                    if(count2!=0)
                        JOptionPane.showMessageDialog(this, "Event Deleted Successfully!");
                    }
                else JOptionPane.showMessageDialog(this, "Error in deleting event. Here.");
                eventList.removeAll();
                eventIdField.setText("");
                eventDetails.removeAll();
                itemHeight=30;
                evidReceived = 0;
                viewEvent.repaint();
                eventList.repaint();
                getEvents();
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error in deleting event.");
            }
        }
        if(e.getSource() == delete){
            String toDelete  = removeBookingOf.getText();
            if(toDelete.length() == 0){
                JOptionPane.showMessageDialog(this, "Enter valid ID to delete.");
            }else{
                String query1 = "delete from bookedBy where id=? and evid=?";
                this.idToDelete = Integer.parseInt(toDelete);
                try{
                    PreparedStatement stmt = conn.prepareStatement(query1);
                    stmt.setInt(1,idToDelete);
                    stmt.setInt(2,evidReceived);
                    int count = stmt.executeUpdate();
                    if(count != 0)
                        JOptionPane.showMessageDialog(this, "Booking Deleted.");
                    else
                        JOptionPane.showMessageDialog(this, "Error in deleting booking.");
                    fetchParticipantsList();
                    participantsList.repaint();
                    participantsDetailsContainer.validate();
                }
                catch(SQLException ex){}
            }
        }
        if(e.getSource() == exit){
            this.dispose();
        }
    }  
    
}

public class ManageEvents{
    static ManageEventsPane frame;
    public static void main(String[] args) {
        frame = new ManageEventsPane();
        frame.setTitle("Extra-Curricular Events Management System");
        frame.setBounds(0, 0, 800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);  
    }
    
    public static void close(){ frame.dispose(); }
}
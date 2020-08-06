package OrganizerData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import DatabaseConnection.ConnectDB;

class EditEvent extends JFrame implements ActionListener{
    private JPanel container = (JPanel)getContentPane();   
    private JLabel title = new JLabel("Change Event");
    private JLabel eTitle = new JLabel("Event Title:");
    private JTextField eTitleField = new JTextField();
    private JLabel eDescription = new JLabel("Event Description (256 characters):");
    private JScrollPane descriptionArea;
    private JTextArea eDescriptionField = new JTextArea(3,300);
    private JLabel eType = new JLabel("Event Type:");    
    private JRadioButton online = new JRadioButton("Online");
    private JRadioButton physical = new JRadioButton("Physical");
    private ButtonGroup eTypeOptions = new ButtonGroup();
    private JLabel eTypeData = new JLabel("At:");
    private JTextField eTypeDataField = new JTextField();
    private JLabel eDate = new JLabel("Event Date:");
    private JLabel yearLabel = new JLabel(" YYYY ");
    private JLabel monthLabel = new JLabel(" MM  ");
    private JLabel dayLabel = new JLabel(" DD");
    private JLabel hourLabel = new JLabel(" HH ");
    private JLabel minutesLabel = new JLabel(" MM");
    private JComboBox year, month, day, hour, minutes, untilYear, untilMonth, untilDay;
    private JLabel eTime = new JLabel("Event Time:");
    private JLabel eParticipants = new JLabel("No. of participants:");
    private JTextField eParticipantsField = new JTextField("0");
    private JLabel eRepeating = new JLabel("Event Repeat:");
    private JComboBox repeating; 
    private JTextField everyCount = new JTextField("1");
    private JLabel suffix = new JLabel();
    private JPanel repeatContainer = new JPanel();
    private JButton edit = new JButton("Update Changes");
    private JLabel warning = new JLabel("Fill in all the fields appropriately.");
    private ArrayList<String> yearsList = new ArrayList<String>();
    private ArrayList<String> monthsList = new ArrayList<String>();
    private ArrayList<String> daysList = new ArrayList<String>();
    private ArrayList<String> hoursList = new ArrayList<String>();
    private ArrayList<String> minutesList = new ArrayList<String>(); 
    private Connection conn;
    private int evid = 0;

    EditEvent(int evid){
        setTitle("Extra-Curricular Events Management System");
        setBounds(0, 0, 400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        this.evid = evid;
        this.conn = new ConnectDB().getConnection();
        setLayoutManagerAndOptions();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        fetchEventData();
    }

    
    public void setLayoutManagerAndOptions() {
        container.setLayout(null);
        repeatContainer.setLayout(null);
        eDescriptionField.setLineWrap(true);
        descriptionArea = new JScrollPane(eDescriptionField, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        for(int i=2020;i<=2025;i++) yearsList.add(String.valueOf(i));
        for(int i=1;i<=12;i++) monthsList.add(String.format("%02d",i));
        for(int i=1;i<=31;i++) daysList.add(String.format("%02d",i));
        for(int i=1;i<=24;i++) hoursList.add(String.format("%02d",i));
        for(int i=0;i<=59;i++) minutesList.add(String.format("%02d",i)); 
        String repeatOption[] = {"Never","Daily","Weekly","Monthly"};   
        year = new JComboBox(yearsList.toArray(new String[yearsList.size()]));
        month = new JComboBox(monthsList.toArray(new String[monthsList.size()]));
        day = new JComboBox(daysList.toArray(new String[daysList.size()]));
        hour = new JComboBox(hoursList.toArray(new String[hoursList.size()]));
        minutes = new JComboBox(minutesList.toArray(new String[minutesList.size()]));
        repeating = new JComboBox(repeatOption);
        untilYear = new JComboBox(yearsList.toArray(new String[yearsList.size()]));
        untilMonth = new JComboBox(monthsList.toArray(new String[monthsList.size()]));
        untilDay = new JComboBox(daysList.toArray(new String[daysList.size()]));
        container.setBackground(Color.CYAN);
        repeatContainer.setBackground(Color.CYAN);
    }

    public void setLocationAndSize() {
        title.setBounds(150,5,100,20);
        eTitle.setBounds(50,30,300,20);
        eTitleField.setBounds(50,50,300,20);
        eDescription.setBounds(50,70,300,20);
        descriptionArea.setBounds(50,90,300,60);
        eType.setBounds(50,150,300,20);
        online.setBounds(50,170,100,20);
        physical.setBounds(150,170,100,20);
        eTypeData.setBounds(50,190,300,20);
        eTypeDataField.setBounds(50,210,300,20);
        eDate.setBounds(50,230,300,20);
        year.setBounds(50,250,60,20);
        yearLabel.setBounds(110,250,50,20);
        month.setBounds(160,250,60,20);
        monthLabel.setBounds(220,250,50,20);
        day.setBounds(270,250,60,20);
        dayLabel.setBounds(330,250,50,20);
        eTime.setBounds(50,270,300,20);
        hour.setBounds(50,290,60,20);
        hourLabel.setBounds(110,290,50,20);
        minutes.setBounds(160,290,60,20);
        minutesLabel.setBounds(220,290,50,20);
        eParticipants.setBounds(50,310,300,20);
        eParticipantsField.setBounds(50,330,300,20);
        eRepeating.setBounds(50,360,80,20);
        repeating.setBounds(130,360,100,20);
        repeatContainer.setBounds(50,390,300,100);
        edit.setBounds(100,500,200,20);
        warning.setBounds(110,520,300,20);
    }

    public void addComponentsToContainer() {
        container.add(title);
        container.add(eTitle);
        container.add(eTitleField);
        container.add(eDescription);
        container.add(descriptionArea);
        container.add(eType);
        eTypeOptions.add(online);
        eTypeOptions.add(physical);
        container.add(online);
        container.add(physical);
        container.add(eTypeData);
        container.add(eTypeDataField);
        container.add(eDate);
        container.add(year);
        container.add(yearLabel);
        container.add(month);
        container.add(monthLabel);
        container.add(day);
        container.add(dayLabel);
        container.add(eTime);
        container.add(hour);
        container.add(hourLabel);
        container.add(minutes);
        container.add(minutesLabel);
        container.add(eParticipants);
        container.add(eParticipantsField);
        container.add(eRepeating);
        container.add(repeating);
        container.add(repeatContainer);
        container.add(edit);
        container.add(warning);
        repeatContainer.repaint();
    }

    public void addActionEvent() {
        repeating.addActionListener(this);
        edit.addActionListener(this);
    }
    
    public void fetchEventData(){
        String titleData="", descriptionData="",typeOfData="", typeData="", dateData="", timeData="", repeatingData="";
        int participantsData = 0;
        String query = "select * from events where evid=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,this.evid);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                titleData = rs.getString("eventTitle");
                descriptionData = rs.getString("eventDescription");
                typeOfData = rs.getString("eventType");
                typeData = rs.getString("eventTypeData");
                dateData = rs.getString("eventDate");
                timeData = rs.getString("eventTime");
                participantsData = rs.getInt("numParticipants");
                repeatingData = rs.getString("repeating");
            }
        }catch(SQLException e){}
        eTitleField.setText(titleData);
        eDescriptionField.setText(descriptionData);
        if(typeOfData.charAt(0) == 'P') physical.setSelected(true);
        else online.setSelected(true);
        eTypeDataField.setText(typeData);
        year.setSelectedItem(dateData.substring(0,4));
        month.setSelectedItem(dateData.substring(5,7));
        day.setSelectedItem(dateData.substring(8));
        hour.setSelectedItem(timeData.substring(0,2));
        minutes.setSelectedItem(timeData.substring(3,5));
        eParticipantsField.setText(String.valueOf(participantsData));
        repeatingSettings(repeatingData);
        container.repaint();
    }

    public void repeatingSettings(String data){
        if(data.equals("Never")) repeating.setSelectedItem("Never");
        else{
            String destructure[] = data.split(" ");
            String selectedOption = destructure[0].substring(0,destructure[0].length()-1);
            repeating.setSelectedItem(selectedOption);
            everyCount.setText(destructure[2]);
            suffix.setText(" " + destructure[3]); 
            untilYear.setSelectedItem(destructure[5].substring(0,4));
            untilMonth.setSelectedItem(destructure[5].substring(5,7));           
            untilDay.setSelectedItem(destructure[5].substring(8));            
            JLabel prefix = new JLabel("Every "); 
            JLabel until = new JLabel("Until: ");
            JLabel untilYearLabel = new JLabel(" YYYY");
            JLabel untilMonthLabel = new JLabel(" MM ");
            JLabel untilDayLabel = new JLabel(" DD ");
            prefix.setBounds(5,5,50,20);
            everyCount.setBounds(55,5,40,20);
            suffix.setBounds(95,5,70,20);
            until.setBounds(5,30,45,20);
            untilYear.setBounds(50,30,60,20);
            untilYearLabel.setBounds(110,30,60,20);
            untilMonth.setBounds(150,30,40,20);
            untilMonthLabel.setBounds(190,30,40,20);
            untilDay.setBounds(230,30,40,20);
            untilDayLabel.setBounds(270,30,40,20);
            repeatContainer.add(prefix);
            repeatContainer.add(everyCount);
            repeatContainer.add(suffix);
            repeatContainer.add(until);
            repeatContainer.add(untilYear);
            repeatContainer.add(untilYearLabel);
            repeatContainer.add(untilMonth);
            repeatContainer.add(untilMonthLabel);
            repeatContainer.add(untilDay);
            repeatContainer.add(untilDayLabel);
            repeatContainer.validate();
            repeatContainer.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        repeatContainer.removeAll();
        if(e.getSource() == repeating){
            suffix.setText("");
            JComboBox cb = (JComboBox)e.getSource();
            String choice = (String)cb.getSelectedItem();
            JLabel prefix = new JLabel("Every ");  
            JLabel until = new JLabel("Until: ");
            JLabel untilYearLabel = new JLabel(" YYYY");
            JLabel untilMonthLabel = new JLabel(" MM ");
            JLabel untilDayLabel = new JLabel(" DD ");
            prefix.setBounds(5,5,50,20);
            everyCount.setBounds(55,5,40,20);
            suffix.setBounds(95,5,70,20);
            until.setBounds(5,30,45,20);
            untilYear.setBounds(50,30,60,20);
            untilYearLabel.setBounds(110,30,60,20);
            untilMonth.setBounds(150,30,40,20);
            untilMonthLabel.setBounds(190,30,40,20);
            untilDay.setBounds(230,30,40,20);
            untilDayLabel.setBounds(270,30,40,20);
            if(!choice.equals("Never")){
                if(choice.equals("Daily")){
                    suffix.setText(" day(s)");
                }else if(choice.equals("Weekly")){
                    suffix.setText(" week(s)");
                }else if(choice.equals("Monthly")){
                    suffix.setText(" month(s)");
                }
                repeatContainer.add(prefix);
                repeatContainer.add(everyCount);
                repeatContainer.add(suffix);
                repeatContainer.add(until);
                repeatContainer.add(untilYear);
                repeatContainer.add(untilYearLabel);
                repeatContainer.add(untilMonth);
                repeatContainer.add(untilMonthLabel);
                repeatContainer.add(untilDay);
                repeatContainer.add(untilDayLabel);
            }
            repeatContainer.repaint();
            container.revalidate();
        }
        if(e.getSource() == edit){
            String query = "update events set eventTitle=?,eventDescription=?,eventType=?,eventTypeData=?,eventDate=?,eventTime=?,numParticipants=?,repeating=? where evid=?";
            String type = "";
            if(online.isSelected()) type = "O";
            else if(physical.isSelected()) type = "P";
            String enteredDate = (String)year.getSelectedItem() + "-" + (String)month.getSelectedItem() + "-" + (String)day.getSelectedItem();
            String enteredTime = (String)hour.getSelectedItem() + ":" + (String)minutes.getSelectedItem();
            String untilDate = (String)untilYear.getSelectedItem() + "-" + (String)untilMonth.getSelectedItem() + "-" + (String)untilDay.getSelectedItem();
            String repeatData = "";
            if(((String)repeating.getSelectedItem()).equals("Never")) repeatData = "Never";
            else{
                repeatData = (String)repeating.getSelectedItem() + ", Every ";
                repeatData+= (String)everyCount.getText();
                repeatData+= (String)suffix.getText() + " ";
                repeatData+= "until "+untilDate;
            }
            try{
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1,eTitleField.getText());
                stmt.setString(2,eDescriptionField.getText());
                stmt.setString(3,type);
                stmt.setString(4,eTypeDataField.getText());
                stmt.setString(5,enteredDate);
                stmt.setString(6,enteredTime);
                stmt.setInt(7,Integer.parseInt(eParticipantsField.getText()));
                stmt.setString(8,repeatData);
                stmt.setInt(9,this.evid);
                int count = stmt.executeUpdate();
                if(count != 0 ){ 
                    JOptionPane.showMessageDialog(this, "Event updated successfully!");
                    this.dispose();
                }
            }
            catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error. Check data fields and then try again.");
            }
        }
    }

    public static void main(String[] args) {
        new EditEvent(111222);
    }
}
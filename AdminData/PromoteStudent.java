package AdminData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import DatabaseConnection.ConnectDB;

class PromoteStudentPane extends JFrame implements ActionListener{
    private JPanel container = (JPanel) getContentPane();
    private JPanel studentList = new JPanel();
    private JPanel promoteWindow = new JPanel();
    private JLabel title1 = new JLabel("Student List");
    private JLabel title2 = new JLabel("Promote Student");
    private JLabel studentIDLabel = new JLabel("Enter Student ID to promote:");
    private JTextField studentIDField = new JTextField();
    private JButton promote = new JButton("Promote");
    private JButton exit = new JButton("Exit");
    private JSplitPane splitPane;
    private JScrollPane scrollPane;
    private int panelHeight = 300;
    private int itemHeight = 20;
    private int idReceived=0;
    private Connection conn;
    
    PromoteStudentPane(){
        this.conn = new ConnectDB().getConnection();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        getStudentList();
    }

    public void setLayoutManager() {
        studentList.setLayout(null);
        promoteWindow.setLayout(null);
    }

    public void setLocationAndSize(){
        studentList.setPreferredSize(new Dimension(200,panelHeight));
        promoteWindow.setPreferredSize(new Dimension(300,400));
        title1.setBounds(80,5,100,20);
        title2.setBounds(90,5,100,20);
        studentIDLabel.setBounds(10,100,200,20);
        studentIDField.setBounds(10,120,200,20);
        promote.setBounds(10,200,200,20);
        exit.setBounds(190,350,70,20);
    }

    public void addActionEvent(){
        promote.addActionListener(this);
        exit.addActionListener(this);
    }

    public void addComponentsToContainer(){
        promoteWindow.add(title2);
        promoteWindow.add(studentIDLabel);
        promoteWindow.add(studentIDField);
        promoteWindow.add(promote);
        promoteWindow.add(exit);
        scrollPane = new JScrollPane(studentList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(200,500));
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, scrollPane, promoteWindow);
        splitPane.setDividerLocation(0.45);
        splitPane.setResizeWeight(0.45);
        container.add(splitPane);
        studentList.setBackground(Color.CYAN);
        promoteWindow.setBackground(Color.CYAN);
    }

    public void getStudentList(){
        studentList.add(title1);
        studentList.repaint();
        String query = "select id, name from people where designation=1";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String eventToList = "";
            while(rs.next()){
                eventToList = String.valueOf(rs.getInt("id")) + " - " + rs.getString("name");
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
        item.setBounds(10,itemHeight,200,20);
        itemHeight+=20;
        if(itemHeight > panelHeight){
            panelHeight+=20;
            studentList.setPreferredSize(new Dimension(200,panelHeight));
            studentList.revalidate();
        }
        studentList.add(item);
        studentList.repaint();
        container.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == promote){
            String query = "update people set designation=2 where id=? and designation=1";
            String idField = studentIDField.getText();
            if(idField.length() == 0){
                JOptionPane.showMessageDialog(this, "Field cannot be empty!");
                this.idReceived=0;
            }else{
                this.idReceived = Integer.parseInt(idField);
                try{
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setInt(1,idReceived);
                    int count = stmt.executeUpdate();
                    if(count != 0){ 
                        JOptionPane.showMessageDialog(this, "Student Promoted!");
                        studentList.removeAll();
                        itemHeight = 20;
                        idReceived = 0;
                        getStudentList();
                    }else{
                        JOptionPane.showMessageDialog(this, "Invalid Student ID!");
                    }
                    studentIDField.setText("");
                }
                catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "Invalid Student ID. Error encountered.");
                }
            } 
        }
        if(e.getSource() == exit) this.dispose();
    }
}

public class PromoteStudent{
    static PromoteStudentPane frame;
    public static void main(String[]args){
        frame = new PromoteStudentPane();
        frame.setTitle("Extra-Curricular Events Management System");
        frame.setBounds(0, 0, 500, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }    
    
    public static void close(){ frame.dispose(); }
}
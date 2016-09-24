/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koryo;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ARMAND
 */
public class Payment {
    private int id,playerID;
    private String pay,serviceCategory,service;
    private double amt_to_pay,paid;
    private Date recording_Date;

    public Payment() {
    }

    

    public Payment(int id, int playerID, String pay, String serviceCategory, double amt_to_pay, double paid, Date recording_Date,String service) {
        this.id = id;
        this.playerID = playerID;
        this.pay = pay;
        this.serviceCategory = serviceCategory;
        this.amt_to_pay = amt_to_pay;
        this.paid = paid;
        this.recording_Date = recording_Date;
        this.service=service;
    }

  
    public static List<Payment> getPlayerPayment(String id){
        List<Payment> l = new ArrayList<Payment>();
        try{
        Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst=con.prepareStatement("select * from servicepayable where ID="+id+"");
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                Payment pp=new Payment();
                pp.setId(rs.getInt(1));
                pp.setPlayerID(rs.getInt(2));
                pp.setServiceCategory(rs.getString(3));
                pp.setPay(rs.getString(4));
                pp.setAmt_to_pay(rs.getDouble(5));
                pp.setPaid(rs.getDouble(6));
                pp.setRecording_Date(rs.getDate(7));
                pp.setService(rs.getString(8));
                l.add(pp);
            }
        }catch(Exception e){
            
        }
        return l;
    }
//=========================================================
    public static int getMaxID() {
        int id = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("select * from servicepayable");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id + 1;
    }
    
//========================================================
    public boolean insert() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("insert into servicepayable values(?,?,?,?,?,?,?,?)");
            pst.setInt(1, id);
            pst.setInt(2,playerID);
            pst.setString(3, serviceCategory);
            pst.setString(4, pay);
            pst.setDouble(5, amt_to_pay);
            pst.setDouble(6, paid);
            pst.setDate(7, new java.sql.Date(recording_Date.getTime()));
            pst.setString(8, service);
//            JOptionPane.showMessageDialog(null, "Inserted Successfully!!");
            pst.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
//========================================================
    public boolean update() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("update servicepayable set AmountPaid=paid+"+paid+" where id=?");
            pst.setInt(1, id);
            
            pst.execute();
            //JOptionPane.showMessageDialog(null, "PLAYER UPDATED SUCCESSULLY!!");
            return true;
        } catch (Exception ex) {
            //ex.printStackTrace();
            //JOptionPane.showMessageDialog(null, ex, "ERROR",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
//========================================================
     public boolean updateAll() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("update servicepayable set AmountToPay=?,AmountPaid=? where id=?");
            pst.setInt(3, id);
            pst.setDouble(1, amt_to_pay);
            pst.setDouble(2, paid);
            pst.execute();
            //JOptionPane.showMessageDialog(null, "PLAYER UPDATED SUCCESSULLY!!");
            return true;
        } catch (Exception ex) {
            //ex.printStackTrace();
            //JOptionPane.showMessageDialog(null, ex, "ERROR",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
//========================================================    
    public static Object[][]PaymentView(){
   List<Payment>l=Payment.getPaymentList();
   int no=0;
   Object[][]v=new Object[l.size()][8];
   for(int i=0;i<l.size();i++){
       no++;
      v[i][0]=no;
        int pid=l.get(i).getPlayerID();
        String pids=pid+"";
        Player pp=Player.getPlayer(pids);
        v[i][1]=pp.getFirstname()+" "+pp.getLastname();
        //v[i][1]=(l.get(i).getPlayerId());
        //v[i][2]=l.get(i).getServiceCategory();
        v[i][2]= l.get(i).getAmt_to_pay()+" "+"Frw";
        v[i][3]=l.get(i).getPaid()+" "+"Frw";
        double a=l.get(i).getAmt_to_pay();
        double b=l.get(i).getPaid();
         v[i][4]=a-b+" "+"Frw";
         v[i][5]=l.get(i).getRecording_Date();
         v[i][6]=l.get(i).getService();
       // v[i][7]= l.get(i).getQty();
   }
   
   return  v;
   
}
//=======================================================
    public static List<Payment> getPaymentList() {
        List<Payment> l = new ArrayList<Payment>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("select * from servicepayable");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Payment pr=new Payment();
                 pr.setId(rs.getInt(1));
                 pr.setPlayerID(rs.getInt(2));
                 pr.setServiceCategory(rs.getString(3));
                 pr.setPay(rs.getString(4));
                 pr.setAmt_to_pay(rs.getDouble(5));
                 pr.setPaid(rs.getDouble(6));
                 pr.setRecording_Date(rs.getDate(7));
                 pr.setService(rs.getString(8));
                l.add(pr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
//=======================================================    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public double getAmt_to_pay() {
        return amt_to_pay;
    }

    public void setAmt_to_pay(double amt_to_pay) {
        this.amt_to_pay = amt_to_pay;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public Date getRecording_Date() {
        return recording_Date;
    }

    public void setRecording_Date(Date recording_Date) {
        this.recording_Date = recording_Date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

//    void setRecording_Date(java.util.Date date) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    void setId(String toString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

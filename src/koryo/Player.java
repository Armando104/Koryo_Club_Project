/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koryo;

import java.awt.Image;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ARMAND
 */
public class Player {

    static List<Player> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    int id;
    String firstname;
    String lastname;
    Date dob;
    String grade;
    File image;
    Date currentDate = new Date();
    boolean valid = true;

    public Player(int id, String firstname, String lastname, Date dob, String grade, File image, Date currentDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.grade = grade;
        this.image = image;
        this.currentDate = currentDate;
    }

    public Player() {
    }

    public static int getMaxID() {
        int id = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("select * from player");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                id = rs.getInt(1);
            }

        } catch (Exception ex) {
            // return false;
        }
        return id + 1;
    }
    //==================================================

    public static List<Player> getplayername(String fname) {
        List<Player> list = new ArrayList<Player>();
        //String status = "scheduled but unsold";
        //Auction au;
        int size = 0;
        byte[] bytearray = new byte[1048576];
        try {
            InputStream sImage;
            //DBconnection c = new DBconnection();
            //Connection con = c.open();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM PLAYER WHERE id='" + fname + "'";
            ResultSet rs = stmt.executeQuery(query);

            File Picture;
            DataOutputStream out;

            while (rs.next()) {
                Player p = new Player();
                p.setId(rs.getInt(1));
                p.setFirstname(rs.getString(2));
                p.setLastname(rs.getString(3));
                p.setDob(rs.getDate(4));
                p.setGrade(rs.getString(5));
                sImage = rs.getBinaryStream(6);
                Picture = new File("D:\\PlayersPictures", p.getId() + ".jpg");
                out = new DataOutputStream(new FileOutputStream(Picture));
                while ((size = sImage.read(bytearray)) != -1) {
                    out.write(bytearray, 0, size);

                }
                p.setCurrentDate(rs.getDate(7));
//                au = Auction.getInfos(p.getProdID());
//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy,hh:mm");
//                Date auct = sdf.parse(au.getDatetime());
//                Date today = new Date();
//                long k = 2 * 60 * 60 * 1000;
//                long twohrbeforems = auct.getTime() - k;
//                long nownow = today.getTime();
//                if (twohrbeforems > nownow) {
//                    list.add(p);
//                }

            }
            rs.close();
            //ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        //System.out.println("SIZE: >> "+list.size());

        return list;
    }
    //===================================================

    public static Player getPlayer(String id) {
        Player pr = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("select * from player where id= " + id + "");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pr = new Player();
                pr.setId(rs.getInt(1));
                pr.setFirstname(rs.getString(2));
                pr.setLastname(rs.getString(3));
                
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pr;
    }
 //===================================================
     public static Player getPlayerName(String name) {
        Player pr = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("select * from player where FirstName= '" + name + "'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pr = new Player();
                pr.setId(rs.getInt(1));
                pr.setFirstname(rs.getString(2));
                pr.setLastname(rs.getString(3));
                
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pr;
    }
//====================================================     

    public boolean delete() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("delete from player where id=?");
            pst.setInt(1, id);

            pst.execute();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    //====================================================    

    public boolean insert() {
        try {
            FileInputStream fin = new FileInputStream(image);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("insert into player values(?,?,?,?,?,?,?)");
            pst.setInt(1, id);
            pst.setString(2, firstname);
            pst.setString(3, lastname);
            pst.setDate(4, new java.sql.Date(dob.getTime()));
            pst.setString(5, grade);
            pst.setBinaryStream(6, fin, (int) image.length());
            pst.setDate(7, new java.sql.Date(currentDate.getTime()));
            JOptionPane.showMessageDialog(null, "Inserted Successfully!!");
            pst.execute();
            return true;
        } catch (Exception ex) {
            //System.out.println(ex);
            ex.printStackTrace();
            return false;
        }
    }
    //=================================================

    public boolean update() {
        try {
            FileInputStream fin = new FileInputStream(image);
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("update player set Grade=?,picture=? where id=?");
            pst.setInt(3, id);
            pst.setString(1, grade);
            pst.setBinaryStream(2, fin, (int) image.length());
            pst.execute();
            JOptionPane.showMessageDialog(null, "PLAYER UPDATED SUCCESSULLY!!");
            return true;
        } catch (Exception ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex, "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    //===================================================

    public Object[][] getPlayerSortingID() {
        Object[][] emps = new Object[100][5];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM PLAYER DESC ID";
            ResultSet rs = stmt.executeQuery(query);

            int i = 0;
            while (rs.next()) {
                emps[i][0] = rs.getInt(1);
                String firstname = rs.getString(2);
                String lastname = rs.getString(3);
                emps[i][1] = firstname + " " + lastname;
                emps[i][2] = rs.getDate(4);
                emps[i][3] = rs.getDate(7);
                emps[i][4] = rs.getString(5);
                //emps[i][4] = rs.getInt(5);
                i++;
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return emps;
    }
    //===================================================
     public static List<Player> getPlayerList1() {
        List<Player> l = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("select * from player");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Player pr = new Player();
                pr.setId(rs.getInt(1));
                pr.setFirstname(rs.getString(2));
                pr.setLastname(rs.getString(3));
                pr.setDob(rs.getDate(4));
                pr.setGrade(rs.getString(5));
                
                l.add(pr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
        }
    //===================================================

    public static List<Player> getAllList() {
        List<Player> l = new ArrayList<>();
        try {
            byte[] imageBytes;
            Image image;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/koryo", "root", "");
            PreparedStatement pst = con.prepareStatement("select * from player");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Player pl = new Player();
                pl.setId(rs.getInt(1));
                pl.setFirstname(rs.getString(2));
                pl.setLastname(rs.getString(3));
                pl.setDob(rs.getDate(4));
                pl.setGrade(rs.getString(5));
                //imageBytes=rs.getBytes(6);

                // pl.setImage(imageBytes);
                l.add(pl);
            }

        } catch (Exception ex) {
            // return false;
        }
        return l;
    }
    //===================================================    

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        if (firstname.length() == 0) {
        }
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public String toString() {
        return firstname;
    }
    
}

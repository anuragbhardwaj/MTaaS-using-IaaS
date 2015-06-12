package edu.sjsu.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.model.Billing;
import DbUtil.DbUtil;

public class BillingDao {

    private Connection connection;

    public BillingDao() {
        connection = DbUtil.getConnection();
    } 

    public void addBilling(Billing billing) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into Billing(price,userid) values (?, ?)");
            // Parameters start with 1
        
            preparedStatement.setString(1, billing.getPrice());
            preparedStatement.setInt(2, billing.getUserid());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBillingt(int billingId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from Billing where billingid=?");
            // Parameters start with 1
            preparedStatement.setInt(1, billingId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBilling(Billing billing) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update Billing set price=?, userid=? where billingid=?");
            // Parameters start with 1
            preparedStatement.setString(1, billing.getPrice());
            preparedStatement.setInt(2, billing.getUserid());
            preparedStatement.setInt(3, billing.getBillingid());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void check() {
    	  try {
    	Statement statement = connection.createStatement();
    	ResultSet a= statement.executeQuery("select * from hubs");
    	a.last();
    	int r1 = a.getRow();
    	ResultSet b= statement.executeQuery("select * from devices");
    	b.last();
    	int r2 = b.getRow();
    	int r=(100*r1+80*r2);
    	 statement.executeQuery("update Billing set price="+r+"where userid=1");
    	
    	  }catch (SQLException e) {
              e.printStackTrace();
          }
    }
    
    public List<Billing> getAllBillings() {
        List<Billing> billings = new ArrayList<Billing>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Billing");
            while (rs.next()) {
                Billing billing = new Billing();
                billing.setBillingid(rs.getInt("billingid"));
                billing.setPrice(rs.getString("price"));
                billing.setUserid(rs.getInt("userid"));
                billings.add(billing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billings;
    }
}
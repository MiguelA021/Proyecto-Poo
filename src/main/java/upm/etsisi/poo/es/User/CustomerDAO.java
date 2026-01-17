package upm.etsisi.poo.es.User;

import upm.etsisi.poo.es.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

public class CustomerDAO {
    public static CustomerDAO instance;
    private CustomerDAO(){}
    public static CustomerDAO getInstance(){
        if(instance == null){
            instance = new CustomerDAO();
        }return instance;
    }
        public boolean addCustomer(Customer customer) {
        boolean result = false;
        Connection conn = null;
            PreparedStatement pstmt = null;
            String add = "INSERT INTO customer (id,name,email,cashierid) VALUES (?,?,?,?)";
            String id = customer.getId();
            String name = customer.getName();
            String email = customer.getEmail();
            int  cashId = customer.getCashierId();
            try{
                conn = ConexionDB.conectar();
                pstmt = conn.prepareStatement(add);
                pstmt.setString(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, email);
                pstmt.setInt(4,cashId);
                int filasAfectadas = pstmt.executeUpdate();
                if(filasAfectadas > 0){result=true;}

            }catch (SQLException ex){
                System.out.println("Error to add customer: " + ex.getMessage());
            }finally {
             try{
                 if(pstmt != null){pstmt.close();}
                 if(conn != null){conn.close();}
             }catch(SQLException ex){
                 System.out.println(ex.getMessage());
             }
            }
            return result;

    }
    public boolean removeCustomer(Customer customer) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String remove = "DELETE FROM customer WHERE id = ?";
        String id= customer.getId();
        try{
            conn = ConexionDB.conectar();
            pstmt = conn.prepareStatement(remove);
            pstmt.setString(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if(filasAfectadas > 0) {result=true;}

        }catch (SQLException ex){
                System.out.println("Error to remove customer: " + ex.getMessage());
            }finally {
             try{
                 if(pstmt != null){pstmt.close();}
                 if(conn != null){conn.close();}
             }catch(SQLException ex){
                 System.out.println(ex.getMessage());
             }
            }
        return result;
    }
    public TreeMap<Integer,Customer> loadCustomers(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        Customer customer = null;
        String sql = "SELECT * FROM customer";
        ResultSet rs = null;
        TreeMap<Integer,Customer> map = new TreeMap<>();
        try{
            conn = ConexionDB.conectar();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                customer = resulSetToCustomer(rs);
                String dni = customer.getId();
                int id = CustomerController.dniToId(dni);
                map.put(id, customer);
            }
        }catch (SQLException ex){
                System.out.println("Error to remove customer: " + ex.getMessage());
            }finally {
             try{
                 if(pstmt != null){pstmt.close();}
                 if(conn != null){conn.close();}
             }catch(SQLException ex){
                 System.out.println(ex.getMessage());
             }
            }
        return map;
    }
    public Customer resulSetToCustomer( ResultSet rs) throws SQLException{
        String id = rs.getString("id");
        String email = rs.getString("email");
        String name = rs.getString("name");
        int cashier = rs.getInt("cashierid");
        return new  Customer(email,name,id,cashier);

    }
}




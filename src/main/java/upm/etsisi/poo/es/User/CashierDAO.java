package upm.etsisi.poo.es.User;

import upm.etsisi.poo.es.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;

public class CashierDAO {
    public static CashierDAO instance;

    private CashierDAO() {
    }

    public static CashierDAO getInstance() {
        if (instance == null) instance = new CashierDAO();
        return instance;
    }

    public boolean addCashier(Cashier cashier) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String add = "INSERT INTO cashier (id,name,email) VALUES (?,?,?)";
        String id = cashier.getId();
        String name = cashier.getName();
        String email = cashier.getEmail();
        try {
            conn = ConexionDB.conectar();
            pstmt = conn.prepareStatement(add);
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                result = true;
            }

        } catch (SQLException ex) {
            System.out.println("Error to add cashier: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;

    }

    public boolean removeCashier(Cashier cashier) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String remove = "DELETE FROM cashier WHERE id = ?";
        String idBD = cashier.getId();
        try {
            conn = ConexionDB.conectar();
            pstmt = conn.prepareStatement(remove);
            pstmt.setString(1, idBD);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                result = true;
            }

        } catch (SQLException ex) {
            System.out.println("Error to remove cashier: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return result;
    }

    public HashMap<Integer, Cashier> loadCashiers() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Cashier cashier = null;
        String sql = "SELECT * FROM cashier";
        ResultSet rs = null;
        HashMap<Integer, Cashier> map = new HashMap<>();
        try {
            conn = ConexionDB.conectar();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cashier = resulSetToCashier(rs);
                int id = Integer.parseInt(cashier.getId());
                map.put(id, cashier);
            }
        } catch (SQLException ex) {
            System.out.println("Error to remove customer: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return map;
    }

    public Cashier resulSetToCashier(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String email = rs.getString("email");
        String name = rs.getString("name");

        return new Cashier(email, name, id);

    }
}



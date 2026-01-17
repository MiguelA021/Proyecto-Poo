package upm.etsisi.poo.es.Tickets;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;

import upm.etsisi.poo.es.ConexionDB;
import upm.etsisi.poo.es.Product.PersonalizedProduct;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Product.ProductDAO;
import upm.etsisi.poo.es.User.CashierController;
import upm.etsisi.poo.es.User.CustomerController;

import static upm.etsisi.poo.es.Tickets.Ticket.DATE_FORMAT;
import static upm.etsisi.poo.es.Tickets.Ticket.MAX_PRODUCT;

public class TicketDAO {
    private static final String ID_ERROR = "The id given has been already used";
    public static final String ERROR_DB = "Error in data base";
    private static TicketDAO instance;
    private CustomerController customerController;
    private CashierController cashierController;


    private TicketDAO() {
        this.cashierController = CashierController.getInstance();
        this.customerController = CustomerController.getInstance();
    }

    public static TicketDAO getInstance() {
        if (instance == null) {
            instance = new TicketDAO();
        }
        return instance;
    }

    public void addTicket(Ticket ticket, String ticketType, int cashierid, int customerid) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            conn = ConexionDB.conectar();
            String sql = "INSERT INTO ticket (id,amount,status,date,type,cashier_id,user_id) VALUES (?, ?, ?, ?, ?, ?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, ticket.getId());
            pstmt.setInt(2, ticket.getAmount());
            pstmt.setString(3, ticket.getStatus().toString());
            pstmt.setString(4, ticket.getDate().format(DATE_FORMAT));
            pstmt.setString(5, ticketType.toUpperCase());
            pstmt.setInt(6, cashierid);
            pstmt.setInt(7, customerid);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al añadir ticket: "+e.getMessage());
        }
    }

    public HashMap<Integer,Ticket> loadTickets() {
        Ticket resul = null;
        HashMap <Integer,Ticket> tickets = new HashMap<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM ticket ";
        try {
            conn = ConexionDB.conectar();

            pstmt = conn.prepareStatement(sql);


            rs = pstmt.executeQuery();

            while (rs.next()) {
                resul = resulSetToTicket(rs,conn);
                if(resul != null) {
                    int idTicket = resul.getId();
                    int idCashier = rs.getInt("cashier_id");
                    int idCustomer = rs.getInt("user_id");
                    cashierController.addTicket(resul.getId(), idCashier);
                    customerController.addTicket(idCustomer, idTicket);
                    tickets.put(resul.getId(), resul);
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
        return tickets;
    }

    private Product[] getProductList(Ticket ticket, Connection conn) {
        Product[] listaProductos = new Product[MAX_PRODUCT];
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT p.id,p.type,p.name,p.price,p.date,p.category,p.maxcustoms,amount,customs " +
                "FROM tickets_products tp JOIN product p ON tp.product_id = p.id " +
                "WHERE ticket_id = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ticket.getId());
            rs = pstmt.executeQuery();
            int iterator = 0;
            while (rs.next()) {

                Product product = ProductDAO.resultSetToProduct(rs);
                if (product instanceof PersonalizedProduct) {
                    PersonalizedProduct personalizedProduct = (PersonalizedProduct) product;
                    String customs = rs.getString("customs");
                    String[] customsArray = customs.split(",");
                    for (int i = 6; i < customsArray.length; i++) {
                        String personalization = customsArray[i];
                        personalizedProduct.addPersonalized(personalization);
                    }
                    personalizedProduct.newPrice();

                }
                int amount = rs.getInt("amount");

                for(int i = 0; i <amount; i++) {
                    listaProductos[iterator] = product;
                    iterator++;
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException ex) {
                System.out.println("Error al cargar productos:"+ex.getMessage());
            }
        }
        return listaProductos;
    }


    public boolean addProduct(int idTicket, int idProduct, int amount, String customs) {
        Boolean resul = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (customs == null || customs.isEmpty()) customs = "";
        String busqueda = "SELECT amount FROM tickets_products WHERE ticket_id = ? and product_id = ? and amount = ? and customs = ?";
        String insertar = "INSERT INTO tickets_products (ticket_id,product_id,amount,customs) VALUES (?,?,?,?)";
        String modificar = "UPDATE tickets_products SET amount = ? WHERE id = ?";
        try {
            conn = ConexionDB.conectar();
            pstmt = conn.prepareStatement(busqueda);
            pstmt.setInt(1, idTicket);
            pstmt.setInt(2, idProduct);
            pstmt.setInt(3,amount);
            pstmt.setString(4, customs);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int befAmount = rs.getInt("amount");
                int finalAmount = amount + befAmount;
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement(modificar);
                pstmt.setInt(1, finalAmount);
                pstmt.setInt(2, id);

                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) resul = true;

            } else {
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement(insertar);
                pstmt.setInt(1, idTicket);
                pstmt.setInt(2, idProduct);
                pstmt.setInt(3, amount);
                pstmt.setString(4, customs);
                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) resul = true;
            }
            updateAmount(idTicket, amount, conn);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return resul;
    }

    public boolean removeProduct(int idTicket, int idProduct, String customs) {
        boolean resul = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String busqueda = "SELECT id,amount FROM tickets_products WHERE ticket_id = ? and product_id = ? and customs = ? LIMIT 1";
        String eliminar = "DELETE tickets_products WHERE id = ?";
        String modificar = "UPDATE tickets_products SET amount = ? WHERE id = ?";
        try {
            conn = ConexionDB.conectar();
            pstmt = conn.prepareStatement(busqueda);
            pstmt.setInt(1, idTicket);
            pstmt.setInt(2, idProduct);
            if (customs == null || customs.isEmpty()) customs = "";
            pstmt.setString(3, customs);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_linea = rs.getInt("id");
                int amount = rs.getInt("amount");
                rs.close();
                pstmt.close();
                if (amount == 1) {
                    pstmt = conn.prepareStatement(eliminar);
                    pstmt.setInt(1, id_linea);
                    int filasAfectadas = pstmt.executeUpdate();
                    if (filasAfectadas > 0) resul = true;
                } else {
                    pstmt = conn.prepareStatement(modificar);
                    pstmt.setInt(1, (amount - 1));
                    pstmt.setInt(2, id_linea);
                    int filasAfectadas = pstmt.executeUpdate();
                    if (filasAfectadas > 0) resul = true;
                }
                updateAmount(idTicket, -1, conn);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return resul;
    }

    private void updateAmount(int idTicket, int amount, Connection conn) {
        PreparedStatement pstmt = null;
        String updAmount = "UPDATE ticket SET amount = amount+ ?  WHERE id = ?";
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(updAmount);
            pstmt.setInt(1, amount);
            pstmt.setInt(2, idTicket);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error Actualizado: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void setStatus(int ticketid, Status status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String update = "UPDATE ticket SET status = ? WHERE id = ?";
        try {
            conn = ConexionDB.conectar();
            pstmt = conn.prepareStatement(update);
            pstmt.setString(1, status.toString());
            pstmt.setInt(2, ticketid);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error Actualizado: " + ex.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private Ticket resulSetToTicket(ResultSet rs, Connection conn) throws SQLException {
        Ticket resul= null;
        int id = rs.getInt("id");
        int amount = rs.getInt("amount");
        String status = rs.getString("status");
        String date = rs.getString("date");
        LocalDateTime dateTime = null;
        if (date != null) dateTime = LocalDateTime.parse(date, DATE_FORMAT);
        int cajero_id = rs.getInt("cashier_id");
        int usuario_id = rs.getInt("user_id");
        String type = rs.getString("type");
        switch (type) {
            case TicketController.PRODUCTS1:
                CustomerTicket customerTicket = new CustomerTicket(id);
                customerTicket.setAmount(amount);
                customerTicket.setStatus(Status.valueOf(status));
                customerTicket.setDate(dateTime);
                customerTicket.setProductList(getProductList(customerTicket, conn));
                resul = customerTicket;
                break;
            case TicketController.SERVICE:
                EnterpriseServiceTicket enterpriseServiceTicket = new EnterpriseServiceTicket(id);
                enterpriseServiceTicket.setStatus(Status.valueOf(status));
                enterpriseServiceTicket.setDate(dateTime);
                enterpriseServiceTicket.addProducts(getProductList(enterpriseServiceTicket, conn));
                resul = enterpriseServiceTicket;
                break;
            case TicketController.COMBINED:
                EnterpriseMixedTicket enterpriseMixedTicket = new EnterpriseMixedTicket(id);
                enterpriseMixedTicket.setStatus(Status.valueOf(status));
                enterpriseMixedTicket.setDate(dateTime);
                enterpriseMixedTicket.addProducts(getProductList(enterpriseMixedTicket, conn));
                resul = enterpriseMixedTicket;
                break;
            default:
                System.out.println(ID_ERROR);
                break;
        }
        return resul;
    }
}

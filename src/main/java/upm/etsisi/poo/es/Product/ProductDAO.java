package upm.etsisi.poo.es.Product;

import upm.etsisi.poo.es.type;
import upm.etsisi.poo.es.ConexionDB;

import java.sql.*;
import java.time.LocalDate;

import static upm.etsisi.poo.es.Product.ProductController.MAX_PRODUCT;

public class ProductDAO {
    private static final String THE_PRODUCT_DOESN_T_EXIST = "The product doesn't exist";
    private static final String ERROR_AL_CONECTAR = "Error al conectar: ";
    private static final String ERROR_AL_DESCONECTAR = "Error al desconectar: ";
    public static final String MEETING = "MEETING";
    public static final String FOOD = "FOOD";
    public static final String SERVICE = "SERVICE";
    public static final String EVENT = "EVENT";
    public static final String BASICPRODUCT = "BASICPRODUCT";
    public static final String PERSONALIZEDPRODUCT = "PERSONALIZEDPRODUCT";
    public static ProductDAO instance;

    private ProductDAO() {
    }

    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    public boolean prodAdd(Product product, String type) {
        boolean done = false;
        Connection conn = null;
        PreparedStatement pst = null;
        String add = "INSERT INTO product (id,name,price,type,date,category,maxcustoms) VALUES ( ?, ?, ?, ?)";
        try {
            conn = ConexionDB.conectar();
            pst = conn.prepareStatement(add);
            pst.setInt(1, product.getId());
            pst.setString(2, product.getName());
            pst.setDouble(3, product.getPrice());
            pst.setString(4, type);
            if (product instanceof Event) {
                Event event = (Event) product;
                pst.setString(5, event.getExpiryDate().toString());
                pst.setNull(6, java.sql.Types.VARCHAR);
                pst.setNull(7, 0);
            } else if (product instanceof Service) {
                Service service = (Service) product;
                pst.setString(5, service.getMaxUseDate().toString());
                pst.setNull(6, java.sql.Types.VARCHAR);
                pst.setNull(7, 0);
            } else {
                pst.setNull(5, java.sql.Types.VARCHAR);

                if (product instanceof PersonalizedProduct) {
                    PersonalizedProduct pProduct = (PersonalizedProduct) product;
                    pst.setString(6, pProduct.getCategory().toString());
                    pst.setInt(7, pProduct.getMaxPers());
                } else {
                    BasicProduct bProduct = (BasicProduct) product;
                    pst.setString(6, bProduct.getCategory().toString());
                    pst.setInt(7, 0);
                }
            }
            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) done = true;

        } catch (SQLException e) {
            System.out.println(ERROR_AL_CONECTAR + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(ERROR_AL_DESCONECTAR + e.getMessage());
            }
        }
        return done;
    }

    public Product getProduct(int id) {
        Product productFound = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String found = "SELECT * FROM product WHERE id = ?";
        try {
            conn = ConexionDB.conectar();
            pst = conn.prepareStatement(found);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                productFound = resultSetToProduct(rs);
            }

        } catch (SQLException e) {
            System.out.println(ERROR_AL_CONECTAR + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(ERROR_AL_DESCONECTAR + e.getMessage());
            }
        }
        return productFound;
    }

    public boolean removeProduct(int id) {
        boolean done = false;
        Connection conn = null;
        PreparedStatement pst = null;
        String remove = "DELETE FROM product WHERE id = ?";
        String objectStr = "";
        try {
            Product deleteProduct = getProduct(id);
            if (deleteProduct == null) {
                System.out.println(THE_PRODUCT_DOESN_T_EXIST);
            } else {
                objectStr = deleteProduct.toString();
                conn = ConexionDB.conectar();
                pst = conn.prepareStatement(remove);
                pst.setInt(1, id);
                int filasAfectadas = pst.executeUpdate();
                if (filasAfectadas > 0) {
                    done = true;
                    System.out.println(objectStr);
                }
            }

        } catch (SQLException e) {
            System.out.println(ERROR_AL_CONECTAR + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(ERROR_AL_DESCONECTAR + e.getMessage());
            }
        }
        return done;
    }

    public Product updateName(int id, String name) {
        Connection conn = null;
        PreparedStatement pst = null;
        Product updatedProduct = null;
        String update = "UPDATE product SET name = ? WHERE id = ?";
        try {
            conn = ConexionDB.conectar();
            pst = conn.prepareStatement(update);
            pst.setString(1, name);
            pst.setInt(2, id);
            pst.close();
            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas > 0) {
                updatedProduct = getProduct(id);
            }
        } catch (SQLException e) {
            System.out.println(ERROR_AL_CONECTAR + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(ERROR_AL_DESCONECTAR + e.getMessage());
            }
        }
        return updatedProduct;
    }

    public Product updatePrice(int id, Double price) {
        Connection conn = null;
        PreparedStatement pst = null;
        Product updatedProduct = null;
        String update = "UPDATE product SET price = ? WHERE id = ?";
        try {
            conn = ConexionDB.conectar();
            pst = conn.prepareStatement(update);
            pst.setDouble(1, price);
            pst.setInt(2, id);
            int filasAfectadas = pst.executeUpdate();
            pst.close();
            if (filasAfectadas > 0) {
                updatedProduct = getProduct(id);
            }
        } catch (SQLException e) {
            System.out.println(ERROR_AL_CONECTAR + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(ERROR_AL_DESCONECTAR + e.getMessage());
            }
        }
        return updatedProduct;
    }

    public Product updateType(int id, type category) {
        Connection conn = null;
        PreparedStatement pst = null;
        Product updatedProduct = null;
        String update = "UPDATE product SET category = ? WHERE id = ?";
        try {
            conn = ConexionDB.conectar();
            pst = conn.prepareStatement(update);
            pst.setString(1, category.toString());
            pst.setInt(2, id);
            int filasAfectadas = pst.executeUpdate();
            pst.close();
            if (filasAfectadas > 0) {
                updatedProduct = getProduct(id);
            }
        } catch (SQLException e) {
            System.out.println(ERROR_AL_CONECTAR + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(ERROR_AL_DESCONECTAR + e.getMessage());
            }
        }
        return updatedProduct;
    }

    public void list() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String list = "SELECT id FROM product";
        try {
            conn = ConexionDB.conectar();
            pst = conn.prepareStatement(list);
            rs = pst.executeQuery();
            while (rs.next()) {
                Product product = resultSetToProduct(rs);
                if (product != null) System.out.println(product.toString());
            }

        } catch (SQLException e) {
            System.out.println(ERROR_AL_CONECTAR + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(ERROR_AL_DESCONECTAR + e.getMessage());
            }
        }
    }

    public static Product resultSetToProduct(ResultSet rs) throws SQLException {
        Product productFound = null;
        int id = rs.getInt("id");
        String type = rs.getString("type");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        String dateStr = rs.getString("date");
        String categoryStr = rs.getString("category");
        int maxCustoms = rs.getInt("maxcustoms");
        upm.etsisi.poo.es.type categoryEnum = null;
        if (categoryStr != null) categoryEnum = upm.etsisi.poo.es.type.valueOf(categoryStr);

        switch (type) {
            case MEETING:
                productFound = new Meeting(id, name, price, dateStr);
                break;
            case FOOD:
                productFound = new Food(id, name, price, dateStr);
                break;
            case SERVICE:
                LocalDate serviceDate = null;
                if (dateStr != null) {
                    serviceDate = LocalDate.parse(dateStr);
                }
                productFound = new Service(serviceDate, name);
                break;
            case BASICPRODUCT:
                productFound = new BasicProduct(id, name, categoryEnum, price);
                break;
            case PERSONALIZEDPRODUCT:
                productFound = new PersonalizedProduct(id, name, categoryEnum, price, maxCustoms);
                break;

        }
        return productFound;
    }

    public static boolean isEmpty() {
        Connection conn = null;
        PreparedStatement pst = null;
        String count = "SELECT COUNT(*) FROM product";
        ResultSet rs = null;
        boolean empty = true;
        try {
            conn = ConexionDB.conectar();
            pst = conn.prepareStatement(count);
            rs = pst.executeQuery();
            if (rs.next()) {
                int amount = rs.getInt(1);
                if (amount > 0) {
                    empty = false;
                }
            }

        } catch (SQLException e) {
            System.out.println(ERROR_AL_CONECTAR + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(ERROR_AL_DESCONECTAR + e.getMessage());
            }
        }
        return empty;
    }

    public Product[] loadProducts() {
        Product[] products = new Product[MAX_PRODUCT];
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String found = "SELECT * FROM product";
        try {
            conn = ConexionDB.conectar();
            pst = conn.prepareStatement(found);
            rs = pst.executeQuery();
            int pos = 0;
            while (rs.next()) {

                Product product = resultSetToProduct(rs);
                products[pos] = product;
                pos++;

            }
        } catch (SQLException e) {
            System.out.println(ERROR_AL_CONECTAR + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println(ERROR_AL_DESCONECTAR + e.getMessage());
            }
        }
        return products;
    }
}




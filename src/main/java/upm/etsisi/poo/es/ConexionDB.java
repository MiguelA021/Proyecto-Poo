package upm.etsisi.poo.es;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    public static final String URL = "jdbc:sqlite:tiendupm.db";
    private static final String ERROR_AL_CREAR_TABLAS = "Error al crear tablas: ";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void inicializarTablas()  {
        Connection conexion = null;
        Statement sentencia = null;
        try {
            conexion = conectar();
            sentencia = conexion.createStatement();
            String cashier = "CREATE TABLE IF NOT EXISTS cashier (" +
                    "   id TEXT  NOT NULL," +
                    "   email TEXT NOT NULL," +
                    "   name TEXT NOT NULL," +
                    "   PRIMARY KEY(id)" +
                    ");";
            sentencia.executeUpdate(cashier);

            String customer =  "CREATE TABLE IF NOT EXISTS customer (" +
                    "   id TEXT NOT NULL," +
                    "   email TEXT NOT NULL," +
                    "   name TEXT NOT NULL," +
                    "   cashierid TEXT NOT NULL," +
                    "   PRIMARY KEY(id)" +
                    "   FOREIGN KEY(cashierid) REFERENCES cashier(id)" +
                    ");";
            sentencia.executeUpdate(customer);

            String product ="CREATE TABLE IF NOT EXISTS product (" +
                    "   id INTEGER NOT NULL," +
                    "   name TEXT NOT NULL," +
                    "   price DECIMAL(10,2) NOT NULL," +
                    "   type TEXT NOT NULL," +
                    "   date TEXT," +
                    "   category TEXT ," +
                    "   maxcustoms INTEGER," +
                    "   PRIMARY KEY(id)" +
                    ");";
            sentencia.executeUpdate(product);
            String ticket ="CREATE TABLE IF NOT EXISTS ticket (" +
                    "   id INTEGER NOT NULL," +
                    "   amount INTEGER," +
                    "   status TEXT NOT NULL," +
                    "   date TEXT NOT NULL," +
                    "   type TEXT NOT NULL," +
                    "   cashier_id TEXT NOT NULL," +
                    "   user_id TEXT NOT NULL," +
                    "   PRIMARY KEY (id)" +
                    "   FOREIGN KEY(cashier_id) REFERENCES cashier(id)" +
                    "   FOREIGN KEY(user_id) REFERENCES usuario(id)" +
                    ");";
            sentencia.executeUpdate(ticket);

            String ticketsProducts = "CREATE TABLE IF NOT EXISTS tickets_products (" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "   ticket_id INTEGER NOT NULL," +
                    "   product_id INTEGER NOT NULL," +
                    "   amount INTEGER NOT NULL," +
                    "   customs TEXT," +
                    "   FOREIGN KEY(ticket_id) REFERENCES ticket(id)," +
                    "   FOREIGN KEY(product_id) REFERENCES product(id) ON DELETE CASCADE);"

                    ;
            sentencia.executeUpdate(ticketsProducts);

        } catch (SQLException e) {
            System.out.println(ERROR_AL_CREAR_TABLAS + e.getMessage());
        }finally {
            try{
            if (sentencia != null) {sentencia.close();}
            if (conexion != null) {
                conexion.close();
            }
            }catch(SQLException e){
                    System.out.println(e.toString());
                }
        }
    }


}
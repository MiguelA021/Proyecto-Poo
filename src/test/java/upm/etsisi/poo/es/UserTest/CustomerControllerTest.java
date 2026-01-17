package upm.etsisi.poo.es.UserTest;
import junit.framework.TestCase;
import upm.etsisi.poo.es.User.Customer;
import upm.etsisi.poo.es.User.CustomerController;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class CustomerControllerTest extends TestCase{

    private final ByteArrayOutputStream capturador = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    //Añadir un CustomerEmpresise y otro que sea simplemente Customer y verificarlo al hacer getCustomer
    public void testAddCustomer(){
        //"COMPANY{identifier='B12345674', name='pepe2', email='pepe5@upm.es', cash=UW1234567}";
        CustomerController.getInstance().addCustomer("Pepe2", "B12345674", "pepe5@upm.es", 1234567); //Este es un CustomerEnterprise
        //"Client{identifier='98948334B', name='Pepe1', email='pepe1@upm.es', cash=UW1234567}";
        CustomerController.getInstance().addCustomer("Pepe1", "98948334B", "pepe1@upm.es", 1234567); //Este es un Customer
        Customer customerEnterprise = CustomerController.getInstance().getCustomer(12345674);
        Customer customer = CustomerController.getInstance().getCustomer(98948334);
        String actual1 = customerEnterprise.toString();
        String expected1 = "COMPANY{identifier='B12345674', name='Pepe2', email='pepe5@upm.es', cash=UW1234567}";
        String actual2 = customer.toString();
        String expected2 = "Client{identifier='98948334B', name='Pepe1', email='pepe1@upm.es', cash=UW1234567}";
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    //Comprueba que de verdad lo elimina y que si se quiere coseguir dicho CustomerEliminado, literalmente sea null porque no existe
    public void testRemoveCustomer(){
        CustomerController.getInstance().addCustomer("Pepe2", "B12345674", "pepe5@upm.es", 1234567);
        CustomerController.getInstance().addCustomer("Pepe1", "98948334B", "pepe1@upm.es", 1234567);
        assertTrue(CustomerController.getInstance().removeCustomer("B12345674"));
        assertNull(CustomerController.getInstance().getCustomer(12345674));
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setOut(new PrintStream(capturador));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.setOut(originalOut);
    }

    public void testListCustomersEmpty(){
        CustomerController.getInstance().listCustomers();

        // .trim() elimina el último salto de línea que añade println automáticamente
        String actual = capturador.toString().trim();
        String expected = "No customers in the store";

        assertEquals(expected, actual);
    }

    public void testListCustomers(){
        CustomerController.getInstance().addCustomer("Pepe1", "98948334B", "pepe1@upm.es", 1234567);
        CustomerController.getInstance().addCustomer("Pepe2", "B12345674", "pepe5@upm.es", 1234567);

        capturador.reset();

        CustomerController.getInstance().listCustomers();

        // Normalizamos salidas para evitar error por \r\n vs \n
        String actual = capturador.toString().replace("\r\n", "\n").trim();

        StringBuilder sc = new StringBuilder();
        sc.append("Client:\n");
        sc.append("  Client{identifier='98948334B', name='Pepe1', email='pepe1@upm.es', cash=UW1234567}\n");
        sc.append("  COMPANY{identifier='B12345674', name='Pepe2', email='pepe5@upm.es', cash=UW1234567}\n");
        sc.append("client list: ok");

        String expected = sc.toString().replace("\r\n", "\n").trim();

        assertEquals(expected, actual);
    }
}

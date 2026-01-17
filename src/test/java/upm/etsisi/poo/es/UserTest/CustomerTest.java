package upm.etsisi.poo.es.UserTest;
import junit.framework.TestCase;
import upm.etsisi.poo.es.User.Customer;


public class CustomerTest extends TestCase{
    public void testToString(){
        Customer customer = new Customer("pepe2@upm.es", "Pepe2", "98948334B", 1234567);
        String expected = "Client{identifier='98948334B', name='Pepe2', email='pepe2@upm.es', cash=UW1234567}";
        String actual = customer.toString();
        assertEquals(expected, actual);
    }
}

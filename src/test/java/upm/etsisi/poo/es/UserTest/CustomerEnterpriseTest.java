package upm.etsisi.poo.es.UserTest;
import junit.framework.TestCase;
import upm.etsisi.poo.es.User.CustomerEnterprise;


public class CustomerEnterpriseTest extends TestCase{
    public void testToString(){
        CustomerEnterprise customerEnterprise = new CustomerEnterprise("pepe5@upm.es", "pepe2", "B12345674", 1234567);
        String expected = "COMPANY{identifier='B12345674', name='pepe2', email='pepe5@upm.es', cash=UW1234567}";
        String actual = customerEnterprise.toString();
        assertEquals(expected, actual);
    }
}

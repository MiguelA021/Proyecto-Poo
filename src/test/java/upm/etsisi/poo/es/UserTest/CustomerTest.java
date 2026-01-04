package upm.etsisi.poo.es.UserTest;
import org.junit.Test;
import upm.etsisi.poo.es.Ticket;
import upm.etsisi.poo.es.User.Customer;

import static org.junit.Assert.*;

public class CustomerTest {

    /**
     * Test: Comprueba que el formato de ToString sea el esperado
     */
    @Test
    public void checkStringTest() {
        Customer customerTest = new Customer("vegeta@upm.es", "Vegeta", "98948334B", 1234567);
        String actual = customerTest.toString();
        String expected = "USER{identifier='98948334B', name='Vegeta', email='vegeta@upm.es', cash='UW1234567'}";
        assertEquals(expected, actual);
    }

    /**
     * Test: Comprueba que añade perfectamente un ticket
     */
    @Test
    public void addTicketTest() {
        Customer customerTest = new Customer("vegeta@upm.es", "Vegeta", "98948334B", 1234567);
        Integer idTicket = 133113;
        Ticket ticket = new Ticket(idTicket);
        customerTest.addTicket(idTicket, ticket);
        Ticket expected = customerTest.getTickets().get(idTicket);
        assertEquals(133113, expected.getId());
    }

}
package upm.etsisi.poo.es.UserTest;
import org.junit.Test;
import upm.etsisi.poo.es.Product.BasicProduct;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Ticket;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.Assert.*;

public class CashierTest {

    /**
     * Test: Comprueba que el formato de la lista de tickets de un CASHIER sea la esperada
     */
    @Test
    public void checkListTicketsTest(){
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");
        String fechaFormateada = ahora.format(formateador);
        Integer ticket1 = 212121;
        Integer ticket2 = 222226;
        Integer ticket3 = 111111;
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "UW1234569");
        cashierTest.addTicket(ticket1);
        cashierTest.addTicket(ticket2);
        cashierTest.addTicket(ticket3);
        Ticket ticketReal3 = cashierTest.getTicketById(ticket3);
        BasicProduct product1 = new BasicProduct(1, "Libro POO V2", type.BOOK, 30);
        ticketReal3.ticketAdd(product1, 3);
        String actual = cashierTest.listTickets();
        StringBuilder sb = new StringBuilder();
        sb.append("  111111->OPEN\n");
        sb.append("  ").append(fechaFormateada).append("-212121->EMPTY\n");
        sb.append("  ").append(fechaFormateada).append("-222226->EMPTY\n");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

    /**
     * Test: Comprueba que se añade correctamente un ticket a traves de un id que sea null
     */
    @Test
    public void addTicketWithoutIdTest(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "UW1234569");
        assertNotNull(cashierTest.addTicket(null));
    }

    /**
     * Test: Comprueba que se añade correctamente un ticket a traves de un id que sea un Integer pero que no este en el TreeMap
     */
    @Test
    public void addTicketWithIdTest(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "UW1234569");
        int actual = cashierTest.addTicket(112341);
        assertEquals(112341, actual);
    }

    /**
     * Test: Comprueba que no permite añadir un ticket con un id que ya existia en la lista de tickets
     */
    @Test
    public void addTicketWithIdExists(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "UW1234569");
        cashierTest.addTicket(112341);
        int actual = cashierTest.addTicket(112341);
        assertEquals(112341, actual);
    }

    /**
     * Test: Comprueba un formato de ToString que es igual al que se espere
     */
    @Test
    public void checkStringTest(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "1234569");
        String actual = cashierTest.toString();
        String expected = "Cash{identifier='UW1234569', name='pepecurro1', email='pepe0@upm.es'}";
        assertEquals(expected, actual);
    }

    /**
     * Test: Comprueba que consigue un ticket a partir de su ID
     */
    @Test
    public void getTicketsByIdTest(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "1234569");
        cashierTest.addTicket(122131);
        Ticket expected = cashierTest.getTicketById(122131);
        assertNotNull(expected);
    }

    /**
     * Test: Comprueba que no consigue un ticket a partir de un ID inexistente
     */
    @Test
    public void getTicketsByIncorrectIdTest(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "1234569");
        cashierTest.addTicket(122138);
        Ticket expected = cashierTest.getTicketById(122131);
        assertNull(expected);
    }

    /**
     * Test: Comprueba que se elimina correctamente un ticket a traves de una busqueda en la que aparezca su ID en el TREEMAP
     */
    @Test
    public void removeTicketTest(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "1234569");
        cashierTest.addTicket(122138);
        cashierTest.addTicket(626168);
        boolean result = cashierTest.removeTicket(626168);
        assertTrue(result);
    }

    /**
     * Test: Comprueba que no elimina correctamente un ticket a traves de una busqueda en la que no aparezca su ID en el TREEMAP
     */
    @Test
    public void removeFakeTicketTest(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "1234569");
        cashierTest.addTicket(122138);
        cashierTest.addTicket(626168);
        boolean result = cashierTest.removeTicket(626161);
        assertFalse("Este ticket no existe", result);
    }
}
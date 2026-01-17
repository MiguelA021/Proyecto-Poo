package upm.etsisi.poo.es.UserTest;
import junit.framework.TestCase;
import upm.etsisi.poo.es.Tickets.TicketData;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.User.CashierController;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CashierControllerTest extends TestCase{

    private final ByteArrayOutputStream capturador = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

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

    public void testAddCashier(){
        boolean result1 = CashierController.getInstance().addCasher(1234569, "pepecurro1", "pepe0@upm.es");
        boolean result2 = CashierController.getInstance().addCasher(1234569, "stefancurro1", "stefan0@upm.es");
        assertTrue(result1);
        assertFalse(result2);
    }
    public void testListCashiersEmpty(){
        CashierController.getInstance().listCashers();
        String actual = capturador.toString().trim();
        String expected = "no cashiers in store";
        assertEquals(expected, actual);
    }
    public void testListCashiers(){
        CashierController.getInstance().addCasher(1234569, "pepecurro1", "pepe0@upm.es");
        CashierController.getInstance().addCasher(1934569, "stefancurro1", "stefan0@upm.es");
        capturador.reset();
        CashierController.getInstance().listCashers();
        String actual = capturador.toString().replace("\r\n", "\n").trim();

        StringBuilder sc = new StringBuilder();
        sc.append("Cash:\n");
        sc.append("  Cash{identifier='UW1234569', name='pepecurro1', email='pepe0@upm.es'}\n");
        sc.append("  Cash{identifier='UW1934569', name='stefancurro1', email='stefan0@upm.es'}\n");
        sc.append("cash list: ok");

        String expected = sc.toString().replace("\r\n", "\n").trim();

        assertEquals(expected, actual);
    }
    public void testListTicketsOnCashier(){
        CashierController.getInstance().addCasher(1234569, "pepecurro1", "pepe0@upm.es");
        CashierController.getInstance().addTicket(331133, 1234569);
        CashierController.getInstance().addTicket(337733, 1234569);
        TicketData.getInstance().addTicket(331133, "products");
        TicketData.getInstance().addTicket(337733, "products");
        capturador.reset();
        CashierController.getInstance().listTicketsOnCasher(1234569);
        String actual = capturador.toString().replace("\r\n", "\n").trim();

        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");
        String fechaFormateada = ahora.format(formateador);
        StringBuilder sc = new StringBuilder();
        sc.append(fechaFormateada).append("-").append(331133).append(" - EMPTY\n");
        sc.append("  ").append(fechaFormateada).append("-").append(337733).append(" - EMPTY\n");

        String expected = sc.toString().replace("\r\n", "\n").trim();

        assertEquals(expected, actual);
    }

    public void testSearchCashierById(){
        CashierController.getInstance().addCasher(1234569, "pepecurro1", "pepe0@upm.es");
        assertNotNull(CashierController.getInstance().searchCasherById(1234569));
        assertNull(CashierController.getInstance().searchCasherById(9227579));
    }
    public void testRemoveCashier(){
        CashierController.getInstance().addCasher(1234569, "pepecurro1", "pepe0@upm.es");
        CashierController.getInstance().addCasher(1234569, "stefancurro1", "stefan0@upm.es");
        boolean result1 = CashierController.getInstance().removeCasher(1234569);
        boolean result2 = CashierController.getInstance().removeCasher(4311346);
        assertTrue(result1);
        assertFalse(result2);
    }
    public void testExistsTicket(){
        CashierController.getInstance().addCasher(1234569, "pepecurro1", "pepe0@upm.es");
        CashierController.getInstance().addTicket(125418, 1234569);
        boolean result1 = CashierController.getInstance().exitsTicket(1234569, 125418);
        boolean result2 = CashierController.getInstance().exitsTicket(1234569, 585438);
        assertTrue(result1);
        assertFalse(result2);
    }
}

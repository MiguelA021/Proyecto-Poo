package upm.etsisi.poo.es.UserTest;
import junit.framework.Test;
import junit.framework.TestCase;
import upm.etsisi.poo.es.Product.BasicProduct;
import upm.etsisi.poo.es.Tickets.CustomerTicket;
import upm.etsisi.poo.es.Tickets.Ticket;
import upm.etsisi.poo.es.Tickets.TicketData;
import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CashierTest extends TestCase {

    //String of the tickets that belongs to one cashier and it's ordering by key
    public void testListTicket(){
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");
        String fechaFormateada = ahora.format(formateador);

        Integer t1 = 212121;
        Integer t2 = 222226;
        Integer t3 = 111111;

        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "1234569");
        cashierTest.addTicket(t1);
        cashierTest.addTicket(t2);
        cashierTest.addTicket(t3);

        //Habra que ver si hay que añadirlo primero a TicketData
        TicketData.getInstance().addTicket(t1, "products");
        TicketData.getInstance().addTicket(t2, "products");
        TicketData.getInstance().addTicket(t3, "products");

        BasicProduct product1 = new BasicProduct(1, "Libro POO V2", type.BOOK, 30);

        Ticket ticket3 = TicketData.getInstance().getTicket(t3); //Para poder acceder al ticket, usaremos TicketData que este nos impide crear copias y sobre ese, independientemente del tipo de ticket, sera actualizado en el HashMap
        CustomerTicket customerTicket3 = (CustomerTicket) ticket3;
        customerTicket3.ticketAdd(product1, 2);
        String actual = cashierTest.listTickets();

        StringBuilder sb = new StringBuilder();
        sb.append("  111111 - OPEN\n");
        sb.append("  ").append(fechaFormateada).append("-212121 - EMPTY\n");
        sb.append("  ").append(fechaFormateada).append("-222226 - EMPTY\n");
        String expected = sb.toString();

        assertEquals(expected, actual);
    }

    //Primero crear un cajero, luego tener que añadirlo a CashierController, a partir de ese Controller localizar al cajero y añadir ese ticket,
    //para mas restriccion, el assert sera sobre el id del ticket y el tamaño de tickets que tendra dicho cajero
    public void testAddTicket(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "1234569");
        Integer t1 = 212121;
        Integer t2 = 222226;
        Integer t3 = 111111;
        cashierTest.addTicket(t1);
        cashierTest.addTicket(t2);
        cashierTest.addTicket(t3);
        assertTrue(cashierTest.getTicketById(212121));
        assertFalse(cashierTest.getTicketById(775326));
        assertTrue(cashierTest.getTicketById(222226));
        assertTrue(cashierTest.getTicketById(111111));
    }

    public void testGetTicketById(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "1234569");
        Integer t1 = 212121;
        Integer t2 = 222226;
        Integer t3 = 111111;
        Integer t4 = 775326;
        cashierTest.addTicket(t1);
        cashierTest.addTicket(t2);
        cashierTest.addTicket(t3);
        assertFalse(cashierTest.getTicketById(t4));
        assertTrue(cashierTest.getTicketById(t2));
    }

    //Comparar formato
    public void testToStringTest(){
        Cashier cashierTest = new Cashier("pepe0@upm.es", "pepecurro1", "1234569");
        String expected = "Cash{identifier='UW1234569', name='pepecurro1', email='pepe0@upm.es'}";
        String actual = cashierTest.toString();
        assertEquals(expected, actual);
    }
}
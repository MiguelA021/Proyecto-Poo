package upm.etsisi.poo.es;
import static org.junit.Assert.*;
import org.junit.Test;
import upm.etsisi.poo.es.Product.BasicProduct;
import upm.etsisi.poo.es.Product.Event;
import upm.etsisi.poo.es.Product.Meeting;
import upm.etsisi.poo.es.Product.Product;

public class TicketTest {
    //public boolean ticketAdd(Product product, int amount)
    //Test: Comprobar que se añada el producto cuando este OPEN o EMPTY
    @Test
    public void ticketAddTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(212123);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new BasicProduct(2, "Caalcetines UPM", type.CLOTHES, 5);
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1,9);
        assertEquals(9, ticketTest.amount);
        assertEquals(Status.OPEN, ticketTest.getStatus());
    }


    //Test: Comprobar que no se puede añadar cuando este CLOSED
    @Test
    public void ticketCloseAddTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(212123);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new BasicProduct(2, "Caalcetines UPM", type.CLOTHES, 5);
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1,9);
        ticketTest.ticketPrint(true);
        boolean result = ticketTest.ticketAdd(product2, 4);
        assertEquals(9, ticketTest.amount);
        assertEquals(Status.CLOSED, ticketTest.getStatus());
        assertFalse("No se puede añadir productos al ticket si su estado es CLOSED", result);
    }

    //Test: Comprobar su funcionamiento con una fecha invalida de Event
    @Test
    public void ticketAddImpossibleDateTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(212123);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new Meeting(23457, "Graduacion ETSISI", 40, "2025-12-31"); //12h minimo, IMPOSIBLE
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1, 5);
        boolean result = ticketTest.ticketAdd(product2, 1);
        assertFalse("No se pueden añadir productos tipo Meeting con menos de 12h de antelacion", result);
    }

    //Test: Que pasa si amount del parametro es 0 en amount: EXPECTED - NO SE PUEDE AÑADIR
    @Test
    public void ticketAddAmountZeroTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(212123);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        storeTest.prodAdd(product1);
        boolean result = ticketTest.ticketAdd(product1, 0);
        assertFalse("Un producto no puede añadirse si su cantidad es 0", result);
    }

    //Test: Que pasa si el amount ya llego al limite y se intenta añadir un producto mas a ticket¿?: EXPECTED - NO SE PUEDE AÑADIR
    @Test
    public void ticketAddLimitAmountTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(113361);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new Meeting(23457, "Graduacion ETSISI", 40, "2026-12-31");
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1, 100);
        boolean result = ticketTest.ticketAdd(product2, 1);
        assertFalse("No se puede añadir el producto ya que sobrepasa el limite de productos permitidos", result);
    }

    //Test: que pasa si this.amount=95 y amount = 6: EXPECTED - NO SE PUEDE AÑADIR
    @Test
    public void ticketAddOverAmountTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(113361);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new BasicProduct(2, "Caalcetines UPM", type.CLOTHES, 5);
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1, 95);
        boolean result = ticketTest.ticketAdd(product2, 6);
        assertFalse("No se puede añadir el producto ya que sobrepasa el limite de productos permitidos", result);
        assertEquals(100, ticketTest.amount);
    }

    //public Product ticketRemove(int prodId)
    //Test: Comprobar que se elimine un producto de ticket a traves de su ID. Hacerlo para
    //los tres tipos de productos: Event; Personalizable; BasicProduct

}


//TICKET.JAVA
/*
public String ticketPrint(boolean close)
Test: Comprobar formato tanto en CLOSE, EMPTY y ACTIVE

public String formatList()
Test: Comprobar buen formato

public String toStringNew()
Test: Comprobar el formato
*/
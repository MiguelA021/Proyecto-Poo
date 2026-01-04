package upm.etsisi.poo.es;
import static org.junit.Assert.*;
import org.junit.Test;
import upm.etsisi.poo.es.Product.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketTest {
    /**
     * Coming from: public boolean ticketAdd() with the next dates: "Product" and "int" amount
     * Test: Comprobar que se añada el producto cuando este OPEN o EMPTY
     */
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

    /**
     * Test: Comprobar que no se pueda añadir un amount mas grande que la maxPeople de EVENT
     */
    @Test
    public void ticketAddImpossibleAmountTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(212123);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new Meeting(23457, "Graduacion ETSISI", 40, "2025-12-31", 90);
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1,9);
        boolean notShouldAdd = ticketTest.ticketAdd(product2,92);
        assertFalse("No debe añadirse una cantidad superior a maxpeople", notShouldAdd);
        assertEquals(9, ticketTest.amount);
        assertEquals(Status.OPEN, ticketTest.getStatus());
    }


    /**
     * Test: Comprobar que no se puede añadar cuando este CLOSED
     */
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

    /**
     * Test: Comprobar su funcionamiento con una fecha invalida de Event
     */
    @Test
    public void ticketAddImpossibleDateTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(212123);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new Meeting(23457, "Graduacion ETSISI", 40, "2025-12-31", 90);
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1, 5);
        boolean result = ticketTest.ticketAdd(product2, 1);
        assertFalse("No se pueden añadir productos tipo Meeting con menos de 12h de antelacion", result);
    }

    /**
     * Test: Que pasa si amount del parametro es 0 en amount: EXPECTED - NO SE PUEDE AÑADIR
     */
    @Test
    public void ticketAddAmountZeroTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(212123);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        storeTest.prodAdd(product1);
        boolean result = ticketTest.ticketAdd(product1, 0);
        assertFalse("Un producto no puede añadirse si su cantidad es 0", result);
    }

    /**
     * Test: Que pasa si el amount ya llego al limite y se intenta añadir un producto mas a ticket¿?: EXPECTED - NO SE PUEDE AÑADIR
     */
    @Test
    public void ticketAddLimitAmountTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(113361);
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new Meeting(23457, "Graduacion ETSISI", 40, "2026-12-31", 10);
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1, 100);
        boolean result = ticketTest.ticketAdd(product2, 1);
        assertFalse("No se puede añadir el producto ya que sobrepasa el limite de productos permitidos", result);
    }

    /**
     * Test: que pasa si this.amount=95 y amount = 6: EXPECTED - NO SE PUEDE AÑADIR
     */
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


    /**
     * Coming from: public Product ticketRemove(int prodId)
     * Test: Comprobar que se elimine un producto de ticket a traves de su ID. Hacerlo para Event
     */
    @Test
    public void ticketRemoveEventTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(109102);
        Product product1 = new BasicProduct(1, "Pantalones UPM", type.CLOTHES, 15);
        Product product2 = new Meeting(23129, "Graduacion ETSISI", 31, "2026-02-21", 50);
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1, 11);
        ticketTest.ticketAdd(product2, 8);
        Product result = ticketTest.ticketRemove(23129);
        assertEquals(product2, result);
        assertEquals(11, ticketTest.amount);
    }

    /**
     * Coming from: public Product ticketRemove(int prodId)
     * Test: Comprobar que se elimine un producto de ticket a traves de su ID. Hacerlo para Personalizable
     */
    @Test
    public void ticketRemovePersonalizableTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(109102);
        PersonalizedProduct product1 = new PersonalizedProduct(1, "Pantalones UPM", type.CLOTHES, 15,  4);
        Product product2 = new Meeting(23129, "Graduacion ETSISI", 31, "2026-02-21", 79);
        product1.addPersonalized("Rojo");
        product1.addPersonalized("Verde");
        product1.addPersonalized("Azul");
        product1.addPersonalized("Violeta");
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1, 11);
        ticketTest.ticketAdd(product2, 56);
        Product result = ticketTest.ticketRemove(1);
        assertEquals(product1, result);
        assertEquals(1, ticketTest.amount);
    }

    /**
     * Coming from: public Product ticketRemove(int prodId)
     * Test: Comprobar que se elimine un producto de ticket a traves de su ID. Hacerlo para BasicProduct
     */
    @Test
    public void ticketRemoveBasicProductTest(){
        Store storeTest = new Store();
        Ticket ticketTest = new Ticket(109102);
        Product product1 = new BasicProduct(1, "Pantalones UPM", type.CLOTHES, 15);
        Product product2 = new Meeting(23129, "Graduacion ETSISI", 31, "2026-02-21", 24);
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1, 11);
        ticketTest.ticketAdd(product2, 6);
        Product result = ticketTest.ticketRemove(1);
        assertEquals(product1, result);
        assertEquals(1, ticketTest.amount);
    }

    /**
     * Coming from: public String ticketPrint()
     * Test: Comprobar que coincide el formato y que el STATUS sea CLOSED
     */
    @Test
    public void ticketPrintTest(){
        Store storeTest = new Store();
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yy-MM-dd-HH:mm");
        String fechaFormateada = ahora.format(formateador);
        Ticket ticketTest = new Ticket(109102);
        Product product1 = new BasicProduct(1, "Pantalones UPM", type.CLOTHES, 15);
        Product product2 = new Meeting(23129, "Graduacion ETSISI", 31, "2026-02-21", 31);
        storeTest.prodAdd(product1);
        storeTest.prodAdd(product2);
        ticketTest.ticketAdd(product1, 11);
        ticketTest.ticketAdd(product2, 10);
        String resultPrint = ticketTest.ticketPrint(true);
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket : 109102-");
        sb.append(fechaFormateada).append("\n");
        sb.append("  {class:Meeting, id:23129, name:'Graduacion ETSISI', price:310.0, date of Event:2026-02-21, max people allowed:31, actual people in event:10}\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  {class:Product, id:1, name:'Pantalones UPM', category:CLOTHES, price:15,00} **discount -1,05\n");
        sb.append("  Total price: 475.000\n");
        sb.append("  Total discount: 11.550\n");
        sb.append("  Final price: 463.450");
        String expected = sb.toString();
        assertEquals(expected, resultPrint);
    }
}
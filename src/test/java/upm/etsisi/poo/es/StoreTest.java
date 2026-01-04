package upm.etsisi.poo.es;

import static org.junit.Assert.*;
import org.junit.Test;
import upm.etsisi.poo.es.Product.*;
import upm.etsisi.poo.es.User.*;
import upm.etsisi.poo.es.Store.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.SocketHandler;

public class StoreTest {

    /**
     * TEST QUE ME DEVUELVA UN PRODUCTO ENTRE LOS QUE ESTAN AÑADIDOS (3 PRODUCTOS AÑADIDOS)
     */
    @Test
    public void getProductTest(){
        Store objectTest = new Store();
        Product productTest = new BasicProduct(1, "Libro BBDD", type.BOOK, 25);
        Product productTest2 = new BasicProduct(2, "Sudadera tala:S UPM", type.CLOTHES, 15);
        Product productTest3 = new BasicProduct(3, "Iphone UPM", type.ELECTRONICS, 200);
        Product productTest4 = new BasicProduct(2, "Sudadera tala:S UPM", type.CLOTHES, 15);
        objectTest.prodAdd(productTest);
        objectTest.prodAdd(productTest2);
        objectTest.prodAdd(productTest3);
        objectTest.prodAdd(productTest4);
        Product productResult = objectTest.getProduct(2);
        assertEquals(productTest2, productResult);
    }

    /**
     * Test from public boolean addFood(int id, String name, int price, String expiryDate, int assistants)
     * SE USA CON GET PRODUCT PERO TIENE SU TEST SIMPLE de aumento de size
     */
    @Test
    public void addFoodTest(){
        Store objectTest = new Store();
        Product productTest = new Food(23458, "Cafeteria ETSISI", 5, "2025-12-21", 10);
        objectTest.prodAdd(productTest);
        assertEquals(1, objectTest.getProdAmount());
    }

    /**
     * Test from public boolean addMeeting(int id, String name, double price, String expiryDate, int assistants)
     * SE USA CON GET PRODUCT PERO TIENE SU TEST SIMPLE
     */
    @Test
    public void addMeetingTest(){
        Store objectTest = new Store();
        Product productTest = new Meeting(23457, "Graduacion ETSISI", 40, "2025-12-21", 80);
        objectTest.prodAdd(productTest);
        assertEquals(1, objectTest.getProdAmount());
    }

    /**
     * Test from public void addCustomer(String name, String dni, String email, int cashId)
     * SE USA CON REMOVE CUSTOMER Y ARRAYLIST PERO TIENE SU TEST SIMPLE
     */
    @Test
    public void addCustomerEnterpriseTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCustomer("Pepe3", "55630667S", "pepe1@upm.es", 1234567);
        objectTest.addCustomer("Marco", "55630668J", "marcoo@upm.es", 1234567);
        objectTest.addCustomer("Darkiel", "34670161A", "darkiel2@upm.es", 1234567);
        assertEquals(3, objectTest.customers.size());
    }

    /**
     * Test de ADDCUSTOMER PERO QUE NO SEA ENTERPRISE
     */
    @Test
    public void addCustomerNotEnterpriseTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCustomer("Pepe3", "926306770", "pepe1@upm.es", 1234567);
        objectTest.addCustomer("Marco", "516706681", "marcoo@upm.es", 1234567);
        objectTest.addCustomer("Darkiel", "346701612", "darkiel2@upm.es", 1234567);
        assertEquals(3, objectTest.customers.size());
    }


    /**
     * Test from public Cashier searchCasherById(int id)
     * TEST QUE ME COMPRUEBA QUE HAYA UN CAJERO ENTRE LOS 3 QUE SE HA AÑADIDO
     */
    @Test
    public void searchCasherByIdTest(){
        Store objectTest = new Store();
        int id = 1234567;
        objectTest.addCasher(1234567, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCasher(2135597, "AndresCurro7", "andresCu77@upm.es");
        objectTest.addCasher(7226967, "AndresCurro9", "andresCu99@upm.es");
        assertEquals(objectTest.cashers.get(id), objectTest.searchCasherById(id));
    }

    /**
     * Test from public int dniToId(String dni)
     * TEST QUE ME COMPRUEBA LA CONVERSION
     * FALLO ARREGLADO: Al hacer id += c hace la suma del CHAR en ASCII, hay que convertir c en un int: id += (c - '0')
     */
    @Test
    public void dniToIdTest(){
        Store objectTest = new Store();
        String dniTest = "55630667S";
        assertEquals(55630667, objectTest.dniToId(dniTest));
    }

    /**
     * Test from public boolean removeCustomer(String dni)
     * TEST QUE ME COMPRUEBA LA ELIMINACION DE UN CLIENTE ENTRE LOS 3 QUE SE AÑADIO
     */
    @Test
    public void removeCustomerTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCustomer("Pepe3", "55630667S", "pepe1@upm.es", 1234567);
        objectTest.addCustomer("Marco", "55630668J", "marcoo@upm.es", 1234567);
        objectTest.addCustomer("Darkiel", "34670161A", "darkiel2@upm.es", 1234567);
        objectTest.removeCustomer("55630667S");
        assertEquals(2, objectTest.customers.size());
    }

    /**
     * Test from public void listCustomers()
     * TEST QUE ME COMPRUEBA EL FORMATO CON DOS CLIENTES AÑADIDOS A TRAVES DE STRINGBUILDER RESULTADO Y ESPERADO
     * FALLO ARREGLADO: Debe ser USER en vez de CLIENT
     */
    @Test
    public void listCustomersTest(){
        Store objectTest = new Store();

        objectTest.addCasher(1234567, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCustomer("Pepe3", "55630667S", "pepe1@upm.es", 1234567);
        objectTest.addCustomer("Marco", "55630668J", "marcoo@upm.es", 1234567);
        objectTest.addCustomer("Darkiel", "34670161A", "darkiel2@upm.es", 1234567);

        PrintStream originalOut = new PrintStream(System.out);
        ByteArrayOutputStream capturador = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturador));

        objectTest.listCustomers();

        String resultFunction = capturador.toString();

        StringBuilder sc = new StringBuilder();
        sc.append("Client:").append(System.lineSeparator());
        sc.append("  USER{identifier='34670161A', name='Darkiel', email='darkiel2@upm.es', cash=UW1234567}").append(System.lineSeparator());
        sc.append("  USER{identifier='55630668J', name='Marco', email='marcoo@upm.es', cash=UW1234567}").append(System.lineSeparator());
        sc.append("  USER{identifier='55630667S', name='Pepe3', email='pepe1@upm.es', cash=UW1234567}").append(System.lineSeparator());
        sc.append("client list: ok").append(System.lineSeparator());
        String salidaEsperada = sc.toString();

        assertEquals(salidaEsperada, resultFunction);
        System.setOut(originalOut);
    }

    /**
     * Test from public void addTicketOnCashier(Integer idTicket, int idCashier, int idCustomer)
     * TEST QUE ME COMPRUEBA SI UN TICKET PERTENECE A UN CAJERO CON EL DICCIONARIO DE STORE A PARTIR DEL ID DEL CAJERO Y ID DEL CLIENTE
     */
    @Test
    public void addTicketOnCashierTest(){
        Store objectTest = new Store();
        int idCasher = 1234567;
        Integer idTicket = 212123;
        objectTest.addCasher(idCasher, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCustomer("Pepe3", "55630667S", "pepe1@upm.es", idCasher);
        Ticket expected = new Ticket(idTicket);
        objectTest.addTicketOnCashier(idTicket, idCasher, 55630667);
        Ticket result = objectTest.cashers.get(idCasher).getTicketById(idTicket);
        assertEquals(expected.getId(), result.getId());
    }


    /**
     * Test from public boolean addCasher(Integer id, String name, String email)
     * TEST que me comprueba que SI el id del nuevo cajero ya existe en cashers NO PERMITE LA ADICION
     */
    @Test
    public void addCashierWithIdExistentTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro", "andresCurr@upm.es");
        objectTest.addCasher(7654321, "PepeCurro", "pepeCurr@upm.es");
        objectTest.addCasher(1726354, "SergioCurro", "sergioCurr@upm.es");

        boolean result = objectTest.addCasher(1726354, "MiguelCurro", "miguelCurr@upm.es");
        assertFalse("It mustn't exist an Cashier with same id that other cashier", result);
    }

    /**
     * Test from public boolean removeCasher(int id)
     * TEST QUE ME COMPRUEBA QUE ESTAN TODOS LOS CAJEROS MENOS EL ELIMINADO COMPARANDO el tamaño de CASHIERS
     */
    @Test
    public void removeCashierTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro", "andresCurr@upm.es");
        objectTest.addCasher(7654321, "PepeCurro", "pepeCurr@upm.es");
        objectTest.addCasher(1726354, "SergioCurro", "sergioCurr@upm.es");
        objectTest.removeCasher(1234567);
        assertEquals(2, objectTest.cashers.size());
    }


    /**
     * Test from public void listCashers()
     * TEST QUE ME COMPRUEBA CON  EL ASSERT DE STRING EL MISMO STRING DE UNO CON LOS CAJEROS AÑADIDOS RESULTADO DE LA FUNCION Y OTRO PROPIO NUESTRO, USAMOS UN LIST CASHERS DE 3
     */
    @Test
    public void listCashiersTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro", "andresCurr@upm.es");
        objectTest.addCasher(7654321, "PepeCurro", "pepeCurr@upm.es");
        objectTest.addCasher(1726354, "SergioCurro", "sergioCurr@upm.es");

        PrintStream originalOut = new PrintStream(System.out);
        ByteArrayOutputStream capturador = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturador));
        objectTest.listCashers();

        String actual = capturador.toString();

        StringBuilder sb = new StringBuilder();
        sb.append("Cash:").append(System.lineSeparator());
        sb.append("  Cash{identifier='UW1234567', name='AndresCurro', email='andresCurr@upm.es'}").append(System.lineSeparator());
        sb.append("  Cash{identifier='UW7654321', name='PepeCurro', email='pepeCurr@upm.es'}").append(System.lineSeparator());
        sb.append("  Cash{identifier='UW1726354', name='SergioCurro', email='sergioCurr@upm.es'}").append(System.lineSeparator());
        sb.append("cash list: ok").append(System.lineSeparator());

        String expected = sb.toString();
        assertEquals(expected, actual);
        System.setOut(originalOut);
    }

    /**
     * Test from public boolean prodRemove(int id)
     * TEST QUE ME COMPRUEBA LA ELIMINACION DE UN PRODUCTO TRAS 3 QUE HABIAN
     * */
    @Test
    public void prodRemoveTest(){
        Store objectTest = new Store();
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new BasicProduct(2, "Calcetines UPM", type.CLOTHES, 10);
        Product product3 = new Meeting(23457, "Graduacion ETSISI", 40, "2025-12-21", 60);
        Product product4 = new Meeting(33316, "Fiesta ETSISI", 80, "2026-09-02", 90);
        objectTest.prodAdd(product1);
        objectTest.prodAdd(product2);
        objectTest.prodAdd(product3);
        objectTest.prodAdd(product4);
        objectTest.prodRemove(23457);
        assertEquals(3, objectTest.getProdAmount());
    }

    /**
     * Test from public Product updateType(int id, type category)
     * TEST QUE ME COMRUEBA EL CAMBIO DE TIPO
     */
    @Test
    public void updateTypeTest(){
        Store objectTest = new Store();
        Product productTest = new BasicProduct(1, "Libro POO", type.MERCH, 15);
        objectTest.prodAdd(productTest);
        Product productUpdate = objectTest.updateType(productTest.getId(), type.CLOTHES);
        BasicProduct productUpdateBasic = (BasicProduct) productUpdate;
        assertEquals(type.CLOTHES, productUpdateBasic.getCategory());
    }


    /**
     * Test from public Product updateName(int id, String name)
     * TEST QUE ME COMRUEBA EL CAMBIO DE NOMBRE
     */
    @Test
    public void updateNameTest(){
        Store objectTest = new Store();
        Product productTest = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        objectTest.prodAdd(productTest);
        Product updateProductTest = objectTest.updateName(productTest.getId(), "Libro Algoritmica");
        BasicProduct updateBasicProductTest = (BasicProduct) updateProductTest;
        assertEquals("Libro Algoritmica", updateBasicProductTest.getName());
    }

    @Test
    public void updateNameEventTest(){
        Store objectTest = new Store();
        Product productTest = new Meeting(23457, "Graduacion ETSISI", 40.0, "2025-12-21", 10);
        objectTest.prodAdd(productTest);
        Product updateProductTest = objectTest.updateName(productTest.getId(), "Graduacion Teleco");
        Event updateBasicProductTest = (Event) updateProductTest;
        assertEquals("Graduacion Teleco", updateBasicProductTest.getName());
    }

    /**
     * Test from public Product updatePrice(int id, double price)
     * TEST QUE ME COMRUEBA EL CAMBIO DE PRECIO
     */
    @Test
    public void updatePriceTest(){
        Store objectTest = new Store();
        Product productTest = new BasicProduct(1, "Libro SQL", type.BOOK, 25.0);
        objectTest.prodAdd(productTest);
        Product updateProductTest = objectTest.updatePrice(productTest.getId(), 30.0);
        BasicProduct updateBasicProductTest = (BasicProduct) updateProductTest;
        assertEquals(30.0, updateBasicProductTest.getPrice(), 0.00001);
    }

    @Test
    public void updatePriceEventTest(){
        Store objectTest = new Store();
        Product productTest = new Meeting(23457, "Graduacion ETSISI", 40.0, "2025-12-21", 45);
        objectTest.prodAdd(productTest);
        Product updateProductTest = objectTest.updatePrice(productTest.getId(), 30d);
        Event updateBasicProductTest = (Event) updateProductTest;
        assertEquals(30d, updateBasicProductTest.getPrice(), 0.00001);
    }


    /**
     * Test from public Cashier getCasher(int cashId)
     * PARA COMPROBAR EL CAJERO COMO EN GET PRODUCT CON 5 CAJEROS
     */
    @Test
    public void getCashierTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro", "andresCurr@upm.es");
        objectTest.addCasher(2194567, "PepeCurro", "pepeCurr@upm.es");
        objectTest.addCasher(1274761, "JoaquinCurro", "joaquinCurr@upm.es");
        objectTest.addCasher(1274761, "WoodzCurro", "woodzCurr@upm.es");
        objectTest.addCasher(1221741, "BryantCurro", "bryantCurr@upm.es");
        Cashier actual = objectTest.getCasher(1274761);
        assertEquals("JoaquinCurro", actual.getName());
    }


    /**
     * Test de que no permite hacer PRODADD cuando este lleno
     */
    @Test
    public void prodAddOnMaxSizeTest(){
        Store objeto = new Store();
        int capacidadMaxima = objeto.getMAX_PRODUCT();
        for (int i = 0; i < capacidadMaxima; i++) {
            objeto.prodAdd(new BasicProduct(i+1, "Camiseta talla:M UPM", type.CLOTHES, 15));
        }
        BasicProduct noPermitido = new BasicProduct(capacidadMaxima, "Libro POO", type.BOOK, 25);
        boolean result = objeto.prodAdd(noPermitido);
        assertFalse("El array de ProductList esta lleno", result);
    }

    /**
     * TEST QUE ME VERIFIQUE QUE AUMENTA EL TAMAÑO DE PRODUCTLIST
     */
    @Test
    public void prodAddSizeTest(){
        Store objectTest = new Store();
        Product productTest = new BasicProduct(1, "Libro BBDD", type.BOOK, 25);
        int size = 0;
        objectTest.prodAdd(productTest);
        assertEquals(objectTest.getProdAmount(), size+1);
    }

}
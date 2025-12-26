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

    //TEST QUE ME DEVUELVA UN PRODUCTO ENTRE LOS QUE ESTAN AÑADIDOS (3 PRODUCTOS AÑADIDOS)
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

    //public boolean addFood(int id, String name, int price, String expiryDate, int assistants)
    //SE USA CON GET PRODUCT PERO TIENE SU TEST SIMPLE de aumento de size 23458 "Cafeteria ETSISI" 5 2025-12-21 300

    @Test
    public void addFoodTest(){
        Store objectTest = new Store();
        Product productTest = new Food(23458, "Cafeteria ETSISI", 5, "2025-12-21");
        objectTest.prodAdd(productTest);
        assertEquals(1, objectTest.getProdAmount());
    }

    //public boolean addMeeting(int id, String name, double price, String expiryDate, int assistants)
    //SE USA CON GET PRODUCT PERO TIENE SU TEST SIMPLE 23457 "Graduacion ETSISI" 40 2025-12-21 30

    @Test
    public void addMeetingTest(){
        Store objectTest = new Store();
        Product productTest = new Meeting(23457, "Graduacion ETSISI", 40, "2025-12-21");
        objectTest.prodAdd(productTest);
        assertEquals(1, objectTest.getProdAmount());
    }

    //public void addCustomer(String name, String dni, String email, int cashId)
    //SE USA CON REMOVE CUSTOMER Y ARRAYLIST PERO TIENE SU TEST SIMPLE "Pepe3" 55630667S pepe1@upm.es U1234567
    // UW1234567 "pepecurro3" pepe0@upm.es

    //FALLO: preferible que el CASHIERID sea STRING
    @Test
    public void addCustomerEnterpriseTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCustomer("Pepe3", "55630667S", "pepe1@upm.es", 1234567);
        objectTest.addCustomer("Marco", "55630668J", "marcoo@upm.es", 1234567);
        objectTest.addCustomer("Darkiel", "34670161A", "darkiel2@upm.es", 1234567);
        assertEquals(3, objectTest.customers.size());
    }

    //Test de ADDCUSTOMER PERO QUE NO SEA ENTERPRISE
    @Test
    public void addCustomerNotEnterpriseTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCustomer("Pepe3", "926306770", "pepe1@upm.es", 1234567);
        objectTest.addCustomer("Marco", "516706681", "marcoo@upm.es", 1234567);
        objectTest.addCustomer("Darkiel", "346701612", "darkiel2@upm.es", 1234567);
        assertEquals(3, objectTest.customers.size());
    }

    //TEST DE ADD CUSTOMER QUE NO PERMITA UN DNI DE DIGITOS MENORES A 9 EN CASO DE NIF

    //CORREGIR CODIGO EN STORE PARA QUE ESTE TEST SALGA BIEN
    @Test
    public void addCorrectCustomerNotEnterpriseTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCustomer("Pepe3", "9263067", "pepe1@upm.es", 1234567);
        objectTest.addCustomer("Marco", "51670661", "marcoo@upm.es", 1234567);
        objectTest.addCustomer("Darkiel", "346701612", "darkiel2@upm.es", 1234567);
        assertEquals(1, objectTest.customers.size());
    }

    //public Cashier searchCasherById(int id)
    //TEST QUE ME COMPRUEBA QUE HAYA UN CAJERO ENTRE LOS 3 QUE SE HA AÑADIDO
    @Test
    public void searchCasherByIdTest(){
        Store objectTest = new Store();
        int id = 1234567;
        objectTest.addCasher(1234567, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCasher(2135597, "AndresCurro7", "andresCu77@upm.es");
        objectTest.addCasher(7226967, "AndresCurro9", "andresCu99@upm.es");
        assertEquals(objectTest.cashers.get(id), objectTest.searchCasherById(id));
    }

    //public int dniToId(String dni)
    //TEST QUE ME COMPRUEBA LA CONVERSION

    //FALLO: Al hacer id += c hace la suma del CHAR en ASCII, hay que convertir c en un int: id += (c - '0')
    @Test
    public void dniToIdTest(){
        Store objectTest = new Store();
        String dniTest = "55630667S";
        assertEquals(55630667, objectTest.dniToId(dniTest));
    }

    //public boolean removeCustomer(String dni)
    //TEST QUE ME COMPRUEBA LA ELIMINACION DE UN CLIENTE ENTRE LOS 3 QUE SE AÑADIO

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

    //public void listCustomers()
    //	TEST QUE ME COMPRUEBA EL FORMATO CON DOS CLIENTES AÑADIDOS A TRAVES DE STRINGBUILDER RESULTADO Y ESPERADO

    //FALLO: Debe ser USER en vez de CLIENT
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

   // public void addTicketOnCashier(Integer idTicket, int idCashier, int idCustomer)
    // TEST QUE ME COMPRUEBA SI UN TICKET PERTENECE A UN CAJERO CON EL DICCIONARIO DE STORE A PARTIR DEL ID DEL CAJERO Y ID DEL CLIENTE

    //FALLO: Mejor que el metodo "addTicketOnCashier" permita que el ID del Customer contenga el DNI con o sin letra. Luego en el metodo
    //se hace la conversion con el dniToId de Store

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


    //SI no nos dan ID, llamar al metodo y que solo se permita la adicion si el id generado
    //NO EXISTE EN CASHERS

    //public boolean addCasher(Integer id, String name, String email)
    //TEST que me comprueba que SI el id del nuevo cajero ya existe en cashers. NO PERMITE LA ADICION
    @Test
    public void addCashierWithIdExistentTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro", "andresCurr@upm.es");
        objectTest.addCasher(7654321, "PepeCurro", "pepeCurr@upm.es");
        objectTest.addCasher(1726354, "SergioCurro", "sergioCurr@upm.es");

        boolean result = objectTest.addCasher(1726354, "MiguelCurro", "miguelCurr@upm.es");
        assertFalse("It mustn't exist an Cashier with same id that other cashier", result);
    }

    //public boolean removeCasher(int id)
    //TEST QUE ME COMPRUEBA QUE ESTAN TODOS LOS CAJEROS MENOS EL ELIMINADO COMPARANDO el tamaño de CASHIERS
    @Test
    public void removeCashierTest(){
        Store objectTest = new Store();
        objectTest.addCasher(1234567, "AndresCurro", "andresCurr@upm.es");
        objectTest.addCasher(7654321, "PepeCurro", "pepeCurr@upm.es");
        objectTest.addCasher(1726354, "SergioCurro", "sergioCurr@upm.es");
        objectTest.removeCasher(1234567);
        assertEquals(2, objectTest.cashers.size());
    }


    //public void listCashers()
    //TEST QUE ME COMPRUEBA CON  EL ASSERT DE STRING EL MISMO STRING DE UNO CON LOS CAJEROS AÑADIDOS RESULTADO DE LA FUNCION Y OTRO PROPIO NUESTRO, USAMOS UN LIST CASHERS DE 3
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



    //public void listTicketsOnCasher(int id)
    //	TEST PARECIDO AL LIST CASHERS Y LIST CUSTOMERS

    /*
    Ticket List:
      25-12-07-22:32-47570 - EMPTY //SIN ID EN LA ENTRADA
      212123 - OPEN //CON ID EN LA ENTRADA
      212121-25-12-07-22:32 - CLOSE //CON ID EN LA ENTRADA
    ticket list: ok
     */
    @Test
    public void listTicketsOnCashierTest(){
        Store objectTest = new Store();
        int idCasher = 1234567;
        Integer idTicket1 = 212123;
        Integer idTicket2 = 111139;
        Integer idTicket3 = 222222;
        Ticket ticket1 = new Ticket(idTicket1);
        Ticket ticket2 = new Ticket(idTicket2);
        Ticket ticket3 = new Ticket(idTicket3);
        objectTest.addCasher(idCasher, "AndresCurro5", "andresCurr@upm.es");
        objectTest.addCustomer("Pepe3", "55630667S", "pepe1@upm.es", idCasher);
        objectTest.addCustomer("Manin", "11111111D", "darkiel@upm.es", idCasher);
        objectTest.addCustomer("Ludo", "22222222E", "ludot@upm.es", idCasher);
        objectTest.addTicketOnCashier(idTicket1, idCasher, 55630667);
        objectTest.addTicketOnCashier(idTicket2, idCasher, 11111111);
        objectTest.addTicketOnCashier(idTicket3, idCasher, 22222222);

        PrintStream originalOut = new PrintStream(System.out);
        ByteArrayOutputStream capturador = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturador));
        objectTest.listTicketsOnCasher(idCasher);
        String actual = capturador.toString();

        StringBuilder sd = new StringBuilder();
        sd.append("  25-12-26-12:58-").append(idTicket2).append(" - EMPTY").append(System.lineSeparator());
        sd.append("  25-12-26-12:58-").append(idTicket1).append(" - EMPTY").append(System.lineSeparator());
        sd.append("  25-12-26-12:58-").append(idTicket3).append(" - EMPTY").append(System.lineSeparator());


        String expected = sd.toString();

        assertEquals(expected, actual);
        System.setOut(originalOut);
    }



    //public boolean prodRemove(int id)
    //	TEST QUE ME COMPRUEBA LA ELIMINACION DE UN PRODUCTO TRAS 3 QUE HABIAN

    //prod add 1 "Libro POO" BOOK 25 //BASIC PRODUCT
    //prod addMeeting 23457 "Graduacion ETSISI" 40 2025-12-21 30

    @Test
    public void prodRemoveTest(){
        Store objectTest = new Store();
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new BasicProduct(2, "Calcetines UPM", type.CLOTHES, 10);
        Product product3 = new Meeting(23457, "Graduacion ETSISI", 40, "2025-12-21");
        Product product4 = new Meeting(33316, "Fiesta ETSISI", 80, "2026-09-02");
        objectTest.prodAdd(product1);
        objectTest.prodAdd(product2);
        objectTest.prodAdd(product3);
        objectTest.prodAdd(product4);
        objectTest.prodRemove(23457);
        assertEquals(3, objectTest.getProdAmount());
    }


    //public void prodList()
    //	TEST QUE ME COMPRUEBA FORMATO, TANTO EL QUE USA ESTA FUNCION COMO EL STRINGBUILDER PROPIO

    //FALLO: Las salidas tanto esperadas como actuales son identicas, es fallo de IntelliJ¿?

    @Test
    public void prodListTest(){
        Store objectTest = new Store();
        Product product1 = new BasicProduct(1, "Libro SQL", type.BOOK, 25);
        Product product2 = new BasicProduct(2, "Calcetines UPM", type.CLOTHES, 10);
        Product product3 = new Meeting(23457, "Graduacion ETSISI", 40.0, "2025-12-21");
        Product product4 = new Meeting(33316, "Fiesta ETSISI", 80.0, "2026-09-02");
        objectTest.prodAdd(product1);
        objectTest.prodAdd(product2);
        objectTest.prodAdd(product3);
        objectTest.prodAdd(product4);
        PrintStream originalOut = new PrintStream(System.out);
        ByteArrayOutputStream cazador = new ByteArrayOutputStream();
        System.setOut(new PrintStream(cazador));
        objectTest.prodList();
        String actual = cazador.toString();
        StringBuilder tec = new StringBuilder();
        tec.append("Catalog:").append(System.lineSeparator());
        tec.append("  {class:Product, id:2, name:'Calcetines UPM', category:CLOTHES, price:10.0}").append(System.lineSeparator());
        tec.append("  {class:Meeting, id:33316, name:'Fiesta ETSISI', price:80.0, date of Event:2026-09-02, max people allowed:100}").append(System.lineSeparator());
        tec.append("  {class:Meeting, id:23457, name:'Graduacion ETSISI', price:40.0, date of Event:2025-12-21, max people allowed:100}").append(System.lineSeparator());
        tec.append("  {class:Product, id:1, name:'Libro SQL', category:BOOK, price:25.0}").append(System.lineSeparator());
        tec.append("prod list: ok").append(System.lineSeparator());
        String expected = tec.toString();
        assertEquals(expected, actual);
        System.setOut(originalOut);
    }


    //public Product updateType(int id, type category)
    //	TEST QUE ME COMRUEBA EL CAMBIO DE TIPO

    @Test
    public void updateTypeTest(){
        Store objectTest = new Store();
        Product productTest = new BasicProduct(1, "Libro POO", type.MERCH, 15);
        objectTest.prodAdd(productTest);
        Product productUpdate = objectTest.updateType(productTest.getId(), type.CLOTHES);
        BasicProduct productUpdateBasic = (BasicProduct) productUpdate;
        assertEquals(type.CLOTHES, productUpdateBasic.getCategory());
    }







    //public Product updateName(int id, String name)
    //	TEST QUE ME COMRUEBA EL CAMBIO DE NOMBRE










    //public Product updatePrice(int id, double price)
    //	TEST QUE ME COMRUEBA EL CAMBIO DE PRECIO








    //public void ticketList()
    //	LO MISMO QUE CON LAS OTRAS COMPARACIONES DE FORMATO








    //public Cashier getCasher(int cashId)
    //	PARA COMPROBAR EL CAJERO COMO EN GET PRODUCT CON 5 CAJEROS










    //Test de que no permite hacer PRODADD cuando este lleno
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

    //TEST QUE ME VERIFIQUE QUE AUMENTA EL TAMAÑO DE PRODUCTLIST
    @Test
    public void prodAddSizeTest(){
        Store objectTest = new Store();
        Product productTest = new BasicProduct(1, "Libro BBDD", type.BOOK, 25);
        int size = 0;
        objectTest.prodAdd(productTest);
        assertEquals(objectTest.getProdAmount(), size+1);
    }

}
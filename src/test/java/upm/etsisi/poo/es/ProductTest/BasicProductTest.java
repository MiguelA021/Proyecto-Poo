package upm.etsisi.poo.es.ProductTest;
import static org.junit.Assert.*;
import org.junit.Test;
import upm.etsisi.poo.es.Product.BasicProduct;
import upm.etsisi.poo.es.type;


public class BasicProductTest {
    /**
     * Test: Comprueba que no haya un nombre mas largo que 100 en longitud
     */
    @Test
    public void checkNameTest(){
        BasicProduct product1 = new BasicProduct(1, "Chaqueta táctica multifuncional de polímero reforzado con nanotecnología repelente al agua y cuarenta y dos bolsillos ocultos diseñados ergonómicamente para el almacenamiento seguro de dispositivos de alta gama", type.BOOK, 30);
        assertNull("No puede existir un producto con un nombre de mas de 100 caracteres", product1.getName());
    }

    /**
     * Test: Comprueba que no haya un nombre vacio
     */
    @Test
    public void checkEmptyNameTest(){
        BasicProduct product1 = new BasicProduct(1, "", type.BOOK, 30);
        assertNull("No puede existir un producto con un nombre vacio", product1.getName());
    }

    /**
     * Test: Comprueba que el precio de un producto no debe ser igual a 0 y en ese caso, debe ser 1
     */
    @Test
    public void checkPrice0Test(){
        BasicProduct product1 = new BasicProduct(1, "Libro POO V2", type.BOOK, 0);
        assertEquals(1 ,product1.getPrice(), 0.00001);
    }

    /**
     * Test: Comprueba que el precio de un producto no debe ser menor a 0 y en ese caso, debe ser 1
     */
    @Test
    public void checkPriceLowerThan0Test(){
        BasicProduct product1 = new BasicProduct(1, "Libro POO V2", type.BOOK, -2);
        assertEquals(1 ,product1.getPrice(), 0.00001);
    }

    /**
     * Test: Comprueba un buen formato del BasicProduct
     */
    @Test
    public void checkFormatString(){
        BasicProduct product1 = new BasicProduct(1, "Libro POO V2", type.BOOK, 30);
        String actual = product1.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("{class:Product, id:1, name:'Libro POO V2', category:BOOK, price:30.0}\n");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }


    /**
     * Test: Comprueba un buen formato del BasicProduct tras el descuento
     * Nota: Aunque haya error por el salto de linea, si funciona
     */
    @Test
    public void checkFormatStringDiscount(){
        BasicProduct product1 = new BasicProduct(1, "Libro POO V2", type.BOOK, 30);
        String actual = product1.toStringDiscount(3.0);
        StringBuilder sb = new StringBuilder();
        sb.append("{class:Product, id:1, name:'Libro POO V2', category:BOOK, price:30,00} **discount -3,00\n");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }
}
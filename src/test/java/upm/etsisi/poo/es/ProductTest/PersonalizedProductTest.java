package upm.etsisi.poo.es.ProductTest;
import org.junit.Test;
import upm.etsisi.poo.es.Product.PersonalizedProduct;
import upm.etsisi.poo.es.type;

import static org.junit.Assert.*;

public class PersonalizedProductTest {
    /**
     * Test: Comprueba que el formato de toString sea el esperado
     */
    @Test
    public void checkStringTest(){
        PersonalizedProduct product1 = new PersonalizedProduct(6, "Camiseta talla:L UPM", type.CLOTHES, 20, 4);
        String actual = product1.toString();
        String expected = "{class:ProductPersonalized, id:6, name:'Camiseta talla:L UPM', category:CLOTHES, price:20.0, maxPersonal:4}\n";
        assertEquals(expected, actual);
    }

    /**
     * Test: Comprueba que el formato de toString sea el esperado tras hacer el discount
     */
    @Test
    public void checkStringDiscountTest(){
        PersonalizedProduct product1 = new PersonalizedProduct(6, "Camiseta talla:L UPM", type.CLOTHES, 20, 4);
        product1.addPersonalized("red");
        product1.addPersonalized("blue");
        String actual = product1.toStringDiscount(1.68);
        String expected = "{class:ProductPersonalized, id:6, name:'Camiseta talla:L UPM', category:CLOTHES, price:24.0, maxPersonal:4, personalizationList:[red, blue]} **discount -1.680\n";
        assertEquals(expected, actual);
    }

    /**
     * Test: Comprueba que se añaden perfectamente las personalizaciones a traves del tamaño del array de personalizaciones
     */
    @Test
    public void addPersonalizedTest(){
        PersonalizedProduct product1 = new PersonalizedProduct(6, "Camiseta talla:L UPM", type.CLOTHES, 20, 4);
        product1.addPersonalized("red");
        product1.addPersonalized("blue");
        int sizeActual = product1.getPersonalizaciones().size();
        assertEquals(2, sizeActual);
    }

    /**
     * Test: Comprueba que no se pueden añadir mas personalizaciones viendo que el tamaño de personalizaciones no aumenta
     */
    @Test
    public void addOverNumberPersonalizedTest(){
        PersonalizedProduct product1 = new PersonalizedProduct(6, "Camiseta talla:L UPM", type.CLOTHES, 20, 4);
        product1.addPersonalized("red");
        product1.addPersonalized("blue");
        product1.addPersonalized("green");
        product1.addPersonalized("yellow");
        product1.addPersonalized("black");
        int sizeActual = product1.getPersonalizaciones().size();
        assertEquals(4, sizeActual);
    }

}
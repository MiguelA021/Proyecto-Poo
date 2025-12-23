package upm.etsisi.poo.es;

import static org.junit.Assert.*;
import org.junit.Test;
import upm.etsisi.poo.es.Product.BasicProduct;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Store.*;

public class StoreTest {

    //***Test de que no permite hacer PRODADD cuando este lleno
    @Test
    public void getProductTest(){
        Store objeto = new Store();
        int capacidadMaxima = objeto.getMAX_PRODUCT();
        for (int i = 0; i < capacidadMaxima; i++) {
            objeto.prodAdd(new BasicProduct(i+1, "Camiseta talla:M UPM", type.CLOTHES, 15));
        }
        BasicProduct noPermitido = new BasicProduct(capacidadMaxima, "Libro POO", type.BOOK, 25);
        boolean result = objeto.prodAdd(noPermitido);
        assertFalse("El array de ProductList esta lleno", result);
    }

    //TEST QUE ME DEVUELVA UN PRODUCTO CON GETPRODUCT



    //TEST QUE ME VERIFIQUE QUE AUMENTA EL TAMAÑO DE PRODUCTLIST




}

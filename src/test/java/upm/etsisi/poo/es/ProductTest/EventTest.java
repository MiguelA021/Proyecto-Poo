package upm.etsisi.poo.es.ProductTest;
import static org.junit.Assert.*;
import org.junit.Test;
import upm.etsisi.poo.es.Product.Event;
import upm.etsisi.poo.es.Product.Food;
import upm.etsisi.poo.es.Product.Product;
import upm.etsisi.poo.es.Store;

import java.time.LocalDateTime;
//La unica diferencia con Food o Meeting es el TIEMPO MINIMO

public class EventTest {
    //Test 1: Crear un objeto de Food o Meeting obligando a hacerlo con fecha valida, si no, NO DEBE CREARSE
    @Test
    public void objectFoodIncorrectDate(){
        Event productTest1 = new Food(23459, "Restaurante Asador", 50, "2025-12-21", 40);
        boolean result = productTest1.fechaValida(LocalDateTime.now());
        assertFalse("No debe existir un producto con una fecha invalida", result);
    }
    //Test 2: Comprobar que no permite una fecha incorrecta
    @Test
    public void objectFoodCorrectDate(){
        Event productTest1 = new Food(23459, "Restaurante Asador", 50, "2026-02-21", 40);
        boolean result = productTest1.fechaValida(LocalDateTime.now());
        assertTrue(result);
    }
    //Mismos metodos para Meeting
    @Test
    public void objectMeetingIncorrectDate(){
        //{class:Meeting, id:23457, name:'Graduacion ETSISI', price:40.0, date of Event:2025-12-21, max people allowed:30}
        Event productTest1 = new Food(23751, "Concierto ETSISI", 40, "2025-12-11", 70);
        boolean result = productTest1.fechaValida(LocalDateTime.now());
        assertFalse("No debe existir un producto con una fecha invalida", result);
    }
    //Test 2: Comprobar que no permite una fecha incorrecta
    @Test
    public void objectMeetingCorrectDate(){
        Event productTest1 = new Food(23751, "Concierto ETSISI", 10, "2026-01-12", 90);
        boolean result = productTest1.fechaValida(LocalDateTime.now());
        assertTrue(result);
    }
}

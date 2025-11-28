package upm.etsisi.poo.es.Product;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Food extends Event{
    private final static int TIEMPO_MINIMO= 3;

    public Food(int id, String name, double price,String expiracyDate, int maxPersonas){
        super(id, name, price, expiracyDate,maxPersonas);
    }
   public boolean fechaValida(LocalDateTime time) {
        long horas = ChronoUnit.HOURS.between(this.getExpiryDate(),time);
        boolean resul = true;
        if (horas < TIEMPO_MINIMO )resul = false;
        return resul;
    }
}

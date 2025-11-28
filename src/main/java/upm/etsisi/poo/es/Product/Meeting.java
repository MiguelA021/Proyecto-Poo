package upm.etsisi.poo.es.Product;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Meeting extends Event{
    private final static int TIEMPO_MINIMO= 12;
    public Meeting(int id, String name, double price,String expiracyDate, int maxPersonas){
        super(id, name, price, expiracyDate,maxPersonas);
    }

    @Override
    public boolean fechaValida(LocalDateTime time) {
        long horas = ChronoUnit.HOURS.between(this.getExpiryDate(),time);
        boolean resul = true;
        if (horas < TIEMPO_MINIMO )resul = false;
        return resul;
    }
}

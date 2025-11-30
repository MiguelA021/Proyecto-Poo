package upm.etsisi.poo.es.Product;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Food extends Event {
    private final static int TIEMPO_MINIMO = 3; // horas

    public Food(int id, String name, double price, String expiracyDate) {
        super(id, name, price, expiracyDate);
    }

    @Override
    public boolean fechaValida(LocalDateTime time) {
        // Primero comprobamos la validación general de Event (fecha >= hoy)
        if (!super.fechaValida(time)) {
            return false;
        }

        // Horas que faltan hasta el evento (evento - ahora)
        long horas = ChronoUnit.HOURS.between(time, this.getExpiryDate());

        // Válido si faltan al menos TIEMPO_MINIMO horas
        return horas >= TIEMPO_MINIMO;
    }

    @Override
    public String toString() {
        return "Class:Food" +
                ", id:" + this.id +
                ", name:" + this.name +
                ", price:" + this.price +
                ", date of Event:" + this.expiracyDate +
                ", max people allowed:" + this.maxPersonas;
    }
}


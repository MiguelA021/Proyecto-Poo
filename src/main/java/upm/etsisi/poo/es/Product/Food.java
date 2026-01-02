package upm.etsisi.poo.es.Product;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Food extends Event {
    private final static int TIEMPO_MINIMO = 72; // días

    public Food(int id, String name, double price, String expiracyDate, int maxPeople) {
        super(id, name, price, expiracyDate, maxPeople);
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
        return "{class:Food" +
                ", id:" + this.id +
                ", name:'" + this.name + "'" +
                ", price:" + this.price +
                ", date of Event:" + this.expiracyDate.toLocalDate() +
                ", max people allowed:" + this.maxPeopleLocal + "}\n";
    }

    @Override
    public String toStringTicketAdd() {
        return "{class:Food" +
                ", id:" + this.id +
                ", name:'" + this.name + "'" +
                ", price:" + this.price +
                ", date of Event:" + this.expiracyDate.toLocalDate() +
                ", max people allowed:" + this.maxPeopleLocal +
                ", actual people in event:"+ this.actualPeople + "}\n";
    }
}


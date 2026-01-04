package upm.etsisi.poo.es.Product;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Food extends Event {
    private final static int TIEMPO_MINIMO = 3; // días

    public Food(int id, String name, double price, String expiracyDate) {
        super(id, name, price, expiracyDate);
    }

    @Override
    /**
     * It also checks the hours due to foods must be planned with at least 72 hours
     */
    public boolean fechaValida(LocalDateTime time) {
        // Primero comprobamos la validación general de Event (fecha >= hoy)
        if (!super.fechaValida(time)) {
            return false;
        }

        // Horas que faltan hasta el evento (evento - ahora)
        long horas = ChronoUnit.DAYS.between(time, this.getExpiryDate());

        // Válido si faltan al menos TIEMPO_MINIMO horas
        return horas >= TIEMPO_MINIMO;
    }

    @Override
    public String toString() {
        return "{class:Food" +
                ", id:" + this.id +
                ", name:" + this.name +
                ", price:" + this.price +
                ", date of Event:" + this.expiracyDate.toLocalDate() +
                ", max people allowed:" + this.maxPersonas +
                "}\n";
    }
}


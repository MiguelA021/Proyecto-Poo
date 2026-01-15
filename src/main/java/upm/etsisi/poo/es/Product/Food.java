package upm.etsisi.poo.es.Product;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
                "}";
    }

    @Override
    public void printCsv(CSVPrinter csvPrinter) throws IOException {
        csvPrinter.printRecord("Food", id, name, pricePerPerson, expiracyDate.toLocalDate());
    }
}


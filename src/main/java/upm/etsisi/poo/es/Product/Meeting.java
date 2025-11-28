package upm.etsisi.poo.es.Product;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
public class Meeting extends Event {
    private final static int TIEMPO_MINIMO= 12;
    public Meeting(int id, String name, double price, String expiracyDate) {
        super(id, name, price, expiracyDate);
}


    @Override
    public boolean fechaValida(LocalDateTime time) {
        long horas = ChronoUnit.HOURS.between(this.getExpiryDate(),time);
        boolean resul = true;
        if (horas < TIEMPO_MINIMO )resul = false;
        return resul;
    }

    public String toString() {
        return "Class:Meeting" + ", id:" + this.id + ", name:" + this.name + ", price:" + this.price + ", date of Event:" + this.expiracyDate +
                ", max people allowed:" + this.maxPersonas;
    }
}

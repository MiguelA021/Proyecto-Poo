package upm.etsisi.poo.es.Product;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


public class Service extends Product {

    private static int NEXT = 1;

    private final String idString;// "1S", "2S", ...
    private final int id;
    private final LocalDate maxUseDate;
    private final String name;

    public Service(LocalDate maxUseDate, String name) {
        this.id = NEXT*-1;
        this.idString = NEXT++ + "S";
        this.maxUseDate = maxUseDate;
        this.name = name;

    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public void setPrice(double price) {

    }

    @Override
    public void setName(String name) {

    }

    public LocalDate getMaxUseDate() {
        return maxUseDate;
    }

     //Para mostrarlo en listados/prints (sin precio ni nombre)

    @Override
    public String toString() {

        Date expiration = Date.from(this.maxUseDate.atStartOfDay(ZoneId.of("Europe/Madrid")).toInstant());

        return "{class:ProductService, id:" + id * -1 + ", category:" + this.name + ", expiration: " + expiration;
    }
}
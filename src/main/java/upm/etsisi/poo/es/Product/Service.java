package upm.etsisi.poo.es.Product;

import java.time.LocalDate;

public class Service extends Product{

    private static int NEXT = 1;

    private final String id;          // "1S", "2S", ...
    private final LocalDate maxUseDate;

    public Service(LocalDate maxUseDate) {
        this.id = NEXT++ + "S";
        this.maxUseDate = maxUseDate;
    }

    public String id(){
        return id;
    }

    public int getId() {
        return Integer.parseInt(id);
    }

    @Override
    public String getName() {
        return "";
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

    /** Para mostrarlo en listados/prints (sin precio ni nombre) */
    @Override
    public String toString() {
        return "Service " + id + " (max use: " + maxUseDate + ")";
    }
}
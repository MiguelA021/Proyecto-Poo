package upm.etsisi.poo.es.Service;

import java.io.Serializable;
import java.time.LocalDate;

public class Service implements Serializable {

    private static int NEXT = 1;

    private final String id;          // "1S", "2S", ...
    private final LocalDate maxUseDate;

    public Service(LocalDate maxUseDate) {
        this.id = NEXT++ + "S";
        this.maxUseDate = maxUseDate;
    }

    public String getId() {
        return id;
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

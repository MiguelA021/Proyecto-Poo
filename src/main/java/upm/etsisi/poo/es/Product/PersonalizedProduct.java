package upm.etsisi.poo.es.Product;

import org.apache.commons.csv.CSVPrinter;
import upm.etsisi.poo.es.type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class PersonalizedProduct extends BasicProduct {
    private int maxPers;
    private ArrayList<String> personalizaciones;

    public PersonalizedProduct(int id, String name, type type, double price, int maxPers) {
        super(id, name, type, price);
        this.maxPers = maxPers;
        this.personalizaciones = new ArrayList<String>(maxPers);
    }

    public int getMaxPers() {
        return this.maxPers;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.personalizaciones.isEmpty()) {
            sb.append("{class:ProductPersonalized, id:").append(this.id).append(", name:'").append(this.name).append("', category: ").append(this.category).append(", price:").append(this.price).append(",");
            sb.append(" maxPersonal:").append(maxPers).append(",\n");
            for (int i = 0; i < this.personalizaciones.size(); i++) {
                if (i == this.personalizaciones.size() - 1) {
                    sb.append(personalizaciones.get(i));
                } else sb.append(personalizaciones.get(i)).append("\t");
            }
        } else {
            sb.append("{class:ProductPersonalized, id:").append(this.id).append(", name:'").append(this.name).append("', category: ").append(this.category).append(", price:").append(this.price);
            sb.append(" maxPersonal:").append(maxPers).append("}");
        }

        return sb.toString();
    }

    /**
     * The method adds the personalization if it is allowed
     *
     * @param personalize the personalization we want to add
     * @return it returns true if it has been allowed, else returns false
     */
    public boolean addPersonalized(String personalize) {
        boolean resul = true;
        if (personalizaciones.size() < maxPers) {
            personalizaciones.add(personalize);
        } else {
            resul = false;
        }
        return resul;
    }

    /**
     * The method changes the price into the new one with the personalization
     */
    public void newPrice() {
        this.price = 0.1 * this.price * personalizaciones.size() + this.price;
    }

    @Override
    public String toStringDiscount(double discountValue) {
        StringBuilder sb = new StringBuilder();
        if (!this.personalizaciones.isEmpty()) {
            sb.append("{class:ProductPersonalized, id:").append(this.id).append(", name:'").append(this.name).append("'").append(", category: ").append(this.category).append(", price:").append(this.price).append(",");
            sb.append(" maxPersonal:").append(maxPers).append(",");
            sb.append("personalizationList:[");

            for (int i = 0; i < this.personalizaciones.size(); i++) {
                if (i == this.personalizaciones.size() - 1) sb.append(personalizaciones.get(i));
                else sb.append(personalizaciones.get(i)).append(", ");

            }
            sb.append("]} ");
        } else {
            sb.append("{class:ProductPersonalized, id:").append(this.id).append(", name:'").append(this.name).append("', category: ").append(this.category).append(", price:").append(this.price).append(", maxPersonal:").append(maxPers).append("}");
        }
        sb.append(String.format(Locale.US, "**discount -%.3f \n", discountValue));

        return sb.toString();

    }
    @Override
    public void printCsv(CSVPrinter csvPrinter) throws IOException {
        csvPrinter.printRecord("PersonalizedProduct", id, name,category.name(), price, maxPers);
    }
}

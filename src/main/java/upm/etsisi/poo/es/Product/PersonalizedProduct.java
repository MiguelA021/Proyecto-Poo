package upm.etsisi.poo.es.Product;

import upm.etsisi.poo.es.type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PersonalizedProduct extends BasicProduct {
    @Column(name = "maxPersonalizaciones")
    private int maxPers;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "producto_personalizaciones",
            joinColumns = @JoinColumn(name = "producto_id")
    )
    @Column(name = "personalizacion")
    private Set<String> personalizaciones;

    public PersonalizedProduct(int id, String name, type type, double price, int maxPers) {
        super(id, name, type, price);
        this.maxPers = maxPers;
        this.personalizaciones = new HashSet<String>(maxPers);
    }

    public int getMaxPers() {
        return this.maxPers;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.personalizaciones.isEmpty()) {
            sb.append("{class:ProductPersonalized, id:").append(this.id).append(", name:'").append(this.name).append("', category: ").append(this.category).append(", price:").append(this.price).append(",");
            sb.append(" maxPersonal:").append(maxPers).append(",\n");
            Iterator it = personalizaciones.iterator();
            if(it.hasNext()){
                sb.append(it.next());
            }
            while (it.hasNext()){
                sb.append(", "+it.next());
            }
        } else {
            sb.append("{class:ProductPersonalized, id:").append(this.id).append(", name:'").append(this.name).append("', category: ").append(this.category).append(", price:").append(this.price);
            sb.append(" maxPersonal:").append(maxPers).append("}\n");
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

            Iterator it = personalizaciones.iterator();
            if(it.hasNext()){
                sb.append(it.next());
            }
            while (it.hasNext()){
                sb.append(", "+it.next());
            }
            sb.append("]} ");
        } else {
            sb.append("{class:ProductPersonalized, id:").append(this.id).append(", name:'").append(this.name).append("', category: ").append(this.category).append(", price:").append(this.price).append(", maxPersonal:").append(maxPers).append("}");
        }
        sb.append(String.format(Locale.US, "**discount -%.3f \n", discountValue));

        return sb.toString();

    }
}

package upm.etsisi.poo.es.Product;

import upm.etsisi.poo.es.type;

import java.util.ArrayList;

public class PersonalizedProduct extends BasicProduct {
    private int maxPers;
    private ArrayList<String> personalizaciones;

    public PersonalizedProduct(int id, String name, type type, double price, int maxPers) {
        super(id, name, type, price);
        this.maxPers = maxPers;
        this.personalizaciones = new ArrayList<String>(maxPers);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.personalizaciones.isEmpty()) {
            sb.append( "{class:PersonalizedProduct, id:" + this.id + ", name:" + this.name + ", category: " + this.category + ", price:"
        + this.price + "}");
            sb.append(" maxPersonal:" + maxPers + "\n");
            for (String personalizacion : personalizaciones) {
                sb.append(personalizacion + "\t");
            }
        } else {
            sb.append("{class:PersonalizedProduct, id:" + this.id + ", name:" + this.name + ", category: " + this.category + ", price:"
        + this.price + "}");
            sb.append(" maxPersonal:" + maxPers + " ");
        }

        return sb.toString();
    }

    public boolean addPersonalized(String personalize) {
        boolean resul = true;
        if (personalizaciones.size() < maxPers) {
            personalizaciones.add(personalize);
        } else {
            resul = false;
        }
        return resul;
    }

    public void newPrice(){
        this.price = 0.1*this.price*personalizaciones.size() + this.price;
    }

    @Override
    public String toStringDiscount(double discountValue) {
        StringBuilder sb = new StringBuilder();
        if (!this.personalizaciones.isEmpty()) {
            sb.append( "{class:PersonalizedProduct, id:" + this.id + ", name:" + this.name + ", category: " + this.category + ", price:"
        + this.price + "}");
            sb.append(" maxPersonal:" + maxPers + " ");
            sb.append("personalizationList[");

            for (String personalizacion : personalizaciones) {
                sb.append(personalizacion + ", ");

            }
            sb.append("]} \n");
        }else {
            sb.append("{class:PersonalizedProduct, id:" + this.id + ", name:" + this.name + ", category: " + this.category + ", price:"
        + this.price + "}");
            sb.append(" maxPersonal:" + maxPers + " ");
        }
        sb.append(String.format("**discount %.3f }\n",discountValue));

        return sb.toString();

    }
}

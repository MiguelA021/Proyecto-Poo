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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.personalizaciones.isEmpty()) {
            sb.append(super.toString());
            sb.append(" maxPerspnalizaciones:" + maxPers + "\n");
            int i = 0;
            while (personalizaciones.get(i) != null && i < maxPers) {
                sb.append(personalizaciones.get(i));
                i++;
            }
        } else {
            sb.append(super.toString());
            sb.append(" maxPerspnalizaciones:" + maxPers + "\n");
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
}

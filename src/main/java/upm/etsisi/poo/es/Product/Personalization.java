package upm.etsisi.poo.es.Product;

import javax.persistence.*;

@Entity
@Table(name = "pers")
public class Personalization {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "products")
    private PersonalizedProduct personalizedProduct;

    @Column(name = "personalization")
    private String personalization;

    public Personalization(){
        //required by hibernate
    }
    public Personalization(String personalization){
        this.personalization = personalization;
    }
}

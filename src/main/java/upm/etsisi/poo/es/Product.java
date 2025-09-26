package upm.etsisi.poo.es;

enum type {
  MERCH, PAPELERIA, ROPA, LIBRO, ELECTRONICA
}

public class Product {
  private int Id;
  private String name;
  private int price;

    public Product(String name, int price, int id) {
        this.name = name;
        this.price = price;
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        Id = id;
    }
    //El nombre del producto debe ser menos de 100 caracteres y no debe ser vacio.
    public void setName(String name) {
      String[] nameComplete = name.split(" ");
      int n=0;
      if(nameComplete.length<100 && !name.isEmpty()){
        this.name = name;
      }else{
        if(!(nameComplete.length<100)){
          System.out.println("The name should have less of 100 characters");
        }
        if(name.isEmpty()){
          System.out.println("The name shouldn't be empty");
        }
      }
    }
    //Obligar que el valor del precio sea siempre positivo
    public void setPrice(int price) {
      if(price>0){
        this.price = price;
      }else{
        this.price = 1;
        System.out.println("WARNING: The price should be a positive number");
      }


    }
}

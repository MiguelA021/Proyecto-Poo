package upm.etsisi.poo.es;

public abstract class User {
      protected String name;
      protected String email;
      protected int id;

      public  String getName(){
            return name;
      }

      public int getId(){return id;}

      public String getEmail(){
            return email;
      }
}

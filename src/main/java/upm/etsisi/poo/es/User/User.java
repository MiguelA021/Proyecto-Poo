package upm.etsisi.poo.es.User;
import upm.etsisi.poo.es.Ticket;

import java.util.TreeMap;

public abstract class User {
      protected String name;
      protected String email;
      protected int id;
      protected TreeMap<Integer, Ticket> tickets;

      public  String getName(){
            return name;
      }

      public int getId(){return id;}

      public String getEmail(){
            return email;
      }
}

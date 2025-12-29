package upm.etsisi.poo.es.User;
import upm.etsisi.poo.es.Ticket;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class User {
      protected String name;
      protected String email;
      protected String id;
      protected ArrayList<Integer> tickets;

      public  String getName(){
            return name;
      }

      public String getId(){return id;}

      public String getEmail(){
            return email;
      }
}

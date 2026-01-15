package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.User.Cashier;
import upm.etsisi.poo.es.User.CashierController;

public class CashierCommand implements Command {

  @Override
  public String getName() {
    return "cash";
  }


  @Override
  public boolean execute(String fullLine, String[] args) {
      CashierController cashierController = CashierController.getInstance();
    if (args.length < 2) {
      System.out.println(INCORRECT);
      return false;
    }

    String sub = args[1];

    switch (sub) {
      case "add":
        cashierAdd(args, cashierController);
        break;
      case "remove":
        cashierRemove(args, cashierController);
        break;
      case "list":
        list(cashierController);
        break;
      case "tickets":
        cashTickets(args, cashierController);
        break;
      default:
        System.out.println(INCORRECT);
    }

    return false;
  }

  private void cashierAdd(String[] args, CashierController cashierController) {
    if (args.length != 5 && args.length != 4) {
      System.out.println(INCORRECT);
    }
    boolean resul = false;
    try {
      String name;
      String email;
      if (args.length == 4) {
        name = args[2];
        email = args[3];
        resul = cashierController.addCasher(null, name, email, false);
      } else {
        String casherId = args[2].replaceAll("UW", "");
        int cash = Integer.parseInt(casherId);
        name = args[3];
        email = args[4];
        resul = cashierController.addCasher(cash, name, email,false);
      }
      if(resul) {
          System.out.println("cash add: ok");
      }
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
      return;
    }

  }

  private void cashierRemove(String[] args, CashierController cashierController) {
    if (args.length != 3) {
      System.out.println(INCORRECT);
      return;
    }
    try {
      String casherId = args[2].replaceAll("UW", "");
      int cash = Integer.parseInt(casherId);
      boolean removed = cashierController.removeCasher(cash);
      if (!removed) {
        System.out.println("Could not find th cashier");
      } else {
        System.out.println("cash remove: ok");
      }

    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
      return;
    }
  }

  private void list(CashierController cashierController) {
    cashierController.listCashers();
  }

  private void cashTickets(String[] args, CashierController cashierController) {
    // cash tickets <id>
    if (args.length != 3) {
      System.out.println(INCORRECT);
      return;
    }
    try {
      String casherId = args[2].replaceAll("UW", "");
      int cash = Integer.parseInt(casherId);
      Cashier casher = cashierController.searchCasherById(cash);
      if(casher == null){
          System.out.println("Cashier not found");
          return;
      }

      // Siempre imprimimos el encabezado
      System.out.println("Tickets: ");

      if (casher != null) {
        String ticketsStr = casher.listTickets(); // lo que ya tengas implementado

        // Si hay algo que imprimir, lo sacamos tal cual
        if (ticketsStr != null && !ticketsStr.isEmpty()) {
          System.out.print(ticketsStr); // ojo: print, no println, por si ya lleva \n
        }
      }

      // Siempre imprimimos el "ok" al final
      System.out.println("cash tickets: ok");

    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
    }
  }
}

package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Product;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.type;

public class ProductCommand extends Command {

  public ProductCommand(String command) {
    super(command);
  }
}

class commandProdAdd extends ProductCommand {
  private double price;
  private int id;
  private boolean correct = true;
  private boolean add = false;
  private String productName;
  private String category;
  private String[] commandArrayedit;

  public commandProdAdd(String command) {
    super(command);
    commandArrayedit = editSplit(command.split(" "));
  }

  private void commandProdAdd(String[] command, String[] name, Store store) {

    try {
      id = Integer.parseInt(commandArrayedit[2]);
      price = Integer.parseInt(commandArrayedit[5]);
      productName = commandArrayedit[3];
      category = commandArrayedit[4];
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
      correct = false;
      productName = "ERROR";
      category = "ERROR";
      id = -1;
      price = -1;
    }
    if (correct) {
      try {
        Product product = new Product(id, productName, type.valueOf(category), price);
        add = store.prodAdd(product);
        if (add) {
          System.out.println(product.toString());
          System.out.println("prod add: ok");
        } else {
          System.out.println(ID_REPEAT);
        }
      } catch (IllegalArgumentException e) {
        System.out.println(INCORRECT);
      }

    }
  }

}

class CommandProdRemove extends ProductCommand {

  public CommandProdRemove(String command) {
    super(command);
  }

  private void commandProdRemove(String[] commandArray, Store store) {
    boolean correct = true;
    int id;
    try {
      id = Integer.parseInt(commandArray[2]);
    } catch (NumberFormatException e) {
      System.out.println(INCORRECT);
      correct = false;
      id = -1;
    }
    if (correct) {
      store.prodRemove(id);
    }

  }
}

class CommandProdUpdate extends ProductCommand {

  public CommandProdUpdate(String command) {
    super(command);
  }

  private void commandProdUpdate(String[] commandArray, Store store, String[] name) {
    boolean done = false;
    boolean format;
    Product product;
    switch (commandArray[3]) {
      case "NAME":
        format = true;
        product = store.updateName(Integer.parseInt(commandArray[2]), name[4]);
        System.out.println(product.toString());
        if (product != null) {
          done = true;
        }
        break;

      case "CATEGORY":
        format = true;
        product = store.updateType(Integer.parseInt(commandArray[2]), type.valueOf(commandArray[4]));
        System.out.println(product.toString());
        if (product != null) {
          done = true;
        }
        break;
      case "PRICE":
        format = true;
        product = store.updatePrice(Integer.parseInt(commandArray[2]), Double.parseDouble(commandArray[4]));
        System.out.println(product.toString());
        if (product != null) {
          done = true;
        }
        break;
      default:
        format = false;
        done = false;
        product = null;
        break;
    }
    if (!format) {
      System.out.println(INCORRECT);
    }
    if (format && !done) {
      System.out.println(NOTEXIST);
    }
    if (done && format) {
      System.out.println("prod update: ok");
    }
  }

}

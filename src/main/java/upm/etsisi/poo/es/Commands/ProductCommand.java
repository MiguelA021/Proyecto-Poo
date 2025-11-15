package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Product;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.type;

public class ProductCommand extends Command {
    private final String[] arrayCommand;

    public ProductCommand(String command) {
        super(command);
        arrayCommand = command.split(" "); // Aquí debe usarse el slpit modificado.
    }

    public void apply(Store store) {
        CommandProdAdd.prodAdd(arrayCommand, store);
        CommandProdRemove.prodRemove(arrayCommand, store);
        CommandProdUpdate.commandProdUpdate(arrayCommand, store);
    }
}

class CommandProdAdd extends ProductCommand {

    public CommandProdAdd(String command) {
        super(command);
    }

    protected static void prodAdd(String[] commandArray, Store store) {
        if (commandArray[1].equals("add")) {
            Product product = convert(commandArray);
            if (product != null) {
                try {
                    boolean allCorrect = store.prodAdd(product);
                    if (allCorrect) {
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

    private static Product convert(String[] commandArray) {
        int id;
        String productName;
        String category;
        double price;
        int maxPersons;
        Product product = null;
        try {
            id = Integer.parseInt(commandArray[2]);

            productName = commandArray[3];
            category = commandArray[4];
            price = Double.parseDouble(commandArray[5]);
            if (commandArray[6] != null) maxPersons = Integer.parseInt(commandArray[6]);
            else maxPersons = -1;
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
            productName = null;
            category = null;
            price = -1;
            maxPersons = -1;
            id = -999;

        }
        if (productName != null) {
            if (maxPersons == -1) product = new Product(id, productName, type.valueOf(category), price);
            //  else  product = new Product(id, productName, type.valueOf(category), price, maxPersons);
        }
        return product;
    }
}


class CommandProdRemove extends ProductCommand {

    public CommandProdRemove(String command) {
        super(command);
    }

    protected static void prodRemove(String[] commandArray, Store store) {
        if (commandArray[1].equals("remove")) {
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
}

class CommandProdUpdate extends ProductCommand {

    public CommandProdUpdate(String command) {
        super(command);
    }

    protected static void commandProdUpdate(String[] commandArray, Store store) {
        if (commandArray[1].equals("update")) {
            boolean done = false;
            boolean format;
            Product product;
            switch (commandArray[3]) {
                case "NAME":
                    format = true;
                    product = store.updateName(Integer.parseInt(commandArray[2]), commandArray[3]);
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

}

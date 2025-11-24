package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Product;
import upm.etsisi.poo.es.Store;
import upm.etsisi.poo.es.Ticket;
import upm.etsisi.poo.es.type;

public class ProductCommand implements Command {

    @Override
    public String getName() {
        return "prod";
    }

    @Override
    public String getDescription() {
        return "prod add|list|update|remove ...  - product management";
    }

    @Override
    public boolean execute(String fullLine, String[] args, Store store) {
        if (args.length < 2) {
            System.out.println(INCORRECT);
            return false;
        }

        String sub = args[1];

        switch (sub) {
            case "add":
                prodAdd(fullLine, args, store);
                break;
            case "list":
                store.prodList();
                break;
            case "update":
                prodUpdate(fullLine, args, store);
                break;
            case "remove":
                prodRemove(args, store);
                break;
            default:
                System.out.println(INCORRECT);
        }
        return false;
    }

    private void prodAdd(String fullLine, String[] args, Store store) {
        try {
            // fullLine: prod add <id> "<name>" <category> <price>
            int firstQuote = fullLine.indexOf('"');
            int secondQuote = fullLine.indexOf('"', firstQuote + 1);
            if (firstQuote < 0 || secondQuote < 0) {
                System.out.println(INCORRECT);
                return;
            }

            String beforeName = fullLine.substring(0, firstQuote).trim(); // prod add <id>
            String name = fullLine.substring(firstQuote + 1, secondQuote).trim();
            String afterName = fullLine.substring(secondQuote + 1).trim(); // <category> <price>

            String[] beforeTokens = beforeName.split("\\s+"); // [prod, add, id]
            String[] afterTokens = afterName.split("\\s+");   // [category, price]

            if (beforeTokens.length != 3 || afterTokens.length != 2) {
                System.out.println(INCORRECT);
                return;
            }

            int id = Integer.parseInt(beforeTokens[2]);
            type category = type.valueOf(afterTokens[0]);
            double price = Double.parseDouble(afterTokens[1]);

            Product p = new Product(id, name, category, price);
            boolean done = store.prodAdd(p);
            if (!done) {
                System.out.println(ID_REPEAT);
            } else {
                System.out.println(p.toString());
                System.out.println("prod add: ok");
            }
        } catch (Exception e) {
            System.out.println(INCORRECT);
        }
    }

    private void prodRemove(String[] args, Store store) {
        if (args.length != 3) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            int id = Integer.parseInt(args[2]);
            boolean removed = store.prodRemove(id);
            if (!removed) {
                System.out.println(NOTEXIST);
            }
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT);
        }
    }

    private void prodUpdate(String fullLine, String[] args, Store store) {
        if (args.length < 4) {
            System.out.println(INCORRECT);
            return;
        }
        try {
            int id = Integer.parseInt(args[2]);
            String field = args[3].toUpperCase();

            Product updated = null;

            switch (field) {
                case "NAME":
                    // el resto de la línea tras NAME es el nuevo nombre
                    int idx = fullLine.toUpperCase().indexOf("NAME");
                    String newName = fullLine.substring(idx + "NAME".length()).trim();
                    updated = store.updateName(id, newName);
                    break;
                case "CATEGORY":
                    if (args.length < 5) {
                        System.out.println(INCORRECT);
                        return;
                    }
                    type newType = type.valueOf(args[4]);
                    updated = store.updateType(id, newType);
                    break;
                case "PRICE":
                    if (args.length < 5) {
                        System.out.println(INCORRECT);
                        return;
                    }
                    double newPrice = Double.parseDouble(args[4]);
                    updated = store.updatePrice(id, newPrice);
                    break;
                default:
                    System.out.println(INCORRECT);
                    return;
            }

            if (updated == null) {
                System.out.println(NOTEXIST);
            } else {
                System.out.println(updated.toString());
                System.out.println("prod update: ok");
            }
        } catch (Exception e) {
            System.out.println(INCORRECT);
        }
    }
}

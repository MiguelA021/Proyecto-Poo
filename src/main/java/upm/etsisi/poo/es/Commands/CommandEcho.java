package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;

public class CommandEcho implements Command {

  @Override
  public String getName() {
    return "echo";
  }

  @Override
  public String getDescription() {
    return "\"<text>\"  - prints the text";
  }

  @Override
  public boolean execute(String fullLine, String[] args) {
    try {
      String[] parts = fullLine.split("\"");
      System.out.println("\"" + parts[1].trim() + "\"");
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(INCORRECT);
    }
    return false; // no termina la app
  }
}

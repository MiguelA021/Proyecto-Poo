package upm.etsisi.poo.es.Commands;

import upm.etsisi.poo.es.Store;

public class ExitCommand implements Command {

  @Override
  public String getName() {
    return "exit";
  }

  @Override
  public String getDescription() {
    return "exit  - closes the application";
  }

  @Override
  public boolean execute(String fullLine, String[] args) {
    return true; // indica al controlador que se termine el bucle
  }
}

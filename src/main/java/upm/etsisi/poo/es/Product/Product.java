package upm.etsisi.poo.es.Product;

public abstract class Product {
  protected int id;
  protected String name;
  protected double price;
  protected final static String NAME_LENGTH_ERROR = "WARNING: The name should be less than 100 characters";
  protected final static String NAME_NULL_ERROR = "WARNING: The name shouldn't be empty";
  protected final static String PRICE_POSITIVE_ERROR = "WARNING: The price should be a positive number";
  protected final static String MAX_PERSONALIZED_ERROR = "WARNING: The number of personalized are full";

  public abstract int getId();

  public abstract String getName();

  public abstract double getPrice();

  public abstract void setPrice(double price);

  public abstract void setName(String name);

}

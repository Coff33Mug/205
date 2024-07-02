package main;

public class Item {
	 private String name;
	 private int ID;
	    private int quantity;
	    private double price;

	    public Item(String name, int quantity, double price, int ID) {
	        this.name = name;
	        this.quantity = quantity;
	        this.price = price;
	        this.ID = ID;
	    }

	    public String getName() {
	        return name;
	    }

	    public int getQuantity() {
	        return quantity;
	    }
	    
	    public int getID() {
	    	return ID;
	    }

	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }

	    public double getPrice() {
	        return price;
	    }

	    public void setPrice(double price) {
	        this.price = price;
	    }

	    @Override
	    public String toString() {
	        return name + " (" + quantity + " @ $" + price + " for " + (price * quantity) + " total)";
	    }
}

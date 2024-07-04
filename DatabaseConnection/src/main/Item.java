package main;

public class Item {
	 private String name;
	 private int ID;
	    private int quantity;
	    private double price;
	    private boolean isFilled;

	    public Item(String name, int quantity, double price, int ID) {
	        this.name = name;
	        this.quantity = quantity;
	        this.price = price;
	        this.ID = ID;
	    }
	    
	    public Item(String name, int quantity, double price, boolean isFilled) {
	        this.name = name;
	        this.quantity = quantity;
	        this.price = price;
	        this.isFilled = isFilled;
	    }
	    
	    public Item(String name, int quantity, double price) {
	        this.name = name;
	        this.quantity = quantity;
	        this.price = price;
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
	    
	    public void setFilled(boolean b) {
	    	isFilled = b;
	    }
	    
	    public boolean getFilled() {
	    	return isFilled;
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

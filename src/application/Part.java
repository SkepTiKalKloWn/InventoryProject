package application;

public abstract class Part{
	protected String name;
	protected int partID;
	protected double price;
	protected int inStock;
	protected int min;
	protected int max;
	
	//Name getter/setter
	public String getName(){
		return name;
	}
	public void setName(String pName){
		name = pName;
	}

	//Price getter/setter
	public double getPrice(){
		return price;
	}
	public void setPrice(double partPrice){
		price = partPrice;
	}
	
	//InStock getter/setter
	public int getInStock(){
		return inStock;
	}
	public void setInStock(int stock){
		inStock = stock;
	}
	
	//Min getter/setter
	public int getMin(){
		return min;
	}
	public void setMin(int minimum){
		min = minimum;
	}
	
	//Max getter/setter
	public int getMax(){
		return max;
	}
	public void setMax(int maximum){
		max = maximum;
	}
	
	//PartID getter/setter
	public int getPartID(){
		return partID;
	}
	public void setPartID(int pID){
		partID = pID;
	}
}
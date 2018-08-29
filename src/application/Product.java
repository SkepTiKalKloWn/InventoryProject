package application;

import java.util.*;

public class Product {
	ArrayList<Part> parts = new ArrayList<Part>();
	int productID;
	String name;
	double price;
	int inStock;
	int min;
	int max;
	
	//Name getter/setter
	public String getName(){
		return name;
	}
	public void setName(String pName){
		name = pName;
	}
	
	//InStock getter/setter
	public double getPrice(){
		return price;
	}
	public void setPrice(double productPrice){
		price = productPrice;
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
	
	//Parts management
	public void addPart(Part newPart){
		parts.add(newPart);
	}
	
	//Remove Method
	public boolean removePart(int removeID){
		boolean removed = false;
		
		for (int i = 0;i<(parts.size()-1);i++){
			if (removeID == parts.get(i).getPartID()){
				parts.remove(i);
				removed = true;
				break;
			}
		}
		return removed;
	}
	
	//Lookup Method
	public Part lookupPart(int searchID){
		Part foundPart = null;
		
		for (int i = 0;i<(parts.size()-1);i++){
			if (searchID == parts.get(i).getPartID()){
				foundPart = parts.get(i);
				break;
			}
		}
		return foundPart;
	}
	public void updatePart(int updateID){
		
	}
	
	//ProductID getter/setter
	public int getProductID(){
		return productID;
	}
	public void setProductID(int pID){
		productID = pID;
	}
	public ArrayList<Part> getParts(){
		return parts;
	}
}

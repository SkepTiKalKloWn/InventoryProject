package application;

import java.util.ArrayList;

public class Inventory {
	ArrayList<Product> products = new ArrayList<Product>();
	
	//Product Management
	public void addProduct(Product newProduct){
		products.add(newProduct);
	}
	public boolean removeProduct(int removeID){
		boolean removed = false;
		
		for (int i = 0;i<(products.size()-1);i++){
			if (removeID == products.get(i).getProductID()){
				products.remove(i);
				removed = true;
				break;
			}
		}
		return removed;
	}
	public Product lookupProduct(int searchID){
		Product foundProduct = null;
		
		for (int i = 0;i<(products.size()-1);i++){
			if (searchID == products.get(i).getProductID()){
				foundProduct = products.get(i);
				break;
			}
		}
		return foundProduct;
	}
	public void updateProduct(int updateID){
		for (int i = 0;i<(products.size()-1);i++){
			if (updateID == products.get(i).getProductID()){
				break;
			}
		}
	}
}

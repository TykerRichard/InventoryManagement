
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tyler Richard
 */
public class Product{
    
    private ObservableList<Part> associatedParts=FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     * 
     * @param id takes in id value
     * @param name takes in name string
     * @param price takes in price value
     * @param stock takes in stock value
     * @param min takes in min value
     * @param max takes in max value
     * constructor for products
     */
    public Product(int id,String name,double price, int stock, int min, int max){
        this.id=id;
        this.name=name;
        this.price=price;
        this.stock=stock;
        this.min=min;
        this.max=max;
        
    
}
    /**
     * 
     * @param id 
     * sets the ID for the product
     */
    public void setId(int id){
        this.id=id;
        
    }
    /**
     * 
     * @param name 
     * sets the name for the product
     */
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * 
     * @param price 
     * sets the price for product
     */
    public void setPrice(double price){
        this.price=price;
    }
    
    /**
     * 
     * @param stock 
     * sets the stock for the product
     */
    public void setStock(int stock){
        this.stock=stock;    
    }

     /**
     * 
     * @param min
     * sets min for product
     */
    public void setMin(int min){
        this.min=min;
    }
    
    /**
     * 
     * @param max 
     * sets max for product
     */
    public void setMax(int max){
        this.max=max;
    }

    /**
     * 
     * @return id
     * gets the ID for product
     */
    public int getId(){
        return id;
    }

    /**
     * 
     * @return name
     * returns the name for product
     */
    public String getName(){
        return name;
    }
    /**
     * 
     * @return price
     * returns the price for product
     */
    public double getPrice(){
        return price;
    }
    /**
     * 
     * @return stock
     * returns the stock of the product
     */
    public int getStock(){
        return stock;
    }

    /**
     * 
     * @return min
     * returns the min of the product
     */
    public int getMin(){
        return min;
    }

    /**
     * 
     * @return max
     * returns the max of the product
     */
    public int getMax(){
        return max;
    }
    
    /**
     * 
     * @param part 
     * sets the associated parts of the product
     */
    public void addAssociatedPart(Part part){
          associatedParts.add(part);
    }
    
    /**
     * 
     * @param selectedAssociatedPart takes in the selected part value
     * @return true returns true when part is deleted
     * deletes the selected part and returns true
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    
    /**
     * 
     * @return associatedParts
     * returns the part list of the product
     */
    public ObservableList<Part> getAllAssociatedParts(){
        
        return associatedParts;
    }

}

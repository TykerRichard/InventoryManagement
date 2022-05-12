
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 *
 * @author Tyler Richard
 */
public class Inventory {
 
    private static ObservableList<Part> allParts=FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts=FXCollections.observableArrayList();
    
    

/**
 * 
 * @param newPart 
 * adds a new part to the observableList named allParts
 */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * 
     * @param newProduct 
     * adds a new product to the observableList named allProducts
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    /**
     * 
     * @param partId takes in part ID
     * @return Part returns part or null based off of search
     * this method lookup parts via the partID if it finds it in the list 
     * it returns the part else returns null;
     */
    public static Part lookupPart(int partId){
       
        for(Part ps : allParts){
            if(ps.getId()==partId){
                return ps;
            }
        }
        return null;
    }
        
    
    
    /**
     * 
     * @param productId takes in product id 
     * @return Product reutrns a product or null based off of results 
     * this method looks Product via the productID if it finds it in the list 
     * it returns the product other wise it returns null
     */
    public static Product lookupProduct(int productId){
                
        for(Product prodSearch: allProducts){
            if(prodSearch.getId()==productId){
                return prodSearch;
                
                
            }
        }
        return null;
        
        
    }

    /**
     * 
     * @param partName takes in part name
     * @return  namedPart returns a list of parts
     * makes a new list called namedParts if it finds the the part in the 
     * allParts list it adds it to the namedPart list and then returns list.
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> namedPart=FXCollections.observableArrayList();
          for(Part ps : allParts){
              if(ps.getName().contains(partName)){
                  namedPart.add(ps);
                  
              }
          }
      
          return namedPart;
        
    }

    /**
     * 
     * @param productName takes in product name
     * @return namedProduct returns a list of products
     * makes a new list named lookupProduct if it finds the product in the 
     * allProducts list it adds it to the new list namedProducts and returns the list.
     */
    public static ObservableList<Product> lookupProduct (String productName){
        ObservableList<Product> namedProduct=FXCollections.observableArrayList();
          for(Product prodSearch : allProducts){
              if(prodSearch.getName().contains(productName)){
                  namedProduct.add(prodSearch);
                  
              }
          }
      
          return namedProduct;
        

        
    }

    /**
     * 
     * @param index takes in part index
     * @param selectedPart takes in the part trying to update
     *this method takes in the index and a Part and updates the allParts list 
     *by setting the index to the selected part.
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);

        
        
    }
    
    /**
     * 
     * @param index takes in product index
     * @param newProduct takes in the new product to update 
     * this method takes in the index and Product that and updates the allProducts
     * list by setting the index to the selected Product
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    
    /**
     * 
     * @param selectedPart takes in the selected part
     * @return true returns true when deleted
     * removes the part from the allParts list and true
     */
    public static boolean deletePart(Part selectedPart){
        for(Part sp : allParts){
            if(sp.getId() ==selectedPart.getId()){
                allParts.remove(sp);
                return true;
            }
        }
        return false;
        
    }
    /**
     * 
     * @param selectedProduct takes in the selected product
     * @return true returns true when deleted
     * removes the selected product from the allProducts list and returns true
     */
    public static boolean deleteProduct(Product selectedProduct){
        ObservableList<Part> associatedPartCheck=selectedProduct.getAllAssociatedParts();
        if(associatedPartCheck.size()==0){
        allProducts.remove(selectedProduct);
        return true;
        }else
            return false;
    }

    /**
     * 
     * @return  allParts returns a list of parts 
     * returns list partsList 
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
        
    }
    /**
     * 
     * @return allProdcts returns a list of products
     * returns list allProducts
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
        
        
    }

}



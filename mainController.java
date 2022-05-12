/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Tyler Richard
 */
public class mainController implements Initializable {

    @FXML
    private Button partAddAction;
    
    @FXML
    private TableView<Part> partList;

    @FXML
    private TableColumn partIDCol;

    @FXML
    private TableColumn partNameCol;

    @FXML
    private TableColumn inventoryCol;

    @FXML
    private TextField partSearchBox;
    @FXML
    private Button PartModifyButton;
    @FXML
    private Button PartDeleteButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button modifyProduct;
    @FXML
    private Button deleteProdct;
    @FXML
    private Button exitButton;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn productIDCol;
    @FXML
    private TableColumn productNameCol;
    @FXML
    private TableColumn productInvLevel;
    @FXML
    private TableColumn productPriceCol;
    @FXML
    private TextField productSearchBar;
    @FXML
    private TableColumn priceCol;
    
    
    
    
    
    
    private static ObservableList<Part> allParts=FXCollections.observableArrayList();
    private static ObservableList<Product> allProduct=FXCollections.observableArrayList();
    private static Part selectedPart;
    private static Product selectedProduct;
    private static int partIndex;
    private final errorChecking error=new errorChecking();
    private final Alert alertMessage= new Alert(AlertType.NONE);
    private static int productIndex;
    
    /**
     * 
     * @param url this is auto generated
     * @param rb  this is auto generated
     * initialize the controller 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //gets all parts inventory and then sets the partList (table)
        //try statement is here for the inital running of the application
        

        
        try{
        allParts=Inventory.getAllParts();
        partList.setItems(allParts);
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //gets all products from inventory and then sets the partsTable
        allProduct=Inventory.getAllProducts();
        productTable.setItems(allProduct);
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));        
        }catch(NullPointerException e){
            
        }
        
    }
    
    /**
     * 
     * @throws IOException 
     * calls the Add Part screen 
     */
    @FXML
    private void partAddAction() throws IOException {
        mainMenu parts=new mainMenu();
        parts.partScene();
        Stage stage = (Stage) partAddAction.getScene().getWindow();
        stage.hide();
    }

    /**
     * 
     * @param event
     * @throws IOException
     * @throws Exception 
     * this part loads the modify parts menu
     */
    @FXML
    private void modifyButtonClicked(ActionEvent event) throws IOException, Exception {
        //this checks to make sure a part is selected before calling the next menu.
        //if no part is selected displays an error
        if(!partList.getSelectionModel().isEmpty()){
            selectedPart=partList.getSelectionModel().getSelectedItem();
            partIndex=partList.getSelectionModel().getSelectedIndex();
            mainMenu main=new mainMenu();
            main.partModifyScene();
            Stage stage = (Stage) partAddAction.getScene().getWindow();
            stage.hide();
        }else{
            alertMessage.setAlertType(AlertType.WARNING);
            alertMessage.setContentText("No Part Selected");
            alertMessage.show();
        }
        
    }

    /**
     * 
     * @param event
     * @throws Exception 
     * this method handles the deleting of a part from the main menu
     */
    @FXML
    private void deleteButtonClicked(ActionEvent event) throws Exception {
        //makes sure that a part is selected if so then displays a confirmation 
        //prompt for deleting the part if okay is selected removes part from list 
        //otherwise returns to main menu
        if(!partList.getSelectionModel().isEmpty()){
            
            selectedPart=partList.getSelectionModel().getSelectedItem();
            String selectedPartString=selectedPart.getName();
            String alertString="are you sure you want to delete "+ selectedPartString;
            alertMessage.setAlertType(AlertType.CONFIRMATION);
            alertMessage.setContentText(alertString);
            Optional<ButtonType> buttonPress = alertMessage.showAndWait();
                if (buttonPress.get() == ButtonType.OK){
                    if(Inventory.deletePart(selectedPart)){
                        alertMessage.setAlertType(AlertType.INFORMATION);
                        alertMessage.setContentText("Part Successfully deleted");
                    }
                    
                }
        }else{
            alertMessage.setAlertType(AlertType.WARNING);
            alertMessage.setContentText("No Part Selected");
            alertMessage.show();
          
        }
    
    
    
    }

    /**
     * 
     * @param event 
     * this method is for searching parts when the search box is typed in 
     */
    @FXML
    private void partSearching(ActionEvent event) {
        ObservableList<Part> returnedParts=FXCollections.observableArrayList();
        int partID;
        
        //tries to parse the input if successful searches the inventory for the part 
        //via the id and if it finds it adds it to the returned parts list and then 
        //sets the table to that list. If it does not find it via ID returns error
        try{
            partID=Integer.parseInt(partSearchBox.getText());
            Part searchedPart=Inventory.lookupPart(partID);
            if(searchedPart!=null){
                returnedParts.setAll(searchedPart);
                partList.setItems(returnedParts);
                partList.requestFocus();
                partList.getSelectionModel().select(0);
                partList.getFocusModel().focus(0);
            }else{
                    alertMessage.setAlertType(AlertType.ERROR);
                    alertMessage.setContentText("No part found");
                    alertMessage.show();
                    partList.setItems(allParts);
            }
        //catches number format exception. if letters are entered gets the string
        //compares the string via the lookup part method if it found result get added
        //to the returnparts list and then the list is set to the table. else displays error
        }catch(NumberFormatException e){
            String partName=partSearchBox.getText();
            returnedParts=Inventory.lookupPart(partName);
            partList.setItems(returnedParts);
            if(returnedParts.size()==1){
                partList.requestFocus();
                partList.getSelectionModel().select(0);
                partList.getFocusModel().focus(0);                
            }
                
            
            if(returnedParts.isEmpty()){
                alertMessage.setAlertType(AlertType.ERROR);
                alertMessage.setContentText("no part found");
                alertMessage.show();
                partList.setItems(allParts);

            
                }
        }
        
        //if enter is pressed in search box while blank returns menu back to default
        if("".equals(partSearchBox.getText())){
            partList.setItems(allParts);
        }
    
    }

    @FXML
    /**
     * this method loads the add product menu 
     */
    private void addProductClicked(ActionEvent event) throws IOException {
        mainMenu main=new mainMenu();
        main.addProductScene();
        Stage stage = (Stage) addProductButton.getScene().getWindow();
        stage.hide();        
    }

    @FXML
    /**
     * this method handles events when the modify product button is clicked
     */
    private void modifyProductClicked(ActionEvent event) throws IOException {
        //verifies a product is selected if so launces the modifypart scene
        //else displays error that no product is selected
        if(!productTable.getSelectionModel().isEmpty()){
            selectedProduct=productTable.getSelectionModel().getSelectedItem();
            productIndex=productTable.getSelectionModel().getSelectedIndex();
            mainMenu main=new mainMenu();
            main.modifyProductScene();
            Stage stage = (Stage) partAddAction.getScene().getWindow();
            stage.hide();
        }else{
            alertMessage.setAlertType(AlertType.WARNING);
            alertMessage.setContentText("No Product Selected");
            alertMessage.show();
        }
          
    
    
    }

    /**
     * 
     * @param event 
     * this handles the delete button clicked events
     */
    @FXML
    private void deleteProuctClicked(ActionEvent event) {
        //checks to makes sure a product is selected if so displays a confirmation
        //to delete the product if okay is clicked remove the product otherwise 
        //returns to main menu 
        if(!productTable.getSelectionModel().isEmpty()){
            
            selectedProduct=productTable.getSelectionModel().getSelectedItem();
                String selectedProductString=selectedProduct.getName();
                String alertString="are you sure you want to delete "+ selectedProductString;
                alertMessage.setAlertType(AlertType.CONFIRMATION);
                alertMessage.setContentText(alertString);
                Optional<ButtonType> buttonPress = alertMessage.showAndWait();
                    if (buttonPress.get() == ButtonType.OK && Inventory.deleteProduct(selectedProduct)){
                                alertMessage.setAlertType(AlertType.INFORMATION);
                                alertMessage.setContentText("Product Successfully deleted");
                                alertMessage.show();
                            }else{
                                alertMessage.setAlertType(AlertType.WARNING);
                                alertMessage.setContentText("Product has associated parts and cannot be deleted");
                                alertMessage.show();           
                    }
                
            
        }else if(productTable.getSelectionModel().isEmpty()){
            alertMessage.setAlertType(AlertType.WARNING);
            alertMessage.setContentText("No product selected");
            alertMessage.show();
        }
    }
    
    /**
     * 
     * @param event 
     * exits the application if the exit button is clicked
     */
    @FXML
    private void exitClicked(ActionEvent event) {
        System.exit(0);
    }
    
    /**
    * 
    * @param event 
    * this method is to search products when the search box has texted entered
    */
    @FXML
    private void productSearching(ActionEvent event) {
        ObservableList<Product> returnedProduct=FXCollections.observableArrayList();
        int productID;

        //tries to take input and parse to int and then searches the products in the inventory
        //if product is found via ID adds product to returnedProduct list and then sets 
        //the table to that list otherwise displays an error
        try{
            productID=Integer.parseInt(productSearchBar.getText());
            Product searchedProduct=Inventory.lookupProduct(productID);
            if(searchedProduct!=null){
                returnedProduct.setAll(searchedProduct);
                productTable.setItems(returnedProduct);                   
                productTable.requestFocus();
                productTable.getSelectionModel().select(0);
                productTable.getFocusModel().focus(0);

            }else{
                    alertMessage.setAlertType(AlertType.ERROR);
                    alertMessage.setContentText("No product found");
                    alertMessage.show();
                    productTable.setItems(allProduct);
 
            }
        //catches number format excetions. Takes input text and then searchs the product 
        //via the inventory search from product name. If results are found adds it to the 
        //returnedproduct list and then the table is set to the list
        }catch(NumberFormatException e){
            String productName=productSearchBar.getText();
            returnedProduct=Inventory.lookupProduct(productName);
            productTable.setItems(returnedProduct);
            if(returnedProduct.size()==1){
                productTable.requestFocus();
                productTable.getSelectionModel().select(0);
                productTable.getFocusModel().focus(0);
            }
            if(returnedProduct.isEmpty()){
                    
            alertMessage.setAlertType(AlertType.ERROR);
            alertMessage.setContentText("no product found");
            alertMessage.show();
            productTable.setItems(allProduct);
            
                }
        }    
    }
    /**
     * 
     * @return selected part is returned. 
     * this is for easy access to the part selected off main menu
     */
    public static Part getPart(){
        return selectedPart;
    }
    
    /**
     * 
     * @return partIndex the select part index is returned
     * this is for easy access to the select parts index off main menu
     */
    public static int getPartIndex(){
    
      return partIndex;
    }
    
    /**
     * 
     * @return selectedProduct 
     * this is for easy access to the product selected off main menu
     * 
     */
    public static Product getProduct(){
        return selectedProduct;
    }

    /**
     * 
     * @return productIndex the select product index is returned
     * this is for easy access to the select product index off main menu
     */    
    public static int getProductIndex(){
        return productIndex;
    }


}
    

        
    
    
    
    
    

    
    

    






 






    
    
    
    
    


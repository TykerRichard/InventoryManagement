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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tyler.richard
 */
public class modifyProductController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private TextField AllPartsSearch;
    @FXML
    private Button addButton;
    @FXML
    private TextField AssociatedPartSearch;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField productIDField;
    @FXML
    private TableColumn allPartIDCol;
    @FXML
    private TableColumn allPartNameCol;
    @FXML
    private TableColumn allInvLevelCol;
    @FXML
    private TableColumn allPriceCol;
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private TableColumn associatedPartIDCol;
    @FXML
    private TableColumn associatedPartNameCol;
    @FXML
    private TableColumn associatedInvLevelCol;
    @FXML
    private TableColumn associatedPriceCol;
    @FXML
    private TableView<Part> allPartTable;
    
    private static ObservableList<Part> allParts=FXCollections.observableArrayList();
    private  ObservableList<Part> associatedPartsList=FXCollections.observableArrayList();
    private Button removeAssociatePartsButton;
    private Alert alertMessage=new Alert(Alert.AlertType.NONE);
    private static int partID=1;
    private static final errorChecking error=new errorChecking();
    private int stock,min,max,machineID;
    private double productPrice;
    private String productName;
    private int productID;
    private Product updateProduct;
    private int productIndex;
    private Product addProduct;
    
    
    /**
     * Initializes the controller class.
     * and sets all the tables with the data from adding the parts to each list
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
        updateProduct = mainController.getProduct();
        productID=updateProduct.getId();
        productIndex=mainController.getProductIndex();
        productIDField.setText(String.valueOf(updateProduct.getId()));
        nameField.setText(updateProduct.getName());
        priceField.setText(String.valueOf(updateProduct.getPrice()));
        invField.setText(String.valueOf(updateProduct.getStock()));
        maxField.setText(String.valueOf(updateProduct.getMax()));
        minField.setText(String.valueOf(updateProduct.getMin()));
        
        
        allParts=Inventory.getAllParts();
        allPartTable.setItems(allParts);                
        allPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsList=updateProduct.getAllAssociatedParts();
        associatedPartTable.setItems(associatedPartsList);        
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }catch(NullPointerException e){
    
    }  

        
   
        
    }    

    /**
     * 
     * @param event
     * this is the search for allParts when text is entered into the all parts 
     * text field
     */
    @FXML
    private void allPartSearchAction(ActionEvent event) {
   ObservableList<Part> returnedParts=FXCollections.observableArrayList();    

        //tries to parse Int. if successful it search the part via the lookup part 
        //method and returns the part and sets it to allpartlist which then 
        //is set to the allPartTable or displays an error message
        try{
            partID=Integer.parseInt(AllPartsSearch.getText());
            Part searchedPart=Inventory.lookupPart(partID);
            if(searchedPart!=null){
                returnedParts.setAll(searchedPart);
                allPartTable.setItems(returnedParts);
                allPartTable.requestFocus();
                allPartTable.getSelectionModel().select(0);
                allPartTable.getFocusModel().focus(0);
            }else{

                alertMessage.setAlertType(Alert.AlertType.ERROR);
                alertMessage.setContentText("No part found");
                alertMessage.show();
                allPartTable.setItems(allParts);
            }
        
        //catches NumberFormatException from trying to parse int. If caought 
        //it searchs part Name and then if found it sets the list to the results 
        //the list is then set to the table. If no results returns error
        }catch(NumberFormatException e){
            String partName=AllPartsSearch.getText();
            returnedParts=Inventory.lookupPart(partName);
            allPartTable.setItems(returnedParts);
            if(returnedParts.size()==1){
                allPartTable.requestFocus();
                allPartTable.getSelectionModel().select(0);
                allPartTable.getFocusModel().focus(0);            
            }
                if(returnedParts.isEmpty()){
                    
                    alertMessage.setAlertType(Alert.AlertType.ERROR);
                    alertMessage.setContentText("no part found");
                    alertMessage.show();
                    allPartTable.setItems(allParts);
            
                }
        }
        
        //if enter is pressed in the search box it resets the table
        if("".equals(AllPartsSearch.getText())){
            allPartTable.setItems(allParts);
        }
        

    }
    
    /**
     * 
     * @param event 
     * this method performs checking and sets products after add button is clicked
     */
    @FXML
    private void addButtonClicked(ActionEvent event) {
        
        //checks to make sure a part is selected if so adds it to the associated Part list and then 
        //sets the table to the associated Parts list. Else it shows an error message.
        if((!allPartTable.getSelectionModel().isEmpty())){
            Part selectedPart=allPartTable.getSelectionModel().getSelectedItem();

            associatedPartsList.add(selectedPart);
            associatedPartTable.setItems(associatedPartsList);
        }else{
            alertMessage.setAlertType(Alert.AlertType.WARNING);
            alertMessage.setContentText("No Part Selected");
            alertMessage.show();
        }    
    }
    

    
    /**
     * 
     * @param event 
     *this is the search for allParts when text is entered into the all parts 
     *text field
     */
    @FXML
    private void associatedPartSearchAction(ActionEvent event) {
        ObservableList<Part> returnedParts=FXCollections.observableArrayList();
        //tries to prase input to int if successful it searches the associated part list
        //if the part id is found it returns the reslts to returnedPart list and then 
        //sets the table to that list. Other wise it returns an error message
        try{
            partID=Integer.parseInt(AssociatedPartSearch.getText());
            for(Part sp : associatedPartsList){
                if(sp.getId()== partID){
                    returnedParts.setAll(sp);
                }

            }
            associatedPartTable.setItems(returnedParts);
            associatedPartTable.requestFocus();
            associatedPartTable.getSelectionModel().select(0);
            associatedPartTable.getFocusModel().focus(0);
            if(returnedParts.isEmpty()){
                alertMessage.setAlertType(AlertType.WARNING);
                alertMessage.setContentText("No part found");
                alertMessage.show();
                associatedPartTable.setItems(associatedPartsList);
            }
        
        //cathes the error incase Parse. Take the input converts it to a string
        //searches the associatedPartsList again and if it finds it adds it to
        //the returnedParts list and then sets the table to the list.
        }catch(NumberFormatException | NullPointerException e){
            String partName=AssociatedPartSearch.getText();
            for(Part sp : associatedPartsList){
                if(sp.getName().contains(partName)){
                    returnedParts.setAll(sp);

                }

            }
            
            associatedPartTable.setItems(returnedParts);
            if(returnedParts.size()==1){
                associatedPartTable.requestFocus();
                associatedPartTable.getSelectionModel().select(0);
                associatedPartTable.getFocusModel().focus(0);
            }
            if(returnedParts.isEmpty()){
                    alertMessage.setAlertType(AlertType.ERROR);
                    alertMessage.setContentText("No parts found");
                    alertMessage.show();
                    associatedPartTable.setItems(associatedPartsList);
                    
                }
            //if the part search is empty sets the list back to default
            if(AssociatedPartSearch.getText().isEmpty()){
            associatedPartTable.setItems(associatedPartsList);
        }
                
        }
        
        

     }        
    
    /**
     * 
     * @param event
     * @throws IOException 
     * handle the action of when the saved button is clicked. 
     */
    @FXML
    private void saveButtonClicked(ActionEvent event) throws IOException {
        //variables call errorchecking methods in the error checking class
        //if successfully entered returns the results to the variable 
        min=error.intCheck(minField, "Min field");
        max=error.intCheck(maxField, "Max field");
        stock=error.intCheck(invField, "Price field");
        productName=error.stringCheck(nameField,"Name field");
        productPrice=error.doubleCheck(priceField, productName);
        
        //this checks to make sure max and min are not -1. error checking method returns
        //-1 if min or max is not a valid input. Once checked it confirms that max is not less 
        //than min and if it not it then compares the stock to min and max
        if(max !=-1 && min != -1){
            if(error.minMaxCheck(min, max)){
                if(error.stockCheck(stock, min, max));
            }
        }        
        
 

        //this makes sure that the list is not blank and then calls the check all method from
        //error checking to validate all the data one last time. if true it adds a product
        if(error.checkAll(stock, min, max, productName, productPrice)){
            addProduct=new Product(productID,productName,productPrice,stock,min,max);
            Inventory.updateProduct(productIndex, addProduct);
           //addes the associated parts to the list that is inside of the Product class
            for(Part ap : associatedPartsList){
                addProduct.addAssociatedPart(ap);    
            }
   
            closeWindow();
        }        
    }

    /**
     * 
     * @param event
     * @throws IOException 
     * if canceled button is clicked closes the window
     */
    @FXML
    private void cancelButtonClicked(ActionEvent event) throws IOException{
        closeWindow();
    }

    
    /**
     * 
     * @param event 
     * this method removes parts from the associated parts list
     */
    @FXML
    private void AssociateButtonClicked(ActionEvent event) {
        
        //checks to make sure a part is selected and if so removes it other wise displays error.
        if((!associatedPartTable.getSelectionModel().isEmpty())){
            int associatedPartIndex=associatedPartTable.getSelectionModel().getSelectedIndex();
            alertMessage.setAlertType(AlertType.CONFIRMATION);
            alertMessage.setContentText("are you sure you want to remove the part from the associated table " );
            Optional<ButtonType> buttonPress = alertMessage.showAndWait();
                if (buttonPress.get() == ButtonType.OK){
                    associatedPartsList.remove(associatedPartIndex);
                }
        }else{
            alertMessage.setAlertType(Alert.AlertType.WARNING);
            alertMessage.setContentText("Part removed");
        }
    }
    
    //method created for easy closing of the current window
    private void closeWindow() throws IOException{
        Stage thisStage =(Stage)saveButton.getScene().getWindow();
        thisStage.close();
        mainMenu newMenu=new mainMenu();
        newMenu.mainScreen();
    }  
    


    
}

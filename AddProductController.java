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
public class AddProductController implements Initializable {

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
    private Button removeAssociatePartsButton;

    @FXML
    private TableColumn allPartID;
    @FXML
    private TableColumn allPartName;
    @FXML
    private TableColumn allPartInv;
    @FXML
    private TableColumn allPartCost;
    @FXML
    private TableView<Part> allPartTable;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn associatedPartID;
    @FXML
    private TableColumn associatedPartName;
    @FXML
    private TableColumn associatedPartInv;
    @FXML
    private TableColumn  associatedPartCost;
    
    private static ObservableList<Part> allParts=FXCollections.observableArrayList();
    private ObservableList<Part> associatedPartsList=FXCollections.observableArrayList();
    private Product addProduct;
    private errorChecking error=new errorChecking();
    private static int productID=1000;
    private int min,max,stock,partID;
    private double price;
    private String productName;
    private Part selectedPart;
    private Part associatedPart;
    private Alert alertMessage=new Alert(Alert.AlertType.NONE);
    
    /**
     * Initializes the controller class.
     * @param url this is auto generated
     * @param rb  this is auto generated 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allParts=Inventory.getAllParts();
        allPartTable.setItems(allParts);
       
        allPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        associatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCost.setCellValueFactory(new PropertyValueFactory<>("price"));

    }    


    
  /**
   * 
   * @param event 
   * This method searching when the search bar is typed into. 
   */
    @FXML
    private void allPartSearchAction(ActionEvent event) {
   ObservableList<Part> returnedParts=FXCollections.observableArrayList();    

        //takes in string from search box and tries to parse it to int
        //if successful checks for null return from lookupPartMethod
        //else displays no part found
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
        //catches NullFormatException from parse and then calls lookupPart method 
        // and passes part name. if no parts are found displays error message
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
        
        //resets the table when enter is pressed
        if("".equals(AllPartsSearch.getText())){
            allPartTable.setItems(allParts);
        }
        

    }
    /**
     * 
     * @param event 
     * this method performs actions when the add button is clicked 
     */    
    @FXML
    private void addButtonClicked(ActionEvent event) {
        
        //makes sure that a part is selected and and if it is it adds it to the 
        //associated parts list.
        if((!allPartTable.getSelectionModel().isEmpty())){
            selectedPart=allPartTable.getSelectionModel().getSelectedItem();

            associatedPartsList.add(selectedPart);
            associatedPartsTable.setItems(associatedPartsList);
        }else{
            alertMessage.setAlertType(Alert.AlertType.WARNING);
            alertMessage.setContentText("No Part Selected");
            alertMessage.show();
        }    

        
    
    }
    /**
     * 
     * @param event 
     * this method performs a search when something is typed into the associated
     * parts search box
     */

    @FXML
    private void associatedPartSearchAction(ActionEvent event) {
        ObservableList<Part> returnedParts=FXCollections.observableArrayList();
        
        //tries to search parts via the ID in the associated Parts list if found 
        //returns the part if not displays an error message
        try{
            partID=Integer.parseInt(AssociatedPartSearch.getText());
            for(Part sp : associatedPartsList){
                if(sp.getId() == partID){
                    returnedParts.add(sp);

                }
            }
            if(!(returnedParts.isEmpty())){
            associatedPartsTable.setItems(returnedParts);
            associatedPartsTable.requestFocus();
            associatedPartsTable.getSelectionModel().select(0);
            associatedPartsTable.getFocusModel().focus(0);
            }else{
                alertMessage.setAlertType(Alert.AlertType.ERROR);
                alertMessage.setContentText("no part found");
                alertMessage.show();
                associatedPartsTable.setItems(associatedPartsList);
            }
        
        //catches error from trying to parse int and then tries to search part via 
        //part name. If no part is found displays error message
        }catch(NumberFormatException | NullPointerException e){
            String partName=AssociatedPartSearch.getText();
            for(Part sp : associatedPartsList){
                if(sp.getName().contains(partName)){
                    returnedParts.add(sp);
                }
            }
            associatedPartsTable.setItems(returnedParts);
            if(returnedParts.size()==1){
            associatedPartsTable.requestFocus();
            associatedPartsTable.getSelectionModel().select(0);
            associatedPartsTable.getFocusModel().focus(0);
            }
            
            if(returnedParts.isEmpty()){
                alertMessage.setAlertType(Alert.AlertType.ERROR);
                alertMessage.setContentText("no part found");
                alertMessage.show();
                associatedPartsTable.setItems(associatedPartsList);
            }
        }
        
        //returns search box back to default view of table if enter is pressed
        if("".equals(AssociatedPartSearch.getText())){
            associatedPartsTable.setItems(associatedPartsList);
        }
          
    }
/**
 * 
 * @param event
 * @throws IOException 
 * this method activates when the saved button is clicked. Verifies info entered and
 * adds an object of product and adds the associated parts
 */
    @FXML
    private void saveButtonClicked(ActionEvent event) throws IOException {
        
        //these variables error check via the java class error checking and 
        //if vaidated successful returns the value entered
        min=error.intCheck(minField, "Min field");
        max=error.intCheck(maxField, "Max field");
        stock=error.intCheck(invField, "Inv field");
        productName=error.stringCheck(nameField,"Name field");
        price=error.doubleCheck(priceField, "Price field");

        //intchecking from errorchecking returns -1 or the int entered
        //this if only runs if max and min validate successful. Once validated 
        //it checks the logic for max and min and then checks them with inventory
        if(max !=-1 && min != -1){
            if(error.minMaxCheck(min, max)){
                if(error.stockCheck(stock, min, max));
            }
        }        


        //final check before adding a new product. Validates that an associated part is added
        //calls checkall from errorchecking which does an overall check against all fields already validated.
        //adds a new object of type product and also loops to add associated parts to the list
        if(error.checkAll(stock, min, max, productName, price)){
            addProduct=new Product(productID,productName,price,stock,min,max);
            productID++;
            Inventory.addProduct(addProduct);
            for(Part ap : associatedPartsList){
                addProduct.addAssociatedPart(ap);    
            }
            
            //calls the closed method window inside this class
            closeWindow();
        }
    
    
    
    }

    @FXML
    /**
     * this method just close this menu when the cancel button is clicked
     */
    private void cancelButtonClicked(ActionEvent event) throws IOException {
        closeWindow();
    }

    /**
     * 
     * @param event 
     * this method removes a part from the associated parts list if the remove associated button is clicked
     * 
     */
    @FXML
    private void AssociateButtonClicked(ActionEvent event) {
        //checks to make sure that a part is selected and if it is it removes it from the index
        //else displays an error message
        if((!associatedPartsTable.getSelectionModel().isEmpty())){
            int associatedPartIndex=associatedPartsTable.getSelectionModel().getSelectedIndex();
            alertMessage.setAlertType(Alert.AlertType.CONFIRMATION);
            alertMessage.setContentText("are you sure you want to remove the part from the associated table " );
            Optional<ButtonType> buttonPress = alertMessage.showAndWait();
                if (buttonPress.get() == ButtonType.OK){
                    associatedPartsList.remove(associatedPartIndex);
                }
        }else{
            alertMessage.setAlertType(Alert.AlertType.WARNING);
            alertMessage.setContentText("No part selected");
        }
        
    }
    
    //method for quickly calling and closing window in different buttons
    private void closeWindow() throws IOException{
        Stage thisStage =(Stage)saveButton.getScene().getWindow();
        thisStage.close();
        mainMenu newMenu=new mainMenu();
        newMenu.mainScreen();
    }  
    



    
    
}
        
    


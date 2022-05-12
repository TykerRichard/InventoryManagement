
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.lang.Class.*;

/**
 * FXML Controller class
 *
 * @author tyler
 */
public class partsModifyController implements Initializable {

    @FXML
    private ToggleGroup partType;
    @FXML
    private Label idLabel;
    @FXML
    private Label mfgSourceLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField invField;
    @FXML
    private TextField costField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField machineIDField;
    @FXML
    private TextField mfgField;
    @FXML
    private TextField minField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton inhouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private TextField idField;
    
    private Part updatePart;
    private int partIndex;
    private static int partID=1;
    private static final errorChecking error=new errorChecking();
    private int stock,min,max,machineID;
    private double partPrice;
    private String companyName;
    private String partName;
    private inHouse newInhousePart;
    private outsourced newOutsourcedPart;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try{
            updatePart = mainController.getPart();
            int partID=updatePart.getId();
            partIndex=mainController.getPartIndex();
            idField.setText(String.valueOf(updatePart.getId()));
            nameField.setText(updatePart.getName());
            costField.setText(String.valueOf(updatePart.getPrice()));
            invField.setText(String.valueOf(updatePart.getStock()));
            maxField.setText(String.valueOf(updatePart.getMax()));
            minField.setText(String.valueOf(updatePart.getMin()));

            if(updatePart instanceof outsourced){
                companyName=((outsourced)updatePart).getcompanyName();
                System.out.println(companyName);
                mfgField.setText(companyName);
                mfgField.setVisible(true);
                machineIDField.setVisible(false);
                mfgSourceLabel.setTranslateX(0);
                outsourcedRadio.setSelected(true);
                mfgSourceLabel.setText("Company Name:");
                
            }else if(updatePart instanceof inHouse){
                machineID=((inHouse)updatePart).getMachineID();
                machineIDField.setText(String.valueOf(machineID));
                mfgField.setVisible(false);
                machineIDField.setVisible(true);  
                mfgSourceLabel.setTranslateX(30);
                inhouseRadio.setSelected(true);
                mfgSourceLabel.setText("Machine ID:"); 
            }
        }catch(NullPointerException e){
            
        }
        
        
        // TODO
        
    }    

   
    @FXML
    private void inhouseScene(MouseEvent event) {
        mfgSourceLabel.getText();
        mfgSourceLabel.setText("Machine ID:"); 
        mfgField.setVisible(false);
        machineIDField.setVisible(true);  
        mfgSourceLabel.setTranslateX(30);

    }
    
    
    
    
    @FXML
    private void outsourcedScene(MouseEvent event) {
        mfgSourceLabel.getText();
        mfgSourceLabel.setText("Company Name:"); 
        mfgField.setVisible(true);
        machineIDField.setVisible(false);
        mfgSourceLabel.setTranslateX(0);

    }
    /**
     * 
     * @param event
     * @throws Exception 
     * handles the saved button click
     */
    @FXML
    private void saveClicked(ActionEvent event) throws Exception {
        //variable call error check to validate data and return the data to variables
        max=error.intCheck(maxField,"Max field");
        min=error.intCheck(minField,"Min field");
        stock=error.intCheck(invField,"Inv field");
        partName=error.stringCheck(nameField, "Name field");
        partPrice=error.doubleCheck(costField, "Price field");
        
        //when error checking if data is invalid it returns -1. this makes sure that max 
        //and min are not -1 and if not checks min to max and makes sure they are in range
        //after checks inventory to make sure that is within range of max and min
        if(max!= -1 && min !=-1){
            if(error.minMaxCheck(min, max)){
                if(error.stockCheck(stock, min, max));
            }
        }

    //makes sure mfg label is visable and makes sure that all the variables above are correct
    if(mfgField.isVisible() && error.checkAll(stock, min, max, partName, partPrice)){
        //checks comapny name to make sure its not empty
        companyName=error.stringCheck(mfgField, "Company name field");
        //when company name is not empty updates the part with the data from above and updates the inventory
        if((!companyName.isEmpty())){
            int id=updatePart.getId();
            newOutsourcedPart=new outsourced(id,partName,partPrice,stock,min,max,companyName);
            newOutsourcedPart.setMax(max);
            newOutsourcedPart.setMin(min);
            newOutsourcedPart.setPrice(partPrice);
            newOutsourcedPart.setStock(stock);
            newOutsourcedPart.setName(partName);
            Inventory.updatePart(partIndex, newOutsourcedPart);
            closeWindow();
        }        
        
            //same as above just switched to machineID instead of company name 
            }else if(machineIDField.isVisible() && error.checkAll(stock, min, max, partName, partPrice)){
                machineID=error.intCheck(machineIDField, "MachineID field");
                if(machineID != -1){
                    int id=updatePart.getId();
                    newInhousePart=new inHouse(id,partName,partPrice,stock,min,max,machineID);
                    newInhousePart.setMax(max);
                    newInhousePart.setMin(min);
                    newInhousePart.setPrice(partPrice);
                    newInhousePart.setStock(stock);
                    newInhousePart.setName(partName);
                    Inventory.updatePart(partIndex, newInhousePart);
                
                    closeWindow();
                }
            }
    }
    /**
     * 
     * @param event
     * @throws Exception 
     * if canceled button is clicked closes window
     */  
    @FXML
    private void cancelClicked(ActionEvent event) throws Exception {
        Stage mainStage=new Stage();
        Stage partStage =(Stage)cancelButton.getScene().getWindow();
        partStage.close();
        mainMenu newMenu=new mainMenu();
        newMenu.start(mainStage);        
        
    }
 
    /**
     * 
     * @throws IOException 
     * method created to close window used through the code
     */
    private void closeWindow() throws IOException{
        Stage thisStage =(Stage)saveButton.getScene().getWindow();
        thisStage.close();
        mainMenu newMenu=new mainMenu();
        newMenu.mainScreen();
    }    
}

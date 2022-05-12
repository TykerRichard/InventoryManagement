
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

/**
 * FXML Controller class
 *
 * @author tyler
 */
public class partsController implements Initializable {

    @FXML
    private ToggleGroup partType;
    @FXML
    private RadioButton outsourced;
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
    private TextField mfgField;
    @FXML
    private TextField minField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField machineIDField;
 
    private static int partID=1;
    private static final errorChecking error=new errorChecking();
    private int stock,min,max,machineID;
    private double partPrice;
    private String companyName;
    private String partName;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        
    }    

    /**
     * 
     * @param event 
     *  gets text for the SourceLabel and sets it to machine id if the inhouse
     * radio button is elected and shows the machineID field and hides the mfg field.
     */
    @FXML
    private void inhouseScene(MouseEvent event) {
        mfgSourceLabel.getText();
        mfgSourceLabel.setText("Machine ID:"); 
        mfgField.setVisible(false);
        machineIDField.setVisible(true);
        mfgSourceLabel.setTranslateX(30);
    }
    
    /**
     * 
     * @param event 
     * gets text for the source label and sets it to company name if the outsourced 
     * radio button is selected. This also shows the mfgField and hides the machineID field.
     */
    @FXML
    private void outsourcedScene(MouseEvent event) {
        mfgSourceLabel.getText();
        mfgSourceLabel.setText("Company Name:");
        machineIDField.setVisible(false);
        mfgField.setVisible(true);
        mfgSourceLabel.setTranslateX(0);
    }
    
    
    /**
     * 
     * @param event
     * @throws IOException
     * @throws Exception 
     * this method performs checks and actions when the saved button is clicked 
     */
    @FXML
    private void saveClicked(ActionEvent event) throws IOException, Exception {
        //variables call the errorchecking object and validates data once 
        //data is validated it assigns the variables with the data entered 
        max=error.intCheck(maxField,"Max field");
        min=error.intCheck(minField,"Min field");
        stock=error.intCheck(invField,"Inv field");
        partName=error.stringCheck(nameField, "Name field");
        partPrice=error.doubleCheck(costField, "Price field");
        
        /*
        min and max return -1 if data is invalid in errorchecking class
        this confirms that both fields have valid data and then calls the 
        max min check to make max is not less than min if it ruturns true
        it validates the stock levels of the object.
        */
        if(max !=-1 && min != -1){
            if(error.minMaxCheck(min, max)){
                if(error.stockCheck(stock, min, max));
            }
        }
        
        /*
         checks to make sure that mfg field is visable and then does a checkall from the 
        error checking method to make sure all the vairables assigned previously are 
        proper. 
        */
        if(mfgField.isVisible() && error.checkAll(stock, min, max, partName, partPrice)){
            
            //this checks to make sure company name field is not empty via error checking and assigns 
            //the variable. once the company name is checked is creates a part object with all the info 
            //this is also where the partID is assigned as well
            companyName=error.stringCheck(mfgField, "Company name field");
            if((!companyName.isEmpty())){
            outsourced newPart=new outsourced(partID,partName,partPrice,stock,min,max,companyName);
            partID++;
            Inventory.addPart(newPart);
            
            // calls the closeWindow method inside this class to close the window
            closeWindow();
            }
            
            //refer to previous comment for outsourced parts as this method does the same thing just 
            //changes a few variables for machineID instead of comapnyName.
        }else if(machineIDField.isVisible() && error.checkAll(stock, min, max, partName, partPrice)){
            machineID=error.intCheck(machineIDField, "MachineID field");
            if(machineID!=-1){
            inHouse newPart=new inHouse(partID,partName,partPrice,stock,min,max,machineID);
            partID++;
            Inventory.addPart(newPart);
            closeWindow();
            }
            }
        }
    
    /**
     * 
     * @param event
     * @throws Exception 
     * closes window when cancel button is clicked
     */
    @FXML
    private void cancelClicked(ActionEvent event) throws Exception {
        closeWindow();
    }


/**
 * 
 * @throws IOException 
 * method is created to easily close windows in multiple sections above.
 */
    private void closeWindow() throws IOException{
        Stage thisStage =(Stage)saveButton.getScene().getWindow();
        thisStage.close();
        mainMenu newMenu=new mainMenu();
        newMenu.mainScreen();
        
    }


}
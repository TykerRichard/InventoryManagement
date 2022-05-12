
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;



/**
 *
 * @author tyler
 */
public class errorChecking {
    

    private final Alert newAlert=new Alert(Alert.AlertType.ERROR);
    
    /**
     * 
     * @param inputString takes in the textField 
     * @param field this is used to make custom error message
     * @return inputString returns the string back to where it was called from
     * checks string displays error if blank returns string if not
     */
    public String stringCheck(TextField inputString,String field){
 
        if((inputString.getText()).isEmpty()){
            newAlert.setContentText(field +" is blank");
            newAlert.show();
        }
            return inputString.getText();
        }
    
    
    /**
     * 
     * @param inputDouble takes in the text field where a double was entered
     * @param field his is used to make custom error message
     * @return roubleCheck , -1
     * checks the double returns the double or -1
     */
    public double doubleCheck(TextField inputDouble,String field){
        //tries to parse to double if failed displays error. Returns value on sccuessful
        //parse returns -1 otherwise
        try{
            double doubleCheck=Double.parseDouble(inputDouble.getText());
            if(doubleCheck <= 0){
                newAlert.setAlertType(Alert.AlertType.WARNING);
                newAlert.setContentText(field + " cannot be 0 or less");
                newAlert.show( ); 
               
            }else{
               return doubleCheck;
                }
        }catch (NumberFormatException e){
            newAlert.setAlertType(Alert.AlertType.WARNING);
            newAlert.setContentText(field + " has invalid data");
            newAlert.show( ); 
            
            
        }
        return -1;
    } 
    
    /**
     * 
     * @param inputInt takes in field where int should have been entered
     * @param field his is used to make custom error message
     * @return intCheck or -1
     * checks the value entered for int returns the value as int or -1
     */
    public int intCheck(TextField inputInt,String field){
        //tries to parse int is value is less then 0 displays message
        //if parse succssful and is greater then 0 returns value other wise returns -1
        try{
            int intCheck=Integer.parseInt(inputInt.getText());
            if (intCheck<=0){
                newAlert.setAlertType(Alert.AlertType.WARNING);
                newAlert.setContentText(field +" cannot be less than 0");
                newAlert.show();
            }
            return intCheck;
              
        }catch (NumberFormatException e){
            newAlert.setAlertType(Alert.AlertType.WARNING);
            newAlert.setContentText(field + " has invalid data");
            newAlert.show();
            return -1;
            
        }
      
    }  
        
    
    /**
     * 
     * @param min takes in the min value 
     * @param max takes in the max value
     * @return boolean
     *  checks the min max values
     */
    public boolean minMaxCheck(int min, int max){
        //tries to check check min max displays error if min is less then max
        //if invalid data is entered display message (reduant check of value entered)
        //returns true or false based off of data entered
        try{
        if(min>max){
            newAlert.setAlertType(Alert.AlertType.WARNING);
            newAlert.setContentText("Max cannot be less than min");
            newAlert.show( );
            return false;
        }else return true;
        }catch (NumberFormatException e){
                newAlert.setAlertType(Alert.AlertType.WARNING);
                newAlert.setContentText("Invalid data in min or max field");
                newAlert.show( );
                }
        return false;
        
    } 

    /**
     * 
     * @param inv takes in inv 
     * @param min takes in min value
     * @param max takes in max value
     * @return boolean
     * checks the stock compared to min and max to make sure its within range
     */
    public boolean stockCheck (int inv,int min, int max){
        
        //checks stock level if correct returns true else gives error message
        if(inv <= max && max >=min && inv >= min){
            return true;

        }else
            newAlert.setAlertType(Alert.AlertType.WARNING);
            newAlert.setContentText("Inv has to be with the min / max range");
            newAlert.show();
            return false;
    }
    
    /**
     * 
     * @param inv takes in inv value
     * @param min takes in min value 
     * @param max takes in max value
     * @param name takes in name String
     * @param price takes in price value
     * @return boolean
     * this check is just to make sure the numbers are correct and if they are returns true
     */
    
    //this code is a little reduant but it makes for if statements when adding parts and products shorter
    public boolean checkAll(int inv, int min, int max,String name,Double price){
       if(inv>0 && min>0 && max>0 && price>0 &&max>=min &&price>0 && inv<=max && inv>=min && !(name.isEmpty())){
           return true;
       }
        else return false;
    }
    
    
    

        
   }



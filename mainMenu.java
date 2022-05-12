
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author tyler
 * Main method of the application
 * 
 */
public class mainMenu extends Application{
    
    
    
     @Override
    public void start(Stage mainStage) throws Exception {
        Parent mainParent= FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene mainScene=new Scene(mainParent);
        mainStage.setScene(mainScene);
        mainStage.show();
    }
    
    /**
     *
     * @throws IOException 
     * quick call to main screen from any other class
     */
    public void  mainScreen() throws IOException{
        Stage mainScreen=new Stage();
        Parent mainParent= FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene mainScene=new Scene(mainParent);
        mainScreen.setScene(mainScene);
        mainScreen.show();
        
    }
    
    /**
     * 
     * @throws IOException 
     * quick call to part menu from any other class
     */
     public void partScene() throws IOException{
        Stage partStage=new Stage();
        Parent partParent= FXMLLoader.load(getClass().getResource("partsMenu.fxml"));
        Scene partScene=new Scene(partParent);
        partStage.setScene(partScene);
        partStage.initModality(Modality.APPLICATION_MODAL);
        partStage.show(); 
        partStage.setOnCloseRequest(event -> {
            try {
                mainScreen();
            } catch (IOException ex) {
                
            }
        });

    }
     
     /**
      * 
      * @throws IOException 
      * quick call to part modify menu from any other class
      */
        public void partModifyScene() throws IOException{
            Stage partModifyStage=new Stage();
            Parent partModifyParent= FXMLLoader.load(getClass().getResource("partModifyMenu.fxml"));
            Scene partModifyScene=new Scene(partModifyParent);
            partModifyStage.setScene(partModifyScene);
            partModifyStage.initModality(Modality.APPLICATION_MODAL);
            partModifyStage.show(); 
            partModifyStage.setOnCloseRequest(event -> {
                try {
                    mainScreen();
                } catch (IOException ex) {
                
                }
            });        
    }
    
        /**
         * 
         * @throws IOException 
         * quick call to the add product screen from any other class
         */
        public void addProductScene() throws IOException{
            Stage productStage=new Stage();
            Parent productParent= FXMLLoader.load(getClass().getResource("addProduct.fxml"));
            Scene productScene=new Scene(productParent);
            productStage.setScene(productScene);
            productStage.initModality(Modality.APPLICATION_MODAL);
            productStage.show();         
            productStage.setOnCloseRequest(event -> {
                try {
                    mainScreen();
                } catch (IOException ex) {
                
                }
            });               
        }        
        
        /**
         * 
         * @throws IOException 
         * quick call to the modify product screen from any other class
         */
        public void modifyProductScene() throws IOException{
            Stage modifyProductStage=new Stage();
            Parent modifyProductParent= FXMLLoader.load(getClass().getResource("modifyProduct.fxml"));
            Scene deleteScene=new Scene(modifyProductParent);
            modifyProductStage.setScene(deleteScene);
            modifyProductStage.initModality(Modality.APPLICATION_MODAL);
            modifyProductStage.show();         
            modifyProductStage.setOnCloseRequest(event -> {
                try {
                    mainScreen();
                } catch (IOException ex) {
                
                }
            });            
        
        }
       
        

      
      
    /**
     * 
     * @param args 
     * starts the application 
     * javadocs location: finalProject\dist\javadoc
     *RUN TIMEERROR had to catch multiple NullPointerException errors due to some screen trying to pull data that didnt exist right away
     * FUTURE ENHANCEMENT connect application to data base to keep track of parts and products
     * FUTURE ENHANCEMENT have inventory levels update as new products are being created using parts and display errors when not enough parts or reach a 
     * certain threshold
     */    
    public static void main(String[] args) {
        launch(args);
    }    
 
    
 
    
    

 
}

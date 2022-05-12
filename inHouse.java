
/**
 *
 * @author tyler.richard
 * inHouse class from the inHouse objects
 */


public class inHouse extends Part {
    private int machineID;
    
    /**
     * 
     * @param id takes in the id value
     * @param name takes in the name value
     * @param price takes in the price value
     * @param stock takes in the stock value
     * @param min takes in the min value
     * @param max takes in the max value
     * @param machineID  takes in the machine id 
     * constructor for inHouse class
     */
    public inHouse(int id,String name,double price, int stock, int min, int max, int machineID){
        super(id,name,price,stock,min,max);
        this.machineID=machineID;
    }

    /**
     * 
     * @param machineID takes in machine id
     * sets the machine ID for inhouse class objects
     */
    public void setMachineID(int machineID){
        this.machineID=machineID;
    }
    
    /**
     * 
     * @return machineID returns machine id 
     * returns the machineID for the inHouse objects
     */
    public int getMachineID(){

                
        return machineID;
    }
    
}





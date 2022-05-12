
/**
 *
 * @author tyler.richard
 */
public class outsourced extends Part {
    private String companyName;
    
    /**
     * 
     * @param id takes in id value
     * @param name takes in name value
     * @param price takes in price value
     * @param stock takes in stock value
     * @param min takes in min value
     * @param max takes in max value
     * @param companyName  takes in companyName string
     * constructor for the outsourced class
     */
    public outsourced(int id,String name,double price, int stock, int min, int max, String companyName){
        super(id,name,price,stock,min,max);
        this.companyName=companyName;
    }
    
    /**
     * 
     * @param companyName 
     * takes in company name and sets it
     */
    public void setcompanyName(String companyName){
        this.companyName=companyName;
    }
    
    /**
     * 
     * @return companyName
     * this method returns companyName
     */
    public String getcompanyName(){
        
        return companyName;
    }

    
}




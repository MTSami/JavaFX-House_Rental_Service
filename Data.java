package houserental;

import javafx.beans.property.SimpleStringProperty;

public class Data {

    public final SimpleStringProperty rent;
    public final SimpleStringProperty availability;  
    public final SimpleStringProperty address;  
    public final SimpleStringProperty city;  
    public final SimpleStringProperty zip;
    public final SimpleStringProperty contact;  
    
    public Data(String r,String av, String ad, String c, String z, String con){
        this.rent= new SimpleStringProperty(r);
        this.availability = new SimpleStringProperty(av);
        this.address= new SimpleStringProperty(ad);
        this.city= new SimpleStringProperty(c);
        this.zip= new SimpleStringProperty(z);
        this.contact= new SimpleStringProperty(con);
    }
    
    public String getRent(){
        return rent.get();
    }
    
    public String getAvailability(){
        return availability.get();
    }
    
    public String getAddress(){
        return address.get();
    }
    public String getCity(){
        return city.get();
    }
    public String getZip(){
        return zip.get();
    }
    public String getContact(){
        return contact.get();
    }
    
    
    
    
    
    public void setRent(String r){
        rent.set(r);
    }
    
    public void setAvailability(String av){
        availability.set(av);
    }
    
    public void setAddress(String ad){
        address.set(ad);
    }
    
    public void setCity(String c){
        city.set(c);
    }
    
    public void setZip(String z){
        zip.set(z);
    }
    public void setContact(String con){
        contact.set(con);
    }
}


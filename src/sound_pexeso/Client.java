/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound_pexeso;

/**
 *
 * @author michal
 */
public class Client {
    private String clientName;
    private int clientId;
    
    public Client(){
        this("?", -1);
    }
    public Client(String name, int id){
        this.clientName  = name;
        this.clientId = id;
    }
    public int getClientId(){
        return clientId;
    }
    public String getClientName(){
        return clientName;
    }
    public void setClientId(int id){
        this.clientId = id;
    }
    public void setClientName(String name){
        this.clientName = name;
    }
    
    public boolean setClientIdFromString(String value){
        
        String pomName = value;
        pomName = pomName.replaceFirst("guest", "");
        try{
            this.clientId = Integer.parseInt(pomName);
        }
        catch(NumberFormatException e){
            return false;
        }
        
        System.out.println("Novy id podle jmena je: " + this.clientId );
        return true;
    }
}

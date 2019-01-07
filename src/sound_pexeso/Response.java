/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound_pexeso;

/**
 *
 * @author Michal-PC
 */
public class Response {
    private int responseId;
    private String params;
    private boolean valid;
    
    
    public Response(String rawResponse){
        String[] parts = MessageProcessing.getPartsOfMessage(rawResponse);
        this.valid = true;
        try{
            this.responseId = Integer.parseInt(parts[0]);
        }
        catch(NumberFormatException e){
            this.valid = false;
        }
        if(parts.length >= 2)
            if(parts[1] != null)
                this.params = parts[1];
    }
    
    public int getResponseId(){
        return this.responseId;
    }
    public String getParams(){
        return this.params;
    }
    public boolean isValid(){
        return this.valid;
    }
}

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
public class Request {
    private int requestId;
    private String params;
    private long sendTime;
    private boolean wasResent;
    
    public Request(int requestId, String params){
        this.requestId = requestId;
        this.params = params;
        this.sendTime = System.currentTimeMillis();
        this.wasResent = false;
    }
    public Request(int requestId){
        this(requestId, "");
    }
    
    public int getRequestId(){
        return this.requestId;
    }
    public long getSendTime(){
        return this.sendTime;
    }
    public boolean wasResent(){
        return this.wasResent;
    }
    public String getParams(){
        return this.params;
    }
    public void setSendTime(){
        this.sendTime = System.currentTimeMillis();
    }
    public void setWasResent(boolean value){
        this.wasResent = value;
    }
}

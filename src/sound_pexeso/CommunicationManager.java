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
public class CommunicationManager {
    private Request[] requestsSent;
    private static final int TIME_TO_RESPONSE = 2000;
    
    public CommunicationManager(){
        this.requestsSent = new Request[100];
        
    }
    public void addRequest(Request req){
        requestsSent[req.getRequestId()] = req;
    }
    public void removeRequest(String response){
        
    }
    public boolean checkSentRequests(){
        for(int i = 0; i < requestsSent.length; i++){
            long currentTime = System.currentTimeMillis();
            Request req = requestsSent[i];
            if(req != null && (currentTime - req.getSendTime() >= TIME_TO_RESPONSE)){
                if(req.wasResent()){
                    System.err.println("Request:"+ i +" timeout");
                    return false;
                }
                else{
                    requestsSent[i] = null;
                    System.out.println("Resending request:" + i);
                    TcpClient.getConnection().sendRequest(req);
                    if(requestsSent[i] != null){
                        requestsSent[i].setWasResent(true);
                    }
                }
            }
        }
        return true;
    }
}

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
public class MessageProcessing {
    public static String[] getPartsOfMessage(String message){
        System.out.println("Message to split: " + message);
        String delimiter = "\\|";
        String[] parts = message.split(delimiter);
        return parts;
    }
    public static String createMessageForServer(String clientId, String requestId, String params){
        StringBuilder sb = new StringBuilder();
        if(!clientId.equalsIgnoreCase("")){
            sb.append(clientId);
            sb.append('|');
        }
        
        sb.append(requestId);
        sb.append('|');
        if(!params.equalsIgnoreCase("")){
            sb.append(params);
        }
       
        sb.append('\0');
        return sb.toString();
    }
    public static String createMessageForServer(String requestId, String params){
       return createMessageForServer("",requestId, params);
    }
    public static String createMessageForServerWithoutDelimiter(int requestId){
        StringBuilder sb = new StringBuilder();     
        sb.append(requestId);
        sb.append('\0');
        return sb.toString();
    }
    public static String createMessageForServer(Request request){
        Client client = TcpClient.getClient();
        if(client == null || request == null){
            return null;
        }
        String clientId = Integer.toString(client.getClientId());
        String requestId = Integer.toString(request.getRequestId());
        return createMessageForServer(clientId, requestId, request.getParams());
    }
}

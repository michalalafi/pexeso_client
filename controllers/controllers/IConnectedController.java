/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import sound_pexeso.Client;

/**
 *
 * @author michal
 */
public interface IConnectedController {
    
    public void requestClientName();
    
    public void setClientName(String clientName);
    
    public void setStatus(String status);
    
    public void connected();
    
    public void disconnected();
    
    public void setSessionId(String sessionId);
    
    public void requestSessionId();
    
}

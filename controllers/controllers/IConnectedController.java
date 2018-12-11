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
    
    public void setClientName(Client client);
    
    public void connected();
    
    public void disconnected();
    
}

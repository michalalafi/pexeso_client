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
public class Protocol {
    /**STRUCTURES**/
    public static final int LOGIN_TO_LOBBY_REQUEST = 4;
    
    public static final int NEW_SESSION_REQUEST = 5;  
    public static final int NEW_SESSION_RESPONE = 5; 

    public static final int NEW_GAME_REQUEST = 6;
    public static final int NEW_GAME_RESPONSE = 6;
    /**INFO**/
    public static final int CLIENT_ID_RESPONSE = 10;
    
    public static final int CLIENTS_NAME_REQUEST = 11;
    public static final int CLIENTS_NAME_RESPONSE = 11;
    
    public static final int NUMBER_OF_CLIENTS_ONLINE_REQUEST = 12;
    public static final int NUMBER_OF_CLIENTS_ONLINE_RESPONSE = 12;
    
    public static final int SESSION_ID_REQUEST = 13;
    public static final int SESSION_ID_RESPONSE = 13;
    
    public static final int IS_ALIVE_REQUEST = 14;
    public static final int IS_ALIVE_RESPONSE = 14;
    
    public static final int STATUS_RESPONSE = 15;
    /**ACTIONS**/
    public static final int RETURN_TO_LOBBY_RESPONSE = 20;

    public static final int RECONNECT_REQUEST = 21;
    public static final int RECONNECT_RESPONSE = 21;
    
    public static final int DISCONNECT_REQUEST = 22;
    /**GAME**/
    public static final int WANT_TO_PLAY_GAME_RESPONSE = 30;

    public static final int NEW_GAME_BEGIN_RESPONSE = 31;
    
    public static final int NUMBER_OF_PEXESOS_RESPONSE = 32;
    
    public static final int OPPONENTS_NAME_RESPONSE = 33;
    
    public static final int IS_PLAYER_TURN_RESPONSE = 34;
    
    public static final int PEXESO_REVEAL_REQUEST = 35;
    public static final int PEXESO_REVEAL_RESPONSE = 35;
    
    public static final int PEXESO_REVEAL_ID_RESPONSE = 36;
    
    public static final int SUCCESFULLY_REVEALED_PEXESO_RESPONSE = 37;
    
    public static final int SCORE_RESPONSE = 38;
    
    public static final int END_OF_GAME_RESPONSE = 39;
    
    public static final int PLAY_AGAIN_RESPONSE = 40;
    
    /**ERRORS**/

    public static final int OPPONENT_LEFT_RESPONSE = 94;
    
    public static final int OPPONENT_LEFT_COMPLETELY_RESPONSE = 95;
       
}

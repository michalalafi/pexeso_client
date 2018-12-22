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
    /** LOBBY HANDLE **/
    public static final int LOGIN_TO_LOBBY_REQUEST = 4;
        public static final int CLIENT_ID_RESPONSE = 10;
    /** SESSION HANDLE **/
    public static final int NEW_SESSION_REQUEST = 5;  
    public static final int NEW_SESSION_RESPONE = 5;
    /** NEW GAME **/
    public static final int NEW_GAME_REQUEST = 6;
        public static final int NEW_GAME_BEGIN_RESPONSE = 13;
        public static final int WANT_TO_PLAY_GAME_RESPONSE = 14;
        public static final int PEXESO_REVEAL_REQUEST = 20;
        public static final int PEXESO_REVEAL_RESPONSE = 20; 
        public static final int IS_PLAYER_TURN_RESPONSE = 21;
        public static final int SCORE_RESPONSE = 22;
        public static final int PEXESO_REVEAL_ID_RESPONSE = 24;
        public static final int SUCCESFULLY_REVEALED_PEXESO_RESPONSE = 23;
        public static final int PLAY_AGAIN_RESPONSE = 25;
        public static final int END_OF_GAME_RESPONSE = 27;
        public static final int OPPONENTS_NAME_RESPONSE = 28;
        
        public static final int SESSION_ID_REQUEST = 40;
        public static final int SESSION_ID_RESPONSE = 40;
        
        public static final int CLIENTS_NAME_REQUEST = 41;
        public static final int CLIENTS_NAME_RESPONSE = 41;
        
        public static final int STATUS_RESPONSE = 42;
        public static final int NUMBER_OF_CLIENTS_ONLINE_REQUEST = 43;
        public static final int NUMBER_OF_CLIENTS_ONLINE_RESPONSE = 43;
        
        public static final int RETURN_TO_LOBBY_RESPONSE = 44;
        
        public static final int IS_ALIVE_REQUEST = 45;
        public static final int IS_ALIVE_RESPONSE = 45;
        
        public static final int RECONNECT_REQUEST = 46;
        public static final int RECONNECT_RESPONSE = 46;
        
        public static final int NUMBER_OF_PEXESOS_RESPONSE = 47;
        
        public static final int OPPONENT_LEFT_RESPONSE = 56;
       
}

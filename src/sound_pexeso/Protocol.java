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
        public static final int CLIENT_NAME_RESPONSE = 11;
    /** SESSION HANDLE **/
    public static final int NEW_SESSION_REQUEST = 5;  
        public static final int SESSION_ID_RESPONSE = 5;
    /** NEW GAME **/
    public static final int NEW_GAME_REQUEST = 6;
        public static final int WAIT_FOR_PLAYER_TO_JOIN = 12;
        public static final int NEW_GAME_BEGIN_RESPONSE = 13;
        public static final int WANT_TO_PLAY_GAME_RESPONSE = 14;
        public static final int WAIT_FOR_OPPONENT_DECIDE_RESPONSE = 15;
        public static final int PEXESO_REVEAL_REQUEST = 20;
        public static final int PEXESO_REVEAL_RESPONSE = 20; 
        public static final int IS_PLAYER_TURN_RESPONSE = 21;
        public static final int SCORE_RESPONSE = 22;
        public static final int PEXESO_REVEAL_ID_RESPONSE = 24;
        public static final int SUCCESFULLY_REVEALED_PEXESO_RESPONSE = 23;
        public static final int PLAY_AGAIN_RESPONSE = 25;
}

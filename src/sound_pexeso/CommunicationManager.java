/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound_pexeso;

import controllers.GameController;
import controllers.IConnectedController;
import controllers.LobbyController;
import controllers.WaitingController;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import static sound_pexeso.Protocol.*;
import static sound_pexeso.TcpClient.reconnect;
import utils.SoundPlayer;

/**
 *
 * @author michal
 */
public class CommunicationManager {
    private Request[] requestsSent;
    private static final int TIME_TO_RESPONSE = 2000;
    private static final int TIME_TO_SEND_IS_ALIVE = 10000;
    private static final int REQUEST_COUNT = 100;
    private long lastRequestSendTime = -1;
    
    public CommunicationManager(){
        this.requestsSent = new Request[REQUEST_COUNT];
        this.lastRequestSendTime = -1;
    }
    public void addRequest(Request req){
        req.setSendTime();
        requestsSent[req.getRequestId()] = req;
        
        this.lastRequestSendTime = req.getSendTime();
        
        System.out.printf("REQUEST:%d - Added to check array \n", req.getRequestId());
    }
    public void removeRequest(Response response){
        if(response.isValid()){
            if(requestsSent[response.getResponseId()] == null){
                System.out.printf("REQUEST: %d - Is not in check array \n", response.getResponseId());
            }
            else{
                requestsSent[response.getResponseId()] = null;
                System.out.printf("REQUEST: %d - Remove from check array \n", response.getResponseId());
            }
        }
    }
    public boolean checkSentRequests(){
        for(int i = 0; i < requestsSent.length; i++){
            long currentTime = System.currentTimeMillis();
            Request req = requestsSent[i];
            if(req != null && (currentTime - req.getSendTime() >= TIME_TO_RESPONSE)){
                if(req.wasResent()){
                    System.err.println("CHECK SENT REQUEST - Request:"+ i +" timeout");
                    return false;
                }
                else{
                    requestsSent[i] = null;
                    System.out.println("CHECK SENT REQUEST - Resending request:" + i);
                    TcpClient.getConnection().sendRequest(req);
                    if(requestsSent[i] != null){
                        requestsSent[i].setWasResent(true);
                    }
                }
            }
            if(req != null){
                System.out.printf("CHECK SENT REQUEST - [%d] - Request:%d \n",i, req.getRequestId());
            }
        }
        return true;
    }
    public void resetRequestsSent(){
        this.requestsSent = new Request[REQUEST_COUNT];
        this.lastRequestSendTime = -1;
    }
    public void sendIsAliveIfNotActive(){
        if(lastRequestSendTime != -1){
            //Pokud je prekrocena mez neaktivniho clienta
            if((System.currentTimeMillis() - lastRequestSendTime) > TIME_TO_SEND_IS_ALIVE){
                if(TcpClient.getClient().getClientId() != -1){
                    TcpClient.getConnection().sendRequest(new Request(IS_ALIVE_REQUEST));
                }
            }
        }
    }
    public static void handleResponse(Response response){
        switch(response.getResponseId()){
            case CLIENTS_NAME_RESPONSE:
                    setClientName(response.getParams());
                    break;
            case CLIENT_ID_RESPONSE:
                    setClientId(response.getParams());
                    break;
            case NEW_SESSION_RESPONE:
                    newSessionResponse(response.getParams());
                    break;
            case SESSION_ID_RESPONSE:
                    setSessionId(response.getParams());
                    break; 
            case NEW_GAME_BEGIN_RESPONSE:
                    newGameBegin(response.getParams());
                    break;
            case IS_PLAYER_TURN_RESPONSE:
                    playersTurn(response.getParams());
                    break;
            case PLAY_AGAIN_RESPONSE:
                    playAgain(response.getParams());
                    break;
            case PEXESO_REVEAL_RESPONSE:
                    // play sound
                    revealSound(response.getParams());
                    break;
            case PEXESO_REVEAL_ID_RESPONSE:
                    revealPexesoPlayed(response.getParams());
                    break;
            case SUCCESFULLY_REVEALED_PEXESO_RESPONSE:
                    successfullyRevealedPexesos(response.getParams());
                    break;
            case SCORE_RESPONSE:
                    setScore(response.getParams());
                    break;                     
            case WANT_TO_PLAY_GAME_RESPONSE:
                    wantToPlayGame();
                    break;             
            case END_OF_GAME_RESPONSE:
                    endOfGame();
                    break;
            case OPPONENTS_NAME_RESPONSE:
                    setOpponentsName(response.getParams());
                    break;
            case STATUS_RESPONSE:
                    statusResponse(response.getParams());
                    break;
            case NUMBER_OF_CLIENTS_ONLINE_RESPONSE:
                    setNumberOfClientsOnline(response.getParams());
                    break;
                    
            case RETURN_TO_LOBBY_RESPONSE:
                    returnToLobbyResponse(response.getParams());
                    break;
            case NUMBER_OF_PEXESOS_RESPONSE:
                    setNumberOfPexesos(response.getParams());
                    break;
            case OPPONENT_LEFT_RESPONSE:
                    opponentLeft(response.getParams());
                    break;
                    
            case OPPONENT_LEFT_COMPLETELY_RESPONSE:
                    opponentLeftCompletely(response.getParams());
                    break;
            case NEW_GAME_RESPONSE:
                    //Nemusime delat nic
                    System.out.println("NEW GAME RESPONSE - Nothing to do");
                    break;
        }
    }  
    private static void setClientName(String params){
        System.out.println("SET CLIENT NAME");
        
        TcpClient.getClient().setClientName(params);
        
        Platform.runLater(() -> {
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                controller.setClientName(params);
            }         
        });
    } 
    private static void setClientId(String params){
        System.out.println("SET CLIENT ID");
        
        try{
            TcpClient.getClient().setClientId(Integer.parseInt(params));
        }
        catch(NumberFormatException e){
            System.err.println("SET CLIENT ID - Number format exception");
            return;
        }
        
        reconnect = false;
        Platform.runLater(() -> {
            App.lobby();
        });
    }
    private static void newSessionResponse(String params){
        System.out.println("NEW SESSION RESPONSE");
        
        Platform.runLater(() -> {
            App.waiting();
        });
    }
    private static void setSessionId(String params){
        System.out.println("SET SESSION ID");
        
        Platform.runLater(() -> {
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                controller.setSessionId(params);
            }  
        });
    }
    private static void statusResponse(String params){
        System.out.println("STATUS RESPONSE");
        
        Platform.runLater(() -> {        
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                controller.setStatus(params);
            }  
        });
    }
    private static void playersTurn(String params){
        System.out.println("PLAYERS TURN");
        boolean isPlayersTurn;
        try{
            isPlayersTurn = Integer.parseInt(params) == 1;
        }
        catch(NumberFormatException e){
            System.err.println("PLAYERS TURN - Number format exception");
            return;
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                Platform.runLater(() -> {
                    GameController gameController = App.getGameController();
                    if(gameController != null){
                        gameController.getGameBoard().resetNotRevealedPlaySoundButtons();
                        gameController.getGameBoard().setDisableAllNotReveleadPlaySoundButtons(!isPlayersTurn);
                        gameController.setPlayersTurn(isPlayersTurn);
                    }
                });
            }
        }, 3000);
    }
    private static void playAgain(String params){
        System.out.println("PLAY AGAIN");   
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                Platform.runLater(() -> {
                    GameController gameController = App.getGameController();
                    if(gameController != null){
                        gameController.getGameBoard().setDisableAllNotReveleadPlaySoundButtons(false);
                        gameController.getGameBoard().setFirstClickedButtonDisable(true);
                    }
                });
            }
        }, 3000);
    }
    private static void newGameBegin(String params){
        System.out.println("NEW GAME BEGIN");
        
        Platform.runLater(() -> {
            App.game();
            
            GameController gameController = App.getGameController();
            if(gameController != null){
                gameController.getGameBoard().setDisableAllNotReveleadPlaySoundButtons(true);
            }
        });
    }
    private static void revealSound(String params){
        System.out.println("REVEAL SOUND");
        //Muzeme pustit takhle protoze to bezi asynchrone
        SoundPlayer.playSound(TcpClient.SOUNDS_PATH + params);
    }
    private static void revealPexesoPlayed(String params){
        System.out.println("REVEAL PEXESO PLAYED");
        //Oznacime odhalenou pexeso
        Platform.runLater(() -> {
            try{
                GameController gameController = App.getGameController();
                if(gameController != null){
                    gameController.getGameBoard().setPlaySoundButtonPlayed(Integer.parseInt(params));
                }
            }
            catch(NumberFormatException e){
                System.err.println("REVEAL PEXESO PLAYED - Number format exception");
            }
        });
    }
    private static void successfullyRevealedPexesos(String params){
        System.out.println("SUCCESSFULLY REVEALED PEXESO");
        
        String[] ids = params.split("\\;");
        int firstId, secondId;
        if(ids.length == 2){
            try{
                firstId = Integer.parseInt(ids[0]);
                secondId = Integer.parseInt(ids[1]);
            }
            catch(NumberFormatException e){
                System.err.println("SUCCESFULLY REVEALED PEXESO - Number format exception");
                return;
            }
        }
        else{
            return;
        }
        Platform.runLater(() -> {
            GameController gameController = App.getGameController();
            if(gameController != null){
                gameController.getGameBoard().setPlaySoundButtonRevealed(firstId);
                gameController.getGameBoard().setPlaySoundButtonRevealed(secondId);
            }
        });
    }  
    private static void setScore(String params){
        System.out.println("SET SCORE");
        
        Platform.runLater(() -> {
            GameController gameController = App.getGameController();
            if(gameController != null){
                String[] score = params.split("\\;");
                if(score.length == 2){
                    gameController.setScore(score);
                }
            }
        });
        
    }
    private static void wantToPlayGame(){
        System.out.println("WANT TO PLAY GAME");
        Platform.runLater(() -> {
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                if(controller instanceof WaitingController){
                    WaitingController waitingController = (WaitingController) controller;
                    waitingController.newGameConfirmation();
                }
            }
        });
    }
    private static void endOfGame(){
        System.out.println("END OF GAME");
        
        Platform.runLater(() -> {
            GameController gameController = App.getGameController();
            if(gameController != null){
                gameController.playAgainConfirmation();
            }
        });
    }
    private static void setOpponentsName(String params){
        System.out.println("SET OPPONENT NAME");
        
        Platform.runLater(() -> {
            GameController gameController = App.getGameController();
            if(gameController != null){
                gameController.setOpponentName(params);
            }
        });
    }
    private static void setNumberOfClientsOnline(String params){
        System.out.println("SET NUMBER OF CLIENTS ONLINE");
        Platform.runLater(() -> {
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                if(controller instanceof LobbyController){
                    LobbyController lobbyController = (LobbyController) controller;
                    try {
                        lobbyController.setNumberOfOnlineClients(Integer.parseInt(params));
                    } catch (NumberFormatException e) {
                        System.err.println("SET NUMBER OF CLIENTS ONLINE - number format exception");
                    }
                }
            }
        });      
    }
    private static void returnToLobbyResponse(String params){
        System.out.println("RETURN TO LOBBY");
        
        Platform.runLater(() -> {
            App.lobby();
        });
    }  
    private static void setNumberOfPexesos(String params){
        System.out.println("SET NUMBER OF PEXESOS");
        
        try {
            GameController.numberOfPexesos = Integer.parseInt(params);
        } catch (NumberFormatException e) {
            System.err.println("SET NUMBER OF PEXESOS - number format exception");
        }
    }
    private static void opponentLeft(String params){
        System.out.println("OPPONENT LEFT");
        
        Platform.runLater(() -> {
            GameController gameController = App.getGameController();
            if(gameController != null){
                gameController.getGameBoard().setDisableAllNotReveleadPlaySoundButtons(true);
                gameController.getGameBoard().resetNotRevealedPlaySoundButtons();
            }
        });
        
    }
    private static void opponentLeftCompletely(String params){
        System.out.println("OPPONENT LEFT COMPLETELY");
        
        Platform.runLater(() -> {
            App.waiting();
        });
    }
    
}



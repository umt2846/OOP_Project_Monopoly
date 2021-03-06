package domain.server.controller;

import domain.server.util.GameState;
import domain.util.Flags;
import network.server.serverFacade.ServerFacade;


public class ServerCommunicationHandler {
    private static ServerCommunicationHandler ourInstance;

    private String message;


    private ServerCommunicationHandler() {
    }

    public static ServerCommunicationHandler getInstance() {
        if (ourInstance == null)
            ourInstance = new ServerCommunicationHandler();
        return ourInstance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public synchronized void sendResponse(char flag, String message) {
        ServerFacade.getInstance()
                .send(GameState.getInstance().generateCurrentAction(flag, message));
    }

    //  public synchronized void sendResponse(char flag, String drawnCard , String name) {
    //      ServerFacade.getInstance()
    //              .send(GameState.getInstance().generateCurrentAction(flag, message));
    //  }

    public synchronized void sendResponse(char flag, int index, String message) {
        ServerFacade.getInstance()
                .send(index, GameState.getInstance().generateCurrentAction(flag, message));
    }

    public synchronized void sendResponse(char flag, int index) {
        ServerFacade.getInstance()
                .send(index, GameState.getInstance().generateCurrentAction(flag));
    }

    public synchronized void sendResponse(char flag, String name, int money, String square) {
        ServerFacade.getInstance()
                .send(GameState.getInstance().generateCurrentAction(flag, name, money, square));
    }

    public synchronized void sendResponse(char flag, String name, int money1, int money2, String square) {
        ServerFacade.getInstance()
                .send(GameState.getInstance().generateCurrentAction(flag, name, money1, money2, square));
    }


    public synchronized void sendResponse(char flag, String name, String args) {
        ServerFacade.getInstance()
                .send(GameState.getInstance().generateCurrentAction(flag, name, args));
    }

    public synchronized void sendResponse(char flag, int index, int count, String name) {
        ServerFacade.getInstance()
                .send(index, GameState.getInstance().generateCurrentAction(flag, count, name));
    }

    public synchronized void sendResponse(char flag, String player, String square, String buildingToUporDown) {
        if (flag == Flags.getFlag("Downgrade"))
            System.out.println("SendResponse " + player + " - " + square + " - " + buildingToUporDown);
        ServerFacade.getInstance().send(GameState.getInstance().generateCurrentAction(flag, player, square, buildingToUporDown));
    }

    public synchronized void sendResponse(char flag, int index, String message, String args) {
        ServerFacade.getInstance()
                .send(index, GameState.getInstance().generateCurrentAction(flag, message, args));
    }

    public void sendResponse(char flag, String name, String locat, int args) {
        ServerFacade.getInstance()
                .send(GameState.getInstance().generateCurrentAction(flag, name, locat, args));
    }
}

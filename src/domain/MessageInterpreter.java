package domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.board.*;
import domain.player.Player;
import domain.player.Token;

import java.io.IOException;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Planned as a Class that interprets received Message then updates game state
 */
public class MessageInterpreter {
    private static MessageInterpreter instance;


    private MessageInterpreter() {

    }

    public static MessageInterpreter getInstance() {
        if (instance == null)
            instance = new MessageInterpreter();
        return instance;
    }


    public synchronized void interpret(String m) {

        char flag = m.charAt(0);
        switch (flag) {
            case GameLogic.rollFlag:
                interpretRoll(m.substring(1));
                break;
            case GameLogic.buyFlag:
                interpretBuy(m.substring(1));
                break;
            case GameLogic.payRentFlag:
                interpretRent(m.substring(1));
                break;
            case GameLogic.finishTurnFlag:
                interpretFinishTurn();
                break;
            case GameLogic.bonusFlag:
                break;
            case GameLogic.queueFlag:
                interpretQueue(m.substring(1));
                break;
            case GameLogic.closeFlag:
                interpretClose();
                break;
            case GameLogic.upgradeFlag:
                interpretupdownGrade(m);
                break;
            case GameLogic.downgradeFlag:
                interpretupdownGrade(m);
            case GameLogic.moveFlag:
                interpretMove(m.substring(1));
                break;
            case GameLogic.increaseMoneyFlag:
                interpretMoneyChange(m.substring(1));
                break;
            case GameLogic.tokenFlag:
                interpretTokenMovement(m.substring(1));
                break;
            default:
                break;
        }
    }

    private void interpretTokenMovement(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        int seperate = message.indexOf("£");
        String name = null;
        Token token = null;
            try {
                name = objectMapper.readValue(message.substring(0,seperate), Player.class).getName();
                token = objectMapper.readValue(message.substring(0,seperate), Player.class).getToken();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int [] lastLoc = new int[2];
            int space = message.indexOf("@");
            lastLoc[0] = Integer.parseInt(message.substring(seperate+1,space));
            lastLoc[1] = Integer.parseInt(message.substring(space+1));
            int [] newLoc = token.getLocation();
            if(newLoc[1] > 10){
                UIUpdater.getInstance().setTokenLocation(name,1070 - (newLoc[1] - lastLoc[1]) * 72 , 150);
            }else
            UIUpdater.getInstance().setTokenLocation(name,1070,(newLoc[1] - lastLoc[1]) * 59 + 150);
    }


    private void interpretupdownGrade(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String name = null;
        DeedSquare square = null;
        int balance = 0;

        String playerMessage = message.substring(1, message.indexOf('~'));
        String squareMessage = message.substring(message.indexOf('~') + 1);

        try {
            name = objectMapper.readValue(playerMessage, Player.class).getName();
            square = objectMapper.readValue(squareMessage, DeedSquare.class);
            balance = objectMapper.readValue(playerMessage, Player.class).getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameLogic.getInstance().getPlayer(name).setBalance(balance);
        if (Board.getInstance().getNameGivenSquare(square.getName()) instanceof Property) {
            try {
                square = objectMapper.readValue(squareMessage, Property.class);
                ((Property) Board.getInstance().getNameGivenSquare(square.getName())).setBuildingList(((Property) square).getBuildingList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                square = objectMapper.readValue(squareMessage, Railroad.class);
                ((Railroad) Board.getInstance().getNameGivenSquare(square.getName())).setHasDepot(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int index;
        if (square instanceof Railroad) {
            index = GameLogic.getInstance().getPlayer(name).getOwnedRailroads().indexOf(square);
            GameLogic.getInstance().getPlayer(name).getOwnedRailroads().get(index).setHasDepot(true);

        } else if (square instanceof Property) {
            index = GameLogic.getInstance().getPlayer(name).getOwnedProperties().indexOf(square);
            GameLogic.getInstance().getPlayer(name).getOwnedProperties().get(index).setBuildingList(((Property) square).getBuildingList());
        }
        if (message.charAt(0) == GameLogic.upgradeFlag)
            UIUpdater.getInstance().setMessage(name + " upgraded " + square);
        else
            UIUpdater.getInstance().setMessage(name + " downgraded " + square);

    }

    private void interpretMoneyChange(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        int money = 0;
        String name = null;
        try {
            money = objectMapper.readValue(message, Player.class).getBalance();
            name = objectMapper.readValue(message, Player.class).getName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int changedMoney = money - GameLogic.getInstance().getPlayer(name).getBalance();

        GameLogic.getInstance().getPlayer(name).setBalance(money);
        if (changedMoney > 0) UIUpdater.getInstance().setMessage(name + " 's money increased by " + changedMoney);
        else UIUpdater.getInstance().setMessage(name + " 's money decreased by " + changedMoney);
    }

    private void interpretMove(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        int[] location = new int[2];
        String name = null;
        try {
            location = objectMapper.readValue(message, Player.class).getToken().getLocation();
            name = objectMapper.readValue(message, Player.class).getName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(name + " in the location " + Arrays.toString(location));

        GameLogic.getInstance().getPlayer(name).getToken().setLocation(location);

        UIUpdater.getInstance().setMessage(name + " moved to " + Board.getInstance().getSquare(location[0], location[1]).getName()); //TODO Mrmonopoly
    }

    private void interpretClose() {
        UIUpdater.getInstance().close();
    }

    private void interpretQueue(String q) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            Player[] players = objectMapper.readValue(q, Player[].class);
//            System.out.println(Arrays.toString(objectMapper.readValue(q, Player[].class)));
            Deque<Player> temp = new LinkedList<>();
            for (int i = 0; i < players.length; i++) {
                temp.addLast(players[i]);
            }
            GameLogic.getInstance().setPlayers(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Bots play on only host's program
        if (GameLogic.getInstance().getPlayerList().get(0).getReadiness().equals("Host")) {

            // Check for bots if they starts the game.
            for (Player player : GameLogic.getInstance().getPlayerList()) {
                if (player.getReadiness().equals("Bot") && ((RandomPlayer) player).checkTurn()) {
                    break;
                }
            }
        }
        UIUpdater.getInstance().turnUpdate();
    }

    private void interpretFinishTurn() {
        GameLogic.getInstance().switchTurn();
        UIUpdater.getInstance().turnUpdate();
    }

    private void interpretRoll(String message) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        int[] location = new int[2];
        String name = null;
        int[] faceValues = new int[3];
        try {
            location = objectMapper.readValue(message, Player.class).getToken().getLocation();
            name = objectMapper.readValue(message, Player.class).getName();
            faceValues = objectMapper.readValue(message, Player.class).getFaceValues();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GameLogic.getInstance().getPlayer(name).getToken().setLocation(location);

        UIUpdater.getInstance().setMessage(name + " rolled " + faceValues[0] + " " + faceValues[1] + " " + faceValues[2]); //TODO Mrmonopoly
    }


    public void interpretBuy(String message) {
        /*may be wrong !!!! */
        System.out.println("in interpret buy flag");

        ObjectMapper objectMapper2 = new ObjectMapper();
        objectMapper2.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        int[] location = null;
        String name = null;
        Square square = null;
        try {

            name = objectMapper2.readValue(message, Player.class).getName();
            System.out.println("name");

            location = objectMapper2.readValue(message, Player.class).getToken().getLocation();
            /* sq will be done for other classes */
            System.out.println("location");
            square = Board.getInstance().getSquare(location[0], location[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(square instanceof SpecialSquareStrategy) return;
        if(square==null) return;

        GameLogic.getInstance().getPlayer(name).decreaseMoney(((DeedSquare) square).getBuyValue());

        if (square instanceof Property) {
            GameLogic.getInstance().getPlayer(name).getOwnedProperties().add(((Property) square));
            UIUpdater.getInstance().setMessage(name + " bought " + square); //TODO Mrmonopoly
            ((Property) square).setOwner(GameLogic.getInstance().getPlayer(name));


        } else if (square instanceof Railroad) {
            GameLogic.getInstance().getPlayer(name).getOwnedRailroads().add(((Railroad) square));
            UIUpdater.getInstance().setMessage(name + " bought " + square); //TODO Mrmonopoly
            ((Railroad) square).setOwner(GameLogic.getInstance().getPlayer(name));


        } else if (square instanceof Utility) {
            GameLogic.getInstance().getPlayer(name).getOwnedUtilities().add(((Utility) square));
            UIUpdater.getInstance().setMessage(name + " bought " + square); //TODO Mrmonopoly
            ((Utility) square).setOwner(GameLogic.getInstance().getPlayer(name));
        }


    }


    public void interpretRent(String message) {
        System.out.println("in interpretRent");

        ObjectMapper objectMapper3 = new ObjectMapper();
        objectMapper3.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        int[] location = null;
        String name = null;
        Square square = null;
        int rentVal = 0;
        try {
            name = objectMapper3.readValue(message, Player.class).getName();
            location = objectMapper3.readValue(message, Player.class).getToken().getLocation();
            square = Board.getInstance().getSquare(location[0], location[1]);
            rentVal = ((DeedSquare) square).getRent();
            /* sq will be done for other classes */
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*payer*/
        if(square instanceof SpecialSquareStrategy) return;
        if(square==null) return;
        GameLogic.getInstance().getPlayer(name).decreaseMoney(rentVal);
        /*taker*/

        if (square instanceof Property) {
            GameLogic.getInstance().getPlayer(((Property) square).getOwner().getName()).increaseMoney(rentVal);
            UIUpdater.getInstance().setMessage(name + " paid payRent " + rentVal + " dollars to " + ((DeedSquare) square).getOwner().getName()); //TODO Mrmonopoly

        } else if (square instanceof Railroad) {
            GameLogic.getInstance().getPlayer(((Railroad) square).getOwner().getName()).increaseMoney(rentVal);
            UIUpdater.getInstance().setMessage(name + " paid payRent " + rentVal + " dollars to " + ((DeedSquare) square).getOwner().getName()); //TODO Mrmonopoly

        } else if (square instanceof Utility) {
            GameLogic.getInstance().getPlayer(((Utility) square).getOwner().getName()).increaseMoney(rentVal);
            UIUpdater.getInstance().setMessage(name + " paid payRent " + rentVal + " dollars to " + ((DeedSquare) square).getOwner().getName()); //TODO Mrmonopoly
        }


    }


}

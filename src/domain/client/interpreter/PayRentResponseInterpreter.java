package domain.client.interpreter;

import domain.client.ClientCommunicationHandler;
import domain.client.UIUpdater;
import domain.server.board.Board;
import domain.server.board.DeedSquare;
import domain.util.GameInfo;

public class PayRentResponseInterpreter implements ResponseInterpretable {
    /**
     * This method takes payer player, final balances of payer and payee and square name.
     * It uses square name to get owner of it then sets owner's and payer's balance.
     *
     * @param message The string array that has the rent flag, the payer player's name, payer player's and payee's final balance, and square name.
     */
    @Override
    public void interpret(String[] message) {
        // @requires: message.length()=5
        // @effects: Changes the payer(decreases by rent) and owner(increases by rent) player's balance field.
        String name = message[1];
        int customerFinalMoney = Integer.parseInt(message[2]);
        int ownerFinalMoney = Integer.parseInt(message[3]);
        String squareName = message[4];


        DeedSquare boughtSquare = ((DeedSquare) Board.getInstance().getNameGivenSquare(squareName));
        GameInfo.getInstance().getPlayer(name).setBalance(customerFinalMoney);
        GameInfo.getInstance().getPlayer(boughtSquare.getOwner()).setBalance(ownerFinalMoney);
//        String ownerName = boughtSquare.getOwner();
//        Board.getInstance().getNameGivenSquare(ownerName);


        UIUpdater.getInstance().setMessage(name + " paid rent $" + boughtSquare.getCurrentRent() + " for " + squareName + " to "
                + boughtSquare.getOwner());
//        String name = message[1];
//        String sqName = message[2];
//
//        Player player = GameLogic.getInstance().getPlayer(name);
//        DeedSquare square = (DeedSquare) Board.getInstance().getNameGivenSquare(sqName);
//
//        player.decreaseMoney(square.getRent());
//
//        square.getOwner().increaseMoney(square.getRent());
//        UIUpdater.getInstance().setMessage(name + " paid rent " + square.getRent() + " dollars to " + square.getOwner().getName());

        ClientCommunicationHandler.getInstance().sendReceived();
    }
}

package domain.server.interpreter;

import java.io.DataInputStream;

public class BuyRequestInterpreter implements RequestInterpretable {

    @Override
    public void interpret(DataInputStream dis, String[] message, int index) {
//        String name = message[1];
//        String sqName = message[2];
//
//        Player player = GameLogic.getInstance().getPlayer(name);
//        Square square = Board.getInstance().getNameGivenSquare(sqName);
//
//        player.decreaseMoney(((DeedSquare) square).getBuyValue());
//        player.addDeed(square);
//
//        ((DeedSquare) square).setOwner(player);
//
//        UIUpdater.getInstance().setMessage(name + " bought " + sqName);
    }

}

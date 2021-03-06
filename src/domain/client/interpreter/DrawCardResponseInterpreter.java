package domain.client.interpreter;

import domain.client.UIUpdater;
import domain.server.board.Board;
import domain.server.board.specialSquares.Chance;
import domain.server.board.specialSquares.CommunityChest;
import domain.server.card.ChanceCard;
import domain.server.card.Community;
import domain.util.GameInfo;
import network.client.clientFacade.ClientFacade;

public class DrawCardResponseInterpreter implements ResponseInterpretable {
    @Override
    public void interpret(String[] message) {
        String name = message[1];
        String drawnCard = message[2];

        UIUpdater.getInstance().setMessage(name + " drew " + drawnCard);

        int[] loc = GameInfo.getInstance().getCurrentPlayer().getToken().getLocation().clone();
        if (Board.getInstance().getSquare(loc[0], loc[1]) instanceof Chance) {
            ChanceCard sq = Board.getInstance().getChanceList()[0];
            if(sq.getName().equals("Hurricane") &&
                    (name.equals(ClientFacade.getInstance().getUsername()) ||
                            GameInfo.getInstance().isMyselfHost() && GameInfo.getInstance().isBot(name)))
                sq.doAction(name);
            else if(!sq.getName().equals("Hurricane"))
                sq.doAction(name);
            ChanceCard tmp = Board.getInstance().getChanceList()[0];
            Board.getInstance().setChanceList(0, Board.getInstance().getChanceList()[1]);
            Board.getInstance().setChanceList(1, tmp);
        } else if (Board.getInstance().getSquare(loc[0], loc[1]) instanceof CommunityChest) {
            Board.getInstance().getCommunityList()[0].doAction(name);
            Community tmp = Board.getInstance().getCommunityList()[0];
            Board.getInstance().setCommunityList(0, Board.getInstance().getCommunityList()[1]);
            Board.getInstance().setCommunityList(1, tmp);
        }

    }
}

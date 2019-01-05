package domain.client.interpreter;

import domain.client.ClientCommunicationHandler;
import domain.client.RandomPlayerActionFactory;
import domain.client.RandomPlayerHandler;
import domain.client.UIUpdater;
import domain.server.player.RandomPlayer;
import domain.util.GameInfo;

public class FinishResponseInterpreter implements ResponseInterpretable {
    @Override
    public void interpret(String[] message) {
        // @requires message of the form [Flag, name]
        //           GameInfo deque not null
        // @effects GameInfo playerDeque
        GameInfo.getInstance().nextTurn();
        UIUpdater.getInstance().turnUpdate();

        if (GameInfo.getInstance().isMyselfHost() && GameInfo.getInstance().isPeekBot()) {
            ClientCommunicationHandler.getInstance().sendReceived();
            RandomPlayerActionFactory.getInstance().generateStrategy("Roll").doRandomPlayerAction();
        } else {
            ClientCommunicationHandler.getInstance().sendReceived();
        }
    }
}

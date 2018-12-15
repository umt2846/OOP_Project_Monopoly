package domain.client.interpreter;

import domain.client.ClientCommunicationHandler;
import domain.client.RandomPlayerHandler;
import domain.client.UIUpdater;
import domain.util.GameInfo;

public class FinishResponseInterpreter implements ResponseInterpretable {
    @Override
    public void interpret(String[] message) {
        // @requires message of the form [Flag, name]
        //           GameInfo deque not null
        // @modifies GameInfo playerDeque
        GameInfo.getInstance().nextTurn();
        UIUpdater.getInstance().turnUpdate();

        if (GameInfo.getInstance().isMyselfHost() && GameInfo.getInstance().isPeekBot()) {
            ClientCommunicationHandler.getInstance().sendReceived();
            RandomPlayerHandler.getInstance().playBotTurn();
        } else {
            ClientCommunicationHandler.getInstance().sendReceived();
        }
    }
}

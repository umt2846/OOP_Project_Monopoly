package domain.client.interpreter;

import domain.client.UIUpdater;
import domain.server.util.GameInfo;

public class AddPlayerResponseInterpreter implements ResponseInterpretable {
    @Override
    public void interpret(String[] message) {
        UIUpdater.getInstance().changePanel();
        GameInfo.getInstance().addPlayer(message[1]);
    }
}

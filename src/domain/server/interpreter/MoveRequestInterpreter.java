package domain.server.interpreter;

import java.io.DataInputStream;

public class MoveRequestInterpreter implements RequestInterpretable {
    @Override
    public void interpret(DataInputStream dis, String[] message, int index) {
//        String name = message[1];
//        int[] location = MessageConverter.convertStringToIntArray(message[2]);
//
//        System.out.println(Arrays.toString(MessageConverter.convertStringToIntArray(message[2])));
//        System.out.println(Arrays.toString(location));
//
//        Player player = GameLogic.getInstance().getPlayer(name);
//
//        player.getToken().setLocation(location);
//
//        UIUpdater.getInstance().setMessage(name + " moved to " + Board.getInstance().getSquare(location[0], location[1]).getName()); //TODO Mrmonopoly
    }
}

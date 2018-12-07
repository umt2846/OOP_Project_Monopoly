package domain.server.interpreter;

import domain.server.GameLogic;
import domain.server.board.Board;
import domain.server.controller.ServerCommunicationHandler;
import domain.util.Flags;
import domain.util.MessageConverter;

import java.io.DataInputStream;
import java.io.IOException;

public class RollRequestInterpreter implements RequestInterpretable {
    @Override
    public void interpret(DataInputStream dis, String[] message, int index) {
        System.out.println("\n\nRollResponseInterpreter: interpret\n\n");

        String name = message[1];

        int [] rolled = GameLogic.getInstance().roll(name);

        ServerCommunicationHandler.getInstance().sendResponse(Flags.getFlag("Roll"), name, MessageConverter.convertArrayToString(rolled));

        while (true){
            try {
                String line = dis.readUTF();
                if(line.charAt(0)=='z') break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        GameLogic.getInstance().uptadeDoubleCounter(name);

        if (GameLogic.getInstance().checkMoveConditions(name)) {

            String newLoc = GameLogic.getInstance().move(name);

            String locName = Board.getInstance().getSquare(MessageConverter.convertStringToIntArray(newLoc)[0], MessageConverter.convertStringToIntArray(newLoc)[1]).getName();

            String loc = newLoc + "@" + locName;

            ServerCommunicationHandler.getInstance().sendResponse(Flags.getFlag("Move"), name, loc);
        }

        GameLogic.getInstance().checkMrMonopoly(name);

        if(GameLogic.getInstance().checkSecondTurn(name)){
            //ServerCommunicationHandler.getInstance().sendResponse(Flags.getFlag("Button"), name, "");
        }

        ServerCommunicationHandler.getInstance().sendResponse(Flags.getFlag("Button"), index, "000010000", name);
    }
}

package domain.server.interpreter;

import domain.server.controller.ServerCommunicationHandler;
import domain.server.die.DiceCup;
import domain.server.player.Player;
import domain.util.Flags;
import domain.util.GameInfo;
import domain.util.LoadGameHandler;
import domain.util.MessageConverter;
import network.server.serverFacade.ServerFacade;

import java.util.*;

public class StartRequestInterpreter implements RequestInterpretable {
    public static boolean received = false;

    @Override
    public void interpret(String[] message, int index) {
        String name = message[1];
        int count = GameInfo.getInstance().checkReadiness();
        if (count != 0) {
            ServerCommunicationHandler.getInstance()
                    .sendResponse(Flags.getFlag("DontStart"), index, count, name);
            return;
        }

        if (LoadGameHandler.getInstance().isNewGame()) {
            synchronized (this) {
                ServerCommunicationHandler.getInstance().sendResponse(Flags.getFlag("Start"), name);

                while (!received){
                    continue;
                }

                ServerCommunicationHandler.getInstance().sendResponse(Flags.getFlag("InitQueue"), name, MessageConverter.convertQueueToString(playerOrder()));

                while (!received){
                    continue;
                }
                ServerCommunicationHandler.getInstance().sendResponse(Flags.getFlag("Finish"), name);

                while (!received){
                    continue;
                }
                System.out.println("\n\nCurrPlayer:" + GameInfo.getInstance().getCurrentPlayer() + "\n");

                ServerCommunicationHandler.getInstance().sendResponse(Flags.getFlag("Button"), ServerFacade.getInstance().nameToIndex(GameInfo.getInstance().getCurrentPlayer()), "000001000", name);
            }
        } else {
            LoadGameHandler.getInstance().sendLoad();
        }
    }

    private Deque<String> playerOrder() {

        for (Player p : GameInfo.getInstance().getPlayerList()) {
            p.setFaceValues(DiceCup.getInstance().rollDice("Init"));
            System.out.println(p.getName() + " " + Arrays.toString(p.getFaceValues()));
        }

        ArrayList<Player> playerSortList = new ArrayList<>(GameInfo.getInstance().getPlayerList());
        Collections.sort(playerSortList);

        Deque<String> pQueue = new LinkedList<>();

        for (Player p : playerSortList) {
            pQueue.addLast(p.getName());
        }

        pQueue.addFirst(pQueue.removeLast()); // Added last player to first place since everyone will first pop the queue

        return pQueue;
    }
}

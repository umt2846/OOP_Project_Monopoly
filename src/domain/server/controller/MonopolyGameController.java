package domain.server.controller;

//
//import domain.util.Flags;
//import domain.server.listeners.CloseButtonListener;
//import domain.server.listeners.GameStartedListener;
//import domain.server.listeners.PlayerListChangedListener;
//import domain.server.player.Player;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
public class MonopolyGameController {
}
//    private ArrayList<Player> playerList;
//    private ArrayList<Player> playerSortList;
//    private Deque<Player> playerQueue;
//    private ArrayList<String> selectedColors;
//
//    private static MonopolyGameController monopolyGameController;
//
//    private ArrayList<PlayerListChangedListener> playerListChangedListeners;
//    private ArrayList<GameStartedListener> gameStartedListeners;
//    private ArrayList<CloseButtonListener> closeButtonListeners;
//
//    private MonopolyGameController() {
//        playerList = new ArrayList<>();
//        playerSortList = new ArrayList<>();
//        playerQueue = new LinkedList<>();
//        playerListChangedListeners = new ArrayList<>();
//        gameStartedListeners = new ArrayList<>();
//        closeButtonListeners = new ArrayList<>();
//        selectedColors = new ArrayList<>();
//    }
//
//    public static MonopolyGameController getInstance() {
//        if (monopolyGameController == null) {
//            monopolyGameController = new MonopolyGameController();
//        }
//
//        return monopolyGameController;
//    }
//
//    public boolean addPlayer(Player player) {
////        return playerList.add(player);
////        GameInfo.getInstance().addPlayer(player);
////        System.out.println("\n\nAdded Player" + player + "\n\n");
////        System.out.println(playerList + "\n\n");
////        publishPlayerListEvent();
//        ServerCommunicationHandler.getInstance().sendResponse(Flags.getFlag("AddPlayer"), player.getName());
//        return true;
//    }
//
//    private void publishCloseClickedEvent() {
//        for (CloseButtonListener cbl : closeButtonListeners) {
//            if (cbl == null) continue;
//            cbl.onCloseClickedEvent();
//        }
//    }
//
//    private void publishPlayerListEvent() {
//        for (PlayerListChangedListener plc : playerListChangedListeners) {
//            if (plc == null) continue;
//            plc.onPlayerListChangedEvent(selectedColors);
//        }
//    }
//
////    private void publishGameStartedEvent(ArrayList<String> pcl) {
////        for (GameStartedListener gls : gameStartedListeners) {
////            if (gls == null) continue;
////            gls.onGameStartedEvent();
////        }
////    }
//
//    public boolean addPlayerListChangedListener(PlayerListChangedListener plc) {
//        return playerListChangedListeners.add(plc);
//    }
//
//    public boolean addCloseButtonListener(CloseButtonListener cbl) {
//        return closeButtonListeners.add(cbl);
//    }
//
//    public boolean addGameStartedListener(GameStartedListener gsl) {
//        return gameStartedListeners.add(gsl);
//    }
//
//
//    public ArrayList<Player> getPlayerList() {
//        return playerList;
//    }
//
//    public Player getPlayerFromList(String name){
//        return playerList.stream().filter(player -> player.getName().equals(name)).findFirst().orElse(null);
//    }
//
////    public ArrayList<String> getPlayerListAsJSON() {
////        ObjectMapper mapper = new ObjectMapper();
////        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
////        String playerJson = null;
////        ArrayList<String> playerJSONs = new ArrayList<>();
////        try {
////            for (int i = 0; i < playerList.size(); i++) {
////                playerJson = mapper.writeValueAsString(playerList.get(i));
////                playerJSONs.add(playerJson);
////            }
////        } catch (JsonProcessingException e) {
////            e.printStackTrace();
////        }
////        return playerJSONs;
////    }
//
//    /**
//     * Uses stream to take each player's username and returns them as a list
//     *
//     * @return {@link ArrayList} of {@link Player}'s username
//     */
//    public ArrayList<String> getPlayerListName() {
//        return (ArrayList<String>) playerList.stream().map(Player::getName).collect(Collectors.toList());
//    }
//
//    public ArrayList<String> getPlayerListColor() {
//        return (ArrayList<String>) playerList.stream().map(Player::tokenColor).collect(Collectors.toList());
//    }
//
//    public ArrayList<String> getPlayerColorArray() {
//        ArrayList<String> pList = getPlayerListName();
//        ArrayList<String> cList = getPlayerListColor();
//        ArrayList<String> pca = new ArrayList<>();
//        for (int i = 0; i < pList.size(); i++) {
//            pca.add(i, pList.get(i) + "@" + cList.get(i));
//        }
//        return pca;
//    }
//
//    public void changePlayerColor(int index, String color) { //TODO DON'T USE INDEX
//        if (color == null) return;
//        String oldColor = playerList.get(index).getToken().getColor();
//        playerList.get(index).getToken().setColor(color);
//        System.out.println("Player's color " + playerList.get(0).getToken().getColor());
//        if (playerList.size() > 1) {
//            ConnectGameHandler.getInstance().sendColorChange(playerList.get(index));
//        }
//        selectedColors.add(color);
//        selectedColors.remove(oldColor);
//        publishPlayerListEvent();
//    }
//
//
//    public int checkReadiness() {
////        if (playerList.size() == 1) return -1;
////        if ((playerList.get(0).getReadiness().equals("Bot")) || (playerList.get(0).getReadiness().equals("Ready") &&
////                !playerList.get(0).getReadiness().equals("Host"))) {
////            startGame();
////            return 0; //  Can't return 1 since we return number of players not ready.
////        }
//        int notReady = 0;
//        for (int i = 1; i < playerList.size(); i++) {
//            if (playerList.get(i).getReadiness().equals("Not Ready")) notReady++;
//        }
//        if (notReady != 0) return notReady;
////        startGame();
//        return 0;
//    }
//
////    private synchronized void startGame() {
////        playerList.get(0).setStarted(true);
////        ConnectGameHandler.getInstance().sendGameStartedChange(playerList.get(0));
////        publishGameStartedEvent(getPlayerColorArray());
////        ServerCommunicationHandler.getInstance();
////        UIUpdater.getInstance();
////        GameLogic.getInstance().setPlayerList(playerList);
////        if (playerList.get(0).getReadiness().equals("Host")) {
////            initGame();
////        }
////    }
////
////    private void initGame() { // For now in this method players roll dice with initial roll strategy and they put to the queue corresponding to their total face values.
////        for (Player p : playerList) {
////            DiceCup.getInstance().rollDice("Init");
////            p.setFaceValues(DiceCup.getInstance().getFaceValues());
////            System.out.println(Arrays.toString(DiceCup.getInstance().getFaceValues()));
////        }
////        playerSortList.addAll(playerList);
////        Collections.sort(playerSortList);
////        playerQueue.addAll(playerSortList);
////        System.out.println("/////Player Queue//////");
////        for (Player p : playerQueue) {
////            System.out.println(p);
////        }
////
////        playerQueue.forEach(x -> GameLogic.getInstance().getPlayers().addLast(x.getName()));
//////        GameLogic.getInstance().setPlayers(playerQueue.stream().map(Player::getName).collect(Collectors.toCollection(LinkedList::new)));
////        ServerCommunicationHandler.getInstance().sendResponse(GameLogic.queueFlag, "");
////    }
//
//

//
//    public boolean removePlayer(String username) {
//        playerList.removeIf(player -> player.getName().equals(username));
//        System.out.println("\n\n----------===============---------\n" +
//                playerList + "\n");
//        publishPlayerListEvent();
//        return true;
//    }
//
//    public void reset() {
//        playerList = new ArrayList<>();
//    }
//}

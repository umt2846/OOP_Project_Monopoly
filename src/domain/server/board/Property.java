package domain.server.board;

import domain.server.building.Building;
import domain.server.building.Hotel;
import domain.server.building.Skyscraper;
import domain.util.MessageConverter;

import java.util.ArrayList;

public class Property extends DeedSquare implements Upgradable {
    private String color;
    private ArrayList<Building> buildingList;
    private boolean hasUpgrade;
    private int[] rents = new int[10];
    private int currentRent;

    public Property() {
        this("", 0, 0, 0, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, "");
    }

    public Property(String name, int layer, int index, int buyValue, int[] rents, String color) {
        super(name, layer, index, buyValue, null, rents);
        // this.rents = rents.clone();
        //  this.currentRent = rents[0];
        this.color = color;
        this.buildingList = new ArrayList<>();
        hasUpgrade = false;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUpgraded(boolean hasUpgrade) {
        this.hasUpgrade = hasUpgrade;
    }


    public ArrayList<Building> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(ArrayList<Building> buildingList) {
        this.buildingList = buildingList;
    }

    public int[] getRents() {
        return rents;
    }

    public void setRents(int[] rents) {
        this.rents = rents;
    }

    public int getCurrentRent() {
        return currentRent;
    }

    public void setCurrentRent(int currentRent) {
        this.currentRent = currentRent;
    }

    //    public int getHouseCost() {
//        return 0;
//    }

//    public int getHotelCost() {
//        return 0;
//    }

//    public int getSkyScrapperCost() {
//        return 0;
//    }

    public void updateRent() {
        if (this.buildingList.isEmpty()) {
            setCurrentRent(rents[0]);
        } else if (this.buildingList.get(0).getName().equals("Skyscrapper")) {
            setCurrentRent(rents[6]);
        } else if (this.buildingList.get(0).getName().equals("Hotel")) {
            setCurrentRent(rents[5]);
        } else {
            if (this.buildingList.size() == 4) {
                setCurrentRent(rents[4]);
            } else if (this.buildingList.size() == 3) {
                setCurrentRent(rents[3]);
            } else if (this.buildingList.size() == 2) {
                setCurrentRent(rents[2]);
            } else {
                setCurrentRent(rents[1]);
            }
        }
    }


    /**
     * Checks if all squares in Square's color group are at the same level.
     *
     * @param square
     * @return
     */
    public boolean isUpgradable(Property square,String name) {
        //if(square.getBuildingList().isEmpty()) return false;
        if(!square.getBuildingList().isEmpty() && square.getBuildingList().get(0) instanceof Skyscraper) return false;
        boolean checker = false;
        for (DeedSquare sq : Board.getInstance().getSameColoredSquares(square.getColor())) {
          //  if(square.getName().equals(sq.getName()))continue;
            if(sq.getOwner()==null) continue;
            else if(!sq.getOwner().equals(square.getOwner())) continue;
            if(!square.getBuildingList().isEmpty()&& !((Property)sq).getBuildingList().isEmpty()&& square.getBuildingList().get(0) instanceof Hotel &&
                    (((Property)sq).getBuildingList().get(0) instanceof Hotel || ((Property)sq).getBuildingList().get(0) instanceof Skyscraper)) {
                checker = true;
            }else if(!((Property)sq).getBuildingList().isEmpty() && square.getBuildingCount()==4 &&
                    (((Property)sq).getBuildingList().get(0) instanceof Hotel || ((Property)sq).getBuildingCount()==4)){
                checker = true;
            } else if (square.getBuildingCount() == 3 &&
                    (((Property) sq).getBuildingCount() == 3 || ((Property) sq).getBuildingCount() == 4)) {
                checker = true;
            }else if (square.getBuildingCount()==2 &&
                    (((Property)sq).getBuildingCount()==2 || ((Property)sq).getBuildingCount()==3)){
                checker = true;
            } else if (square.getBuildingCount() == 1 &&
                    (((Property) sq).getBuildingCount() == 1 || ((Property) sq).getBuildingCount() == 2)) {
                checker = true;
            }else if (square.getBuildingCount()==0 &&
                    (((Property)sq).getBuildingCount()==0 || ((Property)sq).getBuildingCount()==1)){
                checker =true;
            }
            else {
                return false;
            }
        }
        return checker;
    }

    public boolean isDowngradable(Property square) {
        boolean checker = false;
        if (square.getBuildingList().isEmpty()) return false;
        for (DeedSquare sq : Board.getInstance().getSameColoredSquares(square.getColor())) {
            if(sq.getOwner()==null)continue;
            else if (!sq.getOwner().equals(square.getOwner()))continue;
            if (!((Property)sq).getBuildingList().isEmpty() && square.getBuildingList().get(0) instanceof Skyscraper &&
                    (((Property) sq).getBuildingList().get(0) instanceof Skyscraper || ((Property) sq).getBuildingList().get(0) instanceof Hotel)) {
                checker = true;
            }else if(!((Property)sq).getBuildingList().isEmpty() && square.getBuildingList().get(0) instanceof Hotel &&
                    (((Property)sq).getBuildingList().get(0) instanceof Hotel
                            || ((Property)sq).getBuildingCount()==4)){
                checker=true;
            }else if(square.getBuildingCount()==4 &&
                (((Property)sq).getBuildingCount()==4 || ((Property)sq).getBuildingCount()==3)){
            checker = true;

            }else if (square.getBuildingCount()==3 &&
                (((Property)sq).getBuildingCount()==3 || ((Property)sq).getBuildingCount()==2)){
            checker = true;
            }else if (square.getBuildingCount()==2 &&
                (((Property)sq).getBuildingCount()==2 || ((Property)sq).getBuildingCount()==1)){
            checker = true;
            }else if (square.getBuildingCount()==1 &&
                    (((Property)sq).getBuildingCount()==1 || ((Property)sq).getBuildingCount()==0)) {
                checker = true;
            }
            else {
                return false;
            }
        }
        return checker;
    }

    @Override
    public String generateSaveInfo() {
        return super.generateSaveInfo() + ";" +
                color + ";" +
                MessageConverter.convertListToString(buildingList) + ";" +
                hasUpgrade + "+";
    }

    @Override
    public boolean isUpgraded() {
        return hasUpgrade;
    }

    @Override
    public int getBuildingCount() {
        return this.buildingList.size();
    }

}

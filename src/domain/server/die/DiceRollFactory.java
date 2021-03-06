package domain.server.die;

import domain.server.die.strategies.RollInJailStrategy;
import domain.server.die.strategies.RollInitialStrategy;
import domain.server.die.strategies.RollNormalStrategy;
import domain.server.die.strategies.RollThreeStrategy;

public class DiceRollFactory {
    private static DiceRollFactory df;

    private DiceRollStrategy diceRollStrategy;

    private DiceRollFactory() {
        //TODO the roll strategy choosing will change
    }

    public static DiceRollFactory getInstance() {
        if (df == null) {
            df = new DiceRollFactory();
        }
        return df;
    }

    public DiceRollStrategy getDiceRollStrategy() {
        return diceRollStrategy;
    }

    public void setDiceRollStrategy(DiceRollStrategy diceRollStrategy) {
        this.diceRollStrategy = diceRollStrategy;
    }

    public DiceRollStrategy chooseDiceRollStrategy(String locName) {  //TODO the roll strategy choosing will change
        DiceRollStrategy dcs;
        switch (locName) {
            case "RollThree":
                dcs = new RollThreeStrategy();
                break;
            case "Init":
                dcs = new RollInitialStrategy();
                break;
            case "Jail":
                dcs = new RollInJailStrategy();
                break;
            default:
                dcs = new RollNormalStrategy();
                break;
        }
        return dcs;
    }
}

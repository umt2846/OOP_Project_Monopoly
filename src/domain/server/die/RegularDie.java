package domain.server.die;

import java.util.Random;

public class RegularDie extends Die {

    private Random rn; // Random number generator added.

    public RegularDie() {
        super();
        setFaces(new int[]{1, 2, 3, 4, 5, 6});
        rn = new Random();
    }

    /**
     * The roll methods that rolls one dice using random class
     *
     * @param diceCup the dice cup that includes the regular dice.
     */
    @Override
    public void roll(DiceCup diceCup) { //TODO Implementation of this method may change
        int roll = rn.nextInt(6);
        this.setFaceValue(this.getFaces()[roll]);
        //diceCup.getInstance().getFaceValues()[index] = this.getFaces()[roll];
    }


}

package domain.server.player;

import domain.server.Savable;

import java.util.Arrays;

public class Token implements Savable {
    private int[] location;
    private String color;


    Token() {
        this(new int[]{1, 0}, "White");
    }

    public Token(String color) {
        this(new int[]{1, 0}, color);
    }

    public Token(int[] location, String color) {
        this.location = location;
        this.color = color;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Token{" +
                "location=" + Arrays.toString(location) +
                ", color='" + color + '\'' +
                '}';
    }

    @Override
    public String generateSaveInfo() {
        return location[0] + "," +
                location[1] + "," +
                color;
    }
}

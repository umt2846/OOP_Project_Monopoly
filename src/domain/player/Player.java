package domain.player;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import domain.board.Property;
import domain.board.Railroad;
import domain.board.Utility;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Player {
    private String name;
    private Token token;
    private int balance;
    private ArrayList<Property> ownedProperties;
    private ArrayList<Utility> ownedUtilities;
    private ArrayList<Railroad> ownedRailroads;
    private HashMap<String, Integer> colorMap; // TODO ?

    //TODO how to take color of token ?

    public Player(){
        this("");
    }

    public Player(String name) {
        this(name, new Token(), 3200, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap<>());
    }

    public Player(String name, Token token, int balance, ArrayList<Property> ownedProperties, ArrayList<Utility> ownedUtilities, ArrayList<Railroad> ownedRailroads, HashMap<String, Integer> colorMap) {
        this.name = name;
        this.token = token;
        this.balance = balance;
        this.ownedProperties = ownedProperties;
        this.ownedUtilities = ownedUtilities;
        this.ownedRailroads = ownedRailroads;
        this.colorMap = colorMap;
    }

    /**
     * Maps {@link Player} object to a JSON formatted String
     * Uses {@link com.fasterxml.jackson.annotation.JacksonAnnotation}
     * @return JSON representation of {@link Player}
     */
    public String toJSON(){
        String result = "";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            result = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Main method to test {@link #toJSON()}
     * @param args cmdline args
     */
    public static void main(String[] args) {
        Player player = new Player("Player32");
        System.out.println(player.toJSON());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}

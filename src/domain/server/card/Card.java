package domain.server.card;

public abstract class Card {
    private String name;

    public Card(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean doAction(String name);
}

public abstract class Card {
    protected String id;
    protected boolean faceUp;

    public Card(String id) {
        this.id = id;
        this.faceUp = false;
    }

    public String getId() {
        return id;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public abstract void flipUp();
    public abstract void flipDown();
}

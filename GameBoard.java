import javax.swing.*;
import java.util.*;

public class GameBoard {
    private int rows;
    private int cols;
    private List<MemoryCard> cards = new ArrayList<>();

    private MemoryCard firstSelected = null;
    private MemoryCard secondSelected = null;

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public void initializeCards(String[] cardIDs, Icon[] frontIcons, Icon backIcon) {
        cards.clear();
        List<String> cardPool = new ArrayList<>();
        for (String id : cardIDs) {
            cardPool.add(id);
            cardPool.add(id);
        }
        Collections.shuffle(cardPool);

        for (String id : cardPool) {
            int idx = Arrays.asList(cardIDs).indexOf(id);
            MemoryCard card = new MemoryCard(id, frontIcons[idx], backIcon);
            cards.add(card);
        }
    }

    public List<MemoryCard> getCards() {
        return cards;
    }

    public MemoryCard getFirstSelected() {
        return firstSelected;
    }

    public MemoryCard getSecondSelected() {
        return secondSelected;
    }

    public boolean selectCard(MemoryCard selected) {
        if (firstSelected == null) {
            firstSelected = selected;
            selected.flipUp();
            return false;
        } else if (secondSelected == null && selected != firstSelected) {
            secondSelected = selected;
            selected.flipUp();
            if (firstSelected.getId().equals(secondSelected.getId())) {
                firstSelected = null;
                secondSelected = null;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void flipBackUnmatched() {
        if (firstSelected != null && secondSelected != null) {
            firstSelected.flipDown();
            secondSelected.flipDown();
            firstSelected = null;
            secondSelected = null;
        }
    }

    public boolean allMatched() {
        for (MemoryCard card : cards) {
            if (!card.isFaceUp()) return false;
        }
        return true;
    }
}

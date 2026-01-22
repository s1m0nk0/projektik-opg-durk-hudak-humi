package modely;

import java.util.ArrayList;

public class Todo {
    private String nazov;
    private ArrayList<Item> polozky;

    public Todo(String nazov) {
        this.nazov = nazov;
        this.polozky = new ArrayList<>();
    }

    public String getNazov() {
        return nazov;
    }

    public void pridajPolozku(Item item) {
        polozky.add(item);
    }

    public void vymazPolozku(String nazovPolozky) {
        Item naVymazanie = null;

        for (Item i : polozky) {
            if (i.getNazov().equals(nazovPolozky)) {
                naVymazanie = i;
                break;
            }
        }

        if (naVymazanie != null) {
            polozky.remove(naVymazanie);
        }
    }

    public void oznacPolozkuAkoSplnenu(String nazovPolozky) {
        for (Item i : polozky) {
            if (i.getNazov().equals(nazovPolozky)) {
                i.markCompleted();
                break;
            }
        }
    }

    public ArrayList<Item> getPolozky() {
        return polozky;
    }
}

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

    public ArrayList<Item> getPolozky() {
        return polozky;
    }
}

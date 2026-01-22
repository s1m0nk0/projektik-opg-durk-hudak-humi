package mozog;

import modely.Todo;
import modely.Item;

public class Vypis {

    public static void vypisTodos(Manazer m) {
        for (Todo t : m.getTodos()) {
            System.out.println(t.getNazov());
        }
    }

    public static void vypisPolozky(Todo t) {
        for (Item i : t.getPolozky()) {
            System.out.println("- " + i.getNazov());
        }
    }
}
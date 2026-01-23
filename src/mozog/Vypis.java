package mozog;

import modely.Todo;
import modely.Item;

import java.util.List;

public class Vypis {

    public static void vypisTodos(Manazer m) {
        for (Todo t : m.getTodos()) {
            System.out.println(t.getNazov());
        }
    }

    public static void vypisPolozky(Todo t) {
        for (Item i : t.getPolozky()) {
            System.out.println(
                    "- " + i.getNazov() +
                    " | " + i.getPopis() +
                    " | deadline: " + (i.getDeadline() != null ? i.getDeadline() : "bez deadline") +
                    " | splnené: " + (i.isSplnene() ? "áno" : "nie")
            );
        }
    }

    public static void vypisPolozky(List<Item> polozky) {
        for (Item i : polozky) {
            System.out.println(
                    "- " + i.getNazov() +
                    " | " + i.getPopis() +
                    " | deadline: " + (i.getDeadline() != null ? i.getDeadline() : "bez deadline") +
                    " | splnené: " + (i.isSplnene() ? "áno" : "nie")
            );
        }
    }
}

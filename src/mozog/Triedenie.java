package mozog;

import modely.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Triedenie {

    public static List<Item> zoradPodlaDeadline(List<Item> polozky) {
        List<Item> zoradene = new ArrayList<>(polozky);
        Collections.sort(zoradene, new DeadlineComparator());
        return zoradene;
    }

    public static List<Item> zoradPodlaNazvu(List<Item> polozky) {
        List<Item> zoradene = new ArrayList<>(polozky);
        Collections.sort(zoradene, new NazovComparator());
        return zoradene;
    }

    private static class DeadlineComparator implements Comparator<Item> {

        @Override
        public int compare(Item a, Item b) {

            if (a.getDeadline() == null && b.getDeadline() == null) {
                return 0;
            }
            if (a.getDeadline() == null) {
                return 1;
            }
            if (b.getDeadline() == null) {
                return -1;
            }

            return a.getDeadline().compareTo(b.getDeadline());
        }

    }

    private static class NazovComparator implements Comparator<Item> {

        @Override
        public int compare(Item a, Item b) {
            return a.getNazov().compareToIgnoreCase(b.getNazov());
        }
    }
}

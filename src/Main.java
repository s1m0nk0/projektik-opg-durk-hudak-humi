import modely.Item;
import modely.Todo;
import mozog.Manazer;
import mozog.Vypis;
import mozog.Triedenie;
public class Main {
    public static void main(String[] args) {

        Manazer manazer = new Manazer();

        Todo skola = new Todo("Škola");
        manazer.pridajTodo(skola);

        skola.pridajPolozku(new Item("Zatematika", "DÚ strana 45", "2026-01-25"));
        skola.pridajPolozku(new Item("Slovenčina", "Čítanie", "2027-02-30"));

        System.out.println("Zoznam TODO:");
        Vypis.vypisTodos(manazer);

        System.out.println("\nPoložky v TODO Škola:");
        Vypis.vypisPolozky(skola);

        System.out.println("\nVytriedene podla nazvu:");
        Vypis.vypisPolozky(Triedenie.zoradPodlaNazvu(skola.getPolozky()));
        System.out.println("\nVytriedene podla deadline:");
        Vypis.vypisPolozky(Triedenie.zoradPodlaDeadline(skola.getPolozky()));


    }
}

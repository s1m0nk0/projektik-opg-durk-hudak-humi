import modely.Item;
import modely.Todo;
import mozog.Manazer;
import mozog.Vypis;
import mozog.Triedenie;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== TEST TODO APLIKÁCIE ===\n");

        // 1. Vytvorenie manažéra
        Manazer manazer = new Manazer();

        // 2. Vytvorenie TODO
        System.out.println("1) Vytváram TODO: Škola");
        Todo skola = new Todo("Škola");
        manazer.pridajTodo(skola);

        // 3. Pridávanie položiek
        System.out.println("\n2) Pridávam položky do TODO Škola");
        skola.pridajPolozku(new Item("Zatematika", "DÚ strana 45", "2026-01-25"));
        skola.pridajPolozku(new Item("Slovenčina", "Čítanie", "2027-02-30"));
        skola.pridajPolozku(new Item("Biológia", "Projekt", null));

        // 4. Výpis všetkých TODO
        System.out.println("\n3) Zoznam všetkých TODO:");
        Vypis.vypisTodos(manazer);

        // 5. Výpis položiek v TODO
        System.out.println("\n4) Položky v TODO Škola:");
        Vypis.vypisPolozky(skola);

        // 6. Označenie položky ako splnenej
        System.out.println("\n5) Označujem položku 'Zatematika' ako splnenú");
        skola.oznacPolozkuAkoSplnenu("Zatematika");

        System.out.println("\nAktuálny stav položiek:");
        Vypis.vypisPolozky(skola);

        // 7. Triedenie podľa názvu
        System.out.println("\n6) Triedenie položiek podľa názvu (splnené sú na konci):");
        Vypis.vypisPolozky(Triedenie.zoradPodlaNazvu(skola.getPolozky()));

        // 8. Triedenie podľa deadline
        System.out.println("\n7) Triedenie položiek podľa deadline (null deadline ide na koniec):");
        Vypis.vypisPolozky(Triedenie.zoradPodlaDeadline(skola.getPolozky()));

        // 9. Vymazanie položky
        System.out.println("\n8) Vymazávam položku 'Slovenčina'");
        skola.vymazPolozku("Slovenčina");

        System.out.println("\nStav po vymazaní položky:");
        Vypis.vypisPolozky(skola);
    }
}

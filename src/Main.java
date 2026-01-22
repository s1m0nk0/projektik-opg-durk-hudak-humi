import modely.Item;
import modely.Todo;
import mozog.Manazer;
import mozog.Vypis;

public class Main {
    public static void main(String[] args) {

        Manazer manazer = new Manazer();

        Todo skola = new Todo("Škola");
        manazer.pridajTodo(skola);

        skola.pridajPolozku(new Item("Matematika", "DÚ strana 45", "2026-01-25"));
        skola.pridajPolozku(new Item("Slovenčina", "Čítanie", null));

        System.out.println("Zoznam TODO:");
        Vypis.vypisTodos(manazer);

        System.out.println("\nPoložky v TODO Škola:");
        Vypis.vypisPolozky(skola);
    }
}

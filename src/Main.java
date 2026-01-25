import modely.Item;
import modely.Todo;
import mozog.Manazer;
import mozog.Vypis;
import mozog.Triedenie;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Manazer manazer = new Manazer();

        boolean bezi = true;

        while (bezi) {
            System.out.println("\n=== TODO APLIKÁCIA ===");
            System.out.println("1 - Vytvor TODO");
            System.out.println("2 - Pridaj položku do TODO");
            System.out.println("3 - Vypíš všetky TODO");
            System.out.println("4 - Vypíš položky v TODO");
            System.out.println("5 - Označ položku ako splnenú");
            System.out.println("6 - Zoradiť položky");
            System.out.println("0 - Koniec");
            System.out.print("Vyber možnosť: ");

            int volba = Integer.parseInt(sc.nextLine());

            switch (volba) {

                case 1:
                    System.out.print("Zadaj názov TODO: ");
                    String nazovTodo = sc.nextLine();
                    manazer.vytvorTodo(nazovTodo);
                    System.out.println("TODO vytvorené.");
                    break;

                case 2:
                    System.out.print("Zadaj názov TODO: ");
                    nazovTodo = sc.nextLine();
                    Todo todo = manazer.najdiTodo(nazovTodo);

                    if (todo == null) {
                        System.out.println("TODO neexistuje.");
                        break;
                    }

                    System.out.print("Názov položky: ");
                    String nazov = sc.nextLine();

                    System.out.print("Popis položky: ");
                    String popis = sc.nextLine();

                    System.out.print("Deadline (YYYY-MM-DD): ");
                    String deadline = sc.nextLine();

                    todo.pridajPolozku(new Item(nazov, popis, deadline));
                    System.out.println("Položka pridaná.");
                    break;

                case 3:
                    System.out.println("\nZoznam TODO:");
                    Vypis.vypisTodos(manazer);
                    break;

                case 4:
                    System.out.print("Zadaj názov TODO: ");
                    nazovTodo = sc.nextLine();
                    todo = manazer.najdiTodo(nazovTodo);

                    if (todo == null) {
                        System.out.println("TODO neexistuje.");
                        break;
                    }

                    System.out.println("Položky:");
                    Vypis.vypisPolozky(todo);
                    break;

                case 5:
                    System.out.print("Zadaj názov TODO: ");
                    nazovTodo = sc.nextLine();
                    todo = manazer.najdiTodo(nazovTodo);

                    if (todo == null) {
                        System.out.println("TODO neexistuje.");
                        break;
                    }

                    System.out.print("Zadaj názov položky: ");
                    String nazovPolozky = sc.nextLine();

                    todo.oznacPolozkuAkoSplnenu(nazovPolozky);
                    System.out.println("Položka označená ako splnená.");
                    break;

                case 6:
                    System.out.print("Zadaj názov TODO: ");
                    nazovTodo = sc.nextLine();
                    todo = manazer.najdiTodo(nazovTodo);

                    if (todo == null) {
                        System.out.println("TODO neexistuje.");
                        break;
                    }

                    System.out.println("1 - Podľa názvu");
                    System.out.println("2 - Podľa deadline");
                    System.out.print("Vyber triedenie: ");
                    int triedenie = Integer.parseInt(sc.nextLine());

                    if (triedenie == 1) {
                        Vypis.vypisPolozky(Triedenie.zoradPodlaNazvu(todo.getPolozky()));
                    } else if (triedenie == 2) {
                        Vypis.vypisPolozky(Triedenie.zoradPodlaDeadline(todo.getPolozky()));
                    } else {
                        System.out.println("Neplatná voľba.");
                    }
                    break;

                case 0:
                    bezi = false;
                    System.out.println("Aplikácia ukončená.");
                    break;

                default:
                    System.out.println("Neplatná voľba.");
            }
        }

        sc.close();
    }
}

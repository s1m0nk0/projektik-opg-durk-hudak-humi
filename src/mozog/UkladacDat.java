package mozog;

import modely.Item;
import modely.Todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UkladacDat {
    private static final String TYP_TODO = "TODO";
    private static final String TYP_ITEM = "ITEM";

    public static void uloz(Manazer manazer, Path cesta) throws IOException {
        Path priecinok = cesta.getParent();

        if (priecinok != null) {
            Files.createDirectories(priecinok);
        }

        List<String> riadky = new ArrayList<>();
        riadky.add("# TODO_DATA_V1");
        riadky.add("# TODO\\tnazov");
        riadky.add("# ITEM\\tnazov\\tpopis\\tdeadline\\tsplnene");

        for (Todo todo : manazer.getTodos()) {
            riadky.add(TYP_TODO + "\t" + escapuj(todo.getNazov()));

            for (Item item : todo.getPolozky()) {
                riadky.add(TYP_ITEM + "\t"
                        + escapuj(item.getNazov()) + "\t"
                        + escapuj(item.getPopis()) + "\t"
                        + escapuj(item.getDeadline()) + "\t"
                        + item.isSplnene());
            }
        }

        Files.write(cesta, riadky, StandardCharsets.UTF_8);
    }

    public static List<Todo> nacitaj(Path cesta) throws IOException {
        return parsuj(Files.readAllLines(cesta, StandardCharsets.UTF_8));
    }

    private static List<Todo> parsuj(List<String> riadky) {
        List<Todo> todos = new ArrayList<>();
        Todo aktualnyTodo = null;

        for (int i = 0; i < riadky.size(); i++) {
            String riadok = riadky.get(i);
            int cisloRiadku = i + 1;

            if (riadok.trim().isEmpty() || riadok.startsWith("#")) {
                continue;
            }

            String[] casti = riadok.split("\t", -1);
            String typ = casti[0];

            if (TYP_TODO.equals(typ)) {
                skontrolujPocet(casti, 2, cisloRiadku);
                String nazov = odEscapuj(casti[1], cisloRiadku);

                if (nazov.isBlank()) {
                    throw new IllegalArgumentException("Riadok " + cisloRiadku + ": TODO musi mat nazov.");
                }

                if (existujeTodo(todos, nazov)) {
                    throw new IllegalArgumentException("Riadok " + cisloRiadku + ": TODO s tymto nazvom uz existuje.");
                }

                aktualnyTodo = new Todo(nazov);
                todos.add(aktualnyTodo);
                continue;
            }

            if (TYP_ITEM.equals(typ)) {
                skontrolujPocet(casti, 5, cisloRiadku);

                if (aktualnyTodo == null) {
                    throw new IllegalArgumentException("Riadok " + cisloRiadku + ": ITEM musi byt za riadkom TODO.");
                }

                String nazov = odEscapuj(casti[1], cisloRiadku);
                String popis = odEscapuj(casti[2], cisloRiadku);
                String deadline = odEscapuj(casti[3], cisloRiadku);
                boolean splnene = nacitajBoolean(casti[4], cisloRiadku);

                if (nazov.isBlank()) {
                    throw new IllegalArgumentException("Riadok " + cisloRiadku + ": ITEM musi mat nazov.");
                }

                Item item = new Item(nazov, popis, deadline);

                if (splnene) {
                    item.oznacAkoSplnene();
                }

                aktualnyTodo.pridajPolozku(item);
                continue;
            }

            throw new IllegalArgumentException("Riadok " + cisloRiadku + ": neznamy typ riadku '" + typ + "'.");
        }

        return todos;
    }

    private static boolean existujeTodo(List<Todo> todos, String nazov) {
        for (Todo todo : todos) {
            if (todo.getNazov().equalsIgnoreCase(nazov)) {
                return true;
            }
        }

        return false;
    }

    private static void skontrolujPocet(String[] casti, int ocakavanyPocet, int cisloRiadku) {
        if (casti.length != ocakavanyPocet) {
            throw new IllegalArgumentException("Riadok " + cisloRiadku + ": nespravny pocet hodnot.");
        }
    }

    private static boolean nacitajBoolean(String hodnota, int cisloRiadku) {
        if ("true".equalsIgnoreCase(hodnota)) {
            return true;
        }

        if ("false".equalsIgnoreCase(hodnota)) {
            return false;
        }

        throw new IllegalArgumentException("Riadok " + cisloRiadku + ": splnene musi byt true alebo false.");
    }

    private static String escapuj(String hodnota) {
        if (hodnota == null) {
            return "";
        }

        return hodnota
                .replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private static String odEscapuj(String hodnota, int cisloRiadku) {
        StringBuilder vysledok = new StringBuilder();
        boolean escape = false;

        for (int i = 0; i < hodnota.length(); i++) {
            char znak = hodnota.charAt(i);

            if (!escape) {
                if (znak == '\\') {
                    escape = true;
                } else {
                    vysledok.append(znak);
                }
                continue;
            }

            switch (znak) {
                case 't' -> vysledok.append('\t');
                case 'n' -> vysledok.append('\n');
                case 'r' -> vysledok.append('\r');
                case '\\' -> vysledok.append('\\');
                default -> throw new IllegalArgumentException("Riadok " + cisloRiadku + ": neznamy escape znak \\" + znak + ".");
            }

            escape = false;
        }

        if (escape) {
            throw new IllegalArgumentException("Riadok " + cisloRiadku + ": riadok konci neukoncenym escape znakom.");
        }

        return vysledok.toString();
    }
}

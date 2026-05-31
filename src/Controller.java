import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modely.Item;
import modely.Todo;
import mozog.Manazer;
import mozog.Triedenie;
import mozog.UkladacDat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Controller {
    private final Manazer manazer = new Manazer();
    private List<Item> zobrazenePolozky = new ArrayList<>();

    @FXML
    private TextField nazovTodoField;

    @FXML
    private TextField nazovPolozkyField;

    @FXML
    private TextField deadlineField;

    @FXML
    private TextArea popisField;

    @FXML
    private TextField suborField;

    @FXML
    private ListView<String> todoList;

    @FXML
    private ListView<String> polozkyList;

    @FXML
    private Label stavLabel;

    @FXML
    private void initialize() {
        todoList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> zobrazPolozky());
        suborField.setText("data/todo-data.txt");
        stavLabel.setText("Vytvor TODO zoznam a pridaj do neho polozky.");
    }

    @FXML
    private void vytvorTodo() {
        String nazov = nazovTodoField.getText().trim();

        if (nazov.isEmpty()) {
            nastavStav("Zadaj nazov TODO zoznamu.");
            return;
        }

        if (manazer.najdiTodo(nazov) != null) {
            nastavStav("TODO s tymto nazvom uz existuje.");
            return;
        }

        manazer.vytvorTodo(nazov);
        nazovTodoField.clear();
        obnovTodoZoznam();
        todoList.getSelectionModel().select(nazov);
        nastavStav("TODO vytvorene.");
    }

    @FXML
    private void vymazTodo() {
        String nazov = todoList.getSelectionModel().getSelectedItem();

        if (nazov == null) {
            nastavStav("Vyber TODO, ktore chces vymazat.");
            return;
        }

        manazer.vymazTodo(nazov);
        obnovTodoZoznam();
        polozkyList.getItems().clear();
        nastavStav("TODO vymazane.");
    }

    @FXML
    private void pridajPolozku() {
        Todo todo = vybraneTodo();

        if (todo == null) {
            nastavStav("Najprv vyber TODO zoznam.");
            return;
        }

        String nazov = nazovPolozkyField.getText().trim();
        String popis = popisField.getText().trim();
        String deadline = deadlineField.getText().trim();

        if (nazov.isEmpty()) {
            nastavStav("Zadaj nazov polozky.");
            return;
        }

        todo.pridajPolozku(new Item(nazov, popis, deadline));
        nazovPolozkyField.clear();
        popisField.clear();
        deadlineField.clear();
        zobrazPolozky();
        nastavStav("Polozka pridana.");
    }

    @FXML
    private void oznacAkoSplnene() {
        Todo todo = vybraneTodo();
        Item item = vybranaPolozka(todo);

        if (item == null) {
            nastavStav("Vyber polozku, ktoru chces oznacit.");
            return;
        }

        todo.oznacPolozkuAkoSplnenu(item.getNazov());
        zobrazPolozky();
        nastavStav("Polozka oznacena ako splnena.");
    }

    @FXML
    private void vymazPolozku() {
        Todo todo = vybraneTodo();
        Item item = vybranaPolozka(todo);

        if (item == null) {
            nastavStav("Vyber polozku, ktoru chces vymazat.");
            return;
        }

        todo.vymazPolozku(item.getNazov());
        zobrazPolozky();
        nastavStav("Polozka vymazana.");
    }

    @FXML
    private void zoradPodlaNazvu() {
        Todo todo = vybraneTodo();

        if (todo == null) {
            nastavStav("Vyber TODO zoznam.");
            return;
        }

        zobrazPolozky(Triedenie.zoradPodlaNazvu(todo.getPolozky()));
        nastavStav("Polozky zoradene podla nazvu.");
    }

    @FXML
    private void zoradPodlaDeadline() {
        Todo todo = vybraneTodo();

        if (todo == null) {
            nastavStav("Vyber TODO zoznam.");
            return;
        }

        zobrazPolozky(Triedenie.zoradPodlaDeadline(todo.getPolozky()));
        nastavStav("Polozky zoradene podla deadline.");
    }

    @FXML
    private void ulozDoSuboru() {
        Path cesta = nacitajCestu();

        if (cesta == null) {
            return;
        }

        try {
            UkladacDat.uloz(manazer, cesta);
            nastavStav("Data ulozene do suboru: " + cesta);
        } catch (IOException e) {
            nastavStav("Subor sa nepodarilo ulozit: " + e.getMessage());
        }
    }

    @FXML
    private void nacitajZoSuboru() {
        Path cesta = nacitajCestu();
/*humi*/
        if (cesta == null) {
            return;
        }

        try {
            nahradData(UkladacDat.nacitaj(cesta));
            nastavStav("Data nacitane zo suboru: " + cesta);
        } catch (IOException | IllegalArgumentException e) {
            nastavStav("Subor sa nepodarilo nacitat: " + e.getMessage());
        }
    }

    private void obnovTodoZoznam() {
        todoList.getItems().setAll(manazer.getTodos().stream().map(Todo::getNazov).toList());
    }

    private void nahradData(List<Todo> todos) {
        manazer.nahradTodos(todos);
        obnovTodoZoznam();

        if (todos.isEmpty()) {
            polozkyList.getItems().clear();
            zobrazenePolozky = new ArrayList<>();
            return;
        }

        todoList.getSelectionModel().select(0);
        zobrazPolozky();
    }

    private Path nacitajCestu() {
        String text = suborField.getText().trim();

        if (text.isEmpty()) {
            nastavStav("Zadaj cestu k suboru.");
            return null;
        }

        try {
            return Path.of(text);
        } catch (IllegalArgumentException e) {
            nastavStav("Neplatna cesta k suboru: " + e.getMessage());
            return null;
        }
    }

    private void zobrazPolozky() {
        Todo todo = vybraneTodo();

        if (todo == null) {
            polozkyList.getItems().clear();
            zobrazenePolozky = new ArrayList<>();
            return;
        }

        zobrazPolozky(todo.getPolozky());
    }

    private void zobrazPolozky(List<Item> polozky) {
        zobrazenePolozky = new ArrayList<>(polozky);
        polozkyList.setItems(FXCollections.observableArrayList(
                zobrazenePolozky.stream()
                        .map(this::formatujPolozku)
                        .toList()
        ));
    }

    private Todo vybraneTodo() {
        String nazov = todoList.getSelectionModel().getSelectedItem();

        if (nazov == null) {
            return null;
        }

        return manazer.najdiTodo(nazov);
    }

    private Item vybranaPolozka(Todo todo) {
        if (todo == null) {
            return null;
        }

        int index = polozkyList.getSelectionModel().getSelectedIndex();

        if (index < 0 || index >= zobrazenePolozky.size()) {
            return null;
        }

        return zobrazenePolozky.get(index);
    }

    private String formatujPolozku(Item item) {
        String stav = item.isSplnene() ? "✅" : "⬜";
        String deadline = item.getDeadline().isEmpty() ? "bez deadline" : item.getDeadline();
        String popis = item.getPopis().isEmpty() ? "bez popisu" : item.getPopis();

        return stav + " " + item.getNazov()
                + "\n   Deadline: " + deadline
                + "\n   Popis: " + popis;
    }

    private void nastavStav(String sprava) {
        stavLabel.setText(sprava);
    }
}

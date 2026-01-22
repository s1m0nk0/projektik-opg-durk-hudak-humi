package mozog;

import modely.Todo;
import java.util.ArrayList;

public class Manazer {
    private ArrayList<Todo> todos;

    public Manazer() {
        todos = new ArrayList<>();
    }

    public void pridajTodo(Todo todo) {
        todos.add(todo);
    }

    public ArrayList<Todo> getTodos() {
        return todos;
    }
}

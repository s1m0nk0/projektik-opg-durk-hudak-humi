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

    public void vytvorTodo(String nazov) {
        if (najdiTodo(nazov) == null) {
            todos.add(new Todo(nazov));
        }
    }


    public void vymazTodo(String nazov) {
        Todo todo = najdiTodo(nazov);
        if (todo != null) {
            todos.remove(todo);
        }
    }

    public Todo najdiTodo(String nazov) {
        for (Todo t : todos) {
            if (t.getNazov().equals(nazov)) {
                return t;
            }
        }
        return null;
    }

    public ArrayList<Todo> getTodos() {
        return todos;
    }

}

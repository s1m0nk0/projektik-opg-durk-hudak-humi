# projektik-opg-durk-hudak-humi

---

## Popis balíčkov a tried

### `modely`
Obsahuje dátové triedy aplikácie.

- **Item** – reprezentuje jednu položku TODO
- **Todo** – reprezentuje jeden TODO zoznam (obsahuje viac položiek)

---

### `mozog`
Obsahuje aplikačnú logiku a analytiku.

- **Manazer** – správa viacerých TODO zoznamov
- **Triedenie** – triedenie položiek podľa názvu alebo deadline
- **Vypis** – výpis TODO zoznamov a položiek

---

### `Main`
Vstupný bod aplikácie, slúži na testovanie funkcionalít aplikácie.

---

## Použité technológie

- Java
- OOP princípy
- Kolekcie (`List`)

---
## Diagramy
### UML Class Diagram
```mermaid
classDiagram

namespace modely {

    class Item {
        -String nazov
        -String popis
        -boolean splnene
        -String deadline

        +Item(String nazov, String popis, String deadline)
        +String getNazov()
        +String getPopis()
        +boolean isSplnene()
        +String getDeadline()
        +void oznacAkoSplnene()
    }

    class Todo {
        -String nazov
        -ArrayList<Item> polozky

        +Todo(String nazov)
        +String getNazov()
        +void pridajPolozku(Item item)
        +void vymazPolozku(String nazovPolozky)
        +void oznacPolozkuAkoSplnenu(String nazovPolozky)
        +ArrayList<Item> getPolozky()
    }
}

namespace mozog {

    class Manazer {
        -ArrayList<Todo> todos

        +Manazer()
        +void pridajTodo(Todo todo)
        +void vytvorTodo(String nazov)
        +void vymazTodo(String nazov)
        +Todo najdiTodo(String nazov)
        +ArrayList<Todo> getTodos()
    }

    class Triedenie {
        +List<Item> zoradPodlaDeadline(List<Item> polozky)
        +List<Item> zoradPodlaNazvu(List<Item> polozky)
    }

    class DeadlineComparator {
        +int compare(Item a, Item b)
    }

    class NazovComparator {
        +int compare(Item a, Item b)
    }

    class Vypis {
        -void vypisItem(Item i)
        +void vypisTodos(Manazer m)
        +void vypisPolozky(Todo t)
        +void vypisPolozky(List<Item> polozky)
    }
}

class Main {
    +main(String[] args)
}

Todo *-- Item : obsahuje
Manazer *-- Todo : spravuje

Triedenie ..> Item : triedi
Triedenie *-- DeadlineComparator : používa
Triedenie *-- NazovComparator : používa

Vypis ..> Manazer : vypisuje
Vypis ..> Todo
Vypis ..> Item

Main ..> Manazer
Main ..> Todo
Main ..> Item
Main ..> Vypis
Main ..> Triedenie
```
### Flowchart Item.java
```mermaid
flowchart TD
    A[Start] --> B[Vytvorenie Item]
    B --> C[Nastav nazov]
    C --> D[Nastav popis]
    D --> E[Nastav deadline]
    E --> F[splnene = false]

    F --> G{Pouzivatel vola metodu?}

    G -->|getNazov| H[Vrati nazov]
    G -->|getPopis| I[Vrati popis]
    G -->|getDeadline| J[Vrati deadline]
    G -->|isSplnene| K[Vrati stav splnenia]

    G -->|oznacAkoSplnene| L[splnene = true]
    L --> M[Item je splneny]

    H --> G
    I --> G
    J --> G
    K --> G
    M --> G
```




## Poznámky

Projekt je určený ako školské zadanie / cvičný projekt. Neobsahuje grafické rozhranie ani ukladanie dát do súborov alebo databázy.

---

## Autori

Projekt vytvorený tímovou spoluprácou (Šimon Durkáč, Dávid Hudák ft. Tomáš Humeňaj).

## Špeciálne poďakovanie
<img src="d2d150f41a9e0c477aec7d49d90a6f5b.jpg" width="600">

package modely;

public class Item {
    private String nazov;
    private String popis;
    private boolean splnene;
    private String deadline;

    public Item(String nazov, String popis, String deadline) {
        this.nazov = nazov;
        this.popis = popis;
        this.deadline = deadline;
        this.splnene = false;
    }

    public String getNazov() {
        return nazov;
    }

    public String getPopis() {
        return popis;
    }

    public boolean isSplnene() {
        return splnene;
    }

    public String getDeadline() {
        return deadline;
    }

    public void oznacAkoSplnene() {
        splnene = true;
    }

}
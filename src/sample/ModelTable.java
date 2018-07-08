package sample;

public class ModelTable {
    String id;
    String pile;
    Integer amount;

    public ModelTable(String id, String pile, Integer amount) {
        this.id = id;
        this.pile = pile;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPile() {
        return pile;
    }

    public void setPile(String pile) {
        this.pile = pile;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}

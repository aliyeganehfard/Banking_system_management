package Model;

public class Bank {
    private Integer id;
    private String name;

    public Bank() {
    }

    public Bank(String bankName) {
        this.name = bankName;
    }

    public Bank(Integer id, String bankName) {
        this.id = id;
        this.name = bankName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "Id=" + id +
                ", bankName='" + name + '\'' +
                '}';
    }
}

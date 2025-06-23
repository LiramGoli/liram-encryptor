package iaf.ofek.omega.bda.menu;

public enum MenuOperations {

    Exit(0),
    Encrypt(1),
    Decrypt(2);

    private final Integer label;

    MenuOperations(Integer label) {
        this.label = label;
    }

    public Integer getLabel() {
        return label;
    }

}

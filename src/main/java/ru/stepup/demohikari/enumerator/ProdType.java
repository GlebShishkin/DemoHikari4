package ru.stepup.demohikari.enumerator;

public enum ProdType {
    счет(0), карта(1);

    int prodTypeId;
    private ProdType(int prodTypeId) {
        this.prodTypeId = prodTypeId;
    }
    public int getProdTypeId() {
        return prodTypeId;
    }

    public int getProdType() {
        return prodTypeId;
    }

}

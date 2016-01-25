package com.example.ivanalekseichuk.realmbug;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
public class ProductSubOption extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private ProductOption option;
    private boolean isNew;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ProductOption getOption() {
        return option;
    }
    public void setOption(ProductOption option) {
        this.option = option;
    }
    public boolean isNew() {
        return isNew;
    }
    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
package com.example.ivanalekseichuk.realmbug;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
public class Product extends RealmObject {
    @PrimaryKey private int id;
    private RealmList<ProductOption> options;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public RealmList<ProductOption> getOptions() {
        return options;
    }
    public void setOptions(RealmList<ProductOption> options) {
        this.options = options;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

package com.example.ivanalekseichuk.realmbug;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
public class ProductOption extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private ProductOption option;
    private RealmList<ProductSubOption> suboptions;
    private Product product;
    private boolean isNew;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public RealmList<ProductSubOption> getSuboptions() {
        return suboptions;
    }
    public void setSuboptions(RealmList<ProductSubOption> suboptions) {
        this.suboptions = suboptions;
    }
    public ProductOption getOption() {
        return option;
    }
    public void setOption(ProductOption option) {
        this.option = option;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }
    public boolean isNew() {
        return isNew;
    }
    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}

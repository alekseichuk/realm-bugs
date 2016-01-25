package com.example.ivanalekseichuk.realmbug;
import io.realm.RealmObject;
import io.realm.RealmResults;
public class SuboptionsActivity extends BaseActivity {
    public static final String OPTION = "optionId";
    @Override
    protected RealmResults<? extends RealmObject> listData() {
        int optionId = getIntent().getIntExtra(OPTION, 0);
        return realm()
                .where(ProductSubOption.class)
                .equalTo("option.id", optionId)
                .findAll();
    }
    @Override
    public String transform(Object object) {
        ProductSubOption subOption = (ProductSubOption) object;
        return subOption.getName() + (subOption.isNew() ? " (NEW)" : "");
    }
}

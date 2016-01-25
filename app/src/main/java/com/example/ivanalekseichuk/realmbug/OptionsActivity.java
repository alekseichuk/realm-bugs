package com.example.ivanalekseichuk.realmbug;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import io.realm.RealmObject;
import io.realm.RealmResults;
public class OptionsActivity extends BaseActivity {
    public static final String PRODUCT = "productId";
    @Override
    protected RealmResults<? extends RealmObject> listData() {
        int productId = getIntent().getIntExtra(PRODUCT, 0);
        return realm()
                .where(ProductOption.class)
                .equalTo("product.id", productId)
                .findAll();
    }
    @Override
    public String transform(Object object) {
        ProductOption option = (ProductOption) object;
        return option.getTitle() + (option.isNew() ? " (NEW)" : "");
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ProductOption option = (ProductOption) getListAdapter().getItem(position);
        Intent intent = new Intent(this, SuboptionsActivity.class);
        intent.putExtra(SuboptionsActivity.OPTION, option.getId());
        startActivity(intent);
    }
}

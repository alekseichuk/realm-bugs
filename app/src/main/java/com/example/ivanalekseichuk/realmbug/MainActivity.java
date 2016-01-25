package com.example.ivanalekseichuk.realmbug;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
public class MainActivity extends BaseActivity {
    private static final String SUB_COUNT = "count_sub";
    private static final String OPT_COUNT = "count_opt";
    private SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getPreferences(0);
        fillRealm();
    }
    @Override
    protected RealmResults<? extends RealmObject> listData() {
        return realm().where(Product.class).findAll();
    }
    //Generate fake data
    private void fillRealm() {
        final int optCount = (int) mPrefs.getLong(OPT_COUNT, 3L);
        final int subCount = (int) mPrefs.getLong(SUB_COUNT, 4L);
        Realm realm = realm();
        realm.beginTransaction();
        for (int i = 0; i < 2; i++) {
            Product p = findOrCreateProduct(realm, i);
            for (int j = 0; j < optCount; j++) {
                final int fakeOptionId = j + i * 10000;
                ProductOption po = findOrCreateOption(realm, p, fakeOptionId);
                p.getOptions().add(po);
                //TODO if to uncomment lines below issue disappears
                //if (!po.isValid()) {
                //    po = realm.copyToRealmOrUpdate(po);
                //}
                for (int k = 0; k < subCount; k++) {
                    int fakeSuboptionId = k + i * 10000 + j * 1000;
                    ProductSubOption pso = findOrCreateSuboption(realm, po, fakeSuboptionId);
                    po.getSuboptions().add(pso);
                }
            }
            realm.copyToRealmOrUpdate(p);
        }
        realm.commitTransaction();
        //Lines below will increase number of options/suboptions during each lunch
        //This is simulates new incoming items e.g. from backend
        mPrefs.edit().putLong(SUB_COUNT, subCount + 1).commit();
        mPrefs.edit().putLong(OPT_COUNT, optCount + 1).commit();
    }
    private Product findOrCreateProduct(Realm realm, int i) {
        Product p = realm.where(Product.class).equalTo("id", i).findFirst();
        if (p == null) {
            p = new Product();
            p.setId(i);
            p.setName("Product " + i);
        }
        //Cleans up all options attached to product to prevent duplication
        p.setOptions(new RealmList<ProductOption>());
        return p;
    }
    private ProductOption findOrCreateOption(Realm realm, Product p, int j) {
        ProductOption po = realm.where(ProductOption.class).equalTo("id", j).findFirst();
        if (po == null) {
            po = new ProductOption();
            po.setId(j);
            po.setTitle("Option " + p.getId() + "." + j);
            po.setProduct(p);
            po.setNew(true);
        } else {
            po.setNew(false);
        }
        //Cleans up all suboptions attached to option to prevent duplication
        po.setSuboptions(new RealmList<ProductSubOption>());
        return po;
    }
    private ProductSubOption findOrCreateSuboption(Realm realm, ProductOption po, int k) {
        ProductSubOption pso = realm.where(ProductSubOption.class).equalTo("id", k).findFirst();
        if (pso == null) {
            pso = new ProductSubOption();
            pso.setId(k);
            pso.setName("Suboption " + po.getProduct().getId() + "." + po.getId() + "." + k);
            pso.setNew(true);
            pso.setOption(po);
        } else {
            pso.setNew(false);
        }
        return pso;
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, OptionsActivity.class);
        i.putExtra(OptionsActivity.PRODUCT, ((Product) l.getAdapter().getItem(position)).getId());
        startActivity(i);
    }
    @Override
    public String transform(Object object) {
        return ((Product)object).getName();
    }
}

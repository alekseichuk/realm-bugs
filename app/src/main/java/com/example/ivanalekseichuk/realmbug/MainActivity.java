package com.example.ivanalekseichuk.realmbug;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity {
    private static final String SUB_COUNT = "count_sub";
    private static final String OPT_COUNT = "count_opt";
    private static final String LANUNCH_COUNT = "launch";
    private SharedPreferences mPrefs;
    private int mLaunch;
    private int mOptCount;
    private int mSubCount;
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRandom = new Random();
        mPrefs = getPreferences(0);
        mOptCount = mPrefs.getInt(OPT_COUNT, 3);
        mSubCount = mPrefs.getInt(SUB_COUNT, 4);
        mLaunch = mPrefs.getInt(LANUNCH_COUNT, 1);
        fillRealm();
    }

    @Override
    protected RealmResults<? extends RealmObject> listData() {
        return realm().where(Product.class).findAll();
    }

    //Generate fake data
    private void fillRealm() {
        Realm realm = realm();
        realm.beginTransaction();
        for (int i = 0; i < 2; i++) {
            Product p = createProduct(i);
            final int optCount = mOptCount + genDiff();
            for (int j = 0; j < optCount; j++) {
                final int fakeOptionId = j + i * 10000;
                ProductOption po = createOption(realm, p, fakeOptionId);
                p.getOptions().add(po);
                final int subCount = mSubCount + genDiff();
                for (int k = 0; k < mSubCount; k++) {
                    int fakeSuboptionId = k + i * 10000 + j * 1000;
                    ProductSubOption pso = createSuboption(realm, po, fakeSuboptionId);
                    po.getSuboptions().add(pso);
                }
            }
            realm.copyToRealmOrUpdate(p);
        }
        realm.commitTransaction();
        //Lines below will increase number of options/suboptions during each lunch
        //This is simulates new incoming items e.g. from backend
        mPrefs.edit()
                .putInt(SUB_COUNT, mSubCount + 1)
                .putInt(OPT_COUNT, mOptCount + 1)
                .putInt(LANUNCH_COUNT, mLaunch + 1)
                .commit();
    }

    private Product createProduct(int i) {
        Product p = new Product();
        p.setId(i);
        p.setName(mLaunch +  " Product " + i + " ");
        p.setOptions(new RealmList<ProductOption>());
        return p;
    }

    private ProductOption createOption(Realm realm, Product p, int j) {
        ProductOption po = new ProductOption();
        po.setId(j);
        po.setTitle(mLaunch + " Option " + p.getId() + "." + j);
        po.setProduct(p);
        po.setNew(realm.where(ProductOption.class).equalTo("id", j).count() == 0);
        po.setSuboptions(new RealmList<ProductSubOption>());
        return po;
    }

    private ProductSubOption createSuboption(Realm realm, ProductOption po, int k) {
        ProductSubOption pso = new ProductSubOption();
        pso.setId(k);
        pso.setName(mLaunch + "Suboption " + po.getProduct().getId() + "." + po.getId() + "." + k);
        pso.setNew(realm.where(ProductSubOption.class).equalTo("id", k).count() == 0);
        pso.setOption(po);
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
        return ((Product) object).getName();
    }

    private int genDiff() {
        return dice() ? 1 : dice() ? -1 : dice() ? -2 : dice() ? 2 : 0;
    }

    private boolean dice() {
        return mRandom.nextInt() % 2 == 0;
    }
}

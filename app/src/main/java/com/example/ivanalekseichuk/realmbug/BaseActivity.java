package com.example.ivanalekseichuk.realmbug;
import android.app.ListActivity;
import android.os.Bundle;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
public abstract class BaseActivity extends ListActivity implements CommonAdapter.Transformer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setListAdapter(new CommonAdapter<>(this, listData(), this));
    }
    /***
     * Convenient method for loading data from Realm
     * @return list of RealmObjects
     */
    protected abstract RealmResults<? extends RealmObject> listData();
    /***
     * Gives access to an instance of Realm. Removes all on conflict.
     * @return instance of Realm
     */
    public Realm realm() {
        return Realm.getInstance(new RealmConfiguration.Builder(getApplicationContext())
                .name("test.db")
                .deleteRealmIfMigrationNeeded()
                .build());
    }
}

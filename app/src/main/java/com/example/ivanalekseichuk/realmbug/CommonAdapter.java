package com.example.ivanalekseichuk.realmbug;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;
public class CommonAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {
    private final Context context;
    private final Transformer transformer;
    public CommonAdapter(Context context, RealmResults<T> realmResults, Transformer transformer) {
        super(context, realmResults, true);
        this.context = context;
        this.transformer = transformer;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        TextView tv = (TextView) LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        tv.setText(transformer.transform(getItem(position)));
        return tv;
    }
    /***
     * As classes extend RealmObject are not allowed to have overridden method
     * {@link Object#toString()} we should do it using transformer.
     */
    public interface Transformer {
        /***
         *  Just helps to transform {@link RealmObject} into {@link String}
         * @param object some descendant of RealmObject
         * @return string representation of {@code object}
         */
        String transform(Object object);
    }
}

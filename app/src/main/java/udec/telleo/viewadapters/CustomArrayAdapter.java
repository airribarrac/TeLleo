package udec.telleo.viewadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

abstract public class CustomArrayAdapter<T> extends ArrayAdapter<T> {
    private List<T> collection;
    public CustomArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return collection == null ? 0 : collection.size();
    }

    @Override
    public T getItem(int i) {
        return collection.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setCollection(List<T> collection){
        this.collection = collection;
        notifyDataSetChanged();
    }

    public List<T> getCollection(){
        return collection;
    }

    public void deleteItem(int i){
        collection.remove(i);
        notifyDataSetChanged();
    }

    public void deleteItem(T object){
        collection.remove(object);
        notifyDataSetChanged();
    }
}

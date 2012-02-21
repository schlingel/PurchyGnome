package at.fundev.purchy.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import at.fundev.purchy.models.PurchaseItem;

public class ItemNameAdapter extends ArrayAdapter<PurchaseItem> {
	private List<PurchaseItem> allItems;
	
	private List<PurchaseItem> filteredItems;
	
	private String filter;
	
	public ItemNameAdapter(Context context, int textViewResourceId,
			List<PurchaseItem> objects) {
		super(context, textViewResourceId, objects);
		allItems = objects;
		filteredItems = objects;
		filter = "";
	}

	public void setFilterName(String filter) {
		this.filter = filter == null ? "" : filter;
		filter();
	}

	private void filter() {
		final ArrayList<PurchaseItem> filtered = new ArrayList<PurchaseItem>();
		
		for(int i = 0; i < allItems.size(); i++) {	
			if(filter.toLowerCase().startsWith(allItems.get(i).getName().toLowerCase())) {
				filtered.add(allItems.get(i));
			}
		}
		
		filteredItems = filtered;
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}
	
	@Override
	public int getCount() {
		return filteredItems.size();
	}
	
	@Override
	public void add(PurchaseItem object) {
		filteredItems.add(object);
	}
	
	@Override
	public PurchaseItem getItem(int position) {
		return filteredItems.get(position);
	}
	
	@Override
	public void clear() {
		filteredItems.clear();
	}
	
	public void addItemAt(PurchaseItem item, int pos) {
		filteredItems.add(pos, item);
	}	
}

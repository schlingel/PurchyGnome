package at.fundev.purchy.ui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import at.fundev.purchy.R;
import at.fundev.purchy.models.PurchaseItem;

public class ItemAdapter extends ArrayAdapter<PurchaseItem> {
	private List<PurchaseItem> items;
	
	private LayoutInflater inflater;
	
	public ItemAdapter(Context context, int textViewResourceId,
			List<PurchaseItem> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.purchase_list_item, null);
		}
		
		final TextView lblName = (TextView)convertView.findViewById(R.id.lblName);
		final TextView lblPrice = (TextView)convertView.findViewById(R.id.lblPrice);
		final PurchaseItem item = getItem(position);
		
		lblName.setText(item.getName());
		lblPrice.setText(getPriceTag(item));
		
		return convertView;
	}
	
	private String getPriceTag(final PurchaseItem item) {
		return String.format("%f €", (float)item.getPriceInCent() / 100f);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public void add(PurchaseItem object) {
		items.add(object);
	}
	
	@Override
	public PurchaseItem getItem(int position) {
		return items.get(position);
	}
	
	@Override
	public void clear() {
		items.clear();
	}
	
	public void addItemAt(PurchaseItem item, int pos) {
		items.add(pos, item);
	}
	
	public List<PurchaseItem> getAllItems() {
		return items;
	}
}

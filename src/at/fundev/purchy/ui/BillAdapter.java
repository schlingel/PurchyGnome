package at.fundev.purchy.ui;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import at.fundev.purchy.R;
import at.fundev.purchy.models.Bill;
import at.fundev.purchy.models.DatabaseHelper;

public class BillAdapter extends ArrayAdapter<Bill> {
	private static final String TAG = BillAdapter.class.getSimpleName();
	
	private LayoutInflater inflater;
	
	private List<Bill> items;
	
	public BillAdapter(Context context, int textViewResourceId,
			List<Bill> objects) {
		super(context, textViewResourceId, objects);
		items = objects;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.bill_list_item, null);
		}
		
		final Bill item = getItem(position);
		final TextView lblDate = (TextView)convertView.findViewById(R.id.lblDate);
		final TextView lblSumPrice = (TextView)convertView.findViewById(R.id.lblSum);
		final Button btnDelete = (Button)convertView.findViewById(R.id.btnDelete);
		btnDelete.setTag(position);
		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Integer pos = (Integer)v.getTag();
				removeAt(pos);
				notifyDataSetChanged();
				AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						final DatabaseHelper helper = new DatabaseHelper(getContext());
						helper.getRTEBillDao().delete(item);
						return null;
					}
				};
				
				task.execute();
			}
		});
		
		lblSumPrice.setText(String.format("%f €", (float)getSum(item) / 100));
		lblDate.setText(getItem(position).getDate().toLocaleString());
		
		return convertView;
	}
	
	private int getSum(Bill bill) {
		int sum = 0;
		
		Log.i(TAG, "items - count: " + Integer.toString(bill.getItems().size()));
		
		for(int i = 0; i < bill.getItems().size(); i++) {
			sum += bill.getItems().get(i).getPriceInCent();
		}
		
		return sum;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}
	
	@Override
	public void add(Bill object) {
		items.add(object);
	}
	
	public void setItems(List<Bill> items) {
		this.items = items;
	}
	
	@Override
	public Bill getItem(int position) {
		return items.get(position);
	}
	
	@Override
	public void clear() {
		items.clear();
	}
	
	public void removeAt(int pos) {
		items.remove(pos);
	}
	
	public void addItemAt(Bill item, int pos) {
		items.add(pos, item);
	}
	
	public List<Bill> getAllItems() {
		return items;
	}
}

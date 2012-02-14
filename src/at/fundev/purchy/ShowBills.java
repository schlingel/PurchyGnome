package at.fundev.purchy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import at.fundev.purchy.models.Bill;
import at.fundev.purchy.models.DatabaseHelper;
import at.fundev.purchy.ui.BillAdapter;
import at.fundev.purchy.ui.ItemAdapter;

public class ShowBills extends Activity implements OnItemClickListener {
	private static final String TAG = ShowBills.class.getSimpleName();
	
	private ListView lvBills;
	
	private BillAdapter billAdapter;
	
	private DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_bills);
		initViews();
		
		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					dbHelper = new DatabaseHelper(ShowBills.this);
					final List<Bill> bills = dbHelper.getBills();
					lvBills.post(new Runnable() {
						
						@Override
						public void run() {
							billAdapter.setItems(bills);
							billAdapter.notifyDataSetChanged();
						}
					});
				} catch(SQLException e) {
					Log.e(TAG, "Couldn't fetch bills", e);
				}
				
				return null;
			}
		};
		
		task.execute();
	}
	
	private void initViews() {	
		billAdapter = new BillAdapter(this, R.layout.bill_list_item, new ArrayList<Bill>());
		lvBills = (ListView)findViewById(R.id.lvBills);
		lvBills.setAdapter(billAdapter);
		lvBills.setOnItemClickListener(this);
	}
	
	public static Intent asIntent(Context sender, Bundle bundle) {
		final Intent intent = new Intent(sender, ShowBills.class);
		intent.putExtras(bundle);
		return intent;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		final Bill bill = billAdapter.getItem(pos);
		startActivity(ShowBill.asIntent(this, bill, new Bundle()));
	}
}

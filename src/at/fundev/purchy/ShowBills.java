package at.fundev.purchy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import at.fundev.purchy.async.AsyncWorkerLauncher;
import at.fundev.purchy.async.IAsyncWorker;
import at.fundev.purchy.models.Bill;
import at.fundev.purchy.models.DatabaseHelper;
import at.fundev.purchy.ui.BillAdapter;

public class ShowBills extends Activity implements OnItemClickListener, IAsyncWorker {
	private static final String TAG = ShowBills.class.getSimpleName();
	
	private ListView lvBills;
	
	private BillAdapter billAdapter;
	
	private DatabaseHelper dbHelper;
	
	private List<Bill> bills;
	
	private Date curSelectedDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_bills);
		initViews();
		AsyncWorkerLauncher.from(this).execute();
	}

	@Override
	public void processAsync() {
		try {
			curSelectedDate = new Date();
			dbHelper = new DatabaseHelper(ShowBills.this);
			bills = dbHelper.getBillsOfYear(curSelectedDate);
		} catch(SQLException e) {
			Log.e(TAG, "Couldn't fetch bills", e);
		}		
	}
	
	@Override
	public void onAsyncFinish() {
		billAdapter.setItems(bills);
		billAdapter.notifyDataSetChanged();
	}
	
	private void initViews() {	
		billAdapter = new BillAdapter(this, R.layout.bill_list_item, new ArrayList<Bill>());
		lvBills = (ListView)findViewById(R.id.lvBills);
		lvBills.setAdapter(billAdapter);
		lvBills.setOnItemClickListener(this);
	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		final Bill bill = billAdapter.getItem(pos);
		startActivity(ShowBill.asIntent(this, bill, new Bundle()));
	}
	
	public static Intent asIntent(Context sender, Bundle bundle) {
		final Intent intent = new Intent(sender, ShowBills.class);
		intent.putExtras(bundle);
		return intent;
	}
}

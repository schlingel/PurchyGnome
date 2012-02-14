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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import at.fundev.purchy.models.Bill;
import at.fundev.purchy.models.DatabaseHelper;
import at.fundev.purchy.models.PurchaseItem;
import at.fundev.purchy.ui.ItemAdapter;

public class AddBill extends Activity implements OnClickListener {
	private static final String TAG = AddBill.class.getName();
	
	private Button btnAddItem;
	
	private Button btnOK;
	
	private Button btnCancel;
	
	private EditText txtBxProductPrice;
	
	private EditText txtBxProductName;
	
	private ListView lvItems;
	
	private ItemAdapter itemAdapter;
	
	private DatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bill);
		initViews();
		dbHelper = new DatabaseHelper(this);
	}

	private void initViews() {
		btnAddItem = (Button)findViewById(R.id.btnAddItem);
		btnOK = (Button)findViewById(R.id.btnOK);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		txtBxProductName = (EditText)findViewById(R.id.txtBxProductName);
		txtBxProductPrice = (EditText)findViewById(R.id.txtBxProductPrice);
		lvItems = (ListView)findViewById(R.id.lvItems);
		
		itemAdapter = new ItemAdapter(this, R.layout.purchase_list_item, new ArrayList<PurchaseItem>());
		lvItems.setAdapter(itemAdapter);
		
		btnAddItem.setOnClickListener(this);
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v.equals(btnAddItem)) {
			addItem();
		} else if(v.equals(btnOK)) {
			if(persistNewBill()) {
				finish(); // only exit current activity when DB insert succeeded
			}
		} else if(v.equals(btnCancel)) {
			finish();
		}
	}   
	
	private boolean persistNewBill() {
		try {
			final List<PurchaseItem> items = itemAdapter.getAllItems();
			final Bill bill = new Bill();
			bill.setDate(new Date());
			dbHelper.getBillDao().create(bill);
			int id = bill.getId();
			
			
			Log.i(TAG, String.format("Persisted bill with id %d", id));
			
			for(int i = 0; i < items.size(); i++) {
				items.get(i).setBillId(id);
				dbHelper.getPurchaseDao().create(items.get(i));
				Log.i(TAG, "created new item - price: " + Long.toString(items.get(i).getPriceInCent()));
			}
			
			return true;
		} catch(SQLException e) {
			Log.e(TAG, "Couldn't create new bill!", e);
			Toast.makeText(this, R.string.msgCouldntAddBill, Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	private void addItem() {
		final PurchaseItem item = new PurchaseItem();
		final Long price = getPriceFromUI();
		final String name = txtBxProductName.getText().toString();
		
		if(price == null) {
			Toast.makeText(this, R.string.msgInvalidPrice, Toast.LENGTH_SHORT).show();
			txtBxProductPrice.setText("");
			return;
		}
		
		if("".equals(name)) {
			Toast.makeText(this, R.string.msgInvalidName, Toast.LENGTH_SHORT).show();
			return;
		}
	
		item.setName(name);
		item.setPriceInCent(price);
		itemAdapter.addItemAt(item, 0);
		itemAdapter.notifyDataSetChanged();
	}
	
	private Long getPriceFromUI() {
		try {
			String sPrice = txtBxProductPrice.getText().toString();
			return Long.parseLong(sPrice);
		} catch(NumberFormatException e) {
			return null;
		}
	}
	
	public static Intent asIntent(Context sender, Bundle bundle) {
		final Intent intent = new Intent(sender, AddBill.class);
		intent.putExtras(bundle);
		return intent;
	}
}

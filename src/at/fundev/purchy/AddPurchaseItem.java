package at.fundev.purchy;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import at.fundev.purchy.async.IAsyncWorker;
import at.fundev.purchy.models.DatabaseHelper;
import at.fundev.purchy.models.PurchaseItem;
import at.fundev.purchy.ui.ItemNameAdapter;

public class AddPurchaseItem extends Activity implements IAsyncWorker, OnItemClickListener {
	private EditText txtBxPrice;
	
	private EditText txtBxName;
	
	private Button btnCancel;
	
	private Button btnAddItem;
	
	private ListView lvItems;
	
	private DatabaseHelper dbHelper;
	
	private ItemNameAdapter itemsAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_purchase_item);
		
		txtBxName = (EditText)findViewById(R.id.txtBxName);
		txtBxPrice = (EditText)findViewById(R.id.txtBxPrice);
		btnAddItem = (Button)findViewById(R.id.btnAddItem);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		lvItems = (ListView)findViewById(R.id.lvItems);
	}

	@Override
	public void processAsync() {
		dbHelper = new DatabaseHelper(this);
		// itemsAdapter = new ItemNameAdapter(AddPurchaseItem.this, R.layout.label_item, dbHelper.getAllPurchaseItems());
	}
	
	@Override
	public void onAsyncFinish() {
		lvItems.setAdapter(itemsAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}
}

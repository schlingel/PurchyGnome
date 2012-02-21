package at.fundev.purchy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import at.fundev.purchy.async.IAsyncWorker;

public class AddPurchaseItem extends Activity implements IAsyncWorker, OnItemClickListener {
	private EditText txtBxPrice;
	
	private EditText txtBxName;
	
	private Button btnCancel;
	
	private Button btnAddItem;
	
	private ListView lvItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAsyncFinish() {
	}

	@Override
	public void processAsync() {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}
}

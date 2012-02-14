package at.fundev.purchy;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import at.fundev.purchy.async.AsyncWorkerLauncher;
import at.fundev.purchy.async.IAsyncWorker;
import at.fundev.purchy.models.Bill;
import at.fundev.purchy.ui.ItemAdapter;
import at.fundev.purchy.ui.Utils;

public class ShowBill extends Activity implements OnClickListener, IAsyncWorker {
	public final static String BILL_KEY = "BILL_KEY";
	
	private TextView lblTotalSum;
	
	private TextView lblItemCount;
	
	private Button btnAction;
	
	private ListView lvItems;
	
	private ItemAdapter itemAdapter;
	
	private Dialog dlgActions;
	
	private Bill curBill;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_bill);
		initViews();
		AsyncWorkerLauncher.from(this).execute();
	}
	
	@Override
	public void processAsync() {
		unwrap();
		itemAdapter = new ItemAdapter(this, R.layout.bill_list_item, curBill.getItems());
	}
	
	private void unwrap() {
		final Intent intent = getIntent();
		final Bundle bundle = intent.getExtras();
		curBill = (Bill)bundle.getSerializable(BILL_KEY);
	}
	
	@Override
	public void onAsyncFinish() {
		lblTotalSum.setText(Utils.format(this, R.string.fmtStrPrice, (float)curBill.getTotalSumInCent() / 100f));
		lblItemCount.setText(Utils.format(this, R.string.fmtStrItemsCount, curBill.getItems().size()));
		lvItems.setAdapter(itemAdapter);
	}
	
	private void initViews() {
		lblTotalSum = (TextView)findViewById(R.id.lblPriceSum);
		lblItemCount = (TextView)findViewById(R.id.lblItemCount);
		btnAction = (Button)findViewById(R.id.btnAction);
		btnAction.setOnClickListener(this);
		lvItems = (ListView)findViewById(R.id.lvItems);
		
		final Builder builder = new Builder(this);
		builder.setItems(R.array.dlgBillActionOptions, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which == 0) {
					
				}
			}
		});
		builder.setTitle(R.string.lblActionDlg);		
		dlgActions = builder.create();
	}

	@Override
	public void onClick(View v) {
		// null check is a must because the user could click before the async init method has finished
		if(v.equals(btnAction) && dlgActions != null) {
			dlgActions.show();
		}
	}
	
	public static Intent asIntent(Context sender, Bill bill, Bundle bundle) {
		final Intent intent = new Intent(sender, ShowBill.class);
		bundle.putSerializable(BILL_KEY, bill);
		intent.putExtras(bundle);
		return intent;
	}

}

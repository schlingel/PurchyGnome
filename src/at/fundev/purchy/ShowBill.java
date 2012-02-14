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
import at.fundev.purchy.models.Bill;
import at.fundev.purchy.ui.ItemAdapter;

public class ShowBill extends Activity implements OnClickListener {
	public final static String BILL_KEY = "BILL_KEY";
	
	private TextView lblTotalSum;
	
	private TextView lblItemCount;
	
	private Button btnAction;
	
	private ListView lvItems;
	
	private ItemAdapter itemAdapter;
	
	private Dialog dlgActions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_bill);
		initViews();
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
	}

	@Override
	public void onClick(View v) {
		
	}
	
	public static Intent asIntent(Context sender, Bill bill, Bundle bundle) {
		final Intent intent = new Intent(sender, ShowBill.class);
		bundle.putSerializable(BILL_KEY, bill);
		intent.putExtras(bundle);
		return intent;
	}
}

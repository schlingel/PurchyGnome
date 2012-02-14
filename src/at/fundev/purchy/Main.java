package at.fundev.purchy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity implements OnClickListener {
	private Button btnAddBill;
	
	private Button btnShowBills;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
    }
    
    private void initViews() {
    	btnAddBill = (Button)findViewById(R.id.btnAddBill);
    	btnShowBills = (Button)findViewById(R.id.btnShowBills);
    	
    	btnAddBill.setOnClickListener(this);
    	btnShowBills.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		if(v.equals(btnAddBill)) {
			startActivity(AddBill.asIntent(this, new Bundle()));
		} else if(v.equals(btnShowBills)) {
			startActivity(ShowBills.asIntent(this, new Bundle()));
		}
	}
}
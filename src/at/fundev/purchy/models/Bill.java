package at.fundev.purchy.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts")
public class Bill implements Serializable {
	private static final long serialVersionUID = 329948578083044737L;

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField()
	private Date date;
	
	private List<PurchaseItem> items;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setItems(List<PurchaseItem> items) {
		this.items = items;
	}
	
	public List<PurchaseItem> getItems() {
		return items;
	}
	
	public long getTotalSumInCent() {
		long sum = 0;
		
		if(items != null) {
			for(int i = 0; i < items.size(); i++) {
				sum += items.get(i).getPriceInCent();
			}
		}
		
		return sum;
	}
}

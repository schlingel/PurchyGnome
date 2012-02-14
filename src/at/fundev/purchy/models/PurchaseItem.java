package at.fundev.purchy.models;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="purchases")
public class PurchaseItem implements Serializable {
	private static final long serialVersionUID = 3246234628235697304L;

	public static final String BILL_FIELD_NAME = "billId";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField()
	private String name;
	
	@DatabaseField()
	private long priceInCent;
	
	@DatabaseField(columnDefinition = BILL_FIELD_NAME)
	private int billId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPriceInCent() {
		return priceInCent;
	}

	public void setPriceInCent(long priceInCent) {
		this.priceInCent = priceInCent;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}
}

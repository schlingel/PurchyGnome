package at.fundev.purchy.models;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "purchygnome.db";

	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	private Dao<Bill, Long> billDao = null;
	private RuntimeExceptionDao<Bill, Long> billRuntimeDao = null;
	
	private Dao<PurchaseItem, Long> purchaseDao = null;
	private RuntimeExceptionDao<PurchaseItem, Long> purchaseRuntimeDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Bill.class);
			TableUtils.createTable(connectionSource, PurchaseItem.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Bill.class, true);
			TableUtils.dropTable(connectionSource, PurchaseItem.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public List<Bill> getBills() throws SQLException {
		final List<Bill> bills = getBillDao().queryForAll();
		for(int i = 0; i < bills.size(); i++) {
			//bills.get(i).setItems(getPurchaseDao().query(getPurchaseDao().queryBuilder().where().eq(PurchaseItem.BILL_FIELD_NAME, bills.get(i).getId()).prepare()));
			final int id = bills.get(i).getId();
			final List<PurchaseItem> items = getPurchaseDao().query(getPurchaseDao().
					queryBuilder().where().eq(PurchaseItem.BILL_FIELD_NAME, id).prepare());
			
			Log.i("DBHELPER", String.format("Found %d items for bill id: %d", items.size(), id));			
			bills.get(i).setItems(items);	
		}
		
		return bills;
	}
	
	public Dao<Bill, Long> getBillDao() throws SQLException {
		if (billDao == null) {
			billDao = getDao(Bill.class);
		}
		return billDao;
	}
	
	public Dao<PurchaseItem, Long> getPurchaseDao() throws SQLException {
		if(purchaseDao == null) {
			purchaseDao = getDao(PurchaseItem.class);
		}
		
		return purchaseDao;
	}
 
	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Bill, Long> getRTEBillDao() {
		if (billRuntimeDao == null) {
			billRuntimeDao = getRuntimeExceptionDao(Bill.class);
		}
		
		return billRuntimeDao;
	}
	
	public RuntimeExceptionDao<PurchaseItem, Long> getRTEPurchaseDao() {
		if(purchaseRuntimeDao == null) {
			purchaseRuntimeDao = getRuntimeExceptionDao(PurchaseItem.class);
		}
		
		return purchaseRuntimeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		billRuntimeDao = null;
	}
}
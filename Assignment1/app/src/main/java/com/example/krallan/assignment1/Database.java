package com.example.krallan.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krallan on 2017-09-17.
 */

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Finance";
    private static final String TABLE_TRANSACTIONS = "transactions";

    private static final String KEY_ID = "_id";
    private static final String KEY_TRANSACTION_TYPE = "transaction_type";
    private static final String KEY_DATE = "date";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_AMOUNT = "amount";

    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_TRANSACTIONS +
            "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_TRANSACTION_TYPE + " TEXT," +
            KEY_DATE + " INTEGER," +
            KEY_TITLE + " TEXT," +
            KEY_CATEGORY + " TEXT," +
            KEY_AMOUNT + " NUMERIC)";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);

        onCreate(db);
    }

    public boolean createTransaction(TransactionModel transactionModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TRANSACTION_TYPE, transactionModel.getTransaction_type());
        values.put(KEY_DATE, transactionModel.getDate());
        values.put(KEY_TITLE, transactionModel.getTitle());
        values.put(KEY_CATEGORY, transactionModel.getCategory());
        values.put(KEY_AMOUNT, transactionModel.getAmount());

        long insertId = db.insert(TABLE_TRANSACTIONS, null, values);

        db.close();

        if (insertId >= 0) {
            return true;
        } else {
            return false;
        }

    }

    public List<TransactionModel> getAllTransactionsByType(String transaction_type, long start_date, long end_date) {

        start_date = start_date - 86400000l;
        List<TransactionModel> transactions = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE "
                + KEY_TRANSACTION_TYPE + " = '" + transaction_type + "' AND "
                + KEY_DATE + " BETWEEN '" + start_date + "' AND '" + end_date + "'";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                TransactionModel transactionModel = new TransactionModel(
                        c.getString(c.getColumnIndex(KEY_TRANSACTION_TYPE)),
                        c.getLong(c.getColumnIndex(KEY_DATE)),
                        c.getString(c.getColumnIndex(KEY_TITLE)),
                        c.getString(c.getColumnIndex(KEY_CATEGORY)),
                        c.getFloat(c.getColumnIndex(KEY_AMOUNT))
                );

                transactions.add(transactionModel);
            } while (c.moveToNext());
        }

        c.close();

        return transactions;
    }
    public List<TransactionModel> getAllTransactionsByType(String transaction_type) {

        List<TransactionModel> transactions = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE "
                + KEY_TRANSACTION_TYPE + " = '" + transaction_type + "' ";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                TransactionModel transactionModel = new TransactionModel(
                        c.getString(c.getColumnIndex(KEY_TRANSACTION_TYPE)),
                        c.getLong(c.getColumnIndex(KEY_DATE)),
                        c.getString(c.getColumnIndex(KEY_TITLE)),
                        c.getString(c.getColumnIndex(KEY_CATEGORY)),
                        c.getFloat(c.getColumnIndex(KEY_AMOUNT))
                );

                transactions.add(transactionModel);
            } while (c.moveToNext());
        }

        c.close();

        return transactions;
    }
}

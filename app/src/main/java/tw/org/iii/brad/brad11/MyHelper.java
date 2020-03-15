package tw.org.iii.brad.brad11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {
    private final String createTable =
            "CREATE TABLE cust (id integer primary key autoincrement," +
            "cname text, tel text, birthday date)";

    public MyHelper(@Nullable Context context,
                    @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//on開頭不是我們呼叫的;會自動幫我們創資料庫
        Log.v("brad","MyHelper:onCreate()");
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

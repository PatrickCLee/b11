package tw.org.iii.brad.brad11;
//非資料庫server,而是存在手機的機制 //資料庫 SQLite //資料在data-data-專案名稱-databases
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db; //透過myHelper
    private MyHelper myHelper;
    private EditText keyword;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHelper = new MyHelper(this,"iii",null,1);//第二參數資料庫名,三為cursor通常不管,四為版本
        // 用 int 當 version 正整數共有 24億(十位數) 剛好可用日期 20200315 後面加上版本流水號,例 2020031501
        db = myHelper.getReadableDatabase();

        keyword = findViewById(R.id.keyword);
        tv = findViewById(R.id.data);
    }

    public void q(View view) {
        // SELECT * from cust
//        db.query("cust",null,
//                null,null,
//                null,null,null);
        Cursor cursor = db.query("cust",null, //若要查欄位則把欄位用String填入columns參數位置,null則是全部*
                "cname like ? or tel like ? or birthday like ?", //三為where,參數用問號代替
                new String[]{"%"+keyword.getText().toString()+"%",  //模糊查詢,因上方的code為or,故只要EditText輸入欄中的關鍵字有在上方三個欄位中出現即會被查詢到
                        "%"+keyword.getText().toString()+"%",
                        "%"+keyword.getText().toString()+"%"},
                null,null,null);
        //第四參數寫問號的值
        Log.v("brad","column count:" + cursor.getColumnCount());

        StringBuffer sb = new StringBuffer();
        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("id"));//getString內參數為columnIndex,但若這樣寫code不易識別,故用getColumnIndex給key傳回index
            String cname = cursor.getString(cursor.getColumnIndex("cname"));
            String tel = cursor.getString(cursor.getColumnIndex("tel"));
            String birthday = cursor.getString(cursor.getColumnIndex("birthday"));
            Log.v("brad", id +":"+cname+":"+tel+":"+birthday);
            String row = id +":"+cname+":"+tel+":"+birthday;
            sb.append(row + "\n");  //一直append的StringBuffer
        }
        tv.setText(sb);
    }
    public void ins(View view) {
        //db.execSQL("INSERT INTO cust (cname,tel,birthday) VALUES ('brad','123','1980-01-01')");
        ContentValues data = new ContentValues();
        data.put("cname","Arya");   //id為自動遞增的主key,不需手動新增輸入
        data.put("tel","089");
        data.put("birthday","14");
        db.insert("cust",null,data);//第二參數針對null要做的動作,資料會放在三參數上
        q(null); //呼叫q,因為(q的code內容)與畫面(view)無關,故參數直接帶null
    }

    public void del(View view) {
        // DELETE FROM cust WHERE id = 1 and cname = 'brad'
        db.delete("cust","id = ? and cname = ?",new String[]{"1","brad"});
        q(null);
    }

    public void update(View view) { //是增和刪的sum,(同時利用到兩者裡面的code)
        //UPDATE cust SET cname = 'Meow', tel='345', WHERE id = 3;
        ContentValues data = new ContentValues();
        data.put("cname","Meow");
        data.put("tel","345");
        db.update("cust",data,"id = ?",new String[]{"3"});
        q(null);
    }

}

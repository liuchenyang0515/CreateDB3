package com.example.createdb3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDBOpenHelper extends SQLiteOpenHelper {
    private String TAG = "MyDBOpenHelper";
    private Context mContext;

    // 第一个参数是上下文
    // 第二个参数是数据库名称
    // 第三个参数null表示使用默认的游标工厂
    // 第四个参数是数据库的版本号，数据库只能升级，不能降级，版本号只能变大不能变小
    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // 更改一下版本号会调用onUpgrade
        mContext = context;
    }

    // 当数据库第一次被创建的时候调用的方法，适合在这个方法里面把数据库的表结构定义出来
    // 当app再次启动会发现已经存在mydb.db数据库了，因此不会再创建一次
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "数据库被创建了: ");
        // MySQL是AUTO_INCREMENT, SQLite是AUTOINCREMENT
        db.execSQL("CREATE TABLE contactinfo(id INTEGER PRIMARY KEY AUTOINCREMENT, NAME CHAR(20), phone VARCHAR(20));");
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    // 当数据库更新的时候调用的方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "数据库被更新了: ");
        //db.execSQL("ALTER TABLE contactinfo ADD account VARCHAR(20);");
        db.execSQL("drop table if exists contactinfo");
        onCreate(db);
    }
}

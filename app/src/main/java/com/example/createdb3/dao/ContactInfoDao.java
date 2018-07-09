package com.example.createdb3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.createdb3.MyDBOpenHelper;

public class ContactInfoDao {

    private final MyDBOpenHelper helper;
    private String TAG = "ContactInfoDao";

    public ContactInfoDao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        helper = new MyDBOpenHelper(context, name, factory, version);
    }

    /**
     * create table aa(id integer primary key autoincrement, name char(20), phone varchar(20));
     * create table temp as select id, name from aa; //
     * temp表没有了PRIMARY KEY AUTOINCREMENT，查看建表语句CREATE TABLE "temp"(id INT,NAME TEXT);
     * integer变成了int
     * char变成text
     * 新表中没有旧表中的primary key,Extra,auto_increment等属性,需要自己手动加,具体参看后面的修改表即字段属性.
     * 添加一条记录
     *
     * @param name  联系人姓名
     * @param phone 联系人电话
     * @return 返回的是添加在数据库的行号，-1代表失败
     */
    public long add(String name, String phone) {
        SQLiteDatabase db = helper.getWritableDatabase(); // 如果数据库已存在就打开，否则创建一个新数据库
        // db.execSQL("insert into contactinfo (name, phone) values(?, ?)", new Object[]{name, phone});
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        long rowId = db.insert("contactinfo", null, values);

        // 记得关闭数据库，释放资源
        db.close();
        return rowId;
    }

    /**
     * 根据姓名删除一条记录
     *
     * @param name 联系人姓名
     * @return 返回0代表的是没有做任何记录，返回的整数int值代表删除了几条数据
     */

    public int delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        // db.execSQL("delete from contactinfo where name = ?", new Object[]{name});
        int rowId = db.delete("contactinfo", "name=?", new String[]{name});
        // 记得关闭数据库，释放资源
        db.close();
        return rowId;
    }

    /**
     * 修改联系人电话号码
     *
     * @param name  联系人姓名
     * @param phone 联系人新电话
     * @return rowId代表更新了多少行记录
     */

    public int update(String phone, String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        // db.execSQL("update contactinfo set phone = ? where name = ?;", new Object[]{phone, name});
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        int rowId = db.update("contactinfo", values, "name = ?", new String[]{name});
        // 记得关闭数据库，释放资源
        db.close();
        return rowId;
    }

    /**
     * 查询联系人的电话号码
     *
     * @param name 联系人姓名
     * @return 电话号码
     */
    public Cursor query(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        // Cursor cursor = db.rawQuery("select phone from contactinfo where name = ?", new String[]{name});
        /*Cursor cursor = db.query("contactinfo", new String[]{"phone"}, "name = ?", new String[]{name}, null, null, null);
        String phone = null;
        if (cursor.moveToNext()) {
            phone = cursor.getString(0);
        }*/
        Cursor cursor = db.query("contactinfo", null, "name = ?", new String[]{name}, null, null, null);
        // 记得关闭数据库，释放资源
        // db.close();// 当返回一个Cursor时，db是不能关闭的
        // 否则抛出java.lang.IllegalStateException: Cannot perform this operation because the connection pool has been closed.
        return cursor;
    }
}

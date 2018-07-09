package com.example.createdb3;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.createdb3.dao.ContactInfoDao;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText et_name;
    private EditText et_phone;
    private ContactInfoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1.找到需要用到的控件
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        // 2.new一个Dao出来
        dao = new ContactInfoDao(this, "mydb.db", null, 2);
    }

    /**
     * 添加一条联系人信息
     *
     * @param view
     */
    public void add(View view) {
        // 做具体的添加操作
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dao.add(name, phone);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除一条联系人信息
     *
     * @param view
     */
    public void delete(View view) {
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dao.delete(name);
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 修改联系人号码
     *
     * @param view
     */
    public void update(View view) {
        String name = et_name.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            dao.update(phone, name);
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查询联系人号码
     *
     * @param view
     */
    public void query(View view) {
        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Cursor cursor = dao.query(name);
            String phone = null;
            StringBuffer str = new StringBuffer();
            if (cursor.moveToFirst()) { // 将光标移动到第一行，如果游标为空，此方法将返回false。
                String str1 = null;
                do {
                    phone = cursor.getString(cursor.getColumnIndex("phone"));
                    str1 = "name:" + name + " phone:" + phone;
                    Log.d(TAG, str1);
                    str.append(str1 + "\n");
                } while (cursor.moveToNext());// 将光标移动到下一行，如果游标已经超过结果集中的最后一个条目，此方法将返回false。
                str.deleteCharAt(str.length() - 1); // StringBuffer没有trim()
            }
            cursor.close();
            if (phone != null) {
                Toast.makeText(this, "查询到的联系人信息为：\n" + str, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "无此联系人信息", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

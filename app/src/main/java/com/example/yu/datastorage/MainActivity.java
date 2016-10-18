package com.example.yu.datastorage;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText ed1,ed2;
    private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = (EditText) findViewById(R.id.edit1);
        ed2 = (EditText) findViewById(R.id.edit2);
        tv1 = (TextView) findViewById(R.id.tv1);
    }
    public void spWrite(View v){
        SharedPreferences user = getSharedPreferences("user",MODE_APPEND);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("account",ed1.getText().toString());
        editor.putString("pass",ed2.getText().toString());
        editor.commit();
        Toast.makeText(this,"Shareferences写入成功了",Toast.LENGTH_LONG).show();
    }
    public void spRead(View v){
        SharedPreferences user = getSharedPreferences("user",MODE_APPEND);
        String acc = user.getString("account","无值");
        String pas = user.getString("pass","无值");
        tv1.setText("账号为："+acc+"密码:"+pas);
        Toast.makeText(this,"Shareferences读取成功了",Toast.LENGTH_LONG).show();
    }
    public void romWrite(View v){
        String acc = ed1.getText().toString();
        String pas = ed2.getText().toString();
        try {
            FileOutputStream fos = openFileOutput("user.txt",MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(acc+":"+pas);
            bw.flush();
            fos.close();
            Toast.makeText(this,"rom写入成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void romRead(View v){
        String acc =ed1.getText().toString();
        String pas =ed2.getText().toString();
        try {
            FileInputStream fis = openFileInput("user.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String s;
            while((s=br.readLine())!=null){
                sb.append(s+"\n");
            }
            fis.close();
            tv1.setText(sb);
            Toast.makeText(this,"Rom读取成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sdWrite(View v){
        String str = ed1.getText().toString()+":"+ed2.getText().toString();
        String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCard+"/test.txt";
        byte data [] = str.getBytes();
        File file = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            fos.close();
            Toast.makeText(this,"SD卡写入成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,"SD卡写入异常",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    public void sdRead(View v){
        String sdCard = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename =sdCard+"/test.txt";
        File file = new File(filename);
        int length = (int) file.length();
        byte[] b = new byte[length];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(b,0,length);
            fis.close();
            tv1.setText(new String(b));
            Toast.makeText(this,"SD卡读取成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,"SD卡读取异常",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}

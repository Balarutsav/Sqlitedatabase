package com.example.sqlitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Databasehelper dbhelper;
    Context context;
    EditText edtname, edtsurname,edtid ,edtmarks;
    Button btnadddata,btnviewdata,btnupdatedata,btndeletedata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();
        btndeletedata=findViewById(R.id.btndeletedata);
        dbhelper = new Databasehelper(this);
        edtmarks=findViewById(R.id.edtmarks);
        edtname =findViewById(R.id.edtname);
        edtsurname=findViewById(R.id.edtsurname);
        btnadddata=findViewById(R.id.btnadddata);
        btnviewdata=findViewById(R.id.btnviewdata);
        btnupdatedata=findViewById(R.id.btnupdate);
        edtid=findViewById(R.id.edtid);
        btndeletedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Integer deleterows=dbhelper.deletedata(edtid.getText().toString());
            if(deleterows>0){
                Toast.makeText(context, "data was deleted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "data was not deleted", Toast.LENGTH_SHORT).show();
            }
            }
        });
        btnupdatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isupdate=dbhelper.updatedata(edtname.getText().toString(),edtsurname.getText().toString(),edtid.getText().toString(),edtmarks.getText().toString());
                if(isupdate==true){
                    Toast.makeText(context, "Your data was updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "your data was not update ", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnadddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isinserted=dbhelper.insertdata(edtname.getText().toString(),edtsurname.getText().toString(),edtmarks.getText().toString());
                if(isinserted=true){
                    Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnviewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=dbhelper.getAllData();
                if(res.getCount()==0){
                    showmessage("Error","Nothing found");
                    return ;
                }
                StringBuffer buffer=new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Surname :"+res.getString(2)+"\n");
                    buffer.append("Marks :"+res.getString(3)+"\n");

                }
                    showmessage("data",buffer.toString());
                }
        });
    }

public void showmessage(String title,String Message){
    AlertDialog.Builder ab=new AlertDialog.Builder(this);
    ab.setCancelable(true);
    ab.setTitle(title);
    ab.setMessage(Message);
    ab.show();
}

}

package com.xy.materialdesign.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.xy.materialdesign.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("XY的提示框");
        builder.setMessage("给XY一个女朋友");
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("不行", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void showListPopupwindow(View view){
        String[] datas = {"条目0","条目1","条目2","条目3","条目4"};
        ListAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,datas);
        ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(view);
        listPopupWindow.show();
    }

    public void showPopupmenuwindow(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.main,popupMenu.getMenu());
        popupMenu.show();
    }

    public void toRecyclerView(View view){
        Intent intent = new Intent(this,RecyclerViewActivity.class);
        startActivity(intent);
    }

    public void toHeaderRecyclerView(View view){
        Intent intent = new Intent(this,HeaderRecyclerViewActivity.class);
        startActivity(intent);
    }
}

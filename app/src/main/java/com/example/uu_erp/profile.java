package com.example.uu_erp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class profile extends AppCompatActivity {
AppCompatButton followmeid;
TextView fbid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        followmeid=findViewById(R.id.followmeid);
        fbid=findViewById(R.id.fbid);
        Toolbar toolbar=findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);


        followmeid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToURL("https://github.com/somanmia?tab=repositories");
            }
        });

        fbid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToURL("https://www.facebook.com/sumonmia.cse/");
            }
        });
        //======== Button Click and working print this page...............//



        //====== end of printing ==============//
    }
    private  void printwebview(WebView webview){
        PrintManager printManager=(PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printDocumentAdapter=webview.createPrintDocumentAdapter();
        String jobname=getString(R.string.app_name)+"Result File";
        printManager.print(jobname,printDocumentAdapter,new PrintAttributes.Builder().build());

    }



    void GoToURL(String url){
        try {
            Uri uri = Uri.parse(url);
            Intent intent= new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id=item.getItemId();

   if(item_id==R.id.menu){
            Intent intent=new Intent(profile.this,MainActivity.class);
            startActivity(intent);
        }else if(item_id==R.id.Exit){

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("আপনি কি এ্যাপ্স থেকে বের হতে চান?");
            builder.setIcon(R.drawable.header_logo);
            builder.setCancelable(false);
            builder.setNegativeButton("না", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.setPositiveButton("হ্যা", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();;
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
        return true;
    }
}
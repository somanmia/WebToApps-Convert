package com.example.uu_erp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    WebView web;
    private ProgressBar progress;
    String weburl="https://erp.uttarauniversity.edu.bd/";
     SwipeRefreshLayout swiprefreshlayout;

    FloatingActionButton printbtn;
ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        final Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                ConnectivityManager manager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activenetwork=manager.getActiveNetworkInfo();
                if(null!=activenetwork){
                    if(activenetwork.getType()==ConnectivityManager.TYPE_WIFI){

                    } if(activenetwork.getType()==ConnectivityManager.TYPE_MOBILE) {
                    }
                    }else{
                        Intent intent=new Intent(getApplicationContext(),no_internet.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        finish();
                    }
                    handler.postDelayed(this,50);
                }

        };
        handler.postDelayed(runnable,50);
        InputMethodManager x= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

     if(x.isAcceptingText())
     {
         x.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
     }
        // Full screen//
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);




        setContentView(R.layout.activity_main);
        Toolbar  toolbar=findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);

        web=findViewById(R.id.myweb);
        web.loadUrl(weburl);

        WebSettings mywebsitting=web.getSettings();
        mywebsitting.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());

        //improbe webview performence...........//
        mywebsitting.setLoadWithOverviewMode(true);
        mywebsitting.setUseWideViewPort(true);
        mywebsitting.setSupportZoom(true);
        mywebsitting.setBuiltInZoomControls(true);
        mywebsitting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mywebsitting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mywebsitting.setDomStorageEnabled(true);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web.setScrollbarFadingEnabled(true);

        web.getSettings().setAllowFileAccess(true);
        mywebsitting.setEnableSmoothTransition(true);
        progress=(ProgressBar) findViewById(R.id.progressbwr);
        printbtn=findViewById(R.id.printbutton);

 web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int progressbar) {
                progress.setProgress(progressbar);
                if(progressbar<100 &&progress.getVisibility()==ProgressBar.GONE){
                    progress.setVisibility(ProgressBar.VISIBLE);
                }if(progressbar==100){
                    progress.setVisibility(ProgressBar.GONE);
                }
            }
        });



        //=======================swip swiprefreshlayout================================
        swiprefreshlayout=findViewById(R.id.swips);
        swiprefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
                            public void onRefresh() {
                                                swiprefreshlayout.setRefreshing(true);
                                                         new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                             public void run() {
                                                            swiprefreshlayout.setRefreshing(false);
                                                            web.reload();
                                                        }
                                                    },1000);
                                            }
                                            });



        //======== Button Click and working print this page...............//

        printbtn=findViewById(R.id.printbutton);
        printbtn.setOnClickListener(view -> {
            printwebview(web);
        });
        //====== end of printing ==============//
    }


    //=================  END OF MAIN FUNCTION BODY==================================//
//=============== FUNCTION CREATE FOR PRINT............//
    private  void printwebview(WebView webview){
        PrintManager printManager=(PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printDocumentAdapter=webview.createPrintDocumentAdapter();
        String jobname=getString(R.string.app_name)+"Result File";
        printManager.print(jobname,printDocumentAdapter,new PrintAttributes.Builder().build());

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
int item_id=item.getItemId();
if(item_id==R.id.Homeid){
Intent intent=new Intent(MainActivity.this,MainActivity.class);
startActivity(intent);
}else if(item_id==R.id.Developer){
    Intent intent=new Intent(MainActivity.this,profile.class);
    startActivity(intent);
}else if(item_id==R.id.printclick){
    printwebview(web);

}
else if(item_id==R.id.Exit){

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
// ==================End Swip Refresh Layout==================//

    //===========START OF BACKPRESSED ======================================//
    public void onBackPressed(){
        if (web.canGoBack()) {

            web.goBack();
        }else{

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
    }


    //========== END OF BACK PRESSED =========================


}
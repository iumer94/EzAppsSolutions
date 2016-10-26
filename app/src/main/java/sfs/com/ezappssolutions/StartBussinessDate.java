package sfs.com.ezappssolutions;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class StartBussinessDate extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button next,ok;
    TextView mainTitle;
    String tittle;
    ImageView iv_back;
    Spinner select_month,select_year,select_month2;
    String st_select_month, st_select_year, st_select_month2;
    Strings list;
    transparentdialog td;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_bussiness_date);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

        mainTitle = (TextView) findViewById(R.id.tv_title);
        Intent intent = getIntent();
        tittle = intent.getStringExtra("tittle");
        mainTitle.setText(tittle);

        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        select_month=(Spinner)findViewById(R.id.select_month);
        select_month.setOnItemSelectedListener(this);
        List<String> select_month_list=new ArrayList<String>();
        list = new Strings();
        select_month_list=list.month();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(StartBussinessDate.this, android.R.layout.simple_spinner_item, select_month_list);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_month.setAdapter(dataAdapter);

        select_month2=(Spinner)findViewById(R.id.select_month2);
        select_month2.setOnItemSelectedListener(StartBussinessDate.this);
        List<String> select_month2_list=new ArrayList<String>();
        select_month2_list=list.month();
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, select_month2_list);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_month2.setAdapter(dataAdapter2);

        select_year=(Spinner)findViewById(R.id.select_year);
        select_year.setOnItemSelectedListener(StartBussinessDate.this);
        List<String> select_year_list=new ArrayList<String>();
        select_year_list=list.year();
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, select_year_list);
        // Drop down layout style - list view with radio button
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_year.setAdapter(dataAdapter3);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_internet_dialog);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ok=(Button) dialog.findViewById(R.id.btn_right);
        ok.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==next.getId()){
            if(st_select_month.equals("Select Month")|| st_select_year.equals("Select year") || st_select_month2.equals("Select Month")){
                Toast.makeText(this, "Please select the valid values", Toast.LENGTH_SHORT).show();
            }else {
                savedDataOnParse();
            }
        }
        else if(view.getId() == ok.getId())
        {

            dialog.dismiss();

        }
        else if(view.getId()== iv_back.getId())
        {
            finish();
        }
        else if(NetUtil.isNetworkAvailable(this) == false )
        {
            dialog.show();
        }

    }
    public void savedDataOnParse() {

        td = new transparentdialog(this, R.drawable.loading_blue);
        td.show();
        ParseTableName obj = new ParseTableName();
        obj.setParseTableName(tittle);
        String tableName = obj.getParseTableName();
        ParseQuery.getQuery(tableName).whereEqualTo("userID", ParseUser.getCurrentUser())
                .getFirstInBackground(new GetCallback<ParseObject>()
                {
                    @Override
                    public void done(ParseObject obj1, final ParseException e) {
                        if (e == null) {
                            obj1.put("startMonth", st_select_month);
                            obj1.put("endMonth", st_select_month2);
                            obj1.put("startYear", st_select_year);
                            obj1.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(StartBussinessDate.this, "Values Store Successfully", Toast.LENGTH_SHORT).show();
                                        td.dismiss();
                                        if (tittle.equals("Estate Of Deceased Ind")) {
                                            Intent i = new Intent(StartBussinessDate.this, question1.class);
                                            i = i.putExtra("tittle", tittle);
                                            startActivity(i);
                                        } /*else if (tittle.equals("Trust")) {
                                            Intent i = new Intent(StartBussinessDate.this, llc_customeragreement.class);
                                            i = i.putExtra("tittle", tittle);
                                            startActivity(i);
                                        } else {
                                            Intent i = new Intent(StartBussinessDate.this, Ask1.class);
                                            i = i.putExtra("tittle", tittle);
                                            startActivity(i);

                                        }*/
                                    }
                                }
                            });
                        }
                    }
                });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String item = adapterView.getItemAtPosition(position).toString();
        if (adapterView.getId() == select_month.getId()) {
            st_select_month = item;
        }
        if (adapterView.getId() == select_year.getId()) {
            st_select_year = item;
        }
        if (adapterView.getId() == select_month2.getId()) {
            st_select_month2 = item;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

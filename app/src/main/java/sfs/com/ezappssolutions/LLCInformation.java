package sfs.com.ezappssolutions;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class LLCInformation extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    TextView mainTitle;
    ImageView iv_back;
    Button next,ok;
    String tittle,currentId;
    Spinner llc_member,llc_member2;
    transparentdialog td;
    Dialog dialog;
    EditText et_llc_name,et_llc_tradeName;
    String st_llc_name,st_llc_tradeName,st_llc_member,st_llc_member2;
    RadioButton btn_yes,btn_no,btn_yes1,btn_no1;
    RadioGroup radio,radio1;
    LinearLayout layout,layout1,spinnerlayout;
    int num,num1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llcinformation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mainTitle=(TextView)findViewById(R.id.tv_title);
        Intent intent= getIntent();
        tittle = getIntent().getStringExtra("tittle");
        mainTitle.setText(tittle);

        next=(Button)findViewById(R.id.next);
        next.setOnClickListener(this);

        iv_back=(ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        et_llc_name=(EditText)findViewById(R.id.et_llc_name);
        et_llc_tradeName=(EditText)findViewById(R.id.et_llc_tradeName);

        btn_yes=(RadioButton)findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(this);
        btn_no=(RadioButton)findViewById(R.id.btn_no);
        btn_no.setOnClickListener(this);

        radio = (RadioGroup) findViewById(R.id.radio);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.btn_yes) {
                    num=1;
                } else if(checkedId == R.id.btn_no) {
                    num=0;
                }
            }
        });

        btn_yes1=(RadioButton)findViewById(R.id.btn_yes1);
        btn_yes1.setOnClickListener(this);
        btn_no1=(RadioButton)findViewById(R.id.btn_no1);
        btn_no1.setOnClickListener(this);

        radio1 = (RadioGroup) findViewById(R.id.radio1);
        radio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.btn_yes1) {
                    num1=1;
                } else if(checkedId == R.id.btn_no1) {
                    num1=0;
                }
            }
        });

        spinnerlayout=(LinearLayout) findViewById(R.id.spinnerlayout);
        spinnerlayout.setVisibility(View.GONE);
        spinnerlayout.setOnClickListener(this);

        layout=(LinearLayout) findViewById(R.id.layout);
        layout.setVisibility(View.GONE);
        layout.setOnClickListener(this);

        layout1=(LinearLayout) findViewById(R.id.layout1);
        layout1.setVisibility(View.GONE);
        layout1.setOnClickListener(this);

        llc_member=(Spinner)findViewById(R.id.llc_member);
        llc_member.setOnItemSelectedListener(this);
        List<String> llc_member_list=new ArrayList<String>();
        llc_member_list.add("Number Of LLC Members");
        llc_member_list.add("1");
        llc_member_list.add("2");
        llc_member_list.add("3");
        llc_member_list.add("4");
        llc_member_list.add("5");
        llc_member_list.add("6");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, llc_member_list);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        llc_member.setAdapter(dataAdapter);

        llc_member2=(Spinner)findViewById(R.id.llc_member2);
        llc_member2.setOnItemSelectedListener(this);
        List<String> llc_member_list1=new ArrayList<String>();
        llc_member_list1.add("LLC Taxation Classification(If Multi Member)");
        llc_member_list1.add("Two Members Non Husband And Wife");
        llc_member_list1.add("Two Members Husband And Wife File As Single Member");
        llc_member_list1.add("Two Members Husband And Wife File As Multiple Member");
        llc_member_list1.add("Three Or  More Members");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, llc_member_list1);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        llc_member2.setAdapter(dataAdapter1);

    }
    @Override
    public void onClick(View view) {
        st_llc_name=et_llc_name.getText().toString();
        st_llc_tradeName=et_llc_tradeName.getText().toString();

        if (view.getId() == next.getId()) {
            if(st_llc_name.equals("")||st_llc_tradeName.equals("")||st_llc_member.equals("Number Of LLC Member")){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
            else if(st_llc_name.equals("")){
                et_llc_name.setError("Please enter you name");
            }
            else if(st_llc_tradeName.equals("")){
                et_llc_tradeName.setError("Please enter you Trade name");
            }
            else if (st_llc_name.equals(st_llc_tradeName)) {
                et_llc_name.setError("Name And your Trade Name cannot be same");
                et_llc_tradeName.setError("Name And your Trade Name cannot be same");
            }
            else if(st_llc_member.equals("Number Of LLC Members")){
                Toast.makeText(this, "Please Select number of LLC Member", Toast.LENGTH_SHORT).show();
            }
            else if(st_llc_member.equals("2") || st_llc_member.equals("3") || st_llc_member.equals("4") || st_llc_member.equals("5") || st_llc_member.equals("6")){
                spinnerlayout.setVisibility(View.VISIBLE);
                if(st_llc_member2.equals("LLC Taxation Classification(If Multi Member)")){
                    Toast.makeText(this, "Select the option Please", Toast.LENGTH_SHORT).show();
                }else if(view.getId()==layout.getId()){
                    layout.setVisibility(View.VISIBLE);
                    if (view.getId() == R.id.btn_yes)
                    {
                        layout1.setVisibility(View.VISIBLE);
                        num=1;
                        if(btn_yes.isChecked()){
                            layout1.setVisibility(View.VISIBLE);
                            if (view.getId() == R.id.btn_yes1)
                            {
                                num1=1;
                            }
                            else if (view.getId() == R.id.btn_no1)
                            {
                                num1=0;
                            }
                        }
                        else{
                            layout1.setVisibility(View.GONE);
                        }
                    }
                    else if (view.getId() == R.id.btn_no)
                    {
                        layout.setVisibility(View.GONE);
                        num=0;
                    }
                }
                else {
                    if(currentId == null)
                        savedDataOnParse();
                    else
                        saveExistingData();
                }
            }
            else {
                if(currentId == null)
                    savedDataOnParse();
                else
                    saveExistingData();
            }
        }

        if(view.getId()==iv_back.getId())        {
            finish();
        }
    }

    public void saveExistingData()
    {
        ParseObject obj = new ParseObject("LLC");
        td = new transparentdialog(this, R.drawable.loading_blue);
        td.show();
        ParseQuery.getQuery("LLC").whereEqualTo("userID", ParseUser.getCurrentUser()).whereEqualTo("objectId", currentId)
                .getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject obj, final ParseException e) {
                        if (e == null) {

                            obj.put("numberLLCMembers", st_llc_member);
                            obj.put("tradeName", st_llc_tradeName);
                            obj.put("LLCName", st_llc_name);
                            obj.put("LLCTaxClassification",st_llc_member2);
                            if(btn_yes.isChecked())
                            {
                                obj.put("couple",num);
                                if(btn_yes1.isChecked()){
                                    obj.put("multiMember",num1);
                                }
                                else{
                                    obj.put("multiMember",num1);
                                }
                            }else{
                                obj.put("couple",num);
                            }
                            obj.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null)//means data save on parse
                                    {
                                        td.dismiss();
                                        Toast.makeText(LLCInformation.this, "LLC data updated successfully", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(getApplicationContext(), BussinessAddress.class);
                                        intent.putExtra("tittle", "Limited Liability Company");
                                        intent.putExtra("objectId", currentId);
                                        startActivity(intent);


                                    } else {
                                        td.dismiss();
                                        Toast.makeText(LLCInformation.this, "LLC data is not saved", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            td.dismiss();
                            Toast.makeText(LLCInformation.this, "Exception:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    public void savedDataOnParse() {
        td = new transparentdialog(this, R.drawable.loading_blue);
        td.show();
        ParseTableName obj1 = new ParseTableName();
        obj1.setParseTableName(tittle);
        final String tableNameStr = obj1.getParseTableName();

        if (tableNameStr.equals("NotMatching")) {
            td.dismiss();
            Toast.makeText(LLCInformation.this, "Table Name Not Matching", Toast.LENGTH_LONG).show();
        } else {

            final ParseObject parseObj = new ParseObject(tableNameStr);

            if (ParseUser.getCurrentUser() == null) {
                td.dismiss();
            } else {
                parseObj.put("userID", ParseUser.getCurrentUser());
            }
            parseObj.put("numberLLCMembers", st_llc_member);
            parseObj.put("tradeName", st_llc_tradeName);
            parseObj.put("LLCName", st_llc_name);
            parseObj.put("LLCTaxClassification",st_llc_member2);
            if(btn_yes.isChecked())
            {
                parseObj.put("couple",num);
                if(btn_yes1.isChecked()){
                    parseObj.put("multiMember",num1);
                }
                else{
                    parseObj.put("multiMember",num1);
                }
            }else{
                parseObj.put("couple",num);
            }

            parseObj.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null)//means data save on parse
                    {
                        td.dismiss();
                        Toast.makeText(LLCInformation.this, "Bussiness Info Data is saved successfully", Toast.LENGTH_LONG).show();
                        currentId = parseObj.getObjectId();
                        Intent intent = new Intent(getApplicationContext(), BussinessAddress.class);
                        intent.putExtra("tittle", "Limited Liability Company");
                        intent.putExtra("objectId", currentId);
                        startActivity(intent);
                    } else {
                        td.dismiss();
                        Toast.makeText(LLCInformation.this, "bussiness Info Data is not saved", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String item = adapterView.getItemAtPosition(position).toString();
        String item2 = adapterView.getItemAtPosition(position).toString();
        if (adapterView.getId() == llc_member.getId()) {
            st_llc_member = item;
        }
        if (adapterView.getId() == llc_member2.getId()) {
            st_llc_member2 = item;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

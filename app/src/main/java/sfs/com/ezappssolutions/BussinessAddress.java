package sfs.com.ezappssolutions;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class BussinessAddress extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button next,ok;
    LinearLayout layout;
    Spinner state, mailingstate;
    ImageView iv_back;
    TextView mainTitle;
    String tittle;
    Strings list;
    String st_trade_name;
    String st_address;
    String st_city;
    String st_zipcode;
    String st_country;
    String st_number;
    String st_number2;
    String st_state;
    String st_mailingaddress;
    String st_mailingcity;
    String st_mailzipcode;
    String st_mailstate;
    String st_mailingcountry;
    EditText et_trade_name, et_address, et_city, et_zipcode, et_country, et_number,et_number2, et_mailingaddress, et_mailingcity, et_mailingzipcode, et_mailingcountry;
    CheckBox cb_mailing_Add,cb_mailing_Add1;
    transparentdialog td;
    Dialog dialog;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness_address);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        list = new Strings();
        mainTitle = (TextView) findViewById(R.id.tv_title);
        tittle = getIntent().getStringExtra("tittle");
        mainTitle.setText(tittle);

        et_address = (EditText) findViewById(R.id.et_address);
        et_city = (EditText) findViewById(R.id.et_city);
        et_zipcode = (EditText) findViewById(R.id.et_zipcode);
        et_country = (EditText) findViewById(R.id.et_country);
        et_number = (EditText) findViewById(R.id.et_number);
        et_number2 = (EditText) findViewById(R.id.et_number2);
        cb_mailing_Add = (CheckBox) findViewById(R.id.cb_mailing_Add);
        cb_mailing_Add.setOnClickListener(this);
        cb_mailing_Add1 = (CheckBox) findViewById(R.id.cb_mailing_Add1);
        cb_mailing_Add1.setOnClickListener(this);
        et_mailingaddress = (EditText) findViewById(R.id.et_mailaddress);
        et_mailingcity = (EditText) findViewById(R.id.et_mailcity);
        et_mailingzipcode = (EditText) findViewById(R.id.et_mailing_zipcode);
        et_mailingcountry = (EditText) findViewById(R.id.et_mailingcountry);
        layout = (LinearLayout) findViewById(R.id.layout);
        layout.setVisibility(View.GONE);
        layout.setOnClickListener(this);
        cb_mailing_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_mailing_Add.isChecked())
                {
                    layout.setVisibility(View.VISIBLE);
                }
                else {
                    layout.setVisibility(View.GONE);
                }

            }
        });
        cb_mailing_Add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_mailing_Add1.isChecked())
                {
                    num=1;
                }
                else {
                    num=2;
                }

            }
        });

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        state = (Spinner)findViewById(R.id.state);
        state.setOnItemSelectedListener(this);
        List<String> liststate = new ArrayList<String>();
        liststate = list.state();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, liststate);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(dataAdapter);

        mailingstate = (Spinner)findViewById(R.id.mailingstate);
        mailingstate = (Spinner)findViewById(R.id.mailingstate);
        mailingstate.setOnItemSelectedListener(this);
        List<String> listmailstate = new ArrayList<String>();
        listmailstate = list.state();
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listmailstate);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mailingstate.setAdapter(dataAdapter1);

        //for phone number verification
        et_number.addTextChangedListener(new PhoneNumberFormattingTextWatcher(){
            private boolean backspacingFlag = false;
            private boolean editedFlag = false;
            private int cursorComplement;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cursorComplement = s.length() - et_number.getSelectionStart();
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                String phone = string.replaceAll("[^\\d]", "");
                if (!editedFlag) {
                    if (phone.length() >= 6 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
                        et_number.setText(ans);et_number.setSelection(et_number.getText().length() - cursorComplement);
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3);
                        et_number.setText(ans);
                        et_number.setSelection(et_number.getText().length() - cursorComplement);
                    }
                } else {
                    editedFlag = false;
                }
            }
        });

        et_number2.addTextChangedListener(new PhoneNumberFormattingTextWatcher(){
            private boolean backspacingFlag = false;
            private boolean editedFlag = false;
            private int cursorComplement;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cursorComplement = s.length() - et_number2.getSelectionStart();
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                String phone = string.replaceAll("[^\\d]", "");
                if (!editedFlag) {
                    if (phone.length() >= 6 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
                        et_number2.setText(ans);et_number2.setSelection(et_number2.getText().length() - cursorComplement);
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3);
                        et_number2.setText(ans);
                        et_number2.setSelection(et_number2.getText().length() - cursorComplement);
                    }
                } else {
                    editedFlag = false;
                }
            }
        });
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
        st_trade_name = et_trade_name.getText().toString();
        st_address = et_address.getText().toString();
        st_city = et_city.getText().toString();
        st_zipcode = et_zipcode.getText().toString();
        st_country = et_country.getText().toString();
        st_number = et_number.getText().toString();
        st_number2 = et_number2.getText().toString();

        st_mailingaddress = et_mailingaddress.getText().toString();
        st_mailingcity = et_mailingcity.getText().toString();
        st_mailzipcode = et_mailingzipcode.getText().toString();
        st_mailingcountry = et_mailingcountry.getText().toString();
        if (view.getId() == next.getId()) {
            if (st_trade_name.equals("") || st_number.equals("") || st_address.equals("") || st_city.equals("") || st_zipcode.equals("") || st_country.equals("")|| st_state.equals("State")) {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            } else if (st_trade_name.isEmpty() || st_trade_name.length() > 32) {
                et_trade_name.setError("Please Enter a valid name");
            } else if (st_number.isEmpty()) {
                et_number.setError("Please Enter a Phone number");
            } else if (st_address.isEmpty()) {
                et_address.setError("Please Enter a valid address");
            } else if (st_city.isEmpty()) {
                et_city.setError("Please Enter valid city name");
            } else if (st_country.isEmpty()) {
                et_country.setError("Please Enter valid country name");
            } else if (st_zipcode.isEmpty()) {
                et_zipcode.setError("Please Enter valid zipcode");
            }else if (cb_mailing_Add.isChecked()){
                if(st_mailingaddress.isEmpty()){
                    et_mailingaddress.setError("Please Enter a valid mailing address");
                }
                else if(st_mailingcity.isEmpty()){
                    et_mailingcity.setError("Please Enter valid city mailing name");
                }
                else if(st_mailingcountry.isEmpty()){
                    et_mailingcountry.setError("Please Enter valid country mailing name");
                }
                else if(st_mailzipcode.isEmpty()){
                    et_mailingzipcode.setError("Please Enter valid mailing zipcode");
                }else if (st_number2.isEmpty()) {
                    et_number2.setError("Please Enter a Phone number");
                }
                else {
                      savedDataOnParse();
                }
            }else if(NetUtil.isNetworkAvailable(this) == false )
            {
                dialog.show();
            }
            else {
                savedDataOnParse();
            }
        }

        else if(view.getId() == ok.getId())
        {

            dialog.dismiss();

        }
        else if (view.getId() == iv_back.getId()) {
            finish();
        }
    }

    /*public void savedDataOnParse() {
        td = new transparentDialog(this, R.drawable.loading_blue);
        td.show();
        ParseTableName obj = new ParseTableName();
        obj.setParseTableName(tittle);
        final String tableName = obj.getParseTableName();
        ParseQuery.getQuery(tableName).whereEqualTo("userID", ParseUser.getCurrentUser())
                .getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject obj1, final ParseException e) {
                        if (e == null) {
                            obj1.put("tradeName", st_trade_name);
                            obj1.put("address", st_address);
                            obj1.put("city", st_city);
                            obj1.put("state",st_state);
                            obj1.put("zipCode",st_zipcode);
                            obj1.put("country",st_country);
                            obj1.put("phoneNumber",st_number);
                            if(cb_mailing_Add.isChecked()) {
                                obj1.put("mailingAddress", st_mailingaddress);
                                obj1.put("mailingCity", st_mailingcity);
                                obj1.put("mailingState",st_mailstate);
                                obj1.put("mailingZipCode",st_mailzipcode);
                                obj1.put("mailingCountry",st_mailingcountry);
                            }
                            obj1.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(BussinessInformation.this, "Values Store Successfully", Toast.LENGTH_SHORT).show();
                                        td.dismiss();
                                        currenId = Obj1.getObjectId();
                                            Intent i = new Intent(BussinessInformation.this, StartBussinessDate.class);
                                            i = i.putExtra("tittle", tittle);
                                            startActivity(i);

                                        }
                                }
                            });
                        }
                    }
                });*/
    public void savedDataOnParse() {

        td = new transparentdialog(this, R.drawable.loading_blue);
        td.show();
        ParseTableName obj = new ParseTableName();
        obj.setParseTableName(tittle);
        String tableName = obj.getParseTableName();
        ParseQuery.getQuery(tableName).whereEqualTo("userID", ParseUser.getCurrentUser())
                .getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject obj1, final ParseException e) {
                        if (e == null) {
                            obj1.put("address", st_address);
                            obj1.put("city", st_city);
                            obj1.put("state", st_state);
                            obj1.put("zipCode", st_zipcode);
                            obj1.put("country", st_country);
                            obj1.put("phoneNumber", st_number);
                            if (cb_mailing_Add1.isChecked()) {
                                obj1.put("ownedAnotherBusiness", num);
                            } else if (cb_mailing_Add.isChecked()) {
                                obj1.put("mailingAddress", st_mailingaddress);
                                obj1.put("mailingCity", st_mailingcity);
                                obj1.put("mailingState", st_mailstate);
                                obj1.put("mailingZipCode", st_mailzipcode);
                                obj1.put("countyCode", st_mailingcountry);
                                obj1.put("mailingPhoneNumber",st_number2 );
                            }

                            obj1.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(BussinessAddress.this, "Values Store Successfully", Toast.LENGTH_SHORT).show();
                                        td.dismiss();
                                        if (tittle.equals("Estate Of Deceased Ind")) {
                                            Intent i = new Intent(BussinessAddress.this, question1.class);
                                            i = i.putExtra("tittle", tittle);
                                            startActivity(i);
                                        } else if (tittle.equals("Trust")) {
                                            Intent i = new Intent(BussinessAddress.this, question1.class);
                                            i = i.putExtra("tittle", tittle);
                                            startActivity(i);
                                        } else {
                                            Intent i = new Intent(BussinessAddress.this, StartBussinessDate.class);
                                            i = i.putExtra("tittle", tittle);
                                            startActivity(i);

                                        }
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
        if (adapterView.getId() == state.getId()) {
            st_state = item;
        }
        if (adapterView.getId() == mailingstate.getId()) {
            st_mailstate = item;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

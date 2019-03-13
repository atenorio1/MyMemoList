package com.example.mymemolist;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MemoActivity extends AppCompatActivity {
    private Memo currentMemo;
    private String rbValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*RadioButton rbLow = (RadioButton) findViewById(R.id.radioLow);
        RadioButton rbMed = (RadioButton) findViewById(R.id.radioMedium);
        RadioButton rbHigh = (RadioButton) findViewById(R.id.radioHigh);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        initTextChangedEvents();
        initSaveButton();
        //initChangePriority();
        initChangePriorityOnClick();
        initListButton();
        initSettingsButton();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            initMemo(extras.getInt("memoID"));
        }
        else {
            currentMemo = new Memo();
        }
       /*if (rbLow.isChecked()) {
            rbValue= "low";
            currentMemo.setMemoPriority(rbValue);

        }
        else if (rbMed.isChecked()) {
            rbValue= "medium";
            currentMemo.setMemoPriority(rbValue);
        }
        else if (rbMed.isChecked()) {
            rbValue= "high";
            currentMemo.setMemoPriority(rbValue);
        }else{
           rbValue= "low";
           currentMemo.setMemoPriority(rbValue);
       }*/
    }

    // Method that initializes the list image button
    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(v -> {
            Intent intent = new Intent(MemoActivity.this, MemoListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    // Method that initializes the settings image button
    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MemoActivity.this, MemoSettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
    private void initMemo(int id) {

        MemoDataSource ds = new MemoDataSource(MemoActivity.this);
        try {
            ds.open();
            currentMemo = ds.getSpecificMemo(id);
            ds.close();
        }
        catch (Exception e) {
            Toast.makeText(this, "Load Memo Failed", Toast.LENGTH_LONG).show();
        }

        EditText eMemo = (EditText) findViewById(R.id.editMemo);
        EditText eName = (EditText) findViewById(R.id.editMemoName);
        TextView tvMemoDate = findViewById(R.id.textDate);

        eMemo.setText(currentMemo.getMemoData());
        eName.setText(currentMemo.getMemoTitle());
        tvMemoDate.setText(currentMemo.getMemoDate());

        String chkMemo = currentMemo.getMemoPriority();

        RadioButton rbLow = findViewById(R.id.radioLow);
        RadioButton rbMed = findViewById(R.id.radioMedium);
        RadioButton rbHigh = findViewById(R.id.radioHigh);

        if(chkMemo.equals("1")){
            rbLow.setChecked(true);
        }else if(chkMemo.equals("2")){
            rbMed.setChecked(true);
        }else{
            rbHigh.setChecked(true);

        }

    }
    private void initTextChangedEvents() {


        final EditText etMemo = (EditText) findViewById(R.id.editMemo);
        etMemo.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                currentMemo.setMemoData(etMemo.getText().toString());
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });

        final EditText etMemoTitle = (EditText) findViewById(R.id.editMemoName);
        etMemoTitle.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                currentMemo.setMemoTitle(etMemoTitle.getText().toString());
            }
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //  Auto-generated method stub
            }
        });
    }


    private void initSaveButton() {


        Button saveButton = (Button) findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date d = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(d);
                currentMemo.setMemoDate(formattedDate);

                hideKeyboard();
                boolean wasSuccessful = false;
                MemoDataSource ds = new MemoDataSource(MemoActivity.this);
                try {
                    ds.open();

                    if (currentMemo.getMemoID() == -1) {
                        wasSuccessful = ds.insertMemo(currentMemo);
                        int newId = ds.getLastMemoID();
                        currentMemo.setMemoID(newId);
                    } else {
                        wasSuccessful = ds.updateMemo(currentMemo);
                    }
                    ds.close();
                }
                catch (Exception e) {
                    wasSuccessful = false;
                }

                if (wasSuccessful) {
                    Toast.makeText(getBaseContext(),  "Successfully Saved",
                            Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editMemo = (EditText) findViewById(R.id.editMemo);
        imm.hideSoftInputFromWindow(editMemo.getWindowToken(), 0);
    }
    /*private void initChangePriority() {
        String sortpriority = getSharedPreferences("MyMemoPreferences", Context.MODE_PRIVATE).getString("sortpriority", "regular");

        RadioButton rbLow = (RadioButton) findViewById(R.id.radioLow);
        RadioButton rbMedium = (RadioButton) findViewById(R.id.radioMedium);
        RadioButton rbHigh = (RadioButton) findViewById(R.id.radioHigh);

            if (sortpriority.equalsIgnoreCase("low")) {
                rbLow.setChecked(true);
            } else if (sortpriority.equalsIgnoreCase("medium")) {
                rbMedium.setChecked(true);
            }else {
                rbHigh.setChecked(true);
            }
    }*/


    private void initChangePriorityOnClick() {
        final RelativeLayout r = (RelativeLayout)findViewById(R.id.activity_memo);
        RadioGroup rgSortByPrio = (RadioGroup) findViewById(R.id.radioGroupSortByPriority);
        rgSortByPrio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            RadioButton rbLow = (RadioButton) findViewById(R.id.radioLow);
            RadioButton rbMed = (RadioButton) findViewById(R.id.radioMedium);
            RadioButton rbHigh = (RadioButton) findViewById(R.id.radioHigh);
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {

                if (rbLow.isChecked()) {
                    rbValue= "1";
                    currentMemo.setMemoPriority(rbValue);
                }
                else if (rbMed.isChecked()) {
                    rbValue= "2";
                    currentMemo.setMemoPriority(rbValue);
                }
                else if (rbHigh.isChecked()){
                    rbValue= "3";
                    currentMemo.setMemoPriority(rbValue);
                }else {
                    rbValue = "1";
                    currentMemo.setMemoPriority(rbValue);
                }

            }
        });
    }
}

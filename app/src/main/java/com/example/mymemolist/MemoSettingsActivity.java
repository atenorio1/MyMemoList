package com.example.mymemolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MemoSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_settings);

        initListButton();
        initSettingsButton();
        initSettings();

        initSortByClick();
        initSortOrderClick();
    }

    // Method that initializes the list image button and switches to the MemoListActivity
    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(v -> {
            Intent intent = new Intent(MemoSettingsActivity.this, MemoListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    // Method that disables the settings image button
    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setEnabled(false);
    }

    // Method that gets values stored in the sharedPreferences file to set RadioButtons to user values when you open this activity
    private void initSettings() {
        String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "datecreated");
        String sortOrder = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortorder", "DESC");

        RadioButton rbName = findViewById(R.id.radioName);
        RadioButton rbDateCreated = findViewById(R.id.radioDateCreated);
        RadioButton rbPriority = findViewById(R.id.radioPriority);

        if (sortBy.equalsIgnoreCase("memoname"))
            rbName.setChecked(true);

        else if (sortBy.equalsIgnoreCase("datecreated"))
            rbDateCreated.setChecked(true);

        else
            rbPriority.setChecked(true);

        RadioButton rbAscending = findViewById(R.id.radioAsc);
        RadioButton rbDescending = findViewById(R.id.radioDesc);

        if(sortOrder.equalsIgnoreCase("ASC"))
            rbAscending.setChecked(true);

        else
            rbDescending.setChecked(true);
    }

    // Method that determines which "Sort Memo By" RadioButton is pressed and saves that value to the SharedPreferences file
    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.radioGroupSortBy);
        rgSortBy.setOnCheckedChangeListener((radioGroup, num) -> {
            RadioButton rbName = findViewById(R.id.radioName);
            RadioButton rbDateCreated = findViewById(R.id.radioDateCreated);

            if (rbName.isChecked())
                getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "memoname").commit();

            else if (rbDateCreated.isChecked())
                getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "datecreated").commit();

            else
                getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).edit().putString("sortfield", "priority").commit();
        });
    }

    // Method that determines which "Sort Order" RadioButton is pressed and saves that value to the SharedPreferences file
    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.radioGroupOrderBy);
        rgSortOrder.setOnCheckedChangeListener((radioGroup, num) -> {
            RadioButton rbAscending = findViewById(R.id.radioAsc);

            if (rbAscending.isChecked())
                getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").commit();

            else
                getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").commit();
        });
    }
}

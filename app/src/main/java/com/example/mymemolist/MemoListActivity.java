package com.example.mymemolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MemoListActivity extends AppCompatActivity {

    ArrayList<Memo> memos;
    boolean isDeleting = false;
    MemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        initListButton();
        initSettingsButton();
        initAddButton();
        //initDeleteButton();
    }

    // Method that initializes the add image button
    private void initAddButton() {
        ImageButton ibAdd = findViewById(R.id.imagebuttonAdd);
        ibAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MemoListActivity.this, MemoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    // Method that initializes the settings image button
    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MemoListActivity.this, MemoSettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    // Method that disables the list image button
    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortfield", "memoPriority");
        String sortOrder = getSharedPreferences("MyMemoListPreferences", Context.MODE_PRIVATE).getString("sortorder", "DESC");

        MemoDataSource ds = new MemoDataSource(this);
        try {
            ds.open();
            memos = ds.getMemo(sortBy, sortOrder);
            ds.close();
            adapter = new MemoAdapter(this, memos);
            ListView listView = (ListView) findViewById(R.id.lvMemo);
            listView.setAdapter(adapter);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
        }

        if (memos.size() > 0) {
            ListView listView = (ListView) findViewById(R.id.lvMemo);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                    Memo selectedMemo = memos.get(position);
                    if (isDeleting) {
                        adapter.showDelete(position, itemClicked, MemoListActivity.this, selectedMemo);
                    } else {
                        Intent intent = new Intent(MemoListActivity.this, MemoActivity.class);
                        intent.putExtra("memoID", selectedMemo.getMemoID());
                        startActivity(intent);
                    }
                }
            });
        }
        else {
            Intent intent = new Intent(MemoListActivity.this, MemoActivity.class);
            startActivity(intent);
        }



    }
    /*private void initDeleteButton() {
        final Button deleteButton = (Button) findViewById(R.id.buttonDeleteMemo);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isDeleting) {
                    deleteButton.setText("Delete");
                    isDeleting = false;
                    adapter.notifyDataSetChanged();
                }
                else {
                    deleteButton.setText("Done Deleting");
                    isDeleting = true;
                }
            }
        });
    }*/
}

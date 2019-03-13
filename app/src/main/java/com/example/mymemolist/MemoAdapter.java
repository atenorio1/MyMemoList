package com.example.mymemolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Danny Kim on 02/19/2019
 */

public class MemoAdapter extends ArrayAdapter<Memo> {


    private ArrayList<Memo> items;
    private Context adapterContext;

    public MemoAdapter(Context context, ArrayList<Memo> items) {
        super(context, R.layout.list_item, items);
        adapterContext = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        try {
            Memo memo = items.get(position);

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }

            TextView memeTitle = v.findViewById(R.id.textMemoName);
            TextView memePriority = v.findViewById(R.id.textPriority);
            TextView memeDateCreated = v.findViewById(R.id.textDateCreated);
            TextView memePreview = v.findViewById(R.id.textMemoPreview);

            Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);

            memeTitle.setText(memo.getMemoTitle());
            memePriority.setText(memo.getMemoPriority());
            memeDateCreated.setText(memo.getMemoDate());
            memePreview.setText(memo.getMemoData());


            b.setVisibility(View.INVISIBLE);

        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return v;
    }

    public void showDelete(final int position, final View convertView, final Context context, final Memo memo) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
        //2
        if (b.getVisibility()== View.INVISIBLE) {
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDelete(position, convertView, context);
                    items.remove(memo);
                    deleteOption(memo.getMemoID(), context);
                }
            });
        }
        else {
            hideDelete(position, convertView, context);
        }
    }
    //3
    private void deleteOption(int memoToDelete, Context context) {
        MemoDataSource db = new MemoDataSource(context);
        try {
            db.open();
            db.deleteMemo(memoToDelete);
            db.close();
        }
        catch (Exception e) {
            Toast.makeText(adapterContext, "Delete Memo Failed", Toast.LENGTH_LONG).show();
        }
        this.notifyDataSetChanged();
    }
    //4
    public void hideDelete(int position, View convertView, Context context) {
        View v = convertView;
        final Button b = (Button) v.findViewById(R.id.buttonDeleteMemo);
        b.setVisibility(View.INVISIBLE);
        b.setOnClickListener(null);
    }

}

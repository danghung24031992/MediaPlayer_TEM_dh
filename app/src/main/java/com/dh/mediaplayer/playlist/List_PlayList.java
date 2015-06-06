package com.dh.mediaplayer.playlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.dh.mediaplayer.R;
import com.dh.mediaplayer.database.MyDataHelper;

/**
 * Created by tiendat on 5/29/2015.
 */
public class List_PlayList extends Activity implements AdapterView.OnItemClickListener {
    private LinearLayout layoutPlaylist;
    private ImageButton choose;
    private ListView listView;
    final Context context = this;
    private Cursor mCursor;
    private SimpleCursorAdapter adapter;

    //thanh phan lam viec voi database
    private MyDataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_playlist);
        layoutPlaylist = (LinearLayout) findViewById(R.id.layout_playlist);
        choose = (ImageButton) findViewById(R.id.imageChoose);
        listView = (ListView) findViewById(R.id.list);

        dataHelper = new MyDataHelper(getApplicationContext());
        mCursor = dataHelper.getData();

        adapter = new SimpleCursorAdapter(
                List_PlayList.this,
                R.layout.list_item,
                mCursor,
                new String[]{"Name"},
                new int[]{R.id.tvSongName}, 0);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(this);


    }

    public void onNewPlayList(View v) {
        //set View cho dialog de hien thi
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialog = layoutInflater.inflate(R.layout.layout_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialog);
        final EditText editText = (EditText) dialog.findViewById(R.id.editTextList);
        builder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    dataHelper = new MyDataHelper(getApplicationContext());
                                    if (editText.getText().length() > 0) {
                                        dataHelper.insertData(editText.getText().toString());
                                        Toast.makeText(List_PlayList.this, R.string.add_data_toast, Toast.LENGTH_LONG).show();
                                    }
                                    mCursor = dataHelper.getData();
                                    adapter = new SimpleCursorAdapter(
                                            List_PlayList.this,
                                            R.layout.list_item,
                                            mCursor,
                                            new String[]{"Name"},
                                            new int[]{R.id.tvSongName}, 0);
                                    listView.setAdapter(adapter);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();

    }

    public void onPlayClickBack(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mCursor.moveToPosition(position);
        Intent add = new Intent(context, ActivityAddSongs.class);
        add.putExtra("playlistid", mCursor.getInt(0));
        add.putExtra("add","no");
        startActivity(add);
    }
}


package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;


public class NoteActivity extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        int position = intent.getIntExtra("pos",-1);
        if(position == -1) {
            editText.setText("Add a Note!");
            MainActivity.notes.add("");
            position = MainActivity.notes.size() - 1;
        }else{
            editText.setText(MainActivity.notes.get(position));
        }
        final int finalPosition = position;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainActivity.notes.set(finalPosition, String.valueOf(s));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.zappycode.notes", Context.MODE_PRIVATE);
                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

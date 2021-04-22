package com.fivesoft.filepickerdemoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.fivesoft.sfilepicker.FilePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FilePicker.from(this)
                .setFileTypes(FilePicker.IMAGE, FilePicker.VIDEO) //Set file types you want to pick.
                .setAllowMultipleFiles(true) //Allow user to select multiple files
                .setListener(files -> { //Wait for results
                    if(files != null && files.size() > 0){
                        //Do something with uris.
                    }
                })
                .setTitle("Pick a file")
                .pick(); //Open file picker

    }
}
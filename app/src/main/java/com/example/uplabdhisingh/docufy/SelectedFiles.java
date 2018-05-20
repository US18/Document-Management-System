package com.example.uplabdhisingh.docufy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SelectedFiles extends AppCompatActivity
{
    private String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    private String TAG = SelectedFiles.class.getSimpleName();
    private String dataPath = SDPath + "/Download/" ;
    private String zipPath = SDPath + "/Download/zip/" ;

    Button zipButton;
    EditText zipFileName;
    CheckBox chkParent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_files);

       zipButton = (Button) findViewById(R.id.btn_zip);
       zipFileName = (EditText) findViewById(R.id.et_zip_file_name);
       chkParent = (CheckBox) findViewById(R.id.chkParent);



       zipButton.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
                    new ZipInBackground().execute();
           }
       });

       Log.d(TAG,"SDPATH :: " +SDPath);

        //Create dummy files
        FileHelper.saveToFile(dataPath, "Resume.pdf");


    }

    public class ZipInBackground extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            String zipName = zipFileName.getText().toString();
            FileHelper.zip(dataPath, zipPath, zipName,
                    chkParent.isChecked());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            Toast.makeText(SelectedFiles.this,
                    "Zip successfully.",Toast.LENGTH_LONG).show();
        }
    }

}

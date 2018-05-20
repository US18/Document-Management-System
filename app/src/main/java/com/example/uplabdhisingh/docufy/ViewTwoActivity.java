package com.example.uplabdhisingh.docufy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uplabdhisingh.docufy.adapter.UploadsListAdapter;
import com.example.uplabdhisingh.docufy.constants.Constants;
import com.example.uplabdhisingh.docufy.constants.Uploads;
import com.example.uplabdhisingh.docufy.ViewOneActivity;
import com.example.uplabdhisingh.docufy.model.Files;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ViewTwoActivity extends AppCompatActivity
{

    ListView listView;
    List<Uploads> uploadsList;
    Button chooseFilesButton;
    Uploads upload;

    static final int PICK_FILE_REQUEST = 101;

    private String pathToFile = "";

    UploadsListAdapter listAdapter;
    DatabaseReference mDatabaseReference;

   View row;

   int selectedPosition;
   String TAG = ViewTwoActivity.class.getSimpleName();
    String selectedItemUri;
    String selectedItemName;

    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_two);

        uploadsList = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        listView = (ListView) findViewById(R.id.listView);


        listAdapter = new UploadsListAdapter
                (this,R.layout.uploads_list_tem,uploadsList);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);
        listView.setAdapter(listAdapter);

        chooseFilesButton = (Button) findViewById(R.id.btn_choose_files);

       /* String[] arrayListviewItems = new String[listView.getAdapter().getCount()];

       *//* int value1 = listAdapter.getValue();

        Log.d(TAG,"SIZE IS : " +value1);*//*



        for(int i=0; i<20;i++)
        {
            Intent intent = getIntent();
            //String uri = upload.getUrl();
          //  Uri uri = Uri.parse(upload.getUrl());
            String name = intent.getStringExtra("fileNameFromViewOne");

           // arrayListviewItems[i]=uri;
//            arrayListviewItems[i]=name;
        }*/

       // Log.d(TAG,"ARRAY IS : "+arrayListviewItems[0]);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                selectedPosition=position;
                //getting the upload
                upload = uploadsList.get(selectedPosition);

                chooseFilesButton.setVisibility(View.VISIBLE);
                listAdapter.updateRecords(uploadsList);
                //Opening the upload file in browser using the upload url
               /* Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(upload.getUrl()));
                startActivity(intent);*/
                row=view;
               if(row!=null)
               {
                   row.setBackgroundResource(R.color.green);
               }
               view.setBackgroundResource(R.color.green);

                Log.d(TAG,"URI IS :::::" +Uri.parse(upload.getUrl()));

           //   selectedItemUri = arrayListviewItems[position];
            //  selectedItemName = arrayListviewItems[position];
            }
        });



        chooseFilesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intentToMergeActivity = new Intent(ViewTwoActivity.this,SelectedFiles.class);
                intentToMergeActivity.putExtra("URI",selectedItemUri);
               intentToMergeActivity.putExtra("NAME",selectedItemName);
                startActivity(intentToMergeActivity);
            }
        });

        mChildEventListener = new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Uploads uploads = dataSnapshot.getValue(Uploads.class);
                listAdapter.add(uploads);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }
}




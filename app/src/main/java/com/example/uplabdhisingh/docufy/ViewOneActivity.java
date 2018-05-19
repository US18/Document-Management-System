package com.example.uplabdhisingh.docufy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.uplabdhisingh.docufy.constants.Constants;
import com.example.uplabdhisingh.docufy.constants.Course;
import com.example.uplabdhisingh.docufy.constants.Uploads;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewOneActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
   EditText fullNameEditText, sessionEditText, fileNameEditText;
   Button uploadFileButton, viewUploadsButton, mergeFileButton;
   Spinner semesterSpinner, courseSpinner;
   final static int PICK_PDF_CODE = 2342;
   ProgressDialog dialog;
   Uri downloadUrl;
   String facultyNameInput, courseNameInput, semesterInput, sessionInput,fileNameInput;
   public static final String ANONYMOUS = "anonymous";
   StorageReference mStorageReference;
   DatabaseReference mDatabaseReference;
   FirebaseAuth mFirebaseAuth;
   FirebaseAuth.AuthStateListener mAuthStateListener;
   private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_one);
    mStorageReference = FirebaseStorage.getInstance().getReference();
    mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
    mFirebaseAuth=FirebaseAuth.getInstance();
    Course course = new Course("B.Tech-CSE-SCF","CS101","B.Tech-CSE-BAO","CS102"
            ,"B.Tech-CSE-TI","CS103");
     mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(course);

     fullNameEditText = (EditText) findViewById(R.id.et_faculty_name);
     sessionEditText = (EditText) findViewById(R.id.et_session);
     fileNameEditText = (EditText) findViewById(R.id.et_file_name);
     uploadFileButton = (Button) findViewById(R.id.btn_upload_file);
     viewUploadsButton = (Button) findViewById(R.id.btn_view_uploads);
     mergeFileButton = (Button) findViewById(R.id.btn_merge_uploads);
     semesterSpinner = (Spinner) findViewById(R.id.spinner_semester);
     courseSpinner = (Spinner) findViewById(R.id.spinner_course);
     semesterSpinner.setOnItemSelectedListener(this);
     courseSpinner.setOnItemSelectedListener(this);
     List<String> semesters = new ArrayList<String>();
        semesters.add("Semester 1");
        semesters.add("Semester 2");
        semesters.add("Semester 3");
        semesters.add("Semester 4");
        semesters.add("Semester 5");
        semesters.add("Semester 6");
        semesters.add("Semester 7");
        semesters.add("Semester 8");
        semesters.add("Semester 9");
        semesters.add("Semester 10");
        semesters.add("Semester 11");
        semesters.add("Semester 12");

     ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this,R.layout.semester_spinner_list,semesters);
    semesterSpinner.setAdapter(dataAdapter);
    List<String> courses = new ArrayList<String>();

    courses.add("B.Tech-CSE-SCF");
    courses.add("B.Tech-CSE-BAO");
    courses.add("B.Tech-CSE-TI");

    ArrayAdapter<String> courseAdapter = new ArrayAdapter<String>(this,R.layout.spinner_course_items,courses);
    courseSpinner.setAdapter(courseAdapter);

    uploadFileButton.setOnClickListener(new View.OnClickListener()
        {
         @Override
         public void onClick(View v)
         {
          Intent intent = new Intent();
          intent.setType("application/pdf");
          intent.setAction(Intent.ACTION_GET_CONTENT);
          startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
         }
        });

    viewUploadsButton.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(ViewOneActivity.this, ViewTwoActivity.class);
            startActivity(intent);
        }
    });

        mAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if( user!=null )
                {
                    onSignedInInitialize(user.getDisplayName());
                } else
                {
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(
                                            Arrays.asList(
                                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                                    new AuthUI.IdpConfig.GoogleBuilder().build()
                                            )
                                    )
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialize(String username)
    {
        facultyNameInput = username;
    }

    private void onSignedOutCleanup()
    {

        facultyNameInput = ANONYMOUS;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        semesterInput = parent.getItemAtPosition(position).toString();
        courseNameInput = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data)
   {
      super.onActivityResult(requestCode, resultCode, data);
       if(requestCode == RC_SIGN_IN)
       {
           if(resultCode == RESULT_OK)
           {
               Toast.makeText(this, "Signed In!", Toast.LENGTH_SHORT).show();

           } else if(resultCode == RESULT_CANCELED)
           {
               Toast.makeText(this, "Sign In Cancelled", Toast.LENGTH_SHORT).show();
               finish();
           }
       } else if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null)
     {
      //if a file is selected
      if (data.getData() != null)
     {
      //uploading the file
      uploadFile(data.getData());
     } else
     {
       Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
     }
     }
   }

    private void uploadFile(Uri data)
      {
          dialog = ProgressDialog.show(ViewOneActivity.this, "",
                  "Loading. Please wait...", true);

          StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
       sRef.putFile(data)
          .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @SuppressWarnings("VisibleForTests")
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
           {

             dialog.dismiss();
            AlertDialog alertDialog = new AlertDialog.Builder(
                    ViewOneActivity.this).create();

            alertDialog.setTitle("Status");

            alertDialog.setMessage("File uploaded Successfully.");

            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener()
            {
             @Override
             public void onClick(DialogInterface dialog, int which)
             {
              dialog.cancel();
             }
            });

            alertDialog.show();

            downloadUrl = taskSnapshot.getDownloadUrl();

            facultyNameInput = fullNameEditText.getText().toString();
            sessionInput=sessionEditText.getText().toString();
            fileNameInput = fileNameEditText.getText().toString();

            assert downloadUrl != null;

            Uploads uploads = new Uploads(facultyNameInput,courseNameInput,fileNameInput,semesterInput,sessionInput,downloadUrl.toString(),false);

            mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(uploads);
           }
          }).addOnFailureListener(new OnFailureListener()
          {
           @Override
           public void onFailure(@NonNull Exception exception)
           {
            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
           }
          });
      }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.view_one_menu,menu);
        return true;
    }
}

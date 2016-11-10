package edu.udel.cisc475.team1.greentech_android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.ListView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class EducationalFragment extends Fragment{

    private Activity mActivity;
    private ArrayList<String> mArrayList;
    private ArrayAdapter<String> mAdapter;
    private ListView mList;
    private DatabaseReference mDatabaseReference;
    private FloatingActionButton mFab;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mActivity = getActivity();
        mAuth = FirebaseAuth.getInstance();

        mFab = (FloatingActionButton) mActivity.findViewById(R.id.fab);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_educational, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setAddEntryListener(mFab);
        populateListView();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void populateListView() {
        mList = (ListView) mActivity.findViewById(R.id.educational_listview);
        mArrayList = new ArrayList<String>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("resources");

        mAuth.signInAnonymously()
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                        //    Log.w(TAG, "signInAnonymously", task.getException());
                            Snackbar.make(mActivity.getCurrentFocus(), "Add button pressed in educational tab", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        // ...
                    }
                });

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Resource value = child.getValue(Resource.class);
                    mArrayList.add(value.getTitle());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        mAdapter = new ArrayAdapter<String>(mActivity.getApplicationContext(), android.R.layout.simple_spinner_item, mArrayList);
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object o = mList.getItemAtPosition(position);

                AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
                alertDialog.setTitle(mArrayList.get(position));

                View dialogView = mActivity.getLayoutInflater().inflate(R.layout.view_entry_educational, null);
                alertDialog.setView(dialogView);

                TextView website = (TextView) dialogView.findViewById(R.id.view_entry_educational_website);
                TextView description = (TextView) dialogView.findViewById(R.id.view_entry_educational_description);

                //website.setText(((Resource)mArrayList.get(position)).getWebsite());
                //description.setText(google.getDescription());

                alertDialog.setButton(mActivity.getString(R.string.view_dialog_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
    }

    private void setAddEntryListener(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
            LayoutInflater inflater = mActivity.getLayoutInflater();
            alertDialog.setTitle(R.string.add_entry_educational_title);

            final View dialogView = inflater.inflate(R.layout.add_entry_educational, null);
            alertDialog.setView(dialogView);

            alertDialog.setButton(mActivity.getString(R.string.entry_dialog_positive_button), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String title = ((EditText) dialogView.findViewById(R.id.add_entry_educational_title)).getText().toString();
                    String website = ((EditText) dialogView.findViewById(R.id.add_entry_educational_website)).getText().toString();
                    String description = ((EditText) dialogView.findViewById(R.id.add_entry_educational_description)).getText().toString();
                    Resource newRes = new Resource(title, website, description);
                    mArrayList.add(newRes.getTitle());
                    mAdapter.notifyDataSetChanged();
                    mDatabaseReference.child("resource").setValue(newRes);
                }
            });
            alertDialog.show();
        }
    });
    }
}
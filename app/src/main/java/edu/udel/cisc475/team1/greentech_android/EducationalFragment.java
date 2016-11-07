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

public class EducationalFragment extends Fragment{

    private Activity mActivity;
    private ArrayList<String> mArrayList;
    private ListView mList;
    private DatabaseReference mDatabaseReference;
    private int mResourcesItemsCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mActivity = getActivity();
        mResourcesItemsCount = 0;

        FloatingActionButton fab = (FloatingActionButton) mActivity.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
                alertDialog.setTitle(R.string.add_entry_educational_title);

                View dialogView = mActivity.getLayoutInflater().inflate(R.layout.add_entry_educational, null);
                alertDialog.setView(dialogView);

                alertDialog.setButton(mActivity.getString(R.string.entry_dialog_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_educational, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        populateListView();
    }

    private void populateListView() {
        mList = (ListView) mActivity.findViewById(R.id.educational_listview);
        mArrayList = new ArrayList<String>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("resources");

        mArrayList.add(mResourcesItemsCount++, (String) mDatabaseReference.child("resource1").child("title").getKey());
        mArrayList.add(mResourcesItemsCount++, (String) mDatabaseReference.child("resource2").child("title").getKey());

        final Resource res2 = new Resource("Facebook", "www.facebook.com", "facebook site");

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mArrayList.add(mResourcesItemsCount++, (String) dataSnapshot.child("resource1").child("title").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       /* mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mArrayList.add(mResourcesItemsCount++, res2.getTitle());

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    mArrayList.add(mResourcesItemsCount++, (String) child.getValue());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity.getApplicationContext(), android.R.layout.simple_spinner_item, mArrayList);
        mList.setAdapter(adapter);

        mDatabaseReference.child("resource3").push().setValue(res2);

        mList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object o = mList.getItemAtPosition(position);

                Snackbar.make(view, o.toString() + " Clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
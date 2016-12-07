package edu.udel.cisc475.team1.greentech_android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.ListView;

import java.util.*;

import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class EducationalFragment extends Fragment{

    private Activity mActivity;
    private ArrayList<Resource> mArrayList;
    private ArrayList<String> mArrayListTitles;
    private ArrayAdapter<String> mAdapter;
    private ListView mList;
    private DatabaseReference mDatabaseReference;
    private FloatingActionButton mFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = getActivity();
        mFab = (FloatingActionButton) mActivity.findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);

        mArrayList = new ArrayList<>();
        mArrayListTitles = new ArrayList<>();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("resources");

        return inflater.inflate(R.layout.fragment_educational, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setAddEntryListener(mFab);
        populateListView();
    }

    private void populateListView() {
        mList = (ListView) mActivity.findViewById(R.id.educational_listview);

        mAdapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_spinner_item, mArrayListTitles);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
                alertDialog.setTitle(mArrayList.get(position).getTitle());

                View dialogView = mActivity.getLayoutInflater().inflate(R.layout.view_entry_educational, null);
                alertDialog.setView(dialogView);

                TextView website = (TextView) dialogView.findViewById(R.id.view_entry_educational_website);
                TextView description = (TextView) dialogView.findViewById(R.id.view_entry_educational_description);

                website.setText(mArrayList.get(position).getWebsite());
                description.setText(mArrayList.get(position).getDescription());

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, mActivity.getString(R.string.view_dialog_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Resource value = child.getValue(Resource.class);
                    if(mAdapter.getPosition(value.getTitle())<0){
                        mArrayList.add(value);
                        mArrayListTitles.add(value.getTitle());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        mDatabaseReference.addValueEventListener(valueEventListener);
    }

    private void setAddEntryListener(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
                LayoutInflater inflater = mActivity.getLayoutInflater();
                alertDialog.setTitle(R.string.add_entry_educational_title);

                final View dialogView = inflater.inflate(R.layout.add_entry_educational, null);
                alertDialog.setView(dialogView);

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, mActivity.getString(R.string.entry_dialog_positive_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String title = ((EditText) dialogView.findViewById(R.id.add_entry_educational_title)).getText().toString();
                        String website = ((EditText) dialogView.findViewById(R.id.add_entry_educational_website)).getText().toString();
                        String description = ((EditText) dialogView.findViewById(R.id.add_entry_educational_description)).getText().toString();
                        Resource newRes = new Resource(title, website, description);
                        mDatabaseReference.child(UUID.randomUUID().toString()).setValue(newRes);
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, mActivity.getString(R.string.entry_dialog_negative_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.hide();
                    }
                });
                alertDialog.show();
            }
        });
    }
}
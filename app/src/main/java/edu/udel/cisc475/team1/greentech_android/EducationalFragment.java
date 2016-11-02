package edu.udel.cisc475.team1.greentech_android;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mActivity = getActivity();

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

    /*public static class Post {

        public String site;

        public Post(String site) {
        }

        public String getSite() {
            return site;
        }

    }*/

    private void populateListView() {
        final ListView list = (ListView) mActivity.findViewById(R.id.educational_listview);
        final ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(0, "Website 1");
        arrayList.add(1, "Website 2");
        arrayList.add(2, "Website 3");

        /*// Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference ref = database.getReference("server/saving-data/fireblog/posts");

        DatabaseReference ref = database.getReference("https://greentechapp-c4a2c.firebaseio.com/sites");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                //System.out.println(post);

                arrayList.add(3, post.getSite());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
*/

        // Adapter: You need three parameters 'the context, id of the layout (it will be where the data is shown),
        // and the array that contains the data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity.getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object o = list.getItemAtPosition(position);

                Snackbar.make(view, o.toString() + " Clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}
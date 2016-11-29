package edu.udel.cisc475.team1.greentech_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.app.Activity;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class MapsFragment extends Fragment{

    private Activity mActivity;
    private GoogleMap map;
    private MapFragment mapFragment;
    private DatabaseReference mDatabaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mActivity = getActivity();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("sites");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapClick();
    }

    private void mapClick() {
        MapsFragment mapsFragment = (MapsFragment) mActivity.getFragmentManager().findFragmentById(R.id.activity_main);
        mapFragment = (MapFragment) mapsFragment.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                setMapListener();
                setDatabaseListener();
            }
        });
    }

    private void setMapListener() {
        map.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                makeAddSiteDialog(point);
            }
        });
    }

    private void makeAddSiteDialog(final LatLng point) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
        LayoutInflater inflater = mActivity.getLayoutInflater();
        alertDialog.setTitle(R.string.add_entry_maps_title);

        final View dialogView = inflater.inflate(R.layout.add_entry_maps, null);
        alertDialog.setView(dialogView);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, mActivity.getString(R.string.entry_dialog_positive_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String title = ((EditText) dialogView.findViewById(R.id.add_entry_maps_title)).getText().toString();
                Site newSite = new Site(title, point.latitude, point.longitude);
                mDatabaseReference.child(UUID.randomUUID().toString()).setValue(newSite);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, mActivity.getString(R.string.entry_dialog_negative_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.hide();
            }
        });
        alertDialog.show();
    }

    private void setDatabaseListener() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Site value = child.getValue(Site.class);
                    map.addMarker(new MarkerOptions()
                                    .position(new LatLng(value.getLat(), value.getLon()))
                                    .title(value.getTitle()));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }};
        mDatabaseReference.addValueEventListener(valueEventListener);
    }
}
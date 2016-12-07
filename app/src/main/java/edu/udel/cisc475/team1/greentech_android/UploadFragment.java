package edu.udel.cisc475.team1.greentech_android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UploadFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().findViewById(R.id.fab).setVisibility(View.INVISIBLE);

        return inflater.inflate(R.layout.fragment_upload, container, false);
    }
}
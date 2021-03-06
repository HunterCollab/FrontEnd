package com.huntercollab.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialmediaapp.R;
import com.huntercollab.app.network.loopjtasks.UpdateCollabData;


public class EditCollabLocationFragment extends Fragment implements UpdateCollabData.UpdateCollabComplete {

    public EditCollabLocationFragment() {
        // Required empty public constructor
    }

    EditCollabTitleFragment.OnDataPass dataPasser;
    private String collabid;
    private EditText editLocation;
    private Button saveLocationButton;
    private EditCollabLocationFragment instance = null;
    private UpdateCollabData updateLocation;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (EditCollabTitleFragment.OnDataPass) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //@author: Hugh Leow
        //@brief: Used to update the correct collaboration
        collabid = dataPasser.onDataPass();

        // Inflate the layout for this fragment
        instance = this;
        View view = inflater.inflate(R.layout.fragment_edit_collab_location, container, false);
        editLocation = (EditText) view.findViewById(R.id.editText);

        //@author: Hugh Leow
        //@brief:
        //API call to update the collaboration data from user input
        //See: UpdateCollabData.java
        //@pre condition: New information not updated in database
        //@post condition: Request to update database with new information
        saveLocationButton = (Button) view.findViewById(R.id.saveLocation);
        saveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newLocation = editLocation.getText().toString();
                updateLocation = new UpdateCollabData(getContext(), instance);
                updateLocation.updateCollabLocation(newLocation, collabid);
                saveLocationButton.setEnabled(false);
            }
        });
        return view;
    }

    //@author: Hugh Leow
    //@brief:
    //Interface function for ASYNC HTTP request from UpdateCollabData.java
    //If the database is updated successfully, close the fragment + activity, otherwise notify the user
    //@params: [Boolean success]
    //@pre condition: New information not updated in database
    //@post condition: Database updated with new information if success = 'true'
    @Override
    public void updateCollabComplete(Boolean success) {
        if (success) {
            getActivity().finish();
        }
        else {
            // show error message to user
            Toast t = Toast.makeText(getContext(), "ERROR. TRY AGAIN.", Toast.LENGTH_LONG);
            t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            t.show();
            saveLocationButton.setEnabled(true);
        }
    }
}

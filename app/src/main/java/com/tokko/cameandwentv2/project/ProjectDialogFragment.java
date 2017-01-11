package com.tokko.cameandwentv2.project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.tokko.cameandwentv2.R;
import com.tokko.cameandwentv2.resourceaccess.ProjectRepository;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

@EFragment
public class ProjectDialogFragment extends DialogFragment{

    EditText locationName;

    @Bean
    ProjectRepository projectRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.project_dialog_fragment, null);
        locationName = (EditText) v.findViewById(R.id.locationName);
        return builder
                .setTitle("Create Project")
                .setView(v)
                .setNegativeButton(getActivity().getString(android.R.string.cancel), (arg0, arg1) -> {
                    dismiss();
                })

                .setPositiveButton(getActivity().getString(android.R.string.ok), (DialogInterface arg0, int arg1) -> {
                    projectRepository.insert(new Project(locationName.getText().toString(), 0, 0));
                    dismiss();
                })
                .create();
    }

    @Click(R.id.setLocationButton)
    public void setLocationClick(){

    }

    public void onOkClick(){
        projectRepository.insert(new Project(locationName.getText().toString(), 0, 0));
        dismiss();
    }

    public void onCancelClick(){
        dismiss();
    }
}

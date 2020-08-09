package com.example.retrofitwithimdb.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.retrofitwithimdb.R;

public class DetailsDialog extends AppCompatDialogFragment {
    TextView titleText,popularityText,isAdultText,stateText,vaText,vcText;
    String title,pop,isAdult,state,va,vc;

    public DetailsDialog(String title, String pop, String isAdult, String state, String va, String vc) {
        this.title = title;
        this.pop = pop;
        this.isAdult = isAdult;
        this.state = state;
        this.va = va;
        this.vc = vc;
    }

    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout,null);
        titleText = view.findViewById(R.id.dialogTitle);
        popularityText = view.findViewById(R.id.dialogPop);
        isAdultText = view.findViewById(R.id.dialogAdult);
        stateText = view.findViewById(R.id.dialogState);
        vaText = view.findViewById(R.id.dialogVoteAverage);
        vcText = view.findViewById(R.id.dialogVoteCount);

        titleText.setText("Title : "+title);
        popularityText.setText("Popularity : "+pop);
        isAdultText.setText("Is Adult : "+isAdult);
        stateText.setText("State : "+state);
        vaText.setText("Vote Average : "+va);
        vcText.setText("Vote Count : "+vc);
        builder.setView(view)
                .setNegativeButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}

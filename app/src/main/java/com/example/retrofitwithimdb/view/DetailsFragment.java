package com.example.retrofitwithimdb.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofitwithimdb.R;
import com.example.retrofitwithimdb.models.DetailsClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    static DetailsClass currentClass;
    View root;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String titleARG = "title";
    private static final String popARG = "popu";
    private static final String adultARG = "adult";
    private static final String stateARG = "state";
    private static final String vaARG = "va";
    private static final String vcARG = "vc";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(DetailsClass detailsClass) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(titleARG,detailsClass.getTitle());
        args.putDouble(popARG,detailsClass.getPopularity());
        args.putBoolean(adultARG,detailsClass.isAdult());
        args.putString(stateARG,detailsClass.getStatus());
        args.putDouble(vaARG,detailsClass.getVote_average());
        args.putInt(vcARG,detailsClass.getVote_count());
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        Toast.makeText(getContext(),this.currentClass.getTitle(),Toast.LENGTH_SHORT);
//        Log.i("tit",currentClass.getTitle());
        root = inflater.inflate(R.layout.fragment_details2, container, false);

        fillData(root);

        return root;


    }


    public void fillData(View root){
        TextView titleText = root.findViewById(R.id.titleText);
        TextView popText = root.findViewById(R.id.popText);
        TextView adult = root.findViewById(R.id.adultText);
        TextView state = root.findViewById(R.id.stateText);
        TextView vAvg = root.findViewById(R.id.vAverageText);
        TextView vCount = root.findViewById(R.id.vCountText);


        titleText.setText(titleText.getText() + getArguments().getString(titleARG));
        popText.setText(popText.getText() + Double.toString(getArguments().getDouble(popARG)));

        adult.setText(adult.getText() + Boolean.toString(getArguments().getBoolean(adultARG)));

        state.setText(state.getText() + getArguments().getString(stateARG));

        vAvg.setText(vAvg.getText() + Double.toString(getArguments().getDouble(vaARG)));

        vCount.setText(vCount.getText() + Integer.toString(getArguments().getInt(vcARG)));
    }
}
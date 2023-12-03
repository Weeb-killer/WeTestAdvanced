package com.example.cognitive_diagnosis_app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.internal.bind.SqlDateTypeAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link moreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class moreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SQLiteDatabase mydb;

    public moreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment moreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static moreFragment newInstance(String param1, String param2) {
        moreFragment fragment = new moreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        Button upQ = view.findViewById(R.id.uploadquestion);
        upQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View moreF = getView();
                mydb = SQLiteDatabase.openDatabase("/data/data/com.example.cognitive_diagnosis_app/databases/student.db", null, SQLiteDatabase.OPEN_READWRITE);
                ContentValues values = new ContentValues();
                EditText QCo,QA,QB,QC,QD,QRA,QE,KP1;
                QCo=moreF.findViewById(R.id.Qcontent);
                QA=moreF.findViewById(R.id.QA);
                QB=moreF.findViewById(R.id.QB);
                QC=moreF.findViewById(R.id.QC);
                QD=moreF.findViewById(R.id.QD);
                QRA=moreF.findViewById(R.id.QRA);
                QE=moreF.findViewById(R.id.QE);
                KP1=moreF.findViewById(R.id.KP);
                values.put("content", QCo.getText().toString());
                values.put("option_A", QA.getText().toString());
                values.put("option_B", QB.getText().toString());
                values.put("option_C", QC.getText().toString());
                values.put("option_D", QD.getText().toString());
                values.put("correct_ot", QRA.getText().toString());
                values.put("explanation", QE.getText().toString());
                String str = KP1.getText().toString();
                System.out.println(str);
                String[] KP = str.split(",");
                values.put("Search_Algorithm", KP[0]);
                values.put("sorting_algorithm", KP[1]);
                values.put("Link_List", KP[2]);
                values.put("queue", KP[3]);
                values.put("stack", KP[4]);
                values.put("array", KP[5]);
                values.put("tree", KP[6]);
                values.put("graph", KP[7]);
                mydb.insert("timutimu", null, values);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

    }

}
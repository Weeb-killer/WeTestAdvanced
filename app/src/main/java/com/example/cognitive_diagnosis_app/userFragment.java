package com.example.cognitive_diagnosis_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link userFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class userFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Animation topAnimation,bottomAnimation,middleAnimation;
    View Moon,Builing,first,second,third,fourth,fifth,star;
    public userFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment userFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static userFragment newInstance(String param1, String param2) {
        userFragment fragment = new userFragment();
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
        return inflater.inflate(R.layout.fragment_user, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        View fragmentView = getView();
        topAnimation= AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.top_animation);
        bottomAnimation= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.bottom_animation);
        middleAnimation= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.middle_animation);
        first=fragmentView.findViewById(R.id.first);
        second=fragmentView.findViewById(R.id.second);
        third=fragmentView.findViewById(R.id.third);
        fourth=fragmentView.findViewById(R.id.fourth);
        fifth=fragmentView.findViewById(R.id.fifth);
        Moon=fragmentView.findViewById(R.id.Moon);
        Builing=fragmentView.findViewById(R.id.building);
        star=fragmentView.findViewById(R.id.Star);
        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        Moon.setAnimation(middleAnimation);
        Builing.setAnimation(bottomAnimation);
        star.setAnimation(middleAnimation);
    }
}
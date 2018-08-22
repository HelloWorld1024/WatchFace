package com.tnt.watchhome.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tnt.watchhome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoiceAssistantFragment extends Fragment {

    private View mView ;

    public VoiceAssistantFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            mView = inflater.inflate(R.layout.fragment_voice_assistant, container, false);
        return mView;
    }

}

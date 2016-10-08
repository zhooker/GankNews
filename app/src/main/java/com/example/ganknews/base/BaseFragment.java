package com.example.ganknews.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ganknews.R;
import com.example.ganknews.util.L;

/**
 * Created by zhooker on 2016/10/8.
 */

public class BaseFragment extends Fragment {

    public static final String GUID = "guid";
    private String guid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        guid = bundle.getString(GUID);
        L.d(guid);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.d(guid);
        View view = inflater.inflate(R.layout.fragment_base, null);
        ((TextView) view.findViewById(R.id.textView)).setText(guid);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        L.d(guid + "," + hidden);
    }

    @Override
    public void onDetach() {
        L.d(guid);
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        L.d(guid);
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        L.d(guid);
        super.onAttach(context);
    }
}

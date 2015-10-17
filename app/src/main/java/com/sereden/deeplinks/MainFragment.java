package com.sereden.deeplinks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by andrii on 17.10.15.
 */
public class MainFragment extends Fragment {
    public static final String TAG = MainFragment.class.getSimpleName();
    private static final String DATA_EXTRA = "DATA_EXTRA";

    public static Fragment getInstance(String data) {
        Fragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATA_EXTRA, data);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static String getData(Bundle bundle) {
        if (bundle != null) {
            return bundle.getString(DATA_EXTRA);
        }
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new TextView(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String data = getData(getArguments());
        ((TextView) view).setText(data == null ? "" : data);
    }
}

package com.halo.haloandroidframework.Badge;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.halo.haloandroidframework.R;
import com.halo.uiview.badge.MaterialBadgeTextView;

/**
 * Created by Frank1 on 2017/1/19.
 */

public class BadgeFragment extends Fragment {
    private MaterialBadgeTextView just_new_feature;

    public BadgeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_badge, container, false);
        just_new_feature = (MaterialBadgeTextView)view.findViewById(R.id.just_new_feature);
        just_new_feature.setHighLightMode();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}

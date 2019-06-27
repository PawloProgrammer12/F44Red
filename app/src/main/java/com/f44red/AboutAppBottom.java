/**
 * com.f44red contains all classes for F44Red application properly working.
 */

package com.f44red;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Displays about application with bottom layout.
 */

public class AboutAppBottom extends BottomSheetDialogFragment {
    public static AboutAppBottom newInstance(){
        return new AboutAppBottom();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.about_app_bottom, container, false);
        return view;
    }
}

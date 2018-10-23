package com.valeriogiocondi.user.proverbsapp.System;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.valeriogiocondi.user.proverbsapp.R;

public class ViewCustomActivityHandler {

    public static void buttonHandler(Context context) {

        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        final Activity activity = (Activity) context;
        ImageView backToPreviousActivity = (ImageView) rootView.findViewById(R.id.back);

        backToPreviousActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                activity.onBackPressed();
            }
        });
    }
}

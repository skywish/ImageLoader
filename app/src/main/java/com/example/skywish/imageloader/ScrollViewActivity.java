package com.example.skywish.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by skywish on 2015/4/13.
 */
public class ScrollViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_myscroll);
    }
}

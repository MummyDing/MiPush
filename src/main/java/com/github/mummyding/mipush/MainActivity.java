package com.github.mummyding.mipush;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (MIUIUtil.isMIUI()) {
            Toast.makeText(this, "MIUI系统", Toast.LENGTH_LONG).show();
        }
    }
}

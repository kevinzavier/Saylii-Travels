package com.release.kevinzavier.sayliitravels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by kevin on 8/26/16.
 */
public class PopMenu extends Activity {
    Button submit;
    EditText summary;
    private String myInfo = "";
    String setData;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState)  ;
        setContentView(R.layout.popup_window);

        submit = (Button) findViewById(R.id.Submit);
        summary = (EditText) findViewById(R.id.summary);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


                myInfo = summary.getText().toString();


                Log.i("","WTFFFFFFFFFFFFFFFFFf");
                Intent person = new Intent();
                Bundle backpack = new Bundle();
                backpack.putString("info", myInfo);
                person.putExtras(backpack);
                setResult(RESULT_OK, person);


                finish();
            }
        });

        summary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submit.setEnabled(!summary.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height * .8));


    }
}

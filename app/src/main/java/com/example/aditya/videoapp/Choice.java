package com.example.aditya.videoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Choice extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    public RadioButton jokesb;
    public RadioButton funFactsb;
    public RadioButton newsb;
    public RadioButton aboutMoviesb;
    public RadioButton noneb;
    public TextView text12;
    public int id12;
    public String choiceName;
    public int checkedIndex;
    public int key;
    public int Select;

    public Button refreshButton;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        jokesb=(RadioButton)findViewById(R.id.jokes);
        funFactsb=(RadioButton)findViewById(R.id.funFacts);
        newsb=(RadioButton)findViewById(R.id.news);
        aboutMoviesb=(RadioButton)findViewById(R.id.aboutMovie);
        noneb=(RadioButton)findViewById(R.id.none);

        text12=(TextView)findViewById(R.id.textView5);
        refreshButton=(Button)findViewById(R.id.refreshButton) ;
        sharedpreferences = getSharedPreferences("keyPref", Context.MODE_PRIVATE);
       int y=sharedpreferences.getInt("keys", 0);
        switch(y){
            case 1: jokesb.setChecked(true);
                break;
            case 2: funFactsb.setChecked(true);
                break;
            case 3: newsb.setChecked(true);
                break;
            case 4: aboutMoviesb.setChecked(true);
                break;
             default: noneb.setChecked(true);
                break;

        }
        text12.setText(Integer.toString(y));



        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroup.getCheckedRadioButtonId()==R.id.jokes){
                    key=1;
                }
                else if(radioGroup.getCheckedRadioButtonId()==R.id.funFacts)
                {
                    key=2;
                }
                else if(radioGroup.getCheckedRadioButtonId()==R.id.news)
                {
                    key=3;
                }
                else if(radioGroup.getCheckedRadioButtonId()==R.id.aboutMovie)
                {
                    key=4;
                }
                else if(radioGroup.getCheckedRadioButtonId()==R.id.none)
                {
                    key=5;
                }


                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove("keys");
                editor.putInt("keys", key);
                editor.commit();
                int y=sharedpreferences.getInt("keys", 0);
                text12.setText(Integer.toString(y));
            }
        });
}


}

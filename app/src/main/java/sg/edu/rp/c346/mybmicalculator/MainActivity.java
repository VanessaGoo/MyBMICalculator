package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etweight;
    EditText etheight;
    Button btnCal;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etweight = findViewById(R.id.editTextWeight);
        etheight = findViewById(R.id.editTextHeight);
        btnCal = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tv = findViewById(R.id.textView);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etheight.setText("");
                etweight.setText("");
                tvDate.setText("Last calculated Date:");
                tvBMI.setText("Last calculated BMI:");
                tv.setText("");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();
            }
        });
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float weight = Float.parseFloat(etweight.getText().toString());
                float height =Float.parseFloat(etheight.getText().toString());
                Float BMI=(weight/(height*height));
                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.setText("Last calculated Date: " + datetime );
                tvBMI.setText("Last calculated BMI: "+String.format("%.3f",BMI));
                if (BMI<18.5){
                    tv.setText("You are underweight");
                }else if (BMI<25){
                    tv.setText("Your BMI is normal");
                }else if (BMI<29.9){
                    tv.setText("You are overweight");
                }else if (BMI>=30){
                    tv.setText("You are obese");
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (!etweight.getText().toString().isEmpty()) {
            float weight = Float.parseFloat(etweight.getText().toString());
            float height =Float.parseFloat(etheight.getText().toString());
            Float BMI=(weight/(height*height));
            Calendar now = Calendar.getInstance();
            String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                    (now.get(Calendar.MONTH)+1) + "/" +
                    now.get(Calendar.YEAR) + " " +
                    now.get(Calendar.HOUR_OF_DAY) + ":" +
                    now.get(Calendar.MINUTE);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefEdit = prefs.edit();
            prefEdit.putFloat("BMI",BMI);
            prefEdit.putString("datetime",datetime);
            prefEdit.commit();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        float latestBMI =prefs.getFloat("BMI",0.0f);
        String lastestDate=prefs.getString("datetime","");
        tvDate.setText("Last calculated Date: " + lastestDate);
        tvBMI.setText("Last calculated BMI: " + latestBMI);
    }
}
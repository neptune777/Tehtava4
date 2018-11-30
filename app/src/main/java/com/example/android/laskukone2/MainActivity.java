package com.example.android.laskukone2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.laskukone2.data.CalculationContract;
import com.example.android.laskukone2.data.CalculationDbHelper;
import com.example.android.laskukone2.data.TestUtil;

import java.util.ArrayList;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    private Button plusbutton;
    private Button minusbutton;
    private Button multiplybutton;
    private Button divisionbutton;
    private Button tyhjennakaikkiButton;
    private Button naytalogiButton;
    private Button tyhjennalogiButton;

    private EditText plusValue1;
    private EditText plusValue2;
    private EditText plusResult;

    private EditText minusValue1;
    private EditText minusValue2;
    private EditText minusResult;

    private EditText multiplyValue1;
    private EditText multiplyValue2;
    private EditText multiplyResult;

    private EditText divisionValue1;
    private EditText divisionValue2;
    private EditText divisionResult;


    private SQLiteDatabase mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plusbutton = (Button) findViewById(R.id.button);
        minusbutton = (Button) findViewById(R.id.button2);
        multiplybutton = (Button) findViewById(R.id.button3);
        divisionbutton = (Button) findViewById(R.id.button4);
        tyhjennakaikkiButton = (Button) findViewById(R.id.button5);
        naytalogiButton = (Button) findViewById(R.id.button6);
        tyhjennalogiButton = (Button) findViewById(R.id.button7);



        plusbutton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                plus();
            }
         });
        minusbutton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                minus();
            }
        });
        multiplybutton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                multiply();
            }
        });
        divisionbutton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                division();
            }
        });

        tyhjennakaikkiButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tyhjennaKaikki();
            }
        });
        naytalogiButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                naytaLogi();
            }
        });

        tyhjennalogiButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                emptyLog();
            }
        });

        plusValue1 = (EditText) findViewById(R.id.editText27);
        plusValue2 = (EditText) findViewById(R.id.editText3);
        plusResult = (EditText) findViewById(R.id.editText9);

        minusValue1 = (EditText) findViewById(R.id.editText28);
        minusValue2 = (EditText) findViewById(R.id.editText4);
        minusResult = (EditText) findViewById(R.id.editText10);

        multiplyValue1 = (EditText) findViewById(R.id.editText29);
        multiplyValue2 = (EditText) findViewById(R.id.editText5);
        multiplyResult = (EditText) findViewById(R.id.editText11);

        divisionValue1 = (EditText) findViewById(R.id.editText31);
        divisionValue2 = (EditText) findViewById(R.id.editText8);
        divisionResult = (EditText) findViewById(R.id.editText12);
        CalculationDbHelper dbHelper = new CalculationDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        emptyLog();

    }


    public void plus(){

        double value1;
        double value2;

        if(plusValue1.getText().length()==0 && plusValue2.getText().length()==0){
           return;
        }

        if(plusValue1.getText().length()!=0) {
            value1 = Double.parseDouble(String.valueOf(plusValue1.getText()));
        }else{
            value1 = 0;
        }
        if(plusValue2.getText().length()!=0) {
            value2 = Double.parseDouble(String.valueOf(plusValue2.getText()));
        }else{
            value2 = 0;
        }

            double result = value1 + value2;
            int S1 = getDigits(value1);
            int S2 = getDigits(value2);

            int returnVal = Double.compare(S1, S2);

            int min;
            int max;
            if(returnVal==0){
                min = (int) S1;
                max = (int) S2;
            }else if(returnVal<0){
                min = (int) S1;
                max = (int) S2;
            }else{
                min = (int) S2;
                max = (int) S1;
            }


            String formattedValue1 = String.format(Locale.US, "%."+S1+"f", value1);
            String formattedValue2 = String.format(Locale.US, "%."+S2+"f", value2);

            String  formattedValue = String.format(Locale.US, "%."+max+"f", result);

            String lasku = formattedValue1 + " + " + formattedValue2 + " = " + formattedValue;

            addNewCalculation(lasku);

            plusResult.setText("" + formattedValue);



    }

    public void minus(){



        double value1;
        double value2;

        if(minusValue1.getText().length()==0 && minusValue2.getText().length()==0){
            return;
        }

        if(minusValue1.getText().length()!=0) {
            value1 = Double.parseDouble(minusValue1.getText().toString());
        }else{
            value1 = 0;
        }
        if(minusValue2.getText().length()!=0) {
            value2 = Double.parseDouble(minusValue2.getText().toString());
        }else{
            value2 = 0;
        }
        double result = value1 - value2;

        int S1 = getDigits(value1);
        int S2 = getDigits(value2);
        int returnVal = Double.compare(S1, S2);

        int min;
        int max;
        if(returnVal==0){
            min = (int) S1;
            max = (int) S2;
        }else if(returnVal<0){
            min = (int) S1;
            max = (int) S2;
        }else{
            min = (int) S2;
            max = (int) S1;
        }


        String formattedValue1 = String.format(Locale.US, "%."+S1+"f", value1);
        String formattedValue2 = String.format(Locale.US, "%."+S2+"f", value2);

        String  formattedValue = String.format(Locale.US, "%."+max+"f", result);

        String lasku = formattedValue1 + " - " + formattedValue2 + " = " + formattedValue;
        addNewCalculation(lasku);

        minusResult.setText(""+formattedValue);
    }

    public void multiply(){

        double value1;
        double value2;

        if(multiplyValue1.getText().length()==0 && multiplyValue2.getText().length()==0){
            return;
        }

        if(multiplyValue1.getText().length()!=0) {
            value1 = Double.parseDouble(multiplyValue1.getText().toString());
        }else{
            value1 = 0;
        }
        if(multiplyValue2.getText().length()!=0) {
            value2 = Double.parseDouble(multiplyValue2.getText().toString());
        }else{
            value2 = 0;
        }
        double result = value1 * value2;

        int S1 = getDigits(value1);
        int S2 = getDigits(value2);
        int returnVal = Double.compare(S1, S2);

        int min;
        int max;
        if(returnVal==0){
            min = (int) S1;
            max = (int) S2;
        }else if(returnVal<0){
            min = (int) S1;
            max = (int) S2;
        }else{
            min = (int) S2;
            max = (int) S1;
        }


        String formattedValue1 = String.format(Locale.US, "%."+S1+"f", value1);
        String formattedValue2 = String.format(Locale.US, "%."+S2+"f", value2);

        String  formattedValue = String.format(Locale.US, "%."+max+"f", result);

        String lasku = formattedValue1 + " * " + formattedValue2 + " = " + formattedValue;
        addNewCalculation(lasku);

        multiplyResult.setText(""+formattedValue);
    }

    public void division(){

        double value1;
        double value2;

        if(divisionValue1.getText().length()==0 && divisionValue2.getText().length()==0){
            return;
        }

        if(divisionValue1.getText().length()!=0) {
            value1 = Double.parseDouble(divisionValue1.getText().toString());
        }else{
            value1 = 0;
        }
        if(divisionValue2.getText().length()!=0) {
            value2 = Double.parseDouble(divisionValue2.getText().toString());
        }else{
            value2 = 0;
        }

        int S1 = getDigits(value1);
        int S2 = getDigits(value2);
        int returnVal = Double.compare(S1, S2);


        int min;
        int max;

        if(value2!=0) {

            double result = value1 / value2;


            if(returnVal==0){
                min = (int) S1;
                max = (int) S2;
            }else if(returnVal<0){
                min = (int) S1;
                max = (int) S2;
            }else{
                min = (int) S2;
                max = (int) S1;
            }
            String  formattedValue;
            if(max==0){
                  formattedValue = String.format(Locale.US, "%."+1+"f", result);
            }else{
                  formattedValue = String.format(Locale.US, "%."+max+"f", result);
            }

            String formattedValue1 = String.format(Locale.US, "%."+S1+"f", value1);
            String formattedValue2 = String.format(Locale.US, "%."+S2+"f", value2);



            String lasku = formattedValue1 + " / " + formattedValue2 + " = " + formattedValue;
            addNewCalculation(lasku);

            divisionResult.setText(formattedValue);
        }
        else {
            String formattedValue1 = String.format(Locale.US, "%."+S1+"f", value1);
            String formattedValue2 = String.format(Locale.US, "%."+S2+"f", value2);
            String lasku = formattedValue1 + " / " + formattedValue2 + " = " + "Inf";
            addNewCalculation(lasku);

            divisionResult.setText("Inf");
        }
    }

    public void tyhjennaKaikki(){

        plusValue1.setText("");
        plusValue2.setText("");
        plusResult.setText("");

        minusValue1.setText("");
        minusValue2.setText("");
        minusResult.setText("");

        multiplyValue1.setText("");
        multiplyValue2.setText("");
        multiplyResult.setText("");

        divisionValue1.setText("");
        divisionValue2.setText("");
        divisionResult.setText("");


    }

    public void naytaLogi(){

        Context context = MainActivity.this;

        /* This is the class that we want to start (and open) when the button is clicked. */
        Class destinationActivity = ChildActivity.class;

        /*
         * Here, we create the Intent that will start the Activity we specified above in
         * the destinationActivity variable. The constructor for an Intent also requires a
         * context, which we stored in the variable named "context".
         */
        Intent startChildActivityIntent = new Intent(context, destinationActivity);



        startChildActivityIntent.putExtra(Intent.EXTRA_TEXT,"");
        /*
         * Once the Intent has been created, we can use Activity's method, "startActivity"
         * to start the ChildActivity.
         */
        startActivity(startChildActivityIntent);

    }


    int getDigits(double num){

        char zero = '0';
        String text = Double.toString(Math.abs(num));
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;

        if(lastDigit(num)==zero && decimalPlaces==1){
            return 0;
        }

        return decimalPlaces;
    }

    char lastDigit(double num){

        String n = "" + num;
        int length = n.length();
        char lastDigit = n.charAt(length-1);

       return lastDigit;
    }

    private long addNewCalculation(String calculation) {

        ContentValues cv = new ContentValues();
        cv.put(CalculationContract.CalculationEntry.COLUMN_CALCULATION, calculation);

        try {
            return mDb.insert(CalculationContract.CalculationEntry.TABLE_NAME, null, cv);
        }catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private boolean emptyLog(){

        try {
            int calculations = mDb.query(
                    CalculationContract.CalculationEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            ).getCount();

            return mDb.delete(CalculationContract.CalculationEntry.TABLE_NAME,
                    CalculationContract.CalculationEntry._ID + ">" + -1, null) > calculations - 1;
        }catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

}

//Bonilla, Steven's Button coding challenge for software intern, Summer 2017

//I made my challenge into the form of an app with three test cases!

package com.mobile.buttonarrayproblem;
import android.content.Context;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.text.InputFilter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getName();

    //result structure is used to avoid copying over elements
    public ArrayList<Object> result = new ArrayList<Object>();
    String inputToFlatten = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize buttons
        final EditText testOne   =  (EditText) findViewById(R.id.editTextInput);
        final Button enterButton  =  (Button) findViewById(R.id.enter_input);
        Button flatten   =  (Button) findViewById(R.id.result);

        //creating my objects
        MyOnClickListener t1 = new MyOnClickListener(MainActivity.this,"test1");
        MyOnClickListener t2 = new MyOnClickListener(MainActivity.this,"test2");
        MyOnClickListener t3 = new MyOnClickListener(MainActivity.this,"test3");
        MyOnClickListener res = new MyOnClickListener(MainActivity.this,"flatten");

        //filter invalid input
        testOne.setFilters(new InputFilter[] { new InputFilter() {
            @Override
            public CharSequence filter(final CharSequence source, final int start, final int end, final Spanned dest, final int dStart, final int dEnd) {
                for (int i = start; i < end; i++) {
                    if (!(source.charAt(i) == '[') && !(source.charAt(i) == ']') && !(source.charAt(i) == ' ')
                            && !(source.charAt(i) == ',')
                            && !Character.isLetterOrDigit(source.charAt(i)) )
                    {
                        Toast.makeText(MainActivity.this, "Invalid input.", Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }
        } });



        //buttons listen for clicks
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //Log.d(TAG,"you clicked Enter");

                //next 4 lines of code close the keyboard
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                String userInput = testOne.getText().toString();
                testOne.setText("");
                Toast.makeText(MainActivity.this, userInput + " entered.", Toast.LENGTH_SHORT).show();

                Parse ps = new Parse(userInput);
                Boolean result = ps.isBalanced();
                if (ps.isBalanced())
                {
                    inputToFlatten = userInput;
                }
                else
                {
                    Toast.makeText(MainActivity.this, "But brackets unbalanced.", Toast.LENGTH_SHORT).show();
                }

                //I can just read, parse, process here.
                //the "flatten" button can just print

                //Toast.makeText(MainActivity.this, userInput, Toast.LENGTH_SHORT).show();
            }
        });

        //testTwo.setOnClickListener(t2);
        //testThree.setOnClickListener(t3);
        flatten.setOnClickListener(res);

        //initialize ImageView
        ImageView logo   = (ImageView) findViewById(R.id.logo);

    }

    //my subclass that handles the structure-flattening
    public class MyOnClickListener implements View.OnClickListener
    {
        //variables for Toast
        Context context;
        String  action;

        //global data structure variables
        Integer ARR_SIZE5 = 5;
        Integer ARR_SIZE2 = 2;
        Integer ARR_SIZE1 = 1;

        Object[] arr = new Object[ARR_SIZE5];
        Object[] subarr1 = new Object[ARR_SIZE2];
        Object[] subarr2 = new Object[ARR_SIZE1];
        Object[] arr3 = new Object[ARR_SIZE5];

        MyOnClickListener(Context context, String action)
        {
            this.context = context;
            this.action = action;
        }

        MyOnClickListener(Context context, String action, ArrayList<Object> flat)
        {
            this.context = context;
            this.action = action;
            //this.result = flat;
        }

        @Override
        public void onClick(View v)
        {
            performAction();
        }

        public void performAction()
        {
            //TO DO: make Keyboard close when user clicks "ENTER"
            if (action.equals("flatten"))
            {
                if (inputToFlatten == null) {
                    Toast.makeText(context, "Enter an array first", Toast.LENGTH_SHORT).show();
                }
                //print result and "reset" user's input to null + array
                else {
                    FlattenString fs = new FlattenString(inputToFlatten);
                    Toast.makeText(context, fs.getResultList().toString(), Toast.LENGTH_LONG).show();
                    fs.clearResultList();
                    inputToFlatten = null;
                }
            }
        }

        /*
        [[[subarray2],null],null,null,null,null] -> [subarray2]
        */
        public Object[] tripleDimensionTest(Object[] array1, Object[] array2, Object[] array3)
        {
            array3[0] = "3dimension!";
            array2[0] = subarr2;
            array1[0] = subarr1;
            result.clear();
            return(array1);
        }

        /*
         * [Is, this, null, convincing, ?] -> [Is, this, convincing, ?]
         */
        public Object[] withNullTest(Object[] array1)
        {
            array1[0] = "Is";
            array1[1] = "this";
            array1[2] = null;
            array1[3] = "convincing";
            array1[4] = "?";
            result.clear();
            return array1;
        }

        /*
         * [[this, is], convincing, integer, 42, null] -> [this, is, convincing, integer, 42]
         */
        public Object[] nestedTest(Object[] array1, Object[] array2)
        {
            //populate subarray
            array2[0] = "this";
            array2[1] = "is";

            //populate outer array
            array1[0] = array2;
            array1[1] = "convincing.";
            array1[2] = "integer";
            array1[3] = 42;

            result.clear();

            return array1;
        }


        public void flattenStructure(Object[] array) throws IllegalArgumentException
        {
            int counter = 0;
            //quit function when given null array
            if (array == null)
            {
                System.out.println("This array is null");
                return;
            }

            while(counter<array.length)
            {

                //effectively excludes null elements from result
                if(array[counter] == null)
                {
                    counter++;
                }
                //
                else if(array[counter].getClass().isArray())
                {
                    //printArr((Object[]) array[counter]);
                    flattenStructure((Object[])array[counter]);
                    counter++;
                }

                else //current element is not an array
                {
                    result.add(array[counter]);
                    counter++;
                }
            }
        }

        //prints the result ArrayList. Use this after flattening structure
        public String printResult()
        {
            return result.toString();
        }
    }

}

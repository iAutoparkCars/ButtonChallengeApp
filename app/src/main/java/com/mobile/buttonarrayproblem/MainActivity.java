package com.mobile.buttonarrayproblem;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.mobile.buttonarrayproblem.R.id.test1;

public class MainActivity extends AppCompatActivity
{
    //result structure is used to avoid copying over elements
    public ArrayList<Object> result = new ArrayList<Object>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //activity_main set as layout. Write your code here

        //initialize buttons
        Button testOne   =  (Button) findViewById(R.id.test1);
        Button testTwo   =  (Button) findViewById(R.id.test2);
        Button testThree =  (Button) findViewById(R.id.test3);
        Button flatten   =  (Button) findViewById(R.id.result);

        //creating my objects
        MyOnClickListener t1 = new MyOnClickListener(MainActivity.this,"test1");
        MyOnClickListener t2 = new MyOnClickListener(MainActivity.this,"test2");
        MyOnClickListener t3 = new MyOnClickListener(MainActivity.this,"test3");
        MyOnClickListener res = new MyOnClickListener(MainActivity.this,"flatten");


        //buttons listen for clicks
        testOne.setOnClickListener(t1);
        testTwo.setOnClickListener(t2);
        testThree.setOnClickListener(t3);
        flatten.setOnClickListener(res);

        //initialize ImageView
        ImageView logo   = (ImageView) findViewById(R.id.logo);

    }

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

            if (action.equals("test1"))
            {
                flattenStructure(tripleDimensionTest(arr,subarr1,subarr2));

                String resultMsg = "Structure: [[[3dimension!],null],null,null,null,null]";
                Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show();
            }

            if (action.equals("test2"))
            {
                flattenStructure(withNullTest(arr));

                String resultMsg = "Structure: [Is, this, null, convincing, ?]";
                Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show();
            }

            if (action.equals("test3"))
            {
                flattenStructure(nestedTest(arr3,subarr1));
                String resultMsg = "Structure: [[this, is], convincing, integer, 42, null]";

                //String resultMsg = "Structure: [[this, is], convincing, integer, 42, null]";
                Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show();
            }

            if (action.equals("flatten"))
            {
                String resultMsg = printResult();
                Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show();
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

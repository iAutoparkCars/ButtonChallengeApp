package com.mobile.buttonarrayproblem;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import static android.R.id.message;

/**
 * Created by Steven on 4/5/2017.
 */

public class MyOnClickListener implements View.OnClickListener
{
    //variables for Toast
    Context context;
    String  action;

    //global data structure variables
    static Integer ARR_SIZE5 = 5;
    static Integer ARR_SIZE2 = 2;
    static Integer ARR_SIZE1 = 1;

    static Object[] arr = new Object[ARR_SIZE5];
    static Object[] subarr1 = new Object[ARR_SIZE2];
    static Object[] subarr2 = new Object[ARR_SIZE1];
    static Object[] arr3 = new Object[ARR_SIZE5];

    //result structure is used to avoid copying over elements
    public static ArrayList<Object> result = new ArrayList<Object>();

    MyOnClickListener(Context context, String action)
    {
        this.context = context;
        this.action = action;
    }

    MyOnClickListener(Context context, String action, ArrayList<Object> flat)
    {
        this.context = context;
        this.action = action;
        this.result = flat;
    }

    @Override
    public void onClick(View v)
    {
       performAction();
    }

    public void performAction()
    {
        
        Object[] input = null;
        if (action.equals("test1"))
        {
            input = tripleDimensionTest(arr,subarr1,subarr2);

            String resultMsg = "Structure: [[[subarray2],null],null,null,null,null]";
            Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show();
        }

        if (action.equals("test2"))
        {
            input = withNullTest(arr);

            String resultMsg = "Structure: [Is, this, null, convincing, ?]";
            Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show();
        }

        if (action.equals("test3"))
        {
            input = nestedTest(arr3,subarr1);
            String resultMsg = "Structure: [[this, is], convincing, integer, 42, null]";

            //String resultMsg = "Structure: [[this, is], convincing, integer, 42, null]";
            Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show();
        }

        if (action.equals("flatten"))
        {
            flattenStructure(input);
            String resultMsg = printResult();
            Toast.makeText(context, resultMsg, Toast.LENGTH_SHORT).show();
        }
    }

    /*
    [[[subarray2],null],null,null,null,null] -> [subarray2]
    */
    public static Object[] tripleDimensionTest(Object[] array1, Object[] array2, Object[] array3)
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
    public static Object[] withNullTest(Object[] array1)
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
    public static Object[] nestedTest(Object[] array1, Object[] array2)
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


    public static void flattenStructure(Object[] array) throws IllegalArgumentException
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
    public static String printResult()
    {
        return result.toString();
    }
}

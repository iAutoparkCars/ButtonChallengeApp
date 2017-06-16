package com.mobile.buttonarrayproblem;

/**
 * Created by Steven on 6/15/2017.
 */

import java.util.ArrayList;
import java.util.Stack;

public class FlattenString
{
    String structure;
    CharSequence chars;

    public FlattenString(String s)
    {
        this.structure = s;
        flatten(s);
        this.chars = s;
    }

    //datastr to store result and temp steps
    ArrayList<String> resultList = new ArrayList<String>();
    ArrayList<String> tempList = new ArrayList<String>();

    private void flatten(String str)
    {
        //base case
        Boolean strHasNumbers =  numbersAreInside(str);
        if (!strHasNumbers)
            return;

        str = str.trim();

        if (strHasNumbers && !bracketsAndCommaInside(str))
        {
            //System.out.println(stripNonDigits(str));
            resultList.add(stripNonDigits(str));
            //System.out.println(resultList);
        }
        else if (strHasNumbers && bracketsAndCommaInside(str))
        {
            System.out.println("true");

            //remove first and last brackets
            StringBuilder sb = new StringBuilder(str);
            sb.setCharAt(0, ' ');

            int removeInd = 0;
            if (sb.charAt(sb.length()-1)==']')
                sb.setCharAt(sb.length()-1, ' ');
            else if (sb.charAt(sb.length()-2)==']')
            {
                sb.setCharAt(sb.length()-1, ' ');
                sb.setCharAt(sb.length()-2, ' ');
            }

            str = sb.toString();

            //create array of substrings
            Stack<String> stack = new Stack<String>();
            String[] array = str.split(",");
            Boolean keepAdding = true;
            int counter = 0;

            for (int i = 0; i < array.length; i++)
            {
                Boolean hasNumbers = numbersAreInside(array[i]);
                Boolean hasBracketsOrComma = bracketsOrCommaInside(array[i]);


                if (hasNumbers && !hasBracketsOrComma)
                {
                    //System.out.println("adding to result : " + array[i] + " ");
                    resultList.add(array[i]);
                }
                if (array[i].contains("[") && keepAdding)
                {
                    //System.out.println(array[i]);
                    //tempList.add(array[i]);
                    counter++;
                    keepAdding = true;
                }
                else if (array[i].contains("]") && keepAdding)
                {
                    StringBuilder build = new StringBuilder();
                    //System.out.println(counter);

                    for (int j = counter; j >= 0; j--)
                    {
                        //tempList.add(array[i-j]);
                        //System.out.println(i + " " + j);
                        build.append(array[i-j]);
                        build.append(", ");

                    }
                    //System.out.println(build.toString());
                    counter = 0;
                    keepAdding = false;
                    flatten(build.toString());
                }

            }

            //List<String> a = Arrays.asList(array);
            //System.out.println(resultList);
        }
    }

    public ArrayList<String> getResultList()
    {
        return resultList;
    }

    public void clearResultList()
    {
        resultList.clear();
    }

    public Boolean openBracketInside(String str)
    {
        return str.contains("[");
    }

    public Boolean bracketsOrCommaInside(String str)
    {
        Boolean result = false;

        if (str.contains("[") || str.contains("]") || str.contains(","))
        {
            result = true;
        }

        return result;
    }

    public Boolean bracketsAndCommaInside(String str)
    {
        Boolean result = false;

        if (str.contains("[") && str.contains("]") && str.contains(","))
        {
            result = true;
        }

        return result;
    }

    public Boolean numbersAreInside(String str)
    {
        Boolean result = false;
        for (char c : str.toCharArray())
        {
            if (Character.isDigit(c))
                result = true;
            //result = (Character.isDigit(c))? true : false;
        }
        return result;
    }

    public String stripNonDigits(String arg)
    {
        StringBuilder sb = new StringBuilder(arg.length());

        for (int i = 0; i < arg.length(); i++)
        {
            char c = arg.charAt(i);
            if(c > 47 && c < 58)
            {
                sb.append(c);
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public String getString()
    {
        return structure;
    }


}

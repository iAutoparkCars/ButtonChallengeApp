package com.mobile.buttonarrayproblem;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Steven on 6/13/2017.
 */

public class Parse
{
    String input;

    Parse(String input)
    {
        this.input = input;
    }

    public Boolean isBalanced()
    {
        String bracket = input;
        Boolean result = true;

        //create stack and convert to char array
        char[] array = bracket.toCharArray();
        ArrayList<Character> brackList = new ArrayList<Character>();

        //add only brackets to new array
        for (int i = 0; i < array.length; i++)
        {
            if(isClosed(array[i]) || isOpen(array[i]))
            {
                brackList.add(array[i]);
            }
        }

        char[] brackArray = new char[brackList.size()];
        int j = 0;
        for (Character ch : brackList)
        {
            brackArray[j] = ch;
            j++;
        }
        j = 0;

        if(brackArray.length%2 != 0)
        {
            //System.out.println("NO");
            result = false;
            return result;
        }

        result = useStack(brackArray);
        return result;
    }

    public Boolean useStack(char[] array)
    {
        Boolean result;
        Stack<Character> stack = new Stack();
        for (int i = 0; i< array.length; i++)
        {
            Character ch = array[i];
            if (isOpen(ch))
            {
                stack.push(ch);
            }
            else if (isClosed(ch) && stack.isEmpty())
            {
                //System.out.println("NO");
                result = false;
                return result;
            }
            else if(isClosed(ch))
            {
                if (isMatched(stack.peek(),ch))
                {
                    stack.pop();
                }
            }

        }
        if (stack.isEmpty())
        {           //System.out.println("YES");
            result = true;
            return result;}
        else
        {
            System.out.println("NO");
            result = false;
            return result;
        }

        //while(!stack.isEmpty())
        //{System.out.println(stack.pop());}
    }

    public Boolean isOpen(char ch)
    {
        return (ch=='{' || ch=='[' || ch=='(');
    }

    public Boolean isClosed(char ch)
    {
        return (ch=='}' || ch==']' || ch==')');
    }

    public Boolean isMatched(char open, char close)
    {
        Boolean result = false;
        if (open=='{' && close=='}')
        {result = true;}
        else if (open=='(' && close==')')
        {result = true;}
        else if (open=='[' && close==']')
        {result = true;  }
        return result;
    }
}

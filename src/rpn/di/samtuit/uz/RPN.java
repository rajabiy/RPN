package rpn.di.samtuit.uz;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amin
 */
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by amin on 08.06.17.
 */
public class RPN {
    private final Deque<String> primary_stack;
    private final Deque<String> secondary_stack;

    private final String exp;

    public RPN(String exp) {
        primary_stack = new LinkedList<>();
        secondary_stack = new LinkedList<>();
        this.exp = exp;
        generateRPN();
    }

    private void generateRPN()
    {
        int priority;

        for (int i=0; i<exp.length()-1;i++)
        {
            String current_char = exp.substring(i,i+1);
            priority = getPriority(current_char);

            if (priority==0)
            {
                int j=0;
                while (exp.charAt(i+j)>='0' &&  exp.charAt(i+j)<='9')
                {
                    j++;
                }                     
                primary_stack.addLast(exp.substring(i,i+j+1));
                i=i+j;
                System.out.println("i="+i);                
            }
            else if (priority==2 || priority==3)
            {    
                if (!secondary_stack.isEmpty())
                    if (getPriority(secondary_stack.getLast())>=priority)
                    {
                        primary_stack.addLast(secondary_stack.pollLast());
                    }
                secondary_stack.addLast(current_char); 

                    
            }
            else if (priority==1)
            {    
                if (")".equals(current_char))
                {
                    while (!("(".equals(secondary_stack.getLast())))
                    {
                        primary_stack.addLast(secondary_stack.pollLast());
                    }
                    secondary_stack.pollLast();
                }
                else secondary_stack.addLast(current_char);
            }            

        }

        while (!secondary_stack.isEmpty())
            primary_stack.addLast(secondary_stack.pollLast());

    }

    public String textRPN()
    {
        String text=new String();
        while (!primary_stack.isEmpty())
            text = text + "," + primary_stack.pollFirst();

        return text;
    }

    private int getPriority(String operator)
    {
        if ("-+".contains(operator))
        {
            return 3;
        }
        else if ("*/".contains(operator))
        {
            return 2;
        }
        else if ("()".contains(operator))
        {
            return 1;
        }

        return 0;

    }
}
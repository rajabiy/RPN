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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

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

        for (int i=0; i<=exp.length()-1; i++)
        {
            String current_char;
            current_char = exp.substring(i,i+1);
            
            priority = getPriority(current_char);
            
            switch (priority) {
                case 0:
                    int j=0;

                    for(int k=0; i+k<=exp.length()-1; k++)
                    {
                        if (exp.charAt(i+k)>='0' &&  exp.charAt(i+k)<='9')
                        {
                            j=k;
                        }
                        else
                            k=exp.length();
                    }   primary_stack.addLast(exp.substring(i,i+j+1));
                    i=i+j;
                    break;
                case 2:
                case 3:
                    if (!secondary_stack.isEmpty())
                        if (getPriority(secondary_stack.getLast())>=priority)
                        {
                            primary_stack.addLast(secondary_stack.pollLast());
                        }
                    secondary_stack.addLast(current_char);
                    break;
                case 1:
                    if (")".equals(current_char))
                    {
                        while (!("(".equals(secondary_stack.getLast())))
                        {
                            primary_stack.addLast(secondary_stack.pollLast());
                        }
                        secondary_stack.pollLast();
                    }
                    else secondary_stack.addLast(current_char);
                    break;
                default:
                    break;
            }

        }

        while (!secondary_stack.isEmpty())
            primary_stack.addLast(secondary_stack.pollLast());
    }

    public String textRPN()
    {  
        String text = new String();
        Iterator itr;
        itr = primary_stack.iterator();
        
        while (itr.hasNext())
        {
            text += itr.next() + " ";
        }
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
    
    public Double calcRPN()
    {
        Double value;
        Stack<Double> stack;
        stack = new Stack<>();
        
        while (!primary_stack.isEmpty())
        {
            String operator;
            Double b;
            Double a;
            if ("+-*/".contains(primary_stack.getFirst()))
            {
                if (!primary_stack.isEmpty())
                {
                    operator = primary_stack.pollFirst();
                    b = stack.pop();
                    a = stack.pop();
                    if ("+".contains(operator))
                        stack.push(a + b);
                    else if ("-".contains(operator))
                        stack.push(a - b);
                    else if ("*".contains(operator))
                        stack.push(a * b);  
                    else if ("/".contains(operator))
                        stack.push(a / b); 
                }
            }
            else
            {
                stack.push(Double.parseDouble(primary_stack.pollFirst()));
            }
        }
        return stack.pop();
    }
 
}
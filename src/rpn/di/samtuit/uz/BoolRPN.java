/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpn.di.samtuit.uz;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author amin
 */
public class BoolRPN{
    private final Deque<String> primary_stack;
    private final Deque<String> secondary_stack;

    private final String exp;    

    public BoolRPN(String exp) {
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
                    if ("T1".contains(current_char.toUpperCase()))
                        primary_stack.addLast("True");
                    else
                        primary_stack.addLast("False");
                    break;
                case 2:
                    secondary_stack.addLast(current_char);
                    break;                    
                case 4:
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
    
    private int getPriority(String operator)
    {
        if ("~".contains(operator))
        {
            return 2;
        }
        else if ("|".contains(operator))
        {
            return 4;
        }
        else if ("&".contains(operator))
        {
            return 3;
        }
        else if ("()".contains(operator))
        {
            return 1;
        }

        return 0;
    }
    
    public Boolean calcRPN()
    {
        System.out.println(primary_stack);
        Stack<Boolean> stack;
        stack = new Stack<>();
        
        while (!primary_stack.isEmpty())
        {
            String operator;
            Boolean b;
            Boolean a;
            if ("&|~".contains(primary_stack.getFirst()))
            {
                if (!primary_stack.isEmpty())
                {
                    operator = primary_stack.pollFirst();
                    if ("&".contains(operator))
                        stack.push(stack.pop() && stack.pop());
                    else if ("|".contains(operator))
                        stack.push(stack.pop() || stack.pop());
                    else if ("~".contains(operator))
                        stack.push(!stack.pop());                  
                }
            }
            else
            {
                stack.push(Boolean.parseBoolean(primary_stack.pollFirst()));
            }
            
        }
        return stack.pop();
    }  
    
    public static void main(String[] args) {
        BoolRPN rpn = new BoolRPN("~(t|f)&t");
        System.out.println(rpn.calcRPN());
    }
}

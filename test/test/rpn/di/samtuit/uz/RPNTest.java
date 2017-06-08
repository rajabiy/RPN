package test.rpn.di.samtuit.uz;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import rpn.di.samtuit.uz.RPN;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author amin
 */
public class RPNTest {
    
    @Test
    public void textRPNtest()
    {
        RPN rpn = new RPN("12*(2+5)/6");
        assertEquals("", rpn.textRPN());
    }

}

package org.jboss.weld.homework.view;

import java.math.BigInteger;

/**
 * Backing bean for the factorial form (factorial.xhtml)
 */
// TODO: turn this class into a named bean with name "factorial"
public class FactorialModel {

    private Long input;
    private BigInteger result;

    public void compute() {
        // TODO: implement using Factorial bean
    }

    public void reset() {
        this.input = null;
        this.result = null;
    }

    public Long getInput() {
        return input;
    }

    public void setInput(Long input) {
        this.input = input;
    }

    public BigInteger getResult() {
        return result;
    }
}

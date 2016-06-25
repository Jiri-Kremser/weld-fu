package org.jboss.weld.homework.view;

import org.jboss.weld.homework.Factorial;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * Backing bean for the factorial form (factorial.xhtml)
 */
@ApplicationScoped
@Named("factorial")
public class FactorialModel {

    private Long input;
    private BigInteger result;

    @Inject
    private Factorial factorial;

    public void compute() {
        BigInteger result = factorial.compute(getInput());
        this.result = result;
    }

    public void reset() {
        this.input = null;
        this.result = null;
    }

    @NotNull
    @Min(0)
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

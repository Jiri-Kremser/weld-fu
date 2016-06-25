package org.jboss.weld.homework;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.math.BigInteger;

/**
 * Created by jkremser on 6/25/16.
 */
@Model
public class FactorialImpl implements Factorial {

    @Inject
    MathOperations mathOperations;

    @Override
    public BigInteger compute(long number) {
        if (Long.valueOf(0).equals(number)) {
            return BigInteger.ONE;
        }
        BigInteger result = mathOperations.multiplySequence(Long.valueOf(1), number);
        return result;
    }
}

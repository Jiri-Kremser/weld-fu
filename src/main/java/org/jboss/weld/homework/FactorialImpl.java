package org.jboss.weld.homework;

import javax.enterprise.event.Event;
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

    @Inject
    Event<FactorialComputationFinished> finishedComputations;

    @Override
    public BigInteger compute(long number) {
        // todo: interceptor
        if (Long.valueOf(0).equals(number)) {
            return BigInteger.ONE;
        }
        BigInteger result = mathOperations.multiplySequence(Long.valueOf(1), number);

        finishedComputations.fire(new FactorialComputationFinished(number, result));
        return result;
    }
}

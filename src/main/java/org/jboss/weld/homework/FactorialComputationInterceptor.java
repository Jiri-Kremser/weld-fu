package org.jboss.weld.homework;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.math.BigInteger;

/**
 * Created by jkremser on 6/25/16.
 */
@FinishesFactorialComputation @Interceptor @Priority(1)
public class FactorialComputationInterceptor {

    @Inject
    Event<FactorialComputationFinished> finishedComputations;

    @AroundInvoke
    public Object factorialComputation(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        if (parameters.length != 1 || !(parameters[0] instanceof Long)) {
            throw new IllegalStateException("Factorial.compute() should have 1 parameter of type long");
        }
        Object result = ctx.proceed();
        if (result instanceof BigInteger) {
            finishedComputations.fire(new FactorialComputationFinished((long) parameters[0], (BigInteger) result));
        } else {
            throw new IllegalStateException("Factorial.compute() should return the result as BigInteger");
        }
        return result;
    }

}

package org.jboss.weld.homework;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
public class FactorialTest {

    @Inject
    private Factorial factorial;

    @Inject
    @Parallel
    private Factorial parallelImplementation;

    @Inject
    private MathOperations mathOperations;

    @Deployment
    public static Archive<?> getDeployment() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addPackages(true, Factorial.class.getPackage())
                .addClass(EventObserver.class);
    }

    @Test
    public void testSequentialFactorial() {
        testFactorial(factorial);
    }

    @Test
    public void testParallelFactorial() {
        testFactorial(parallelImplementation);
    }

    private void testFactorial(Factorial factorial) {
        assertEquals(BigInteger.ONE, factorial.compute(0));
        assertEquals(BigInteger.ONE, factorial.compute(1));
        assertEquals(BigInteger.valueOf(2), factorial.compute(2));
        assertEquals(BigInteger.valueOf(6), factorial.compute(3));
        assertEquals(BigInteger.valueOf(24), factorial.compute(4));
        assertEquals(BigInteger.valueOf(120), factorial.compute(5));
        assertEquals(BigInteger.valueOf(3628800), factorial.compute(10));
        assertEquals(BigInteger.valueOf(1307674368000l), factorial.compute(15));
    }

    @Test
    public void testMathOperations() {
        try {
            // .get will block and wait for the results
            assertEquals(BigInteger.ONE, mathOperations.parallelFactorial(0).get());
            assertEquals(BigInteger.valueOf(2), mathOperations.parallelFactorial(2).get());
            assertEquals(BigInteger.valueOf(3628800), mathOperations.parallelFactorial(10).get());
            assertEquals(BigInteger.valueOf(60), mathOperations.multiplySequence(3, 5));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testEvent(EventObserver observer) {
        observer.reset();
        factorial.compute(6);
        assertNotNull(observer.getEvent());
        assertNotNull(observer.getEvent().getResult());
        assertEquals(6, observer.getEvent().getNumber());
        assertEquals(BigInteger.valueOf(720), observer.getEvent().getResult());
    }

    @ApplicationScoped
    protected static class EventObserver {

        private FactorialComputationFinished event;

        public FactorialComputationFinished getEvent() {
            return event;
        }

        public void reset() {
            this.event = null;
        }

        public void observe(@Observes FactorialComputationFinished event) {
            this.event = event;
        }
    }
}

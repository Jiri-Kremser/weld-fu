package org.jboss.weld.homework;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.ObjLongConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by jkremser on 6/25/16.
 */
@Model
@Parallel
public class FactorialParallel implements Factorial {
    private static int THRESHOLD = 4;

    @Inject
    MathOperations mathOperations;

    @Override
    @FinishesFactorialComputation
    public BigInteger compute(long number) {
        // note: factorial is associative so it's easily parallelizable, here we use a simple approach using the
        // fork/join framework and parallel streams
        // we calculate the factorial sequentially for n < THRESHOLD and spawning some threads otherwise
        if (Long.valueOf(0).equals(number)) {
            return BigInteger.ONE;
        }
        if (number < THRESHOLD) {
            return mathOperations.multiplySequence(Long.valueOf(1), number);
        }


        Collector<Long, BigIntegerAccumulator, BigInteger> collector =
                Collector.of(BigIntegerAccumulator::new,
                        BigIntegerAccumulator::acc,
                        BigIntegerAccumulator::merge,
                        BigIntegerAccumulator::get,
                        Collector.Characteristics.CONCURRENT,
                        Collector.Characteristics.UNORDERED
                );
        return LongStream.rangeClosed(2, number).mapToObj(Long::valueOf).parallel().collect(collector);
    }


    public static class BigIntegerAccumulator {
        private volatile BigInteger state;

        public BigIntegerAccumulator() {
            this.state = BigInteger.ONE;
        }

        public synchronized void acc(Long l) {
            this.state = state.multiply(BigInteger.valueOf(l));
        }

        public synchronized BigIntegerAccumulator merge(BigIntegerAccumulator acc) {
            this.state = state.multiply(acc.get());
            return this;
        }

        public synchronized BigInteger get() {
            return state;
        }
    }
}

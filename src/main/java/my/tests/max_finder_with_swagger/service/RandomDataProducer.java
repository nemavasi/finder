package my.tests.max_finder_with_swagger.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Component
public class RandomDataProducer implements DataProducer<Integer> {

    private int maxCount;
    private int currentCount;
    private IntStream intStream;

    @Autowired
    public RandomDataProducer(@Value("${randomDataProducer.maxCount}") int maxCount) {
        this.maxCount = maxCount;
        this.currentCount = 0;
        this.intStream = IntStream.generate(() -> (int) (Math.random() * 1000));
    }

    @Override
    public boolean hasNext() {
        return currentCount > maxCount;
    }

    @Override
    public Integer next() {
        if (hasNext()) {
            return intStream.iterator().next();
        } else {
            throw new NoSuchElementException();
        }
    }
}

package my.tests.max_finder_with_swagger.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class RandomDataProducer implements DataProducer<Integer> {

    private int maxCount;

    @Autowired
    public RandomDataProducer(@Value("${randomDataProducer.maxCount}") int maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public Stream<Integer> resolveStream(String urlStore) {
        return IntStream.generate((() -> (int) (Math.random() * 1000))).limit(maxCount).boxed();
    }
}

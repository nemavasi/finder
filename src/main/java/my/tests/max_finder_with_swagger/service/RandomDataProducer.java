package my.tests.max_finder_with_swagger.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RandomDataProducer implements DataProducer<Integer> {

    private int maxCount;
    private int currentCount;

    @Autowired
    public RandomDataProducer(@Value("${randomDataProducer.maxCount}") int maxCount) {
        this.maxCount = maxCount;
        this.currentCount = 0;
    }

    @Override
    public boolean hasNext() {
        return currentCount < maxCount;
    }

    @Override
    public Integer next() {
        if (hasNext()) {
            currentCount++;
            return (int) (Math.random() * 1000);
        } else {
            throw new NoSuchElementException();
        }
    }
}

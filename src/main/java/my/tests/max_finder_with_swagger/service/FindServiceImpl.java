package my.tests.max_finder_with_swagger.service;

import my.tests.max_finder_with_swagger.service.alg.SortedSetOfNMax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class FindServiceImpl implements FindService {

    private final DataProducer<Integer> dataProducer;

    @Autowired
    public FindServiceImpl(DataProducer<Integer> dataProducer) {
        this.dataProducer = dataProducer;
    }

    @Override
    public Integer findNMaxFormFile(String pathToFile, Integer n) {
        validateParameters(pathToFile, n);
        SortedSetOfNMax<Integer> set = new SortedSetOfNMax<>(n, Integer::compare);
        Stream<Integer> stream = dataProducer.resolveStream(pathToFile);
        stream.forEach(set::addItem);
        return set.getResult();
    }

    private static void validateParameters(String pathToFile, Integer n) {
        if (pathToFile == null || pathToFile.length() < 6 || !pathToFile.endsWith(".xlsx")) {
            throw new WrongParameterException("Wrong file name. It must ends with xlsx!");
        }
        if (n == null || n < 1) {
            throw new WrongParameterException("Wrong number!");
        }
    }
}

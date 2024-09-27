package my.tests.max_finder_with_swagger.service;

import my.tests.max_finder_with_swagger.service.alg.SortedSetOfNMax;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service
public class FindServiceImpl implements FindService {

    @Override
    public Integer findNMaxFormFile(String pathToFile, Integer n) {
        validateParameters(pathToFile, n);
        SortedSetOfNMax<Integer> set = new SortedSetOfNMax<>(n, Integer::compare);
        DataProducer<Integer> dataProducer = lookUpDataProducer();
        while (dataProducer.hasNext()) {
            set.addItem(dataProducer.next());
        }
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

    @Lookup
    DataProducer<Integer> lookUpDataProducer(){
        return null;
    }
}

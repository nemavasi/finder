package my.tests.max_finder_with_swagger.service;

import my.tests.max_finder_with_swagger.service.alg.SetOfNMax;

public interface DataProducer<T>  {
    void resolveStream(String urlStore, SetOfNMax<T> set);
}

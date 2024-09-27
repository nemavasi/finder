package my.tests.max_finder_with_swagger.service;

import java.util.stream.Stream;

public interface DataProducer<T>  {
    Stream<T> resolveStream(String urlStore);
}

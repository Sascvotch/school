package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import java.util.stream.Stream;
@Service
public class InfoService {
    public int getIntSum() {
        //  int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );
        int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000).parallel() .reduce(0, (a, b) -> a + b );
        return sum;
    }
}

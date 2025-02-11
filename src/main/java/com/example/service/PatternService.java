package com.example.service;

import com.example.model.Pattern;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PatternService {
    Flux<Pattern> getAllPatterns();
    Mono<Pattern> getPatternById(String id);
    Flux<Pattern> getPatternsByQuadrant(String quadrant);
    Flux<Pattern> getPatternsByRing(String ring);
    Mono<Pattern> createPattern(Pattern pattern);
    Mono<Pattern> updatePattern(String id, Pattern pattern);
    Mono<Boolean> deletePattern(String id);
}

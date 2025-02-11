package com.example.service;

import com.example.model.Pattern;
import com.example.model.Tag;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PatternServiceImpl implements PatternService {
    private final Map<String, Pattern> patterns = new ConcurrentHashMap<>();

    public PatternServiceImpl() {
        // Initialize with sample data
        Tag tag1 = Tag.builder()
                .tagId("architecturedesign")
                .tagName("Architecture Design")
                .tagValue("Microservices Architecture")
                .className("fas fa-cubes")
                .build();

        Tag tag2 = Tag.builder()
                .tagId("platform")
                .tagName("Platform")
                .tagValue("AWS")
                .build();

        Pattern pattern1 = Pattern.builder()
                .id("1")
                .title("ECS with MSK - Rediscache")
                .name("ECS with MSK - Rediscache")
                .url("/solution/ECS with MSK - Rediscache")
                .ring("identify")
                .quadrant("enterprise")
                .status("Moved In")
                .isNew("FALSE")
                .description("High availability and performance for customer applications with ECS, MSK, and Redis")
                .pattern(new ArrayList<>())
                .useCase(new ArrayList<>())
                .tags(List.of(tag1, tag2))
                .build();

        patterns.put(pattern1.getId(), pattern1);
    }

    @Override
    public Flux<Pattern> getAllPatterns() {
        return Flux.fromIterable(patterns.values());
    }

    @Override
    public Mono<Pattern> getPatternById(String id) {
        return Mono.justOrEmpty(patterns.get(id));
    }

    @Override
    public Flux<Pattern> getPatternsByQuadrant(String quadrant) {
        return Flux.fromIterable(patterns.values())
                .filter(pattern -> pattern.getQuadrant().equalsIgnoreCase(quadrant));
    }

    @Override
    public Flux<Pattern> getPatternsByRing(String ring) {
        return Flux.fromIterable(patterns.values())
                .filter(pattern -> pattern.getRing().equalsIgnoreCase(ring));
    }

    @Override
    public Mono<Pattern> createPattern(Pattern pattern) {
        patterns.put(pattern.getId(), pattern);
        return Mono.just(pattern);
    }

    @Override
    public Mono<Pattern> updatePattern(String id, Pattern pattern) {
        if (patterns.containsKey(id)) {
            pattern.setId(id);
            patterns.put(id, pattern);
            return Mono.just(pattern);
        }
        return Mono.empty();
    }

    @Override
    public Mono<Boolean> deletePattern(String id) {
        return Mono.just(patterns.remove(id) != null);
    }
}

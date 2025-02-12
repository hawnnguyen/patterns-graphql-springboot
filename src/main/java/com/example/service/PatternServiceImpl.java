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

        // Data Broker Pattern
        Tag dataBrokerTag1 = Tag.builder()
                .tagId("architecturedesign")
                .tagName("Architecture Design")
                .tagValue("Data Broker")
                .className("fas fa-network-wired")
                .build();

        Tag dataBrokerTag2 = Tag.builder()
                .tagId("architecturedesign")
                .tagName("Architecture Design")
                .tagValue("Event Driven Architecture")
                .className("fa-broadcast-tower")
                .build();

        Pattern pattern2 = Pattern.builder()
                .id("15")
                .title("Data Broker for SaaS, AWS, and On-Premises Systems")
                .name("Data Broker for SaaS, AWS, and On-Premises Systems")
                .url("/solution/Data Broker for SaaS, AWS, and On-Premises Systems")
                .ring("identify")
                .quadrant("data")
                .status("Moved In")
                .isNew("FALSE")
                .description("Scalable broker system for connecting SaaS, AWS, and on-premises systems.")
                .pattern(new ArrayList<>())
                .useCase(List.of("Third-Party Data Connections and Centralized Broker Solutions"))
                .tags(List.of(dataBrokerTag1, dataBrokerTag2))
                .build();

        // External API Client Pattern
        Tag apiClientTag1 = Tag.builder()
                .tagId("platform")
                .tagName("Platform")
                .tagValue("AWS")
                .build();

        Tag apiClientTag2 = Tag.builder()
                .tagId("platform")
                .tagName("Platform")
                .tagValue("ECS")
                .build();

        Tag apiClientTag3 = Tag.builder()
                .tagId("architecturedesign")
                .tagName("Architecture Design")
                .tagValue("Microservices Architecture")
                .className("fas fa-cubes")
                .build();

        Pattern pattern3 = Pattern.builder()
                .id("16")
                .title("External API Client")
                .name("External API Client")
                .url("/solution/External API Client")
                .ring("identify")
                .quadrant("integration")
                .status("Moved In")
                .isNew("FALSE")
                .description("Providing product capabilities to external clients and internal users via Markets.")
                .pattern(new ArrayList<>())
                .useCase(List.of("Public API Design and Security"))
                .tags(List.of(apiClientTag1, apiClientTag2, apiClientTag3))
                .build();

        patterns.put(pattern1.getId(), pattern1);
        patterns.put(pattern2.getId(), pattern2);
        patterns.put(pattern3.getId(), pattern3);
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

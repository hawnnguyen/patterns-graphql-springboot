package com.example.service;

import com.example.model.Pattern;
import com.example.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PatternServiceTest {
    private PatternService patternService;
    private Pattern testPattern;

    @BeforeEach
    void setUp() {
        patternService = new PatternServiceImpl();
        
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

        testPattern = Pattern.builder()
                .id("2")
                .title("Test Pattern")
                .name("Test Pattern")
                .url("/solution/test-pattern")
                .ring("adopt")
                .quadrant("application design")
                .status("Moved In")
                .isNew("FALSE")
                .description("Test pattern description")
                .pattern(new ArrayList<>())
                .useCase(List.of("Test Use Case"))
                .tags(List.of(tag1, tag2))
                .build();
    }

    @Test
    void getAllPatterns_ShouldReturnPatterns() {
        StepVerifier.create(patternService.getAllPatterns())
                .assertNext(pattern -> {
                    assertNotNull(pattern);
                    assertEquals("1", pattern.getId());
                    assertEquals("ECS with MSK - Rediscache", pattern.getName());
                })
                .verifyComplete();
    }

    @Test
    void getPatternById_ShouldReturnPattern() {
        StepVerifier.create(patternService.getPatternById("1"))
                .assertNext(pattern -> {
                    assertNotNull(pattern);
                    assertEquals("1", pattern.getId());
                    assertEquals("ECS with MSK - Rediscache", pattern.getName());
                    assertEquals("enterprise", pattern.getQuadrant());
                    assertEquals("identify", pattern.getRing());
                    assertNotNull(pattern.getTags());
                    assertEquals(2, pattern.getTags().size());
                })
                .verifyComplete();

        // Test non-existent pattern
        StepVerifier.create(patternService.getPatternById("999"))
                .verifyComplete();
    }

    @Test
    void getPatternsByQuadrant_ShouldReturnPatterns() {
        StepVerifier.create(patternService.getPatternsByQuadrant("enterprise"))
                .assertNext(pattern -> {
                    assertNotNull(pattern);
                    assertEquals("enterprise", pattern.getQuadrant());
                    assertEquals("1", pattern.getId());
                })
                .verifyComplete();

        // Test case-insensitive matching
        StepVerifier.create(patternService.getPatternsByQuadrant("ENTERPRISE"))
                .assertNext(pattern -> assertEquals("enterprise", pattern.getQuadrant()))
                .verifyComplete();

        // Test non-existent quadrant
        StepVerifier.create(patternService.getPatternsByQuadrant("nonexistent"))
                .verifyComplete();
    }

    @Test
    void getPatternsByRing_ShouldReturnPatterns() {
        StepVerifier.create(patternService.getPatternsByRing("identify"))
                .assertNext(pattern -> {
                    assertNotNull(pattern);
                    assertEquals("identify", pattern.getRing());
                    assertEquals("1", pattern.getId());
                })
                .verifyComplete();

        // Test case-insensitive matching
        StepVerifier.create(patternService.getPatternsByRing("IDENTIFY"))
                .assertNext(pattern -> assertEquals("identify", pattern.getRing()))
                .verifyComplete();

        // Test non-existent ring
        StepVerifier.create(patternService.getPatternsByRing("nonexistent"))
                .verifyComplete();
    }

    @Test
    void createPattern_ShouldCreateAndReturnPattern() {
        StepVerifier.create(patternService.createPattern(testPattern))
                .assertNext(pattern -> {
                    assertNotNull(pattern);
                    assertEquals("2", pattern.getId());
                    assertEquals("Test Pattern", pattern.getName());
                    assertEquals("application design", pattern.getQuadrant());
                    assertNotNull(pattern.getTags());
                    assertEquals(2, pattern.getTags().size());
                })
                .verifyComplete();
    }

    @Test
    void updatePattern_ShouldUpdateAndReturnPattern() {
        patternService.createPattern(testPattern).block();
        
        Pattern updatedPattern = testPattern.toBuilder()
                .name("Updated Pattern")
                .description("Updated description")
                .ring("trial")
                .quadrant("tools")
                .status("Updated")
                .isNew("TRUE")
                .tags(List.of(Tag.builder()
                        .tagId("updated")
                        .tagName("Updated")
                        .tagValue("New Value")
                        .build()))
                .build();

        StepVerifier.create(patternService.updatePattern("2", updatedPattern))
                .assertNext(pattern -> {
                    assertNotNull(pattern);
                    assertEquals("2", pattern.getId());
                    assertEquals("Updated Pattern", pattern.getName());
                    assertEquals("Updated description", pattern.getDescription());
                    assertEquals("trial", pattern.getRing());
                    assertEquals("tools", pattern.getQuadrant());
                    assertEquals("Updated", pattern.getStatus());
                    assertEquals("TRUE", pattern.getIsNew());
                    assertEquals(1, pattern.getTags().size());
                    assertEquals("updated", pattern.getTags().get(0).getTagId());
                })
                .verifyComplete();

        // Test updating non-existent pattern
        StepVerifier.create(patternService.updatePattern("999", updatedPattern))
                .verifyComplete();
    }

    @Test
    void deletePattern_ShouldDeleteAndReturnTrue() {
        patternService.createPattern(testPattern).block();

        // Delete existing pattern
        StepVerifier.create(patternService.deletePattern("2"))
                .assertNext(result -> assertEquals(true, result))
                .verifyComplete();

        // Verify pattern is deleted
        StepVerifier.create(patternService.getPatternById("2"))
                .verifyComplete();

        // Delete non-existent pattern
        StepVerifier.create(patternService.deletePattern("999"))
                .assertNext(result -> assertEquals(false, result))
                .verifyComplete();
    }
}

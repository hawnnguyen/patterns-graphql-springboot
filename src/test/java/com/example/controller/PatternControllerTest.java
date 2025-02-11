package com.example.controller;

import com.example.service.PatternService;
import com.example.service.PatternServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureGraphQlTester
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PatternControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PatternService patternService() {
            return new PatternServiceImpl();
        }
    }

    @Autowired
    private GraphQlTester graphQlTester;

    @BeforeEach
    void setup() {
        String mutation = """
            mutation {
              createPattern(pattern: {
                id: "2"
                title: "Test Pattern"
                name: "Test Pattern"
                url: "/solution/test-pattern"
                ring: "adopt"
                quadrant: "application design"
                status: "Moved In"
                isNew: "FALSE"
                description: "Test pattern description"
                pattern: []
                useCase: ["Test Use Case"]
                tags: [{
                  tagId: "architecturedesign"
                  tagName: "Architecture Design"
                  tagValue: "Microservices Architecture"
                  className: "fas fa-cubes"
                }]
              }) {
                id
                name
                quadrant
                tags {
                  tagValue
                }
              }
            }
            """;

        graphQlTester.document(mutation)
                .execute()
                .path("data.createPattern.name").entity(String.class).isEqualTo("Test Pattern")
                .path("data.createPattern.quadrant").entity(String.class).isEqualTo("application design");
    }

    @Test
    @Order(1)
    void getAllPatterns_ShouldReturnPatterns() {
        String query = """
            query {
              patterns {
                id
                title
                name
                url
                ring
                quadrant
                status
                isNew
                description
                tags {
                  tagId
                  tagName
                  tagValue
                  className
                }
              }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("data.patterns[0].name").entity(String.class).isEqualTo("ECS with MSK - Rediscache")
                .path("data.patterns[0].quadrant").entity(String.class).isEqualTo("enterprise")
                .path("data.patterns[0].tags[0].tagValue").entity(String.class).isEqualTo("Microservices Architecture");
    }

    @Test
    @Order(2)
    void getPatternById_ShouldReturnPattern() {
        String query = """
            query {
              patternById(id: "1") {
                id
                title
                name
                url
                ring
                quadrant
                tags {
                  tagId
                  tagName
                  tagValue
                }
              }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("data.patternById.name").entity(String.class).isEqualTo("ECS with MSK - Rediscache")
                .path("data.patternById.quadrant").entity(String.class).isEqualTo("enterprise");
    }

    @Test
    @Order(3)
    void getPatternsByQuadrant_ShouldReturnPatterns() {
        String query = """
            query {
              patternsByQuadrant(quadrant: "enterprise") {
                id
                name
                quadrant
                tags {
                  tagValue
                }
              }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("data.patternsByQuadrant[0].quadrant").entity(String.class).isEqualTo("enterprise");
    }

    @Test
    @Order(4)
    void getPatternsByRing_ShouldReturnPatterns() {
        String query = """
            query {
              patternsByRing(ring: "identify") {
                id
                name
                ring
                tags {
                  tagValue
                }
              }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("data.patternsByRing[0].ring").entity(String.class).isEqualTo("identify");
    }

    @Test
    @Order(5)
    void updatePattern_ShouldUpdateAndReturnPattern() {
        String mutation = """
            mutation {
              updatePattern(
                id: "2"
                pattern: {
                  id: "2"
                  title: "Updated Pattern"
                  name: "Updated Pattern"
                  url: "/solution/updated-pattern"
                  ring: "identify"
                  quadrant: "enterprise"
                  status: "Moved In"
                  isNew: "FALSE"
                  description: "Updated description"
                  pattern: []
                  useCase: []
                  tags: []
                }
              ) {
                id
                name
                description
              }
            }
            """;

        graphQlTester.document(mutation)
                .execute()
                .path("data.updatePattern.name").entity(String.class).isEqualTo("Updated Pattern")
                .path("data.updatePattern.description").entity(String.class).isEqualTo("Updated description");
    }

    @Test
    @Order(6)
    void deletePattern_ShouldReturnTrue() {
        String mutation = """
            mutation {
              deletePattern(id: "2")
            }
            """;

        graphQlTester.document(mutation)
                .execute()
                .path("data.deletePattern").entity(Boolean.class).isEqualTo(true);
    }
}

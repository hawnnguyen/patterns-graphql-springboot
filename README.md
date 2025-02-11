# Spring Boot WebFlux GraphQL Pattern Service

A reactive Spring Boot application demonstrating GraphQL implementation with WebFlux, showcasing a pattern management system.

## Features

- Reactive GraphQL API using Spring WebFlux
- Pattern management with tags support
- Filtering patterns by quadrant and ring
- CRUD operations for patterns
- Comprehensive test coverage

## Technology Stack

- Java 23
- Spring Boot 3.2.2
- Spring WebFlux
- GraphQL Spring Boot Starter
- Project Lombok
- JUnit 5

## Data Model

### Pattern
```graphql
type Pattern {
    id: ID!
    title: String!
    name: String!
    url: String
    ring: String!
    quadrant: String!
    status: String
    isNew: String
    description: String
    pattern: [String]
    useCase: [String]
    tags: [Tag]
}
```

### Tag
```graphql
type Tag {
    tagId: String!
    tagName: String!
    tagValue: String
    className: String
}
```

## GraphQL API

### Queries

1. Get All Patterns
```graphql
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
```

2. Get Pattern by ID
```graphql
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
```

3. Get Patterns by Quadrant
```graphql
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
```

4. Get Patterns by Ring
```graphql
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
```

### Mutations

1. Create Pattern
```graphql
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
```

2. Update Pattern
```graphql
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
```

3. Delete Pattern
```graphql
mutation {
  deletePattern(id: "2")
}
```

## Running the Application

1. Build the project:
```bash
mvn clean package
```

2. Run the application:
```bash
java -jar target/pattern-service-1.0.0.jar
```

3. Access the GraphQL endpoint at:
```
http://localhost:8080/graphql
```

## Testing

The application includes comprehensive test coverage for both service and controller layers:

- `PatternServiceTest`: Unit tests for service layer functionality
- `PatternControllerTest`: Integration tests for GraphQL endpoints

Run tests using:
```bash
mvn test
```

## Recent Changes

1. Updated GraphQL Schema:
   - Added new Tag type for better organization
   - Enhanced Pattern type with additional fields
   - Added new query methods for filtering

2. Java Model Updates:
   - Added Tag class with Lombok annotations
   - Updated Pattern class with new fields and tag support
   - Implemented builder pattern with toBuilder support

3. Service Layer:
   - Added methods for querying by quadrant and ring
   - Implemented CRUD operations with reactive support
   - Added sample data initialization

4. Testing Improvements:
   - Added comprehensive test cases
   - Implemented test ordering for better isolation
   - Added assertions for new fields and relationships

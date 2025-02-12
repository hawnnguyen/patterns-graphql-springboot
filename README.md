# Spring Boot GraphQL Pattern Service

A reactive Spring Boot application demonstrating GraphQL implementation with WebFlux, showcasing a pattern management system.

## Features

- Reactive GraphQL API using Spring WebFlux
- Pattern management with tags support
- Filtering patterns by quadrant and ring
- CRUD operations for patterns
- Comprehensive test coverage

## Technology Stack

- Java 21
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

## GraphiQL Examples

The application includes GraphiQL, an in-browser IDE for exploring GraphQL. Access it at:
```
http://localhost:8080/graphiql
```

### Example 1: Query All Patterns

Request:
```graphql
query {
  patterns {
    id
    name
    quadrant
    ring
    tags {
      tagId
      tagName
      tagValue
      className
    }
  }
}
```

Response:
```json
{
  "data": {
    "patterns": [
      {
        "id": "1",
        "name": "ECS with MSK - Rediscache",
        "quadrant": "enterprise",
        "ring": "identify",
        "tags": [
          {
            "tagId": "architecturedesign",
            "tagName": "Architecture Design",
            "tagValue": "Microservices Architecture",
            "className": "fas fa-cubes"
          },
          {
            "tagId": "platform",
            "tagName": "Platform",
            "tagValue": "AWS"
          }
        ]
      },
      {
        "id": "15",
        "name": "Data Broker for SaaS, AWS, and On-Premises Systems",
        "quadrant": "data",
        "ring": "identify",
        "tags": [
          {
            "tagId": "architecturedesign",
            "tagName": "Architecture Design",
            "tagValue": "Data Broker",
            "className": "fas fa-network-wired"
          },
          {
            "tagId": "architecturedesign",
            "tagName": "Architecture Design",
            "tagValue": "Event Driven Architecture",
            "className": "fa-broadcast-tower"
          }
        ]
      },
      {
        "id": "16",
        "name": "External API Client",
        "quadrant": "integration",
        "ring": "identify",
        "tags": [
          {
            "tagId": "platform",
            "tagName": "Platform",
            "tagValue": "AWS"
          },
          {
            "tagId": "platform",
            "tagName": "Platform",
            "tagValue": "ECS"
          },
          {
            "tagId": "architecturedesign",
            "tagName": "Architecture Design",
            "tagValue": "Microservices Architecture",
            "className": "fas fa-cubes"
          }
        ]
      }
    ]
  }
}
```

### Example 2: Query Patterns by Quadrant

Request:
```graphql
query {
  patternsByQuadrant(quadrant: "data") {
    id
    name
    quadrant
    tags {
      tagValue
      className
    }
  }
}
```

Response:
```json
{
  "data": {
    "patternsByQuadrant": [
      {
        "id": "15",
        "name": "Data Broker for SaaS, AWS, and On-Premises Systems",
        "quadrant": "data",
        "tags": [
          {
            "tagValue": "Data Broker",
            "className": "fas fa-network-wired"
          },
          {
            "tagValue": "Event Driven Architecture",
            "className": "fa-broadcast-tower"
          }
        ]
      }
    ]
  }
}
```

### Example 3: Query Patterns by Ring

Request:
```graphql
query {
  patternsByRing(ring: "identify") {
    id
    name
    ring
    quadrant
  }
}
```

Response:
```json
{
  "data": {
    "patternsByRing": [
      {
        "id": "1",
        "name": "ECS with MSK - Rediscache",
        "ring": "identify",
        "quadrant": "enterprise"
      },
      {
        "id": "15",
        "name": "Data Broker for SaaS, AWS, and On-Premises Systems",
        "ring": "identify",
        "quadrant": "data"
      },
      {
        "id": "16",
        "name": "External API Client",
        "ring": "identify",
        "quadrant": "integration"
      }
    ]
  }
}
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


[def]: docs/graphql-interface-pattern.png
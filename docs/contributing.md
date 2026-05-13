# Contributing

Contributions to the fulfillmenttools Java SDK are welcome. Please follow the guidelines below to ensure a smooth contribution process.

## Building

Build the entire project:

```bash
./gradlew build
```

## Running Tests

Run all tests:

```bash
./gradlew test
```

Run a specific test class:

```bash
./gradlew test --tests "fully.qualified.ClassName"
```

Run a specific test method:

```bash
./gradlew test --tests "fully.qualified.ClassName.methodName"
```

## Documentation

Documentation is built with VitePress and hosted on GitHub Pages. The source files are in the `docs/` directory.

### Building Docs Locally

```bash
cd docs
npm install
npm run docs:dev
```

The docs site will be available at `http://localhost:5173`.

### Building for Production

```bash
cd docs
npm run docs:build
```

The built site is in `docs/.vitepress/dist/`.

## Pull Request Guidelines

1. **Branch naming**: Use descriptive branch names, e.g., `feature/new-client`, `fix/auth-issue`
2. **Commit messages**: Write clear, concise commit messages
3. **Tests**: Add tests for new features; ensure all tests pass before submitting
4. **Documentation**: Update relevant documentation for new APIs or behavioral changes
5. **Code style**: Follow the existing code style and conventions in the repository

## Setting up Your Development Environment

### Requirements

- Java 21 or higher
- Gradle 8 or higher
- Node.js 18+ (for documentation)

### Cloning and Setup

```bash
git clone https://github.com/Joessst-Dev/fulfillmenttools-java-sdk.git
cd fulfillmenttools-java-sdk
./gradlew build
```

## Stack Overview

- **Build tool**: Gradle 9 with Kotlin DSL
- **Language**: Java 21+
- **Test framework**: JUnit Jupiter (JUnit 5)
- **Documentation**: VitePress 1.3
- **CI/CD**: GitHub Actions

## Common Tasks

### Creating a New Resource Client

1. Define the client interface in `sdk-core/src/main/java/de/joesst/dev/fulfillmenttools/{resource}/`
2. Implement the client in `sdk-core/src/main/java/de/joesst/dev/fulfillmenttools/internal/{resource}/`
3. Add tests in `sdk-core/src/test/java/de/joesst/dev/fulfillmenttools/{resource}/`
4. Document the client in `docs/clients/{resource}.md`

### Adding a New API Method

1. Add the method signature to the client interface
2. Implement it in the client implementation
3. Add a test for the method
4. Update the relevant documentation page

### Updating Documentation

Documentation pages are in Markdown format in the `docs/` directory. Follow the existing structure and style.

## Getting Help

For questions or issues, please open a GitHub issue or discussion.

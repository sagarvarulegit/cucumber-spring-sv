# AI Assistant Instructions for cucumber-spring-sv

## Project Overview
This is a comprehensive test automation framework built with Spring Boot and Cucumber, supporting:
- API Testing (REST API validation)
- Mobile Testing (Appium + BrowserStack)
- Performance Testing (Locust)
- AI-powered Visual Testing (Gemini API)

## Key Architecture Components

### 1. Multi-Module Maven Structure
- `api/` - API and performance testing
- `mobile/` - Mobile testing with Appium/BrowserStack
- Root `pom.xml` manages common dependencies and properties

### 2. Test Organization Patterns
- Feature files in `src/test/resources/features/`
- Step definitions follow feature file structure
- Page Objects for mobile UI elements in `mobile/src/test/java/com/sagarvarule/pages/`
- Spring Boot configuration in application*.properties files

## Essential Test Workflows

### Mobile Testing
```bash
# BrowserStack execution (primary method)
mvn test -Dspring.profiles.active=browserstack

# AI visual testing
mvn test -Dspring.profiles.active=browserstack -Dcucumber.filter.tags="@AI"

# Local Appium testing
mvn test -Dspring.profiles.active=local
```

### Performance Testing (in api/performance-tests/locust)
```bash
# Setup
cd api/performance-tests/locust
pip install -r requirements.txt

# Run tests
./scripts/run_tests.sh
```

## Key Patterns & Conventions

1. **Test Tags**
   - `@AI` - Visual validation tests using Gemini API
   - `@ignore` - Tests to be skipped

2. **Configuration Management**
   - Environment variables for sensitive data (BrowserStack, Gemini credentials)
   - Profile-specific properties in application-{profile}.properties

3. **Visual Testing Pattern**
   Example from Destination.feature:
   ```gherkin
   @AI
   Scenario: AI Visual Test for screen "Find your Stay"
     Given user is at destination screen
     Then user should see following elements:
       | Status bar             |
       | Title "Find Your Stay" |
   ```

## Integration Points
1. **BrowserStack**
   - Credentials: `BROWSERSTACK_USERNAME`, `BROWSERSTACK_ACCESS_KEY`
   - App ID: `BROWSERSTACK_APP`

2. **Gemini AI**
   - API Key: `GEMINI_API_KEY`
   - Used for visual element validation

3. **Load Testing**
   - Target: Vercel-hosted sample app
   - Configuration in `load_scenarios.py`

## Debugging Tips
1. For AI visual test failures:
   - Check Gemini API key is set
   - Review screenshot baseline in test resources
   
2. For BrowserStack issues:
   - Verify app upload using app hash
   - Check device/OS compatibility in config

## Common Pitfalls
1. Running AI tests without Gemini key
2. Missing BrowserStack environment variables
3. Incorrect Python version for Locust tests (needs 3.7+)
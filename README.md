# Cucumber Spring Test Automation Framework

A comprehensive test automation framework built with Spring Boot, Cucumber, and Maven supporting API, Mobile, and Performance testing.

## 🏗️ Project Structure

```
cucumber-spring-sv/
├── api/                          # API Testing Module
│   ├── src/test/java/           # API test classes
│   ├── performance-tests/       # Locust performance tests
│   └── pom.xml
├── mobile/                      # Mobile Testing Module
│   ├── src/test/java/          # Mobile test classes
│   ├── src/test/resources/     # Test resources & configs
│   └── pom.xml
└── pom.xml                     # Parent POM
```

## 🚀 Quick Start

### Prerequisites

- **Java 21+**
- **Maven 3.6+**
- **Python 3.7+** (for performance tests)
- **BrowserStack Account** (for mobile cloud testing)
- **Gemini API Key** (for AI visual testing)

### Environment Setup

```bash
# Clone the repository
git clone https://github.com/sagarvarulegit/cucumber-spring-sv.git
cd cucumber-spring-sv

# Build all modules
mvn clean compile
```

---

## 📱 Mobile Testing

### Overview
Mobile testing module supports both local Appium and BrowserStack cloud execution with AI-powered visual validation.

### Features
- **BrowserStack Integration**: Cloud-based mobile testing
- **Parallel Execution**: Run tests on multiple devices simultaneously
- **AI Visual Testing**: Gemini-powered screenshot validation
- **Page Object Model**: Maintainable test structure
- **Spring Boot Integration**: Dependency injection and configuration management

### Setup

#### BrowserStack Configuration
```bash
# Set your BrowserStack credentials
export BROWSERSTACK_USERNAME=your_username
export BROWSERSTACK_ACCESS_KEY=your_access_key
export BROWSERSTACK_APP=bs://your_app_id

# Optional: Set Gemini API key for AI visual tests
export GEMINI_API_KEY=your_gemini_api_key
```

#### Local Appium Setup (Alternative)
```bash
# Install Appium
npm install -g appium
appium driver install uiautomator2

# Start Appium server
appium --port 4723
```

### Running Mobile Tests

#### BrowserStack Execution (Recommended)
```bash
cd mobile

# Run all tests on BrowserStack
mvn test -Dspring.profiles.active=browserstack

# Run specific scenarios
mvn test -Dspring.profiles.active=browserstack -Dcucumber.filter.tags="not @ignore"

# Run AI visual tests (requires GEMINI_API_KEY)
mvn test -Dspring.profiles.active=browserstack -Dcucumber.filter.tags="@AI" -DGEMINI_API_KEY=${GEMINI_API_KEY}

# Run parallel tests on multiple devices
mvn test -Dspring.profiles.active=browserstack -Dcucumber.filter.tags="not @AI and not @ignore"
```

#### Local Execution
```bash
cd mobile

# Ensure Appium server is running and APK is available
mvn test -Dspring.profiles.active=local
```

### Mobile Test Configuration

#### BrowserStack Profile (`application-browserstack.properties`)
```properties
mobile.use-browserstack=true
mobile.browserstack-app=bs://699adcf1a5eaf2e8df1e81aa33d12dd03b88a36f
mobile.browserstack-username=${BROWSERSTACK_USERNAME}
mobile.browserstack-access-key=${BROWSERSTACK_ACCESS_KEY}
mobile.device-name=Samsung Galaxy S22
mobile.platform-version=12.0
```

#### Parallel Device Configuration
Tests automatically distribute across:
- Samsung Galaxy S22 (Android 12.0)
- Google Pixel 6 (Android 12.0)
- OnePlus 9 (Android 11.0)

### Mobile Test Reports
- **Cucumber Reports**: `mobile/target/cucumber-reports/`
- **Surefire Reports**: `mobile/target/surefire-reports/`
- **Screenshots**: Captured automatically on failures

---

## 🌐 API Testing

### Overview
API testing module provides comprehensive REST API testing with Spring Boot integration.

### Features
- **REST API Testing**: Full HTTP method support
- **Spring Boot Integration**: Configuration and dependency management
- **JSON Validation**: Response structure and data validation
- **Test Data Management**: Configurable test data sets

### Setup
```bash
cd api

# Install dependencies
mvn clean compile test-compile
```

### Running API Tests

#### Basic Execution
```bash
cd api

# Run all API tests
mvn test

# Run specific test classes
mvn test -Dtest=DestinationApiTest

# Run with specific profiles
mvn test -Dspring.profiles.active=test
```

#### Test Endpoints
The framework tests these endpoints:
- **Destinations**: `GET /api/destination`
- **Search Results**: `GET /api/searchResults?city={city}&from_date={date}&to_date={date}`

#### Sample API Test Execution
```bash
# Test destination endpoint
curl -X GET "https://samplehotelsearchapp-brnsoo0jo-sagarvs-projects.vercel.app/api/destination"

# Test search endpoint
curl -X GET "https://samplehotelsearchapp-brnsoo0jo-sagarvs-projects.vercel.app/api/searchResults?city=New%20York&from_date=2023-12-15&to_date=2023-12-20"
```

### API Test Configuration
- **Base URL**: Configured in `application.properties`
- **Timeouts**: Configurable request/response timeouts
- **Retry Logic**: Automatic retry for flaky tests

---

## ⚡ Performance Testing

### Overview
Locust-based performance testing suite for load testing API endpoints with realistic user behavior simulation.

### Features
- **Multiple Load Scenarios**: Light, Medium, Heavy, Stress, Spike, Endurance
- **User Behavior Patterns**: Normal browsing, search-heavy, destination-focused
- **Real-time Monitoring**: Web UI with live metrics
- **Automated Analysis**: Performance threshold validation
- **CI/CD Integration**: Headless execution with reports

### Setup

#### Environment Setup
```bash
cd api/performance-tests/locust

# Automated setup (recommended)
./setup.sh

# Manual setup (alternative)
sudo apt install python3.12-venv -y
python3 -m venv venv
source venv/bin/activate
pip install -r requirements.txt
```

### Running Performance Tests

#### Quick Start Scenarios
```bash
cd api/performance-tests/locust

# Light load (10 users, 5 min)
./scripts/run_tests.sh light

# Medium load (50 users, 10 min)
./scripts/run_tests.sh medium

# Heavy load (200 users, 15 min)
./scripts/run_tests.sh heavy

# Stress test (500 users, 20 min)
./scripts/run_tests.sh stress
```

#### Interactive Web UI
```bash
# Start Locust web interface
./scripts/run_tests.sh web

# Open http://localhost:8089 in browser
# Configure users, spawn rate, and duration
```

#### Custom Load Tests
```bash
# Custom parameters: users, spawn_rate, duration
./scripts/run_tests.sh custom 25 3 '8m'
```

#### Specialized User Behavior
```bash
# Focus on destination browsing
./scripts/run_tests.sh browse-heavy

# Focus on search operations
./scripts/run_tests.sh search-heavy
```

### Performance Test Scenarios

| Scenario | Users | Spawn Rate | Duration | Use Case |
|----------|-------|------------|----------|----------|
| Light | 10 | 2/sec | 5 min | Normal business hours |
| Medium | 50 | 5/sec | 10 min | Peak business hours |
| Heavy | 200 | 10/sec | 15 min | Holiday/Black Friday |
| Stress | 500 | 20/sec | 20 min | Beyond normal capacity |
| Spike | 100 | 50/sec | 5 min | Sudden traffic surge |
| Endurance | 30 | 3/sec | 60 min | Long-term stability |

### Performance Thresholds
- **Destination Endpoint**: Max 500ms, P95 < 300ms, Error rate < 1%
- **Search Endpoint**: Max 2000ms, P95 < 1500ms, Error rate < 2%

### Results Analysis
```bash
# Analyze latest test results
python scripts/analyze_results.py

# View reports in reports/ directory
ls reports/
```

---

## 🔧 Configuration Management

### Spring Profiles
- **`browserstack`**: Mobile testing on BrowserStack cloud
- **`local`**: Local Appium execution
- **`test`**: API testing configuration

### Environment Variables
```bash
# BrowserStack
export BROWSERSTACK_USERNAME=your_username
export BROWSERSTACK_ACCESS_KEY=your_access_key
export BROWSERSTACK_APP=bs://your_app_id

# AI Testing
export GEMINI_API_KEY=your_gemini_api_key

# Custom configurations
export MOBILE_DEVICE_NAME="Samsung Galaxy S22"
export MOBILE_PLATFORM_VERSION="12.0"
```

### Configuration Files
- **Mobile**: `mobile/src/test/resources/application-{profile}.properties`
- **API**: `api/src/test/resources/application.properties`
- **Performance**: `api/performance-tests/locust/config/load_scenarios.py`

---

## 📊 Reporting & Monitoring

### Test Reports
- **Cucumber HTML Reports**: Detailed scenario execution
- **Maven Surefire Reports**: JUnit-style test results
- **Performance Reports**: Locust HTML reports with charts
- **AI Visual Reports**: Screenshot analysis results

### CI/CD Integration

#### GitHub Actions Example
```yaml
name: Test Automation
on: [push, pull_request]
jobs:
  mobile-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '21'
      - name: Run Mobile Tests
        run: mvn test -pl mobile -Dspring.profiles.active=browserstack
        env:
          BROWSERSTACK_USERNAME: ${{ secrets.BROWSERSTACK_USERNAME }}
          BROWSERSTACK_ACCESS_KEY: ${{ secrets.BROWSERSTACK_ACCESS_KEY }}
  
  performance-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-python@v4
        with:
          python-version: '3.9'
      - name: Run Performance Tests
        run: |
          cd api/performance-tests/locust
          ./setup.sh
          ./scripts/run_tests.sh medium
```

---

## 🛠️ Development & Debugging

### Debug Mode
```bash
# Mobile tests with debug logging
mvn test -pl mobile -Dspring.profiles.active=browserstack -Dlogging.level.com.sagarvarule=DEBUG

# Performance tests with verbose output
cd api/performance-tests/locust
locust -f locustfile.py --loglevel DEBUG
```

### Troubleshooting

#### Mobile Testing Issues
- **BrowserStack Connection**: Verify credentials and app upload
- **Local Appium**: Ensure server is running on port 4723
- **APK Issues**: Check APK file exists and is valid

#### Performance Testing Issues
- **Virtual Environment**: Run `./setup.sh` to fix Python environment
- **Connection Errors**: Verify API endpoint availability
- **High Response Times**: Check server resources and network

### Contributing
1. Fork the repository
2. Create feature branch: `git checkout -b feature/new-feature`
3. Commit changes: `git commit -am 'Add new feature'`
4. Push to branch: `git push origin feature/new-feature`
5. Submit pull request

---

## 📚 Additional Resources

- **BrowserStack Documentation**: [https://www.browserstack.com/docs](https://www.browserstack.com/docs)
- **Locust Documentation**: [https://docs.locust.io](https://docs.locust.io)
- **Cucumber Documentation**: [https://cucumber.io/docs](https://cucumber.io/docs)
- **Spring Boot Testing**: [https://spring.io/guides/gs/testing-web/](https://spring.io/guides/gs/testing-web/)

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
# cucumber-spring-sv

# Set your access key
export BROWSERSTACK_ACCESS_KEY=your_access_key_here

# Option 1: Use BrowserStack properties file
mvn test -Dspring.config.location=classpath:properties/testrun-browserstack.properties -pl mobile

# Option 2: Override use-browserstack property
mvn test -Dmobiletest.use-browserstack=true -DBROWSERSTACK_ACCESS_KEY=your_key -pl mobile

# Option 3: Run specific AI tests on BrowserStack
mvn test -Dcucumber.filter.tags="@AI" -Dmobiletest.use-browserstack=true -DBROWSERSTACK_ACCESS_KEY=your_key -pl mobile
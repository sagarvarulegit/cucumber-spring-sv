"""
Load Test Scenario Configurations for Hotel Search API
Define different load testing scenarios with specific user patterns and loads
"""

# Light Load Scenario - Normal business hours traffic
LIGHT_LOAD = {
    "users": 10,
    "spawn_rate": 2,
    "run_time": "5m",
    "description": "Light load simulation - 10 concurrent users over 5 minutes"
}

# Medium Load Scenario - Peak business hours
MEDIUM_LOAD = {
    "users": 50,
    "spawn_rate": 5,
    "run_time": "10m", 
    "description": "Medium load simulation - 50 concurrent users over 10 minutes"
}

# Heavy Load Scenario - Black Friday / Holiday peak traffic
HEAVY_LOAD = {
    "users": 200,
    "spawn_rate": 10,
    "run_time": "15m",
    "description": "Heavy load simulation - 200 concurrent users over 15 minutes"
}

# Stress Test Scenario - Beyond normal capacity
STRESS_TEST = {
    "users": 500,
    "spawn_rate": 20,
    "run_time": "20m",
    "description": "Stress test - 500 concurrent users over 20 minutes"
}

# Spike Test Scenario - Sudden traffic surge
SPIKE_TEST = {
    "users": 100,
    "spawn_rate": 50,  # Very fast ramp-up
    "run_time": "5m",
    "description": "Spike test - Rapid ramp-up to 100 users in 2 seconds"
}

# Endurance Test Scenario - Long-running stability test
ENDURANCE_TEST = {
    "users": 30,
    "spawn_rate": 3,
    "run_time": "60m",
    "description": "Endurance test - 30 users over 1 hour for stability testing"
}

# User behavior patterns for different scenarios
USER_PATTERNS = {
    "normal_browsing": {
        "HotelSearchUser": 70,      # 70% normal users
        "DestinationOnlyUser": 20,  # 20% just browsing destinations
        "SearchHeavyUser": 10       # 10% power users
    },
    "search_heavy": {
        "HotelSearchUser": 40,      # 40% normal users
        "DestinationOnlyUser": 10,  # 10% browsing
        "SearchHeavyUser": 50       # 50% power users (search intensive)
    },
    "browse_heavy": {
        "HotelSearchUser": 30,      # 30% normal users
        "DestinationOnlyUser": 60,  # 60% just browsing
        "SearchHeavyUser": 10       # 10% power users
    }
}

# Performance thresholds for different endpoints
PERFORMANCE_THRESHOLDS = {
    "destination_endpoint": {
        "max_response_time_ms": 500,
        "percentile_95_ms": 300,
        "error_rate_percent": 1.0
    },
    "search_endpoint": {
        "max_response_time_ms": 2000,
        "percentile_95_ms": 1500,
        "error_rate_percent": 2.0
    }
}

"""
Locust Performance Test Suite for Hotel Search API
Tests the destination and searchResults endpoints with various load scenarios
"""

import random
from datetime import datetime, timedelta
from locust import HttpUser, task, between
from urllib.parse import urlencode


class HotelSearchUser(HttpUser):
    """
    Simulates user behavior for hotel search application
    Tests both destination listing and search results endpoints
    """
    
    # Wait time between requests (1-3 seconds to simulate real user behavior)
    wait_time = between(1, 3)
    
    # Base URL for the API
    host = "https://samplehotelsearchapp-brnsoo0jo-sagarvs-projects.vercel.app"
    
    def on_start(self):
        """Initialize test data when user starts"""
        self.destinations = [
            "New York", "London", "Paris", "Tokyo", "Sydney", 
            "Dubai", "Singapore", "Rome", "Barcelona", "Istanbul"
        ]
        
        # Generate realistic date ranges for search
        self.date_ranges = self._generate_date_ranges()
    
    def _generate_date_ranges(self):
        """Generate realistic date ranges for hotel searches"""
        date_ranges = []
        base_date = datetime.now()
        
        # Generate various date ranges (1-14 days ahead, 1-7 day stays)
        for days_ahead in range(1, 15):
            for stay_duration in range(1, 8):
                from_date = base_date + timedelta(days=days_ahead)
                to_date = from_date + timedelta(days=stay_duration)
                date_ranges.append({
                    'from_date': from_date.strftime('%Y-%m-%d'),
                    'to_date': to_date.strftime('%Y-%m-%d')
                })
        
        return date_ranges
    
    @task(3)
    def get_destinations(self):
        """
        Test the destinations endpoint
        Weight: 3 (higher frequency as users often browse destinations)
        """
        with self.client.get(
            "/api/destination",
            name="GET /api/destination",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                try:
                    destinations = response.json()
                    if isinstance(destinations, list) and len(destinations) > 0:
                        # Update local destinations list with actual response
                        self.destinations = destinations
                        response.success()
                    else:
                        response.failure("Empty or invalid destinations response")
                except Exception as e:
                    response.failure(f"Failed to parse destinations JSON: {e}")
            else:
                response.failure(f"Got status code {response.status_code}")
    
    @task(5)
    def search_hotels(self):
        """
        Test the searchResults endpoint with various parameters
        Weight: 5 (main user action - searching for hotels)
        """
        # Select random destination and date range
        city = random.choice(self.destinations)
        date_range = random.choice(self.date_ranges)
        
        # Build query parameters
        params = {
            'city': city,
            'from_date': date_range['from_date'],
            'to_date': date_range['to_date']
        }
        
        query_string = urlencode(params)
        endpoint = f"/api/searchResults?{query_string}"
        
        with self.client.get(
            endpoint,
            name="GET /api/searchResults",
            catch_response=True
        ) as response:
            if response.status_code == 200:
                try:
                    results = response.json()
                    if isinstance(results, list):
                        # Validate response structure
                        if len(results) > 0:
                            # Check if first result has expected fields
                            first_result = results[0]
                            required_fields = ['name', 'city', 'available_from', 'available_to', 'image_url']
                            
                            if all(field in first_result for field in required_fields):
                                response.success()
                            else:
                                response.failure("Missing required fields in search results")
                        else:
                            # Empty results are valid (no hotels available)
                            response.success()
                    else:
                        response.failure("Search results response is not a list")
                except Exception as e:
                    response.failure(f"Failed to parse search results JSON: {e}")
            else:
                response.failure(f"Got status code {response.status_code}")
    
    @task(1)
    def search_invalid_city(self):
        """
        Test search with invalid city to check error handling
        Weight: 1 (occasional edge case testing)
        """
        invalid_cities = ["InvalidCity", "NonExistentPlace", "TestCity123"]
        city = random.choice(invalid_cities)
        date_range = random.choice(self.date_ranges)
        
        params = {
            'city': city,
            'from_date': date_range['from_date'],
            'to_date': date_range['to_date']
        }
        
        query_string = urlencode(params)
        endpoint = f"/api/searchResults?{query_string}"
        
        with self.client.get(
            endpoint,
            name="GET /api/searchResults (invalid city)",
            catch_response=True
        ) as response:
            # Accept both empty results (200) or error responses (4xx)
            if response.status_code in [200, 400, 404]:
                response.success()
            else:
                response.failure(f"Unexpected status code {response.status_code} for invalid city")
    
    @task(1)
    def search_invalid_dates(self):
        """
        Test search with invalid date formats to check error handling
        Weight: 1 (occasional edge case testing)
        """
        city = random.choice(self.destinations)
        
        # Test various invalid date scenarios
        invalid_date_scenarios = [
            {'from_date': 'invalid-date', 'to_date': '2023-12-20'},
            {'from_date': '2023-12-20', 'to_date': 'invalid-date'},
            {'from_date': '2023-12-25', 'to_date': '2023-12-20'},  # from > to
            {'from_date': '', 'to_date': '2023-12-20'},
            {'from_date': '2023-12-20', 'to_date': ''}
        ]
        
        params = random.choice(invalid_date_scenarios)
        params['city'] = city
        
        query_string = urlencode(params)
        endpoint = f"/api/searchResults?{query_string}"
        
        with self.client.get(
            endpoint,
            name="GET /api/searchResults (invalid dates)",
            catch_response=True
        ) as response:
            # Accept error responses or empty results for invalid dates
            if response.status_code in [200, 400, 422]:
                response.success()
            else:
                response.failure(f"Unexpected status code {response.status_code} for invalid dates")


class DestinationOnlyUser(HttpUser):
    """
    User that only fetches destinations (simulates browsing behavior)
    """
    wait_time = between(2, 5)
    host = "https://samplehotelsearchapp-brnsoo0jo-sagarvs-projects.vercel.app"
    
    @task
    def get_destinations_only(self):
        """Continuously fetch destinations"""
        with self.client.get("/api/destination", name="GET /api/destination (browse only)") as response:
            if response.status_code != 200:
                print(f"Destinations endpoint failed with status {response.status_code}")


class SearchHeavyUser(HttpUser):
    """
    User that performs many searches (simulates power user behavior)
    """
    wait_time = between(0.5, 1.5)
    host = "https://samplehotelsearchapp-brnsoo0jo-sagarvs-projects.vercel.app"
    
    def on_start(self):
        """Initialize with destinations"""
        # Fetch destinations first
        response = self.client.get("/api/destination")
        if response.status_code == 200:
            self.destinations = response.json()
        else:
            self.destinations = ["New York", "London", "Paris"]  # Fallback
        
        # Pre-generate many date ranges
        self.date_ranges = []
        base_date = datetime.now()
        for i in range(30):  # 30 different date combinations
            from_date = base_date + timedelta(days=random.randint(1, 60))
            to_date = from_date + timedelta(days=random.randint(1, 14))
            self.date_ranges.append({
                'from_date': from_date.strftime('%Y-%m-%d'),
                'to_date': to_date.strftime('%Y-%m-%d')
            })
    
    @task
    def rapid_search(self):
        """Perform rapid hotel searches"""
        city = random.choice(self.destinations)
        date_range = random.choice(self.date_ranges)
        
        params = {
            'city': city,
            'from_date': date_range['from_date'],
            'to_date': date_range['to_date']
        }
        
        query_string = urlencode(params)
        
        with self.client.get(
            f"/api/searchResults?{query_string}",
            name="GET /api/searchResults (rapid search)"
        ) as response:
            if response.status_code != 200:
                print(f"Search failed for {city}: {response.status_code}")

#!/usr/bin/env python3
"""
Quick validation script to test the Hotel Search API endpoints
and verify Locust setup is working correctly
"""

import requests
import json
from datetime import datetime, timedelta

def test_destination_endpoint():
    """Test the destination endpoint"""
    url = "https://samplehotelsearchapp-brnsoo0jo-sagarvs-projects.vercel.app/api/destination"
    
    try:
        response = requests.get(url, timeout=10)
        print(f"Destination endpoint status: {response.status_code}")
        
        if response.status_code == 200:
            destinations = response.json()
            print(f"Retrieved {len(destinations)} destinations:")
            for dest in destinations[:5]:  # Show first 5
                print(f"  - {dest}")
            if len(destinations) > 5:
                print(f"  ... and {len(destinations) - 5} more")
            return destinations
        else:
            print(f"Error: {response.text}")
            return []
    except Exception as e:
        print(f"Error testing destination endpoint: {e}")
        return []

def test_search_endpoint():
    """Test the search results endpoint"""
    base_url = "https://samplehotelsearchapp-brnsoo0jo-sagarvs-projects.vercel.app/api/searchResults"
    
    # Test with New York
    params = {
        'city': 'New York',
        'from_date': '2023-12-15',
        'to_date': '2023-12-20'
    }
    
    try:
        response = requests.get(base_url, params=params, timeout=10)
        print(f"\nSearch endpoint status: {response.status_code}")
        
        if response.status_code == 200:
            results = response.json()
            print(f"Found {len(results)} hotels for New York:")
            for hotel in results:
                print(f"  - {hotel['name']} (Available: {hotel['available_from']} to {hotel['available_to']})")
            return True
        else:
            print(f"Error: {response.text}")
            return False
    except Exception as e:
        print(f"Error testing search endpoint: {e}")
        return False

def validate_locust_file():
    """Validate the locust file syntax"""
    try:
        # Try to import the locust file
        import sys
        import os
        sys.path.append(os.path.dirname(__file__))
        
        # This will raise an exception if there are syntax errors
        with open('locustfile.py', 'r') as f:
            compile(f.read(), 'locustfile.py', 'exec')
        
        print("\nLocust file validation: PASSED")
        return True
    except Exception as e:
        print(f"\nLocust file validation: FAILED - {e}")
        return False

def main():
    print("=" * 60)
    print("HOTEL SEARCH API PERFORMANCE TEST SETUP VALIDATION")
    print("=" * 60)
    
    # Test API endpoints
    destinations = test_destination_endpoint()
    search_works = test_search_endpoint()
    locust_valid = validate_locust_file()
    
    print("\n" + "=" * 60)
    print("VALIDATION SUMMARY")
    print("=" * 60)
    
    print(f"✓ Destination endpoint: {'WORKING' if destinations else 'FAILED'}")
    print(f"✓ Search endpoint: {'WORKING' if search_works else 'FAILED'}")
    print(f"✓ Locust file syntax: {'VALID' if locust_valid else 'INVALID'}")
    
    if destinations and search_works and locust_valid:
        print("\n🎉 All validations passed! Ready to run performance tests.")
        print("\nNext steps:")
        print("1. Install Locust: pip3 install -r requirements.txt")
        print("2. Run light test: ./scripts/run_tests.sh light")
        print("3. Or start web UI: ./scripts/run_tests.sh web")
    else:
        print("\n❌ Some validations failed. Please check the errors above.")

if __name__ == "__main__":
    main()

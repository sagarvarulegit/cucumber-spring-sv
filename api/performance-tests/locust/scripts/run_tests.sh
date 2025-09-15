#!/bin/bash

# Hotel Search API Performance Test Runner
# Provides easy commands to run different load test scenarios

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Script directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOCUST_DIR="$(dirname "$SCRIPT_DIR")"

echo -e "${BLUE}Hotel Search API Performance Test Runner${NC}"
echo "============================================="

# Activate virtual environment if it exists
if [ -d "$LOCUST_DIR/venv" ]; then
    echo -e "${BLUE}Activating virtual environment...${NC}"
    source "$LOCUST_DIR/venv/bin/activate"
fi

# Check if locust is installed
if ! command -v locust &> /dev/null; then
    echo -e "${RED}Error: Locust is not installed${NC}"
    echo "Please run the following commands:"
    echo "1. sudo apt install python3.12-venv -y"
    echo "2. python3 -m venv venv"
    echo "3. source venv/bin/activate"
    echo "4. pip install -r requirements.txt"
    exit 1
fi

# Function to run locust with specific parameters
run_locust() {
    local users=$1
    local spawn_rate=$2
    local run_time=$3
    local description=$4
    local user_classes=$5
    
    echo -e "${GREEN}Starting: $description${NC}"
    echo "Users: $users, Spawn Rate: $spawn_rate, Duration: $run_time"
    echo "User Classes: $user_classes"
    echo "----------------------------------------"
    
    cd "$LOCUST_DIR"
    
    if [ -n "$user_classes" ]; then
        locust -f locustfile.py \
               --users $users \
               --spawn-rate $spawn_rate \
               --run-time $run_time \
               --user-classes $user_classes \
               --headless \
               --html reports/report_$(date +%Y%m%d_%H%M%S).html \
               --csv reports/results_$(date +%Y%m%d_%H%M%S)
    else
        locust -f locustfile.py \
               --users $users \
               --spawn-rate $spawn_rate \
               --run-time $run_time \
               --headless \
               --html reports/report_$(date +%Y%m%d_%H%M%S).html \
               --csv reports/results_$(date +%Y%m%d_%H%M%S)
    fi
}

# Create reports directory
mkdir -p "$LOCUST_DIR/reports"

# Parse command line arguments
case "${1:-help}" in
    "light")
        run_locust 10 2 "5m" "Light Load Test - 10 users over 5 minutes"
        ;;
    "medium")
        run_locust 50 5 "10m" "Medium Load Test - 50 users over 10 minutes"
        ;;
    "heavy")
        run_locust 200 10 "15m" "Heavy Load Test - 200 users over 15 minutes"
        ;;
    "stress")
        run_locust 500 20 "20m" "Stress Test - 500 users over 20 minutes"
        ;;
    "spike")
        run_locust 100 50 "5m" "Spike Test - Rapid ramp-up to 100 users"
        ;;
    "endurance")
        run_locust 30 3 "60m" "Endurance Test - 30 users over 1 hour"
        ;;
    "browse-heavy")
        run_locust 50 5 "10m" "Browse Heavy Test - Destination browsing focus" "DestinationOnlyUser"
        ;;
    "search-heavy")
        run_locust 50 5 "10m" "Search Heavy Test - Search intensive" "SearchHeavyUser"
        ;;
    "custom")
        if [ $# -ne 4 ]; then
            echo -e "${RED}Usage: $0 custom <users> <spawn_rate> <run_time>${NC}"
            echo "Example: $0 custom 25 3 '8m'"
            exit 1
        fi
        run_locust $2 $3 $4 "Custom Test - $2 users, spawn rate $3, duration $4"
        ;;
    "web")
        echo -e "${GREEN}Starting Locust Web UI${NC}"
        echo "Open http://localhost:8089 in your browser"
        echo "Press Ctrl+C to stop"
        cd "$LOCUST_DIR"
        locust -f locustfile.py --host=https://samplehotelsearchapp-brnsoo0jo-sagarvs-projects.vercel.app
        ;;
    "help"|*)
        echo -e "${YELLOW}Available test scenarios:${NC}"
        echo "  light      - Light load (10 users, 5 min)"
        echo "  medium     - Medium load (50 users, 10 min)"
        echo "  heavy      - Heavy load (200 users, 15 min)"
        echo "  stress     - Stress test (500 users, 20 min)"
        echo "  spike      - Spike test (100 users, rapid ramp-up)"
        echo "  endurance  - Endurance test (30 users, 1 hour)"
        echo ""
        echo -e "${YELLOW}Specialized user behavior tests:${NC}"
        echo "  browse-heavy - Focus on destination browsing"
        echo "  search-heavy - Focus on hotel searches"
        echo ""
        echo -e "${YELLOW}Other options:${NC}"
        echo "  web        - Start Locust web UI"
        echo "  custom <users> <spawn_rate> <time> - Custom test"
        echo ""
        echo -e "${YELLOW}Examples:${NC}"
        echo "  $0 light"
        echo "  $0 custom 25 3 '8m'"
        echo "  $0 web"
        ;;
esac

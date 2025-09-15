#!/bin/bash

# Setup script for Locust performance testing environment
# Handles virtual environment creation and dependency installation

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}Setting up Locust Performance Testing Environment${NC}"
echo "=================================================="

# Check if python3-venv is installed
if ! dpkg -l | grep -q python3.*-venv; then
    echo -e "${YELLOW}Installing python3-venv package...${NC}"
    sudo apt update
    sudo apt install python3.12-venv -y
    echo -e "${GREEN}python3-venv installed successfully${NC}"
else
    echo -e "${GREEN}python3-venv is already installed${NC}"
fi

# Create virtual environment if it doesn't exist
if [ ! -d "venv" ]; then
    echo -e "${YELLOW}Creating virtual environment...${NC}"
    python3 -m venv venv
    echo -e "${GREEN}Virtual environment created${NC}"
else
    echo -e "${GREEN}Virtual environment already exists${NC}"
fi

# Activate virtual environment
echo -e "${YELLOW}Activating virtual environment...${NC}"
source venv/bin/activate

# Install dependencies
echo -e "${YELLOW}Installing Locust dependencies...${NC}"
pip install --upgrade pip
pip install -r requirements.txt

echo -e "${GREEN}Installation completed successfully!${NC}"
echo ""
echo -e "${BLUE}Next steps:${NC}"
echo "1. Activate virtual environment: source venv/bin/activate"
echo "2. Run validation test: python test_setup.py"
echo "3. Start web UI: ./scripts/run_tests.sh web"
echo "4. Or run a quick test: ./scripts/run_tests.sh light"
echo ""
echo -e "${YELLOW}Note: The run_tests.sh script will automatically activate the virtual environment${NC}"

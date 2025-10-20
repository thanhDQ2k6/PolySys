#!/bin/bash

# Test Execution Script for PolySys
# This script sets up the database and runs all tests

set -e

echo "=========================================="
echo "PolySys Test Execution Script"
echo "=========================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configuration
DB_NAME="polysys"
DB_USER="root"
DB_PASS="12345"
DB_HOST="localhost"

# Function to print colored output
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

print_info() {
    echo -e "${YELLOW}ℹ $1${NC}"
}

# Check if MySQL is running
echo "Step 1: Checking MySQL service..."
if sudo service mysql status > /dev/null 2>&1; then
    print_success "MySQL is running"
else
    print_info "Starting MySQL service..."
    sudo service mysql start
    sleep 3
    print_success "MySQL started"
fi

# Check if database exists, create if not
echo ""
echo "Step 2: Setting up database..."
if mysql -u ${DB_USER} -p${DB_PASS} -e "USE ${DB_NAME}" 2>/dev/null; then
    print_info "Database '${DB_NAME}' already exists"
else
    print_info "Creating database '${DB_NAME}'..."
    mysql -u ${DB_USER} -p${DB_PASS} -e "CREATE DATABASE ${DB_NAME};" 2>/dev/null
    print_success "Database created"
fi

# Import schema
echo ""
echo "Step 3: Importing database schema..."
if [ -f "src/main/resources/Database Generator/db-boot.sql" ]; then
    mysql -u ${DB_USER} -p${DB_PASS} ${DB_NAME} < "src/main/resources/Database Generator/db-boot.sql" 2>/dev/null
    print_success "Schema imported"
else
    print_error "Schema file not found!"
    exit 1
fi

# Import sample data from CSV
echo ""
echo "Step 4: Loading sample data..."

# Load user data
print_info "Loading users from CSV..."
mysql -u ${DB_USER} -p${DB_PASS} ${DB_NAME} << 'EOF'
LOAD DATA LOCAL INFILE 'src/main/resources/Database Generator/user.csv'
INTO TABLE user
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(Id, Password, FullName, Email, @admin)
SET Admin = IF(@admin = 'true', 1, 0);
EOF

# Load video data
print_info "Loading videos from CSV..."
mysql -u ${DB_USER} -p${DB_PASS} ${DB_NAME} << 'EOF'
LOAD DATA LOCAL INFILE 'src/main/resources/Database Generator/video.csv'
INTO TABLE video
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(Id, Title, Poster, @desc, @active, Views, Link)
SET `Desc` = @desc,
    Active = IF(@active = 'true', 1, 0);
EOF

print_success "Sample data loaded"

# Verify data
echo ""
echo "Step 5: Verifying data..."
USER_COUNT=$(mysql -u ${DB_USER} -p${DB_PASS} ${DB_NAME} -se "SELECT COUNT(*) FROM user;")
VIDEO_COUNT=$(mysql -u ${DB_USER} -p${DB_PASS} ${DB_NAME} -se "SELECT COUNT(*) FROM video;")

print_info "Users in database: ${USER_COUNT}"
print_info "Videos in database: ${VIDEO_COUNT}"

if [ "${USER_COUNT}" -ge 5 ] && [ "${VIDEO_COUNT}" -ge 21 ]; then
    print_success "Data verification passed"
else
    print_error "Data verification failed! Expected at least 5 users and 21 videos"
fi

# Compile project
echo ""
echo "Step 6: Compiling project..."
mvn clean compile test-compile -DskipTests > /dev/null 2>&1
if [ $? -eq 0 ]; then
    print_success "Project compiled successfully"
else
    print_error "Compilation failed!"
    exit 1
fi

# Run tests
echo ""
echo "=========================================="
echo "Running Tests"
echo "=========================================="
echo ""

# Choose test type
if [ "$1" == "backend" ]; then
    print_info "Running backend DAO tests only..."
    mvn test -Dtest=*DAOTest
elif [ "$1" == "ui" ]; then
    print_info "Running UI tests only..."
    print_info "Note: Application server must be running at http://localhost:8080/PolySys"
    mvn test -Dtest=*UITest
elif [ "$1" == "user" ]; then
    print_info "Running UserDAO tests only..."
    mvn test -Dtest=UserDAOTest
elif [ "$1" == "video" ]; then
    print_info "Running VideoDAO tests only..."
    mvn test -Dtest=VideoDAOTest
elif [ "$1" == "favorite" ]; then
    print_info "Running FavoriteDAO tests only..."
    mvn test -Dtest=FavoriteDAOTest
elif [ "$1" == "share" ]; then
    print_info "Running ShareDAO tests only..."
    mvn test -Dtest=ShareDAOTest
elif [ "$1" == "login" ]; then
    print_info "Running Login UI tests only..."
    print_info "Note: Application server must be running"
    mvn test -Dtest=LoginUITest
elif [ "$1" == "browse" ]; then
    print_info "Running Video Browsing UI tests only..."
    print_info "Note: Application server must be running"
    mvn test -Dtest=VideoBrowsingUITest
else
    print_info "Running all tests..."
    print_info "Note: UI tests will be skipped if server is not running"
    mvn test
fi

# Test execution complete
echo ""
echo "=========================================="
echo "Test Execution Complete"
echo "=========================================="
echo ""
print_info "Check target/surefire-reports/ for detailed test reports"
print_info "Test results are also available in TEST-RESULTS.md"
echo ""
print_info "Available test options:"
echo "  ./run-tests.sh          - Run all tests"
echo "  ./run-tests.sh backend  - Run all backend DAO tests"
echo "  ./run-tests.sh ui       - Run all UI tests"
echo "  ./run-tests.sh user     - Run UserDAO tests"
echo "  ./run-tests.sh video    - Run VideoDAO tests"
echo "  ./run-tests.sh favorite - Run FavoriteDAO tests"
echo "  ./run-tests.sh share    - Run ShareDAO tests"
echo "  ./run-tests.sh login    - Run Login UI tests"
echo "  ./run-tests.sh browse   - Run Video Browsing UI tests"

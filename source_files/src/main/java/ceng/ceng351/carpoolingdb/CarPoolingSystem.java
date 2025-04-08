package ceng.ceng351.carpoolingdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarPoolingSystem implements ICarPoolingSystem {

    private static String url = "jdbc:h2:mem:carpoolingdb;DB_CLOSE_DELAY=-1"; // In-memory database
    private static String user = "sa";          // H2 default username
    private static String password = "";        // H2 default password

    private Connection connection;

    public void initialize(Connection connection) {
        this.connection = connection;
    }

    //Given: getAllDrivers()
    //Testing 5.16: All Drivers after Updating the Ratings
    @Override
    public Driver[] getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        
        //uncomment following code slice
        String query = "SELECT PIN, rating FROM Drivers ORDER BY PIN ASC;";

        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int PIN = rs.getInt("PIN");
                double rating = rs.getDouble("rating");

                // Create a Driver object with only PIN and rating
                Driver driver = new Driver(PIN, rating);
                drivers.add(driver);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        
        return drivers.toArray(new Driver[0]); 
    }

    
    //5.1 Task 1 Create tables
    @Override
    public int createTables() {
        int tableCount = 0;

        String createParticipantsTable = "CREATE TABLE IF NOT EXISTS Participants (" +
                "PIN INT PRIMARY KEY," +
                "p_name VARCHAR(50)," +
                "age INT" +
                ");";

        String createPassengersTable = "CREATE TABLE IF NOT EXISTS Passengers (" +
                "PIN INT PRIMARY KEY," +
                "membership_status VARCHAR(50)," +
                "FOREIGN KEY (PIN) REFERENCES Participants(PIN)" +
                ");";

        String createDriversTable = "CREATE TABLE IF NOT EXISTS Drivers (" +
                "PIN INT PRIMARY KEY," +
                "rating DOUBLE" +
                ");";

        String createCarsTable = "CREATE TABLE IF NOT EXISTS Cars (" +
                "CarID INT PRIMARY KEY," +
                "PIN INT," +
                "color VARCHAR(50)," +
                "brand VARCHAR(50)," +
                "FOREIGN KEY (PIN) REFERENCES Participants(PIN)" +
                ");";

        String createTripsTable = "CREATE TABLE IF NOT EXISTS Trips (" +
                "TripID INT PRIMARY KEY," +
                "CarID INT," +
                "date DATE," +
                "departure VARCHAR(50)," +
                "destination VARCHAR(50)," +
                "num_seats_available INT," +
                "FOREIGN KEY (CarID) REFERENCES Cars(CarID)" +
                ");";

        String createBookingsTable = "CREATE TABLE IF NOT EXISTS Bookings (" +
                "TripID INT," +
                "PIN INT," +
                "PRIMARY KEY (TripID, PIN)," +
                "booking_status VARCHAR(50)," +
                "FOREIGN KEY (PIN) REFERENCES Participants(PIN)," +
                "FOREIGN KEY (TripID) REFERENCES Trips(TripID)" +
                ");";

        try (Statement statement = this.connection.createStatement()){
            try {
                statement.executeUpdate(createParticipantsTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to create Participants table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createPassengersTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to create Passengers table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createDriversTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to create Drivers table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createCarsTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to create Cars table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createTripsTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to create Trips table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createBookingsTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to create Bookings table: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Failed to create statement: " + e.getMessage());
        }

        return tableCount;
    }


    //5.17 Task 17 Drop tables
    @Override
    public int dropTables() {
        int tableCount = 0;

        String createParticipantsTable = "DROP TABLE Participants;";

        String createPassengersTable = "DROP TABLE Passengers;";

        String createDriversTable = "DROP TABLE Drivers;";

        String createCarsTable = "DROP TABLE Cars;";

        String createTripsTable = "DROP TABLE Trips;";

        String createBookingsTable = "DROP TABLE Bookings;";

        try (Statement statement = this.connection.createStatement()){
            try {
                statement.executeUpdate(createBookingsTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to drop Bookings table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createTripsTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to drop Trips table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createCarsTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to drop Cars table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createDriversTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to drop Drivers table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createPassengersTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to drop Passengers table: " + e.getMessage());
            }

            try {
                statement.executeUpdate(createParticipantsTable);
                tableCount++;
            } catch (SQLException e) {
                System.out.println("Failed to drop Participants table: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Failed to create statement: " + e.getMessage());
        }

        return tableCount;
    }
    
    
    //5.2 Task 2 Insert Participants
    @Override
    public int insertParticipants(Participant[] participants) {
        int rowsInserted = 0;

        int pin = 0;
        int age = 0;
        String name = "";
        String insert = "INSERT INTO Participants (PIN,p_name,age) VALUES (?,?,?)";

        for (Participant participant : participants) {
            age = participant.getAge();
            pin = participant.getPIN();
            name = participant.getP_name();


            try {
                PreparedStatement pstmt = connection.prepareStatement(insert);
                pstmt.setInt(1, pin);
                pstmt.setString(2, name);
                pstmt.setInt(3, age);
                pstmt.executeUpdate();
                rowsInserted++;

            } catch (SQLException e) {
                System.out.println("Failed to insert participant: " + e.getMessage());
            }

        }

        return rowsInserted;
    }

    
    //5.2 Task 2 Insert Passengers
    @Override
    public int insertPassengers(Passenger[] passengers) {
        int rowsInserted = 0;

        int pin = 0;
        String membership_status = "";
        String insert = "INSERT INTO Passengers (PIN,membership_status) VALUES (?,?)";

        for (Passenger passenger : passengers) {
            membership_status = passenger.getMembership_status();
            pin = passenger.getPIN();

            try {
                PreparedStatement pstmt = connection.prepareStatement(insert);
                pstmt.setInt(1, pin);
                pstmt.setString(2, membership_status);
                pstmt.executeUpdate();
                rowsInserted++;

            } catch (SQLException e) {
                System.out.println("Failed to insert passenger: " + e.getMessage());
            }
        }
        
        return rowsInserted;
    }


    //5.2 Task 2 Insert Drivers
    @Override
    public int insertDrivers(Driver[] drivers) {
        int rowsInserted = 0;

        int pin = 0;
        double rating = 0;
        String insert = "INSERT INTO Drivers (PIN,rating) VALUES (?,?)";

        for (Driver driver : drivers) {
            rating = driver.getRating();
            pin = driver.getPIN();

            try {
                PreparedStatement pstmt = connection.prepareStatement(insert);
                pstmt.setInt(1, pin);
                pstmt.setDouble(2, rating);
                pstmt.executeUpdate();
                rowsInserted++;

            } catch (SQLException e) {
                System.out.println("Failed to insert driver: " + e.getMessage());
            }
        }
        
        return rowsInserted;
    }

    
    //5.2 Task 2 Insert Cars
    @Override
    public int insertCars(Car[] cars) {
        int rowsInserted = 0;

        int pin = 0;
        int carid = 0;
        String color = "";
        String brand = "";
        String insert = "INSERT INTO Cars (PIN,CarID,color,brand) VALUES (?,?,?,?)";

        for (Car car : cars) {
            carid = car.getCarID();
            pin = car.getPIN();
            color = car.getColor();
            brand = car.getBrand();

            try {
                PreparedStatement pstmt = connection.prepareStatement(insert);
                pstmt.setInt(1, pin);
                pstmt.setInt(2, carid);
                pstmt.setString(3, color);
                pstmt.setString(4, brand);
                pstmt.executeUpdate();
                rowsInserted++;

            } catch (SQLException e) {
                System.out.println("Failed to insert car: " + e.getMessage());
            }
        }

        return rowsInserted;
    }


    //5.2 Task 2 Insert Trips
    @Override
    public int insertTrips(Trip[] trips) {
        int rowsInserted = 0;

        int tripid = 0;
        int carid = 0;
        String date = "";
        String departure = "";
        String destination = "";
        int numSeatsAvailable = 0;

        String insert = "INSERT INTO Trips (TripID,CarID,date,departure,destination,num_Seats_Available) VALUES (?,?,?,?,?,?)";

        for (Trip trip : trips) {
            tripid = trip.getTripID();
            carid = trip.getCarID();
            date = trip.getDate();
            departure = trip.getDeparture();
            destination = trip.getDestination();
            numSeatsAvailable = trip.getNum_seats_available();

            try {
                PreparedStatement pstmt = connection.prepareStatement(insert);
                pstmt.setInt(1, tripid);
                pstmt.setInt(2, carid);
                pstmt.setString(3, date);
                pstmt.setString(4, departure);
                pstmt.setString(5, destination);
                pstmt.setInt(6, numSeatsAvailable);
                pstmt.executeUpdate();
                rowsInserted++;

            } catch (SQLException e) {
                System.out.println("Failed to insert trip: " + e.getMessage());
            }
        }

        return rowsInserted;
    }

    //5.2 Task 2 Insert Bookings
    @Override
    public int insertBookings(Booking[] bookings) {
        int rowsInserted = 0;
        
        int tripid = 0;
        int pin = 0;
        String booking_status = "";

        String insert = "INSERT INTO Bookings (TripID,PIN,booking_status) VALUES (?,?,?)";

        for (Booking booking : bookings) {
            tripid = booking.getTripID();
            pin = booking.getPIN();
            booking_status = booking.getBooking_status();

            try {
                PreparedStatement pstmt = connection.prepareStatement(insert);
                pstmt.setInt(1,tripid);
                pstmt.setInt(2, pin);
                pstmt.setString(3, booking_status);
                pstmt.executeUpdate();
                rowsInserted++;

            } catch (SQLException e) {
                System.out.println("Failed to insert bookings: " + e.getMessage());
            }
        }

        return rowsInserted;
    }

    
    //5.3 Task 3 Find all participants who are recorded as both drivers and passengers
    @Override
    public Participant[] getBothPassengersAndDrivers() {

        List<Participant> PnD = new ArrayList<>();
        String query = "SELECT * FROM Participants " +
                "WHERE PIN IN (SELECT d.PIN FROM Drivers d, Passengers p WHERE d.PIN = p.PIN) " +
                "ORDER BY PIN ASC;";
    	try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                Participant p = new Participant(rs.getInt("PIN"), rs.getString("p_name"), rs.getInt("age"));
                PnD.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get passengers and drivers: " + e.getMessage());
        }

    	return PnD.toArray(new Participant[0]);
    }

 
    //5.4 Task 4 Find the PINs, names, ages, and ratings of drivers who do not own any cars
    @Override
    public QueryResult.DriverPINNameAgeRating[] getDriversWithNoCars() {
    	
        List<QueryResult.DriverPINNameAgeRating> results = new ArrayList<>();
        String query ="SELECT p.PIN, p.p_name, p.age, d.rating " +
                "FROM Drivers d, Participants p " +
                "WHERE d.PIN = p.PIN AND d.PIN NOT IN (SELECT C.PIN FROM Cars c) " +
                "ORDER BY p.PIN ASC;";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                QueryResult.DriverPINNameAgeRating p = new QueryResult.DriverPINNameAgeRating(rs.getInt("PIN"),
                        rs.getString("p_name"),
                        rs.getInt("age"),
                        rs.getDouble("rating"));

                results.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get drivers with no car: " + e.getMessage());
        }
    	
    	return results.toArray(new QueryResult.DriverPINNameAgeRating[0]);
    }
 
    
    //5.5 Task 5 Delete Drivers who do not own any cars
    @Override
    public int deleteDriversWithNoCars() {
        int rowsDeleted = 0;

        List<Integer> pins = new ArrayList<>();
        String query = "SELECT d.PIN FROM Drivers d WHERE d.PIN NOT IN (SELECT c.PIN FROM Cars c);";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){pins.add(rs.getInt("PIN"));}

        } catch (SQLException e) {
            System.out.println("Failed to get passengers and drivers: " + e.getMessage());
        }

        query = "DELETE FROM Drivers d WHERE d.PIN = ?;";
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            try {
                for (int pin : pins) {
                    pstmt.setInt(1, pin);
                    pstmt.executeUpdate();
                    rowsDeleted++;
                }
            } catch (SQLException e) {
                System.out.println("Failed to delete drivers with no :" + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Failed to create statement: " + e.getMessage());
        }
        return rowsDeleted;  
    }

    
    //5.6 Task 6 Find all cars that are not taken part in any trips
    @Override
    public Car[] getCarsWithNoTrips() {

        List<Car> results = new ArrayList<>();
        String query ="SELECT c.CarID, c.PIN, c.color, c.brand " +
                "FROM Cars c " +
                "WHERE c.CarID NOT IN (SELECT t.CarID FROM Trips t) " +
                "ORDER BY c.CarID ASC;";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                Car C = new Car(rs.getInt("CarID"),
                        rs.getInt("PIN"),
                        rs.getString("color"),
                        rs.getString("brand"));

                results.add(C);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get cars that are not taken part in any trips: " + e.getMessage());
        }
    	
        return results.toArray(new Car[0]);
    }
    
    
    //5.7 Task 7 Find all passengers who didn't book any trips
    @Override
    public Passenger[] getPassengersWithNoBooks() {

        List<Passenger> results = new ArrayList<>();
        String query ="SELECT p.PIN, p.membership_status " +
                "FROM Passengers p " +
                "WHERE p.PIN NOT IN (SELECT b.PIN FROM Bookings b) " +
                "ORDER BY p.PIN ASC;";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                Passenger p = new Passenger(rs.getInt("PIN"),
                        rs.getString("membership_status"));

                results.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get  passengers who didn't book any trips: " + e.getMessage());
        }
    	
        return results.toArray(new Passenger[0]);
    }


    //5.8 Task 8 Find all trips that depart from the specified city to specified destination city on specific date
    @Override
    public Trip[] getTripsFromToCitiesOnSpecificDate(String departure, String destination, String date) {

        List<Trip> results = new ArrayList<>();
        String query ="SELECT * " +
                "FROM Trips t " +
                "WHERE t.date = ? AND t.departure = ? AND t.destination = ? " +
                "ORDER BY t.TripID ASC;";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, date);
            pstmt.setString(2, departure);
            pstmt.setString(3, destination);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Trip t = new Trip(rs.getInt("TripID"),
                        rs.getInt("CarID"),
                        rs.getString("date"),
                        rs.getString("departure"),
                        rs.getString("destination"),
                        rs.getInt("num_seats_available"));
                results.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get trips that specified: " + e.getMessage());
        }

        return results.toArray(new Trip[0]);
    }


    //5.9 Task 9 Find the PINs, names, ages, and membership_status of passengers who have bookings on all trips destined at a particular city
    @Override
    public QueryResult.PassengerPINNameAgeMembershipStatus[] getPassengersWithBookingsToAllTripsForCity(String city) {

        List<QueryResult.PassengerPINNameAgeMembershipStatus> results = new ArrayList<>();
        String query ="SELECT p.PIN, p2.p_name, p2.age, p.membership_status " +
                "FROM Passengers p, Participants p2 " +
                "WHERE p.PIN = p2.PIN AND NOT EXISTS ( " +
                "SELECT * " +
                "FROM Trips t " +
                "WHERE t.destination = ? AND NOT EXISTS ( " +
                "SELECT * " +
                "FROM Bookings b " +
                "WHERE p.PIN = b.PIN AND b.TripID = t.TripID)) " +
                "ORDER BY p.PIN ASC;";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                QueryResult.PassengerPINNameAgeMembershipStatus p =
                        new QueryResult.PassengerPINNameAgeMembershipStatus(rs.getInt("PIN"),
                        rs.getString("p_name"),
                        rs.getInt("age"),
                        rs.getString("membership_status"));
                results.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get passengers who have bookings on all trips destined at a particular city: " + e.getMessage());
        }
    	
        return results.toArray(new QueryResult.PassengerPINNameAgeMembershipStatus[0]);
    }

    
    //5.10 Task 10 For a given driver PIN, find the CarIDs that the driver owns and were booked at most twice.    
    @Override
    public Integer[] getDriverCarsWithAtMost2Bookings(int driverPIN) {

        List<Integer> results = new ArrayList<>();
        String query ="SELECT c.CarID " +
                "FROM Cars c, Trips t, Bookings b " +
                "WHERE c.PIN = ? AND c.CarID = t.CarID AND b.TripID = t.TripID " +
                "GROUP BY c.CarID " +
                "HAVING COUNT(*) < 3 " +
                "ORDER BY c.CarID ASC;";


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, driverPIN);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                results.add(rs.getInt("CarID"));
            }

        } catch (SQLException e) {
            System.out.println("Failed to get the CarIDs: " + e.getMessage());
        }
    	
        return results.toArray(new Integer[0]);  // Return the list as an array
    }


    //5.11 Task 11 Find the average age of passengers with "Confirmed" bookings (i.e., booking_status is ”Confirmed”) on trips departing from a given city and within a specified date range
    @Override
    public Double getAvgAgeOfPassengersDepartFromCityBetweenTwoDates(String city, String start_date, String end_date) {
        Double averageAge = null;

        String query ="SELECT AVG(p.age) AS AvgAge " +
                "FROM Participants p, Trips t, Bookings b " +
                "WHERE b.booking_status = 'Confirmed' AND b.TripID = t.TripID AND t.departure = ? AND t.date >= ? AND t.date <= ? AND p.PIN = b.PIN;";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city);
            pstmt.setString(2, start_date);
            pstmt.setString(3, end_date);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) averageAge = rs.getDouble("AvgAge");

        } catch (SQLException e) {
            System.out.println("Failed to get the average age: " + e.getMessage());
        }

        return averageAge;
    }


    //5.12 Task 12 Find Passengers in a Given Trip.
    @Override
    public QueryResult.PassengerPINNameAgeMembershipStatus[] getPassengerInGivenTrip(int TripID) {

        List<QueryResult.PassengerPINNameAgeMembershipStatus> results = new ArrayList<>();
        String query ="SELECT p.PIN, p2.p_name, p2.age, p.membership_status " +
                "FROM Passengers p, Participants p2, Bookings b " +
                "WHERE p.PIN = p2.PIN AND p.PIN = b.PIN AND b.TripID = ? " +
                "ORDER BY p.PIN ASC;";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, TripID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                QueryResult.PassengerPINNameAgeMembershipStatus p =
                        new QueryResult.PassengerPINNameAgeMembershipStatus(rs.getInt("PIN"),
                                rs.getString("p_name"),
                                rs.getInt("age"),
                                rs.getString("membership_status"));
                results.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get passengers in a Given Trip: " + e.getMessage());
        }

        return results.toArray(new QueryResult.PassengerPINNameAgeMembershipStatus[0]);
    }


    //5.13 Task 13 Find Drivers’ Scores
    @Override
    public QueryResult.DriverScoreRatingNumberOfBookingsPIN[] getDriversScores() {
        
    	List<QueryResult.DriverScoreRatingNumberOfBookingsPIN> results = new ArrayList<>();
        String query ="SELECT d.rating, COUNT(*) AS numberOfBookings, COUNT(*) * d.rating AS score, d.PIN " +
                "FROM Drivers d, Trips t, Bookings b, Cars c " +
                "WHERE d.PIN = c.PIN AND t.TripID = b.TripID AND t.CarID = c.CarID " +
                "GROUP BY d.PIN " +
                "ORDER BY score DESC;";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                QueryResult.DriverScoreRatingNumberOfBookingsPIN p =
                        new QueryResult.DriverScoreRatingNumberOfBookingsPIN(rs.getDouble("score"),
                                rs.getDouble("rating"),
                                rs.getInt("numberOfBookings"),
                                rs.getInt("PIN"));
                results.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get Drivers’ Scores: " + e.getMessage());
        }

        return results.toArray(new QueryResult.DriverScoreRatingNumberOfBookingsPIN[0]);
    }

    
    //5.14 Task 14 Find average ratings of drivers who have trips destined to each city
    @Override
    public QueryResult.CityAndAverageDriverRating[] getDriversAverageRatingsToEachDestinatedCity() {

        List<QueryResult.CityAndAverageDriverRating> results = new ArrayList<>();
        String query ="SELECT t.destination AS city, AVG(d.rating) AS avgRating " +
                "FROM Drivers d, Trips t, Cars c " +
                "WHERE d.PIN = c.PIN AND c.CarID = t.CarID " +
                "GROUP BY t.destination " +
                "ORDER BY city ASC;";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                QueryResult.CityAndAverageDriverRating p =
                        new QueryResult.CityAndAverageDriverRating(rs.getString("city"),
                                rs.getDouble("avgRating"));
                results.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get passengers in a Given Trip: " + e.getMessage());
        }
    	
        return results.toArray(new QueryResult.CityAndAverageDriverRating[0]);
    }


    //5.15 Task 15 Find total number of bookings of passengers for each membership status
    @Override
    public QueryResult.MembershipStatusAndTotalBookings[] getTotalBookingsEachMembershipStatus() {

        List<QueryResult.MembershipStatusAndTotalBookings> results = new ArrayList<>();
        String query ="SELECT p.membership_status, COUNT(*) AS totalC " +
                "FROM Passengers p, Bookings b " +
                "WHERE p.PIN = b.PIN " +
                "GROUP BY p.membership_status " +
                "ORDER BY p.membership_status ASC;";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                QueryResult.MembershipStatusAndTotalBookings p =
                        new QueryResult.MembershipStatusAndTotalBookings(rs.getString("membership_status"),
                                rs.getInt("totalC"));
                results.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get passengers in a Given Trip: " + e.getMessage());
        }
    	
        return results.toArray(new QueryResult.MembershipStatusAndTotalBookings[0]);
    }

    
    //5.16 Task 16 For the drivers' ratings, if rating is smaller than 2.0 or equal to 2.0, update the rating by adding 0.5.
    @Override
    public int updateDriverRatings() {
        int rowsUpdated = 0;

        List<Integer> pins = new ArrayList<>();
        String query = "SELECT d.PIN FROM Drivers d WHERE d.rating <= 2;";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){pins.add(rs.getInt("PIN"));}

        } catch (SQLException e) {
            System.out.println("Failed to get drivers <= 2: " + e.getMessage());
        }

        query = "UPDATE Drivers SET rating = rating + 0.5 WHERE PIN = ?;";
        try (PreparedStatement pstmt = this.connection.prepareStatement(query)) {
            try {
                for (int pin : pins) {
                    pstmt.setInt(1, pin);
                    pstmt.executeUpdate();
                    rowsUpdated++;
                }
            } catch (SQLException e) {
                System.out.println("Failed to update drivers:" + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Failed to create statement: " + e.getMessage());
        }
    	
        return rowsUpdated;
    }
    

    //6.1 (Optional) Task 18 Find trips departing from the given city
    @Override
    public Trip[] getTripsFromCity(String city) {
        
    	/*****************************************************/
        /*****************************************************/
        /*****************  TODO  (Optional)   ***************/
        /*****************************************************/
        /*****************************************************/
    	
        return new Trip[0];
    }
    
    
    //6.2 (Optional) Task 19 Find all trips that have never been booked
    @Override
    public Trip[] getTripsWithNoBooks() {
        
    	/*****************************************************/
        /*****************************************************/
        /*****************  TODO  (Optional)   ***************/
        /*****************************************************/
        /*****************************************************/
    	
        return new Trip[0];
    }
    
    
    //6.3 (Optional) Task 20 For each driver, find the trip(s) with the highest number of bookings
    @Override
    public QueryResult.DriverPINandTripIDandNumberOfBookings[] getTheMostBookedTripsPerDriver() {
        
    	/*****************************************************/
        /*****************************************************/
        /*****************  TODO  (Optional)   ***************/
        /*****************************************************/
        /*****************************************************/
    	
        return new QueryResult.DriverPINandTripIDandNumberOfBookings[0];
    }
    
    
    //6.4 (Optional) Task 21 Find Full Cars
    @Override
    public QueryResult.FullCars[] getFullCars() {
        
    	/*****************************************************/
        /*****************************************************/
        /*****************  TODO  (Optional)   ***************/
        /*****************************************************/
        /*****************************************************/
    	
        return new QueryResult.FullCars[0];
    }

}

# CENG351 PA1 - Car Pooling System

## Overview
This project implements a **Car Pooling System** using Java and SQL.  
You will manage a database through a Java application by executing SQL queries inside Java methods.  
The project uses an **embedded H2 database** and **Maven** for dependency management.

The goal is to perform database operations like table creation, insertion, query, update, and deletion through Java.

## Features
- Create database tables with correct primary and foreign keys.
- Insert participants, passengers, drivers, cars, trips, and bookings.
- Find participants who are both drivers and passengers.
- Find drivers without cars and delete them.
- Find unbooked passengers and unused cars.
- Query trips between cities and passengers on trips.
- Perform aggregate queries (e.g., driver scores, average ratings).
- Update driver ratings based on performance.
- Drop database tables cleanly.
- **(Optional)** Solve additional challenges like finding full cars and most booked trips.

## Project Structure
- `CarPoolingSystem.java`: Your main implementation file.
- Other helper classes like `Booking.java`, `Car.java`, `Participant.java`, etc., are **provided** and **must not be modified**.
- `Evaluation.java`: Test runner that generates outputs for grading.
- Data files (`.txt`) for populating initial database entries.
- `pom.xml`: Maven configuration file (manages dependencies like H2 database).

## How to Compile and Run

### Compile with Maven
```bash
mvn compile
```

### Run Evaluation
```bash
mvn exec:java -Dexec.mainClass="ceng.ceng351.carPoolingSystem.Evaluation"
```

This command will:
- Create the in-memory H2 database.
- Call your implemented methods in `CarPoolingSystem.java`.
- Write outputs into `output/Output.txt`.

## Database Information
- **Database**: H2 In-Memory Database (`jdbc:h2:mem:carpoolingdb`)
- **Username**: `sa`
- **Password**: *(empty)*

No installation needed. The database is created automatically during execution.

## Tasks
You are responsible for implementing **17 mandatory tasks** such as:
- Creating and dropping tables.
- Inserting data.
- Performing queries with sorting requirements.
- Updating data.
- Calculating statistics like averages, totals, scores.

Each method is predefined in the `ICarPoolingSystem` interface and must be completed inside `CarPoolingSystem.java`.

Extra (optional) tasks like finding full cars are provided for additional practice but **will not be graded**.

## Important Notes
- Use SQL queries inside Java (`Statement` and `PreparedStatement`).
- Do not use other Java-only techniques to process data (e.g., filtering results manually).
- Follow exactly the expected output format and sorting orders.
- Submit only your `CarPoolingSystem.java` file.
- Cheating will be strictly penalized.

## Example Maven Commands
```bash
# Clean project
mvn clean

# Compile project
mvn compile

# Execute Evaluation tests
mvn exec:java -Dexec.mainClass="ceng.ceng351.carPoolingSystem.Evaluation"
```

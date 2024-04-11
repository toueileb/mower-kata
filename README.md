# Mower Kata

## 1. Prerequisites

To run this program, ensure that you have Java and Maven installed on your computer, with Java version 17 or higher.

## 2. Running the Program

1. **Step 1:** Navigate to the root directory of the project where the `pom.xml` file is located.
2. **Step 2:** Run the following Maven command to build the project:
   mvn clean install
3. **Step 3:** After successfully building the project, you can run the program using the following command:
   java -jar target/mower-challenge-1.0-SNAPSHOT-jar-with-dependencies.jar

## 3. Input File Format

The application will prompt you to enter the file path containing the instructions for the mowers.

1. Enter the file path and press Enter.

### Example Input File:

5 5
1 2 N
GAGAGAGAA
3 3 E
AADAADADDA

### Example Expected Final Positions:

[1 3 N, 5 1 E]


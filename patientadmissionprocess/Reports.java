/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.patientadmissionprocess;

/**
 *
 * @author Student
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Reports {

static Scanner scanner = new Scanner(System.in);    // scanner for user input

// Reports sub-menu

public static void reportsMenu() {

int choice;

do {

System.out.println("\n----------------------------------------");
System.out.println("                REPORTS                   ");
System.out.println("----------------------------------------");
System.out.println("\n1. Display All Registered Patients");
System.out.println("\n2. Display All Available Beds");
System.out.println("\n3. Display All Occupied Beds");
System.out.println("\n4. Display Total Number of Registered Patients");
System.out.println("\n5. Display Total Number of Occupied Beds");
System.out.println("\n6. Display Ward Occupancy Percentage");
System.out.println("\n7. Back to Main Menu");
System.out.println("\n----------------------------------------");
System.out.print("Enter your choice: ");

choice = getMenuChoice();

switch (choice) {

case 1:

displayAllPatients();

//to end loop
              break;

case 2:

displayAvailableBeds();

//to end loop
              break;

case 3:

displayOccupiedBeds();

//to end loop
              break;

case 4:

displayTotalPatients();

//to end loop
              break;

case 5:

displayTotalOccupiedBeds();

//to end loop
              break;

case 6:

displayOccupancyPercentage();

//to end loop
              break;

case 7:

System.out.println("\nReturning to Main Menu.");

//to end loop
              break;

default:

System.out.println("\nInvalid choice, please try again and select 1 up to 7.");

    }

} while (choice != 7);

}

// menu validation, same pattern as PatientAdmissionProcess and BedManagement

public static int getMenuChoice() {

while (true) {

String input = scanner.nextLine();

try {

int choice = Integer.parseInt(input);

if (choice >= 1 && choice <= 7) {

return choice;

}

System.out.print("Please enter a number between 1 up to 7: ");

} catch (NumberFormatException e) {

System.out.print("Invalid input. Please enter a number: ");

        }
    }
}

// Display all registered patients, sorted by Patient ID

public static void displayAllPatients() {

System.out.println("\n--------------------------------------------");
System.out.println("       ALL REGISTERED PATIENTS (SORTED)       ");
System.out.println("--------------------------------------------");

if (PatientAdmissionProcess.patients.isEmpty()) {

System.out.println("There are currently no registered patients.");

// back to reports menu
              return;
}

ArrayList<Patient> sortedPatients = sortPatientsByID();

System.out.println("Total Patients: " + sortedPatients.size());
System.out.println();

for (int i = 0; i < sortedPatients.size(); i++) {

System.out.println("\n--------------------------------------------");
System.out.println("Patient #" + (i + 1));
System.out.println("\n--------------------------------------------");

sortedPatients.get(i).displayDetails();

    }
}

// Sort a copy of the patient list by Patient ID using a simple bubble sort

public static ArrayList<Patient> sortPatientsByID() {

ArrayList<Patient> sortedPatients = new ArrayList<>(PatientAdmissionProcess.patients);

int n = sortedPatients.size();

for (int i = 0; i < n - 1; i++) {

for (int j = 0; j < n - 1 - i; j++) {

Patient current = sortedPatients.get(j);
Patient next = sortedPatients.get(j + 1);

if (current.getPatientID().compareToIgnoreCase(next.getPatientID()) > 0) {

// swap the two patients

sortedPatients.set(j, next);
sortedPatients.set(j + 1, current);

        }
    }
}

    return sortedPatients;
}

// Display all available beds (delegates to BedManagement)

public static void displayAvailableBeds() {

BedManagement.displayAvailableBeds();

}

// Display all occupied beds (delegates to BedManagement)

public static void displayOccupiedBeds() {

BedManagement.displayOccupiedBeds();

}

// Display the total number of registered patients

public static void displayTotalPatients() {

System.out.println("\n--------------------------------------------");
System.out.println("Total Registered Patients: " + PatientAdmissionProcess.patients.size());
System.out.println("--------------------------------------------");

}

// Display the total number of occupied beds

public static void displayTotalOccupiedBeds() {

int occupiedCount = countOccupiedBeds();

System.out.println("\n--------------------------------------------");
System.out.println("Total Occupied Beds: " + occupiedCount);
System.out.println("--------------------------------------------");

}

// Display the ward occupancy percentage

public static void displayOccupancyPercentage() {

int totalBeds = 20;

int occupiedCount = countOccupiedBeds();

double percentage = ((double) occupiedCount / totalBeds) * 100;

System.out.println("\n--------------------------------------------");
System.out.println("Occupied Beds : " + occupiedCount + " / " + totalBeds);
System.out.printf("Ward Occupancy: %.2f%%\n", percentage);
System.out.println("--------------------------------------------");

}

// Count how many beds in the ward are currently occupied

public static int countOccupiedBeds() {

int count = 0;

for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

if (BedManagement.ward[row][col].isOccupied()) {

count++;

        }
    }
}

    return count;
}

}
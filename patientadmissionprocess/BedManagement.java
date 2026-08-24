/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.patientadmissionprocess;

/**
 *
 * @author Student
 */

import java.util.Scanner;

public class BedManagement {

static Scanner scanner = new Scanner(System.in);    // scanner for user input

// ward is a 4 x 5 layout of beds, giving 20 beds total (B01 - B20)

static Bed[][] ward = new Bed[4][5];

// to make sure the ward is initialized before it is used to display available wardsd and beds
    
public static void initializeWard() {

int bedNumber = 1;

for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

// format bed ID as B01 up to B20

String bedID = String.format("B%02d", bedNumber);

ward[row][col] = new Bed(bedID);

bedNumber++;

        }
    }
}

// bed management sub-menu

public static void bedManagementMenu() {

// to make sure the ward exists before opening the menu

if (ward[0][0] == null) {
    
initializeWard();
       
}

int choice;

do {

System.out.println("\n----------------------------------------");
System.out.println("            BED MANAGEMENT               ");
System.out.println("----------------------------------------");
System.out.println("\n1. Allocate Bed to Inpatient");
System.out.println("\n2. Release Bed");
System.out.println("\n3. Display Ward Layout");
System.out.println("\n4. Display Available Beds");
System.out.println("\n5. Display Occupied Beds");
System.out.println("\n6. Back to Main Menu");
System.out.println("\n----------------------------------------");
System.out.print("Enter your choice: ");

choice = getMenuChoice();

switch (choice) {

case 1:

allocateBed();

// to end loop fo the choices menu and move to the program selected

    break;

case 2:

releaseBed();

    break;

case 3:

displayWardLayout();

break;

case 4:

displayAvailableBeds();

break;

case 5:

displayOccupiedBeds();

break;

case 6:

System.out.println("\nReturning to Main Menu.");

break;

default:

System.out.println("\nInvalid choice, please try again and select 1 up to 6.");

        }

    } while (choice != 6);

}

// menu validation, same pattern as PatientAdmissionProcess

public static int getMenuChoice() {

while (true) {

String input = scanner.nextLine();

try {

int choice = Integer.parseInt(input);

if (choice >= 1 && choice <= 6) {

return choice;

        }

System.out.print("Please enter a number between 1 up to 6: ");

} catch (NumberFormatException e) {

System.out.print("Invalid input. Please enter a number: ");

        }
    }
}

// display an available bed to an inpatient

public static void allocateBed() {

System.out.println("--------------------------------------------");
System.out.println("              ALLOCATE BED                   ");
System.out.println("--------------------------------------------");

System.out.print("Enter Patient ID: ");

String patientID = scanner.nextLine().trim();

// to prevent empty Patient ID
        
if (patientID.isEmpty()) {

System.out.println("\nERROR! Patient ID cannot be empty.");

return;
        
}

// use the patient list and findPatient method already in PatientAdmissionProcess

Patient patient = PatientAdmissionProcess.findPatient(patientID);

if (patient == null) {

System.out.println("\nNo patient was found with Patient ID: " + patientID);

return;
        
}

// only inpatients may be allocated a bed

if (patient.getPatientCategory() != PatientCategory.INPATIENT) {

System.out.println("\nERROR! Only Inpatients may be allocated a hospital bed.");
System.out.println("Patient category is: " + patient.getPatientCategory());

return;
        
}

// check the patient does not already occupy a bed


if (findBedByPatientID(patientID) != null) {

System.out.println("\nERROR! This patient already occupies a bed.");

    return;
        
}

// find the first available bed

Bed bed = findAvailableBed();

if (bed == null) {

System.out.println("\nERROR! No beds are available at this time.");

    return;
       
}

bed.setOccupied(true);
bed.setPatientID(patientID);

// save the updated bed data to file

DataStorage.saveBeds();

System.out.println("\n--------------------------------------------");
System.out.println("Bed " + bed.getBedID() + " allocated to Patient ID: " + patientID);
System.out.println("--------------------------------------------");

}

// release a bed when a patient is discharged

public static void releaseBed() {

System.out.println("--------------------------------------------");
System.out.println("               RELEASE BED                    ");
System.out.println("--------------------------------------------");

System.out.print("Enter Patient ID to discharge: ");

String patientID = scanner.nextLine().trim();

// display empty Patient ID

if (patientID.isEmpty()) {

System.out.println("\nERROR! Patient ID cannot be empty.");

    return;
        
}

Bed bed = findBedByPatientID(patientID);

if (bed == null) {

System.out.println("\nNo occupied bed was found for Patient ID: " + patientID);

    return;
        
}

bed.setOccupied(false);
bed.setPatientID(null);

// save the updated bed data to file

DataStorage.saveBeds();

System.out.println("\n--------------------------------------------");
System.out.println("Bed " + bed.getBedID() + " has been released.");
System.out.println("--------------------------------------------");

}

// display the complete ward layout using nested loops (4 x 5)

public static void displayWardLayout() {

// to make sure beds have been created
        
if (ward[0][0] == null) {
            
initializeWard();
        
}

System.out.println("\n--------------------------------------------");
System.out.println("              WARD BED LAYOUT                 ");
System.out.println("--------------------------------------------");

        for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

Bed bed = ward[row][col];

// to check that bed exists before using it
                
if (bed != null) {

// mark occupied beds with an asterisk

String marker = bed.isOccupied() ? "*" : " ";

System.out.print(bed.getBedID() + marker + "  ");
                
        }
    }

System.out.println();

}

System.out.println("\n(* means occupied bed)");
System.out.println("--------------------------------------------");

}

// display available (unoccupied) beds

public static void displayAvailableBeds() {

if (ward[0][0] == null) {
            
initializeWard();
   
}

System.out.println("\n--------------------------------------------");
System.out.println("              AVAILABLE BEDS                  ");
System.out.println("--------------------------------------------");

boolean foundAny = false;

for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

Bed bed = ward[row][col];

// to make sure bed exists
                
if (bed != null && !bed.isOccupied()) {

System.out.println(bed.getBedID());

foundAny = true;

        }
    }
}

if (!foundAny) {

System.out.println("There are currently no available beds.");

}

System.out.println("--------------------------------------------");

}

// display occupied beds along with the patient ID assigned to each

public static void displayOccupiedBeds() {

if (ward[0][0] == null) {
            
initializeWard();
        
}

System.out.println("\n--------------------------------------------");
System.out.println("              OCCUPIED BEDS                   ");
System.out.println("--------------------------------------------");

boolean foundAny = false;

for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

Bed bed = ward[row][col];

// to make sure bed exists
                
if (bed != null && bed.isOccupied()) {

System.out.println(bed.getBedID() + " - Patient ID: " + bed.getPatientID());

foundAny = true;

        }
    }
}

if (!foundAny) {

System.out.println("There are currently no occupied beds.");

    }
}

// find the first available bed by scanning the ward, row by row

public static Bed findAvailableBed() {

if (ward[0][0] == null) {
            
initializeWard();
       
}

for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

if (ward[row][col] != null && !ward[row][col].isOccupied()) {

return ward[row][col];

        }
    }
}

return null;
    
}

// find the bed currently occupied by a given Patient ID

public static Bed findBedByPatientID(String patientID) {

if (ward[0][0] == null) {
            
initializeWard();
        
}

for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

Bed bed = ward[row][col];

// to check patient ID is not null before using equalsIgnoreCase

if (bed != null && bed.isOccupied() && bed.getPatientID() != null && bed.getPatientID().equalsIgnoreCase(patientID)) {

return bed;

        }
    }
}

return null;
    
}

// bed class

static class Bed {

private String bedID;
private boolean occupied;
private String patientID;

// Constructor

public Bed(String bedID) {

this.bedID = bedID;
this.occupied = false;
this.patientID = null;

}

// Getters

public String getBedID() {

return bedID;

}

public boolean isOccupied() {

return occupied;

}

public String getPatientID() {

return patientID;

}

// Setters

public void setOccupied(boolean occupied) {

this.occupied = occupied;

}

public void setPatientID(String patientID) {

this.patientID = patientID;

        }
    }
}
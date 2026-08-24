/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.patientadmissionprocess;

/**
 *
 * @author Student
 */

import java.util.ArrayList;
import java.util.Scanner;     //accept user input

public class PatientAdmissionProcess {

static Scanner scanner = new Scanner(System.in);    // scanner for user input

static ArrayList<Patient> patients = new ArrayList<>();     // arraylist to store patient records

public static void main(String[] args) {

// initialize the ward beds before the menu starts

BedManagement.initializeWard();

// load any previously saved patient and bed data

DataStorage.loadPatients();
DataStorage.loadBeds();

// menu for selecting options by the user

int choice;

do {
    
displayMenu();
choice = getMenuChoice();

switch (choice) {

case 1:
    
registerPatient();

//to end loop
              break;

case 2:
                    
searchPatient();

//to end loop
               break;

case 3:
    
updatePatient();

//to end loop
                break;

case 4:
      
deletePatient();

//to end loop
                 break;

case 5:
    
displayAllPatients();

//to end loop
                 break;

case 6:

BedManagement.bedManagementMenu();

//to end loop
                 break;

case 7:

Reports.reportsMenu();

//to end loop
                 break;

case 8:
    
System.out.println("\n----------------------------------------");
System.out.println("          MediCare Hospital               ");
System.out.println("   Hospital Patient Admission System.     ");
System.out.println("\n----------------------------------------");
                    
//to end loop
             break;

default:
    
System.out.println("\nInvalid choice, please try again and select 1 up to 8.");

    }

} while (choice != 8);

scanner.close();

}

public static void displayMenu() {

System.out.println("\n");
System.out.println("\n----------------------------------------");
System.out.println("MEDICARE HOSPITAL: PATIENT ADMISSION SYSTEM");
System.out.println("      Handled with care and love.         ");
System.out.println("\n----------------------------------------");
System.out.println("         PATIENT MANAGEMENT               ");
System.out.println("----------------------------------------------------");

// choices for users options

System.out.println("\n1. Register New Patient");
System.out.println("\n2. Search Patient");
System.out.println("\n3. Update Patient Details");
System.out.println("\n4. Delete Patient");
System.out.println("\n5. Display All Patients");
System.out.println("\n6. Bed Management");
System.out.println("\n7. Reports");
System.out.println("\n8. Exit");
System.out.println("\n----------------------------------------");
System.out.print("Enter your choice: ");

}
    
// menu validation

public static int getMenuChoice() {

while (true) {

String input = scanner.nextLine();

try {

int choice = Integer.parseInt(input);

if (choice >= 1 && choice <= 8) {
    
return choice;
                
}

System.out.print("Please enter a number between 1 up to 8: ");

} catch (NumberFormatException e) {

System.out.print("Invalid input. Please enter a number: ");

        }
    }
}

// Register Patient
    
public static void registerPatient() {

String patientID;

// Patient ID ( created by user )

while (true) {

//prompt questions

System.out.print("Enter Patient ID: ");
patientID = scanner.nextLine().trim();

if (patientID.isEmpty()) {
    
System.out.println("Patient ID cannot be empty.");

//do not endloop program but execute the next step 
                continue;
}

if (findPatient(patientID) != null) {
 
System.out.println("ERROR! Invalid Patient ID entry.");
System.out.println("A patient with this ID already exists.");

//do not endloop program but execute the next step 
                continue;
    }

            break;
}

// Patient personal deatils 
String firstName = getName("Enter First Name: ");       // First Name
String lastName = getName("Enter Last Name: ");         // Last Name
int age = getAge();                                     // Age class and data type
String gender = getGender();                            // Gender class and data type
String medicalCondition;                                // Medical Condition class and data type         

while (true) {

System.out.print("Enter Medical Condition: ");
medicalCondition = scanner.nextLine().trim();

if (!medicalCondition.isEmpty()) {
    
// end loop until correct information is entered.
                break;
}

System.out.println("ERROR! Invalid medical condition entry.");
System.out.println("Medical condition cannot be empty.");

}

// Patient Category
        
PatientCategory category = getPatientCategory();

// Create patient object - Inpatients need ward and bed information,
// Outpatients and Emergency patients use the base Patient class

Patient patient;

if (category == PatientCategory.INPATIENT) {

String wardNumber = getWardOrBedNumber("Enter Ward Number: ");
String bedNumber = getWardOrBedNumber("Enter Bed Number: ");

patient = new Inpatient(patientID, firstName, lastName, age, gender, medicalCondition, wardNumber, bedNumber);

} else {

patient = new Patient(patientID, firstName, lastName, age, gender, medicalCondition, category);

}

// Add patient to ArrayList

patients.add(patient);

// save the updated patient list to file

DataStorage.savePatients();

System.out.println("\n--------------------------------------------");
System.out.println("Patient is successfully registered.");
System.out.println("Patient ID: " + patientID);
System.out.println("--------------------------------------------");
   
}

// Search patient by using Patient ID

public static void searchPatient() {

System.out.println("--------------------------------------------");
System.out.println("                 SEARCH PATIENT             ");
System.out.println("--------------------------------------------");

// enter patients credentials

System.out.print("Enter Patient ID: ");
String patientID = scanner.nextLine().trim();

Patient patient = findPatient(patientID);

if (patient != null) {

System.out.println("\nPatient found.");
patient.displayDetails();

} else {

System.out.println("\nNo patient was found with Patient ID: " + patientID);
        
   }    
}

// Update patient credentials

public static void updatePatient() {

System.out.println("--------------------------------------------");
System.out.println("          UPDATE PATIENT DETAILS            ");
System.out.println("--------------------------------------------");

System.out.print("Enter Patient ID to update: ");
String patientID = scanner.nextLine().trim();

Patient patient = findPatient(patientID);

if (patient == null) {

System.out.println("\nPatient not found.");

// to allow user to enter the correct credentials 
            return;
}

System.out.println("\nCurrent Patient Details:");
patient.displayDetails();

System.out.println("\nEnter the new patient information.");

// Update first name

String firstName = getName("Enter new First Name: ");
patient.setFirstName(firstName);

// Update last name

String lastName = getName("Enter new Last Name: ");
patient.setLastName(lastName);

// Update age

int age = getAge();
patient.setAge(age);

// Update gender
        
String gender = getGender();
patient.setGender(gender);

// Update medical condition
        
String medicalCondition;

while (true) {

System.out.print("Enter new Medical Condition: ");
medicalCondition = scanner.nextLine().trim();

if (!medicalCondition.isEmpty()) {
    
// end loop until correct information is entered.
                break;
}

System.out.println("\nMedical condition cannot be empty.");
            
}

patient.setMedicalCondition(medicalCondition);

// If this is an Inpatient, also allow the ward and bed number to be updated

if (patient instanceof Inpatient) {

Inpatient inpatient = (Inpatient) patient;

String wardNumber = getWardOrBedNumber("Enter new Ward Number: ");
String bedNumber = getWardOrBedNumber("Enter new Bed Number: ");

inpatient.setWardNumber(wardNumber);
inpatient.setBedNumber(bedNumber);

}

// save the updated patient list to file

DataStorage.savePatients();

System.out.println("\n--------------------------------------------");
System.out.println("    Patient details updated successfully.     ");
System.out.println("--------------------------------------------");
    
}

// Delete patient credentials

public static void deletePatient() {

System.out.println("--------------------------------------------");
System.out.println("              DELETE PATIENT                 ");
System.out.println("--------------------------------------------");

System.out.print("Enter Patient ID to delete: ");
String patientID = scanner.nextLine().trim();

Patient patient = findPatient(patientID);

if (patient == null) {

System.out.println("\nPatient not found.");

// back to menu
            return;
}

System.out.println("\nPatient to be deleted on system:");
patient.displayDetails();

System.out.print("\nAre you sure you want to delete this patient? (Yes/No): ");

String confirmation = scanner.nextLine().trim();

if (confirmation.equalsIgnoreCase("Yes")) {

patients.remove(patient);

// save the updated patient list to file

DataStorage.savePatients();

System.out.println("\n--------------------------------------------");
System.out.println("Patient deleted successfully.");
System.out.println("\n--------------------------------------------");

} else {

System.out.println("\nDelete operation cancelled.");

    }
}

// Display all patients

public static void displayAllPatients() {

System.out.println("\n--------------------------------------------");
System.out.println("            ALL REGISTERED PATIENTS           ");
System.out.println("\n--------------------------------------------");

if (patients.isEmpty()) {

System.out.println("There are currently no registered patients.");

// make user go back until correect credentials are given
              return;
}

System.out.println("Total Patients: " + patients.size());
System.out.println();

for (int i = 0; i < patients.size(); i++) {

System.out.println("\n--------------------------------------------");
System.out.println("Patient #" + (i + 1));
System.out.println("\n--------------------------------------------");

patients.get(i).displayDetails();

    }    
}

// Find patient using  Patient ID

public static Patient findPatient(String patientID) {

    for (Patient patient : patients) {

    if (patient.getPatientID().equalsIgnoreCase(patientID)) {
                
    return patient;
            
    }
}

    return null;
}

// Get name

public static String getName(String message) {

while (true) {

System.out.print(message);

String name = scanner.nextLine().trim();

if (name.isEmpty()) {

System.out.println("\nName cannot be empty.");

//continue the loop until it ends execution
                continue;
}

// Make sure name contains only letters, spaces or hyphens

if (!name.matches("[a-zA-ZÀ-ÿ' -]+")) {                    // name creation complexity

System.out.println("\nInvalid name, please use letters only.");

//continue the loop until it ends execution
                continue;
        }

                return name;
    }
}

// Get age of patient with user input

public static int getAge() {

while (true) {

System.out.print("Enter Age: ");
String input = scanner.nextLine();

try {

int age = Integer.parseInt(input);

if (age >= 0 && age <= 120) {
   
// return age to whatever called the variable
                    return age;
}

System.out.println("Age must be between 0 and 120.");

} catch (NumberFormatException e) {

System.out.println("\nInvalid age, please enter a whole number.");
                
        }
    }
}

// Get Patoents gender

public static String getGender() {

while (true) {

System.out.println("\nSelect Gender:");
System.out.println("1. Male");
System.out.println("2. Female");
System.out.println("3. Other");

System.out.print("Enter choice: ");

String choice = scanner.nextLine().trim();

switch (choice) {

case "1":
                    
    return "Male";

case "2":
                    
    return "Female";

case "3":
                    
    return "Other";

default:
                   
System.out.println("Invalid choice. Please select 1, 2 or 3.");
                                  
        }
    }
}

// Get patient category - returns a PatientCategory enum value

public static PatientCategory getPatientCategory() {

while (true) {

System.out.println("\nSelect Patient Category:");
System.out.println("1. Inpatient");
System.out.println("2. Outpatient");
System.out.println("3. Emergency");

System.out.print("Enter choice: ");
String choice = scanner.nextLine().trim();

switch (choice) {

case "1":
                    
    return PatientCategory.INPATIENT;

case "2":
                    
    return PatientCategory.OUTPATIENT;

case "3":
                    
    return PatientCategory.EMERGENCY;

default:
                    
System.out.println("Invalid choice, please select 1, 2 or 3.");
                   
        }
    }
}

// Get ward number or bed number for an Inpatient

public static String getWardOrBedNumber(String message) {

while (true) {

System.out.print(message);

String value = scanner.nextLine().trim();

if (!value.isEmpty()) {

                return value;
}

System.out.println("This field cannot be empty.");

    }
}

}
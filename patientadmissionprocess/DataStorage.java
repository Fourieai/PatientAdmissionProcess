/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.patientadmissionprocess;

/**
 *
 * @author Student
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Handles saving and loading patient records and bed allocations
// to plain text files, so the data survives closing the program

public class DataStorage {

static final String PATIENTS_FILE = "patients.txt";
static final String BEDS_FILE = "beds.txt";

// field separator used inside each saved line

static final String SEPARATOR = "|";

// Save all patients in the ArrayList to patients.txt

public static void savePatients() {

try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATIENTS_FILE))) {

for (Patient patient : PatientAdmissionProcess.patients) {

String wardNumber = "-";
String bedNumber = "-";

if (patient instanceof Inpatient) {

Inpatient inpatient = (Inpatient) patient;

wardNumber = inpatient.getWardNumber();
bedNumber = inpatient.getBedNumber();

}

String line = patient.getPatientID() + SEPARATOR
+ patient.getFirstName() + SEPARATOR
+ patient.getLastName() + SEPARATOR
+ patient.getAge() + SEPARATOR
+ patient.getGender() + SEPARATOR
+ patient.getMedicalCondition() + SEPARATOR
+ patient.getPatientCategory() + SEPARATOR
+ wardNumber + SEPARATOR
+ bedNumber;

writer.write(line);
writer.newLine();

    }

} catch (IOException e) {

System.out.println("\nERROR! Could not save patient data: " + e.getMessage());

    }
}

// Load patients from patients.txt into the ArrayList, if the file exists

public static void loadPatients() {

File file = new File(PATIENTS_FILE);

if (!file.exists()) {

// nothing to load yet, this is the first time the program has run
            return;
}

try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

PatientAdmissionProcess.patients.clear();

String line;

while ((line = reader.readLine()) != null) {

if (line.trim().isEmpty()) {

//skip blank lines
                continue;
}

String[] fields = line.split("\\" + SEPARATOR, -1);

if (fields.length < 9) {

//skip malformed lines
                continue;
}

String patientID = fields[0];
String firstName = fields[1];
String lastName = fields[2];
int age = Integer.parseInt(fields[3]);
String gender = fields[4];
String medicalCondition = fields[5];
PatientCategory category = PatientCategory.valueOf(fields[6]);
String wardNumber = fields[7];
String bedNumber = fields[8];

Patient patient;

if (category == PatientCategory.INPATIENT) {

patient = new Inpatient(patientID, firstName, lastName, age, gender, medicalCondition, wardNumber, bedNumber);

} else {

patient = new Patient(patientID, firstName, lastName, age, gender, medicalCondition, category);

}

PatientAdmissionProcess.patients.add(patient);

    }

} catch (IOException e) {

System.out.println("\nERROR! Could not load patient data: " + e.getMessage());

} catch (NumberFormatException e) {

System.out.println("\nERROR! Patient data file is corrupted: " + e.getMessage());

    }
}

// Save the current bed occupancy state to beds.txt

public static void saveBeds() {

try (BufferedWriter writer = new BufferedWriter(new FileWriter(BEDS_FILE))) {

for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

BedManagement.Bed bed = BedManagement.ward[row][col];

String patientID = bed.isOccupied() ? bed.getPatientID() : "-";

String line = bed.getBedID() + SEPARATOR + bed.isOccupied() + SEPARATOR + patientID;

writer.write(line);
writer.newLine();

        }
    }

} catch (IOException e) {

System.out.println("\nERROR! Could not save bed data: " + e.getMessage());

    }
}

// Load bed occupancy from beds.txt onto the already-initialized ward, if the file exists

public static void loadBeds() {

File file = new File(BEDS_FILE);

if (!file.exists()) {

// nothing to load yet, this is the first time the program has run
            return;
}

try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

String line;

while ((line = reader.readLine()) != null) {

if (line.trim().isEmpty()) {

//skip blank lines
                continue;
}

String[] fields = line.split("\\" + SEPARATOR, -1);

if (fields.length < 3) {

//skip malformed lines
                continue;
}

String bedID = fields[0];
boolean occupied = Boolean.parseBoolean(fields[1]);
String patientID = fields[2];

BedManagement.Bed matchingBed = findBedByID(bedID);

if (matchingBed != null && occupied) {

matchingBed.setOccupied(true);
matchingBed.setPatientID(patientID);

        }
    }

} catch (IOException e) {

System.out.println("\nERROR! Could not load bed data: " + e.getMessage());

    }
}

// Helper to find a bed in the ward by its Bed ID

private static BedManagement.Bed findBedByID(String bedID) {

for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

if (BedManagement.ward[row][col].getBedID().equalsIgnoreCase(bedID)) {

return BedManagement.ward[row][col];

        }
    }
}

    return null;
    }
}
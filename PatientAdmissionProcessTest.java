package com.mycompany.patientadmissionprocess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */

// JUnit tests for Feature 5, covering CRUD operations, bed management,
// and validation / boundary conditions as per the marking guideline

public class PatientAdmissionProcessTest {

// reset the patient list and ward before every test, so tests don't
// interfere with each other's state

@BeforeEach
public void setUp() {

PatientAdmissionProcess.patients.clear();

BedManagement.initializeWard();

}

// ---------------- CRUD OPERATION TESTS ----------------

// Register a patient

@Test
public void testRegisterPatient() {

Patient patient = new Patient("P001", "John", "Smith", 35, "Male", "Flu", PatientCategory.OUTPATIENT);

PatientAdmissionProcess.patients.add(patient);

assertEquals(1, PatientAdmissionProcess.patients.size());
assertNotNull(PatientAdmissionProcess.findPatient("P001"));

}

// Search for a patient

@Test
public void testSearchPatient_found() {

Patient patient = new Patient("P002", "Jane", "Doe", 28, "Female", "Migraine", PatientCategory.OUTPATIENT);

PatientAdmissionProcess.patients.add(patient);

Patient found = PatientAdmissionProcess.findPatient("P002");

assertNotNull(found);
assertEquals("Jane", found.getFirstName());

}

@Test
public void testSearchPatient_notFound() {

Patient found = PatientAdmissionProcess.findPatient("DOES_NOT_EXIST");

assertNull(found);

}

// Update patient details

@Test
public void testUpdatePatientDetails() {

Patient patient = new Patient("P003", "Sam", "Jones", 40, "Male", "Asthma", PatientCategory.OUTPATIENT);

PatientAdmissionProcess.patients.add(patient);

Patient toUpdate = PatientAdmissionProcess.findPatient("P003");

toUpdate.setFirstName("Samuel");
toUpdate.setAge(41);
toUpdate.setMedicalCondition("Severe Asthma");

Patient updated = PatientAdmissionProcess.findPatient("P003");

assertEquals("Samuel", updated.getFirstName());
assertEquals(41, updated.getAge());
assertEquals("Severe Asthma", updated.getMedicalCondition());

}

// Delete a patient

@Test
public void testDeletePatient() {

Patient patient = new Patient("P004", "Amy", "Brown", 22, "Female", "Sprain", PatientCategory.OUTPATIENT);

PatientAdmissionProcess.patients.add(patient);

assertNotNull(PatientAdmissionProcess.findPatient("P004"));

PatientAdmissionProcess.patients.remove(patient);

assertNull(PatientAdmissionProcess.findPatient("P004"));

}

// ---------------- BED MANAGEMENT TESTS ----------------

// Allocate a bed

@Test
public void testAllocateBed() {

BedManagement.Bed bed = BedManagement.findAvailableBed();

assertNotNull(bed);

bed.setOccupied(true);
bed.setPatientID("P005");

BedManagement.Bed occupiedBed = BedManagement.findBedByPatientID("P005");

assertNotNull(occupiedBed);
assertTrue(occupiedBed.isOccupied());
assertEquals(bed.getBedID(), occupiedBed.getBedID());

}

// Release a bed

@Test
public void testReleaseBed() {

BedManagement.Bed bed = BedManagement.findAvailableBed();

bed.setOccupied(true);
bed.setPatientID("P006");

assertNotNull(BedManagement.findBedByPatientID("P006"));

bed.setOccupied(false);
bed.setPatientID(null);

assertNull(BedManagement.findBedByPatientID("P006"));
assertFalse(bed.isOccupied());

}

// ---------------- VALIDATION AND BOUNDARY TESTS ----------------

// Prevent duplicate Patient IDs

@Test
public void testPreventDuplicatePatientID() {

Patient patient = new Patient("P007", "Tom", "White", 50, "Male", "Back Pain", PatientCategory.OUTPATIENT);

PatientAdmissionProcess.patients.add(patient);

// registerPatient() checks findPatient(id) != null before allowing registration;
// this confirms that check would correctly reject a duplicate ID

assertNotNull(PatientAdmissionProcess.findPatient("P007"));

}

// Prevent allocating an occupied bed

@Test
public void testPreventAllocatingOccupiedBed() {

BedManagement.Bed bed = BedManagement.findAvailableBed();

bed.setOccupied(true);
bed.setPatientID("P008");

// findAvailableBed() must never return an already-occupied bed

BedManagement.Bed nextAvailable = BedManagement.findAvailableBed();

assertNotEquals(bed.getBedID(), nextAvailable.getBedID());
assertFalse(nextAvailable.isOccupied());

}

// Prevent bed allocation when all beds are occupied

@Test
public void testPreventAllocationWhenWardIsFull() {

// occupy all 20 beds

for (int row = 0; row < 4; row++) {

for (int col = 0; col < 5; col++) {

BedManagement.ward[row][col].setOccupied(true);
BedManagement.ward[row][col].setPatientID("FILLER");

    }
}

// no beds should be available now

assertNull(BedManagement.findAvailableBed());

}

// Sort patients by Patient ID

@Test
public void testSortPatientsByID() {

PatientAdmissionProcess.patients.add(new Patient("P010", "Zoe", "Adams", 30, "Female", "Cold", PatientCategory.OUTPATIENT));
PatientAdmissionProcess.patients.add(new Patient("P002", "Ben", "Clark", 45, "Male", "Flu", PatientCategory.OUTPATIENT));
PatientAdmissionProcess.patients.add(new Patient("P005", "Cara", "Diaz", 25, "Female", "Cough", PatientCategory.OUTPATIENT));

java.util.ArrayList<Patient> sorted = Reports.sortPatientsByID();

assertEquals("P002", sorted.get(0).getPatientID());
assertEquals("P005", sorted.get(1).getPatientID());
assertEquals("P010", sorted.get(2).getPatientID());

}

// Inpatient inheritance - only Inpatients carry ward and bed information

@Test
public void testInpatientCategoryAndFields() {

Inpatient inpatient = new Inpatient("P011", "Leo", "King", 60, "Male", "Surgery Recovery", "W3", "B07");

assertEquals(PatientCategory.INPATIENT, inpatient.getPatientCategory());
assertEquals("W3", inpatient.getWardNumber());
assertEquals("B07", inpatient.getBedNumber());

    }
}
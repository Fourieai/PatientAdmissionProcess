/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.patientadmissionprocess;

/**
 *
 * @author Student
 */

// enum to represent the three patient categories the hospital treats
// package-private (no "public") since Patient is the only public type allowed in this file

enum PatientCategory {

INPATIENT,
OUTPATIENT,
EMERGENCY

}

// Patient class, used directly for Outpatients and Emergency patients,
// and extended by Inpatient for patients who occupy a hospital bed

public class Patient {

private String patientID;
private String firstName;
private String lastName;
private int age;
private String gender;
private String medicalCondition;
private PatientCategory patientCategory;

// Constructor

public Patient(String patientID, String firstName, String lastName, int age, String gender, String medicalCondition, PatientCategory patientCategory) {

this.patientID = patientID;
this.firstName = firstName;
this.lastName = lastName;
this.age = age;
this.gender = gender;
this.medicalCondition = medicalCondition;
this.patientCategory = patientCategory;

}

// Getters

public String getPatientID() {

    return patientID;
}

public String getFirstName() {

    return firstName;
}

public String getLastName() {

    return lastName;
}

public int getAge() {

    return age;
}

public String getGender() {

    return gender;
}

public String getMedicalCondition() {

    return medicalCondition;
}

public PatientCategory getPatientCategory() {

    return patientCategory;
}

// Setters

public void setFirstName(String firstName) {

    this.firstName = firstName;
}

public void setLastName(String lastName) {

    this.lastName = lastName;
}

public void setAge(int age) {

    this.age = age;
}

public void setGender(String gender) {

    this.gender = gender;
}

public void setMedicalCondition(String medicalCondition) {

    this.medicalCondition = medicalCondition;
}

public void setPatientCategory(PatientCategory patientCategory) {

    this.patientCategory = patientCategory;
}

// Display patient details. Inpatient overrides this to add ward and bed information

public void displayDetails() {

System.out.println("Patient ID        : " + patientID);
System.out.println("First Name        : " + firstName);
System.out.println("Last Name         : " + lastName);
System.out.println("Age               : " + age);
System.out.println("Gender            : " + gender);
System.out.println("Medical Condition : " + medicalCondition);
System.out.println("Patient Category  : " + patientCategory);

    }
}

// Inpatient extends Patient, adding the ward and bed information
// that only applies to patients who are admitted to a hospital bed
// package-private (no "public") for the same reason as PatientCategory above

class Inpatient extends Patient {

private String wardNumber;
private String bedNumber;

// Constructor - uses super() to initialise the inherited Patient attributes

public Inpatient(String patientID, String firstName, String lastName, int age, String gender, String medicalCondition, String wardNumber, String bedNumber) {

super(patientID, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);

this.wardNumber = wardNumber;
this.bedNumber = bedNumber;

}

// Getters

public String getWardNumber() {

    return wardNumber;
}

public String getBedNumber() {

    return bedNumber;
}

// Setters

public void setWardNumber(String wardNumber) {

    this.wardNumber = wardNumber;
}

public void setBedNumber(String bedNumber) {

    this.bedNumber = bedNumber;
}

// Override displayDetails() to extend the superclass behaviour
// with the additional ward and bed information

@Override
public void displayDetails() {

super.displayDetails();

System.out.println("Ward Number       : " + wardNumber);
System.out.println("Bed Number        : " + bedNumber);

    }
}
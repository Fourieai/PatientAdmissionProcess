/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testsitting;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class RunApplication {
public static void main(String[] args) {

Scanner input = new Scanner(System.in);

//declare 
        
int choice;
String place;


System.out.println("Select the beverage type");
System.out.println("\n1. PS5");
System.out.println("\n2. XBOX");
System.out.println("\n3. SWITCH");

System.out.print("Enter option: ");
choice = input.nextInt();

System.out.print("Enter the store: ");
place = input.nextInt();

System.out.print("Enter the total sales of " + choice + "consoles for" + choice + "" + getLocation);
total = input.nextInt();

storeTotalReport report = new RoadAccidentReport(choice, place, total);
report.printConsoleReport();

input.close();

}
}

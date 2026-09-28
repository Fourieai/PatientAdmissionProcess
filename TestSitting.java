/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.testsitting;

/**
 *
 * @author Student
 */
import java.util.Arrays;

public class TestSitting {

public static void main(String[] args) {
 
//declare 

String [] cities = {"Cape Town", "Port Elizabeth", "Pretoria"};
int psFive = {1000, 2000, 1500};
int xBox = {2000, 3000, 1100};
int switch = {3000, 4000, 1200};
int [][] data = new int [3][3];
        
        
//System layout

System.out.println("---------------------------------------");
System.out.println("GAMING CONSOLE REPORT");
System.out.println("---------------------------------------");

//print the rows and columns

for (int i = 0; i < cities.length; i++) {
    
System.out.println("%-15s-10d%n", cities [i], data[i][o], data[i]);

}

//console sale total

System.out.println("---------------------------------------");
System.out.println("CONSOLE SALES TOTALS FOR EACH CITY");
System.out.println("---------------------------------------");

//cities console sale total

System.out.println("CAPE TOWN:" + capeTown);
System.out.println("PORT ELIZABETH:" + portEli);
System.out.println("PRETORIA:" + pta);

//MOST TOTAL

int [] totals = new int[3];
int maxTotal = 0;
int maxIndex = 0;

for (int i = 0; i < cities.length; i++) {
    
total[i] = data[i][0] + data[i][1];

}

if (totals[i] > maxTotal){

maxTotal = totals[i];
maxIndex = i;

  }

System.out.println("CITY WITH THE MOST SALES: " + cities[maxIndex]);
System.out.println("---------------------------------------");


    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covid19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.*;
import java.math.*;

/**
 *
 * @author Harry Brar
 */
public class Main {

static Map<String, String[]> hMap = new HashMap<>();
static Map<String, Integer[]> hMap2 = new HashMap<>();
static Map<String, String> hMap3 = new HashMap<>();


      static void loadData() {

        String pathFile1 = "./src/pkg920060268final/locations.csv";
        String pathFile2 = "./src/pkg920060268final/full_data.csv";
        String row;
        try {

            BufferedReader csvReader = new BufferedReader(new FileReader(pathFile1));

            while ((row = csvReader.readLine()) != null) {//this is accessing "locations.csv"
                
                String[] info = row.split(",");//splitting the accessed row into an array
                String[] continentPopulation = new String[2];//an array of size 2, will have continent at index 0 and population at index 1
                
                if (!info[1].equals("location")) {//info[1] is country, and skipping when info[1] is "location"
                    continentPopulation[0] = info[2];//info[2] is continent and putting this continent at index 0 
                    continentPopulation[1] = info[4];//info[4] is population and putting this population at index 1
                    hMap.put(info[1], continentPopulation);//putting country as key and array[continent, population] as value
                }
          }
            csvReader.close();


            csvReader = new BufferedReader(new FileReader(pathFile2));

            while ((row = csvReader.readLine()) != null) {//this is accessing "full_data.csv"
                
                String[] info = row.split(",");//splitting each row
                int totalCases;//this would keep count of total cases of each continent
                int totalDeaths;//this would keep count of total deaths of each continent
                Integer[] casesDeaths = new Integer[2];//an array of size 2, will have total cases at index 0 and total deaths at index 1 wrt each continent

                        if(!info[1].equals("location") && hMap.containsKey(info[1])) {

                                   if (hMap2.containsKey(hMap.get(info[1])[0])) {//info[1] is country, and hMap.get(info[1]) will return the value, which is an array of [continent, population], so "[0]" would access just the continent.
                                       // IF hMap2 has that continent then the statement would be true and this would continue
                                        totalCases = (hMap2.get(hMap.get(info[1])[0]))[0] + Integer.parseInt(info[4]);//"hMap2.get(hMap.get(info[1])[0]))[0]" would return the count of total cases upto that point of a continent and here we are adding inf0[4], i.e. the next total number of cases of country
                                        totalDeaths =  (hMap2.get(hMap.get(info[1])[0]))[1] + Integer.parseInt(info[5]);//this is adding the number of deaths just as same as above
                                        casesDeaths[0] = totalCases;//putting total cases at index 0
                                        casesDeaths[1] = totalDeaths;//putting total deaths at index 1
                                        hMap2.put(hMap.get(info[1])[0], casesDeaths);//"hMap.get(info[1])[0]" would retrun continent, and adding it as key, and adding the array{total cases, deaths} as value
                                    }
                                   else if (hMap.containsKey(info[1]) ){//this would only run if the read country is in hMap
                                       //if hMap2 doesn't already have the continent then this "else if" will add continent and array{cases, deaths} to it
                                       totalCases = Integer.parseInt(info[4]);
                                       totalDeaths = Integer.parseInt(info[5]);
                                       casesDeaths[0] = totalCases;
                                       casesDeaths[1] = totalDeaths;
                                       hMap2.put(hMap.get(info[1])[0], casesDeaths);
                                    }
                                  hMap3.put(info[1], info[4]);//adding countries and total cases to hMap3 for later use in print cases per million
                    }
            }
            csvReader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
      
      static void printAscendingOrder() {//this would print the total number of cases in each continent in ascengding order
          System.out.println("Continent"+"\t\t"+"Total Cases");
          ArrayList<Integer> data = new ArrayList<>(); 
          Map<Integer, String> sortedCases = new HashMap<>();
          
          for (Map.Entry<String, Integer[]> me : hMap2.entrySet()) {//iterating through each element of hMap2
              //as we know values of hMap2 are in the form of array, and here we will be accessing only first element of that array, which is total cases, and we will be reversing the order and adding it to new hashmap called sorted 
             data.add(me.getValue()[0]);//me.getValue() would return the array{cases, deaths} and "[0]" would just return the cases, then adding cases to array list "data"
             sortedCases.put(me.getValue()[0], me.getKey());//me.getValue()[0] would retrun number of cases, me.getKey is continent and putting it in our new hashmap called sortedCases
        }
          Collections.sort(data);//sorting the total cases in ascending order 
          //PRINTING HERE
          for(int i = 0; i < data.size(); i++) {//iterating through each element of sorted array list "data" 
              System.out.println((i+1)+". "+sortedCases.get(data.get(i))+"\t\t"+data.get(i));//sortedCases.get(data.get(i)) will return value related to this key (key here is total number of cases), and value will be the continent
          }
      }
      
      public static void printDescendingOrder() {//this would print the total number of cases in each continent in descending order 
          //as we know values of hMap2 are in the form of array, and here we will be accessing only first element of that array, which is total cases, and we will be reversing the order and adding it to new hashmap called sorted
          //everything is same as I commented in "printAscendingOder" except we are sorting the array list "data" in reverse on line 115 
          System.out.println("Continent"+"\t\t"+"Total Cases");
          ArrayList<Integer> data = new ArrayList<>();
          Map<Integer, String> sortedCases = new HashMap<>();
          
           for (Map.Entry<String, Integer[]> me : hMap2.entrySet()) {
             data.add(me.getValue()[0]);
             sortedCases.put(me.getValue()[0], me.getKey());
        }
          Collections.sort(data,Collections.reverseOrder());//this would sort the array list "data" in descending order 
          
          for(int i = 0; i < data.size(); i++) {//printing everything in descending order 
              System.out.println((i+1)+". "+sortedCases.get(data.get(i))+"\t\t"+data.get(i));
          }
      }
      
      public static void printAscendingOrderDeaths() {//this would print the total number of deaths related to each continent in ascending order 
          //as we know values of hMap2 are in the form of array, and here we will be accessing only second element of that array, which is total deaths, and we will be reversing the order and adding it to new hashmap called sorted
          System.out.println("Continent"+"\t\t"+"Total Deaths");
          ArrayList<Integer> data = new ArrayList<>();
          Map<Integer, String> sortedDeaths = new HashMap<>();
          
          for (Map.Entry<String, Integer[]> me : hMap2.entrySet()) {
             data.add(me.getValue()[1]);//me.getValue() would return the array{cases, deaths} and "[1]" would just return the deaths, then adding cases to array list "data"
             sortedDeaths.put(me.getValue()[1], me.getKey());//me.getValue()[1] would retrun number of deaths, me.getKey is continent, and putting it in our new hashmap called sortedDeaths
        }
          Collections.sort(data);//sorting the total deaths in ascending order
          //PRINTING HERE
          for(int i = 0; i < data.size(); i++) {//iterating through each element of sorted array list "data"
              System.out.println((i+1)+". "+sortedDeaths.get(data.get(i))+"\t\t"+data.get(i));//sortedCases.get(data.get(i)) will return value related to this key (key here is total number of deaths), and value will be the continent
          }
      }
      
      public static void printDescendingOrderDeaths() {//this would print the total number of deaths related to each continent in descending order
          //as we know values of hMap2 are in the form of array, and here we will be accessing only second element of that array, which is total deaths, and we will be reversing the order and adding it to new hashmap called sorted
          //everything is same as I commented in "printAscendingOderDeaths" except we are sorting the array list "data" in reverse on line 149 
          System.out.println("Continent"+"\t\t"+"Total Deaths");
          ArrayList<Integer> data = new ArrayList<>();
          Map<Integer, String> sortedDeaths = new HashMap<>();
          for (Map.Entry<String, Integer[]> me : hMap2.entrySet()) {
             data.add(me.getValue()[1]);
             sortedDeaths.put(me.getValue()[1], me.getKey());
        }
          Collections.sort(data, Collections.reverseOrder());//this would sort the array list "data" in descending order           
          for(int i = 0; i < data.size(); i++) {//printing in descending order 
              System.out.println((i+1)+". "+sortedDeaths.get(data.get(i))+"\t\t"+data.get(i));
          }
      }
      
      public static void casesPerMillion() {//this would print the cases per million
          Map<String, BigDecimal> lastMap = new HashMap<>();
          BigDecimal bg1, bg2, bg3;
          //as we know hMap3 contains each country as key and cases as values AND hMap consisted of countries as keys and array of [continent, population] as value, we will be focusing in the second element which is at index "[1]"
          for (Map.Entry<String, String> me : hMap3.entrySet()) {//iteraating through each element of hMap3
            if(hMap.containsKey(me.getKey())) {//if hMap contains the current coutnry that we are iterating through then only this statement would be true and the code will go in "if"
                bg1 = new BigDecimal(me.getValue());//bg1 here is equal to the "me.getValue()", which is the cases of that country
                bg2 = new BigDecimal(hMap.get(me.getKey())[1]);//bg2 here is equal to the population of that country, hMap.get(me.getKey()) will return an array {continent, pupulation} and "[1]" would return just the popoulation
                bg3 = bg1.divide(bg2, 5, RoundingMode.CEILING);//learned this from the instructions, this would divide bg1(cases) by bg2(population) upto scale 5 with rounding mode ceiling
                lastMap.put(me.getKey(), bg3);//putting the country as key and case per million as value
            }
        }
          //lines 168 to 174 will sort the values of lastMap using comparator
          ArrayList<Map.Entry<String, BigDecimal>> sorted = new ArrayList<>(lastMap.entrySet());
          Collections.sort(sorted, new Comparator<Map.Entry<String, BigDecimal>>() {
          @Override
          public int compare(Map.Entry<String, BigDecimal> o1, Map.Entry<String, BigDecimal> o2) {
              return (o2.getValue()).compareTo(o1.getValue());
          }
          });
          //lines 176 to 186 will ask user how many values does she want to print and will print from highest to lowest accordingly
          Scanner input = new Scanner(System.in);
          System.out.println("How many countires you want to print?");
          int counter = input.nextInt();
          int x = 0;
          System.out.println("Top 10 countries based on cases per 1 million from highest to lowest:");
          System.out.println("Country"+"\t\t"+"Total Cases Per Million");
     
            for (Map.Entry<String, BigDecimal> me : sorted) {
                if (x < counter){
            System.out.println(me.getKey()+"\t\t"+me.getValue());
            x++;}
            }
      }
//MAIN METHOD
    public static void main(String[] args) {


        Scanner scnr = new Scanner(System.in);
        int choice = -1;
        System.out.println("*************************************************************");
        System.out.println("****COVID 19 Global Statistics Menu (LAst Update May 3rd)****");
        System.out.println("*************************************************************");
        do {
            System.out.println("[1] Load Data From Files");
            System.out.println("[2] Print Continents Total Cases (Lowest to Highest)");
            System.out.println("[3] Print Continents Total Cases (Highest to Lowest)");
            System.out.println("[4] Print Continents Total Deaths (Lowest to Highest)");
            System.out.println("[5] Print Continents Total Deaths (Highest to Lowest)");
            System.out.println("[6] Prioritize top countries for testing based on new cases per 1 million");
            System.out.println("[7] To Exit");
            System.out.println("Please enter your choice:");
            choice = scnr.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Loading files ...");
                    loadData();//calling load data on line 26
                    System.out.println("Files loaded successfully!");
                    break;
                case 2:
                    printAscendingOrder();//calling "printAscendingOrder" on line 87
                    break;
                case 3:
                    printDescendingOrder();//calling "printDescendingOrder" on line 104
                    break;
                case 4:
                    printAscendingOrderDeaths();//calling "printAscendingOrderDeaths" on line 122
                    break;
                case 5:
                    printDescendingOrderDeaths();//calling "printDescendingOrderDeaths" on line 139
                    break;
                case 6:
                    casesPerMillion();//calling "casesPerMillion" om line 155
                    break;
                case 7:
                    System.out.println("Thank you for using our system..Goodbye!");
                    break;
                default:
                    System.out.println("Please a choice 1 - 7");
                    break;
            }
        } while (choice != 7);
    }
    
}

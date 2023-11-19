import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static java.lang.Integer.parseInt;

/**
 * The Main class represents the main program for processing SaleRecord data.
 * It reads data from a CSV file, inserts records into a HashMap with customer last names as keys,
 * handles collisions using chaining, and reports the average number of comparisons during searches.
 * Additionally, the hash map is resized when the loading factor exceeds a specified threshold.
 */
public class Main {

    //constant variables
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 2f;

    //customer last name will be key
    static HashMap<String, LinkedList<SaleRecord>> hashMap = new HashMap<>(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    static ArrayList<SaleRecord> arrayList = new ArrayList<>();

    /**
     * Inserts SaleRecord data from a CSV file into the hash map and the array list.
     *
     * @param csvFile the path to the CSV file containing SaleRecord data
     */

    public Main() {
        hashMap = new HashMap<>(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
        arrayList = new ArrayList<>();
    }


    public void readCSVAndBuildHashTable(String filePath, String key, int experimentNumber) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println(data[5]);
                final SaleRecord saleRecord = new SaleRecord(data[0], data[1], data[2], data[3], data[4], parseInt(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]));

                arrayList.add(saleRecord);
                addToHashTable(saleRecord, key, experimentNumber);

                // Assuming you have a HashMap<String, SaleRecord> customerLastNameMap
                int tableSize = hashMap.size(); // Get the current table size
                int numberOfElements = arrayList.size();

                // Loading factor L = N/T (number of entries / table size)
                double loadFactor = (double) numberOfElements / tableSize;



                if (loadFactor > 2) {
                    resize();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addToHashTable(SaleRecord record, String key, int experimentNumber) {
        String keyValue = getKey(record, key, experimentNumber);
        int hashCode = getHashCode(keyValue);

        String customerLastName = record.getCustomerLastName();
        if (!hashMap.containsKey(customerLastName)) {
            hashMap.put(key, new LinkedList<>());
        }

        // Add the sale record to the linked list at the specified key
        hashMap.get(customerLastName).add(record);
    }

    private int getHashCode(String value) {
        // Implement this method to get the hash code based on the experiment number
        return value.hashCode();
    }

    private String getKey(SaleRecord record, String key, int experimentNumber) {
        // Implement this method to get the key based on the specified field (customer or salesperson last name)
        if(experimentNumber == 1){
            return record.getCustomerLastName();
        }else if(experimentNumber == 2){
            return  record.getSalePersonLastName();
        }
        return null;
    }





    public static void insert(String csvFile, String key) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip the header row
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming the order of columns in the CSV matches the constructor
                final SaleRecord saleRecord = new SaleRecord(data[0], data[1], data[2], data[3], data[4], parseInt(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]));

                // Gets the customer last name from CSV (used in the first experiment)
                // Handles collisions
                String customerLastName = saleRecord.getCustomerLastName();
                if (!hashMap.containsKey(customerLastName)) {
                    hashMap.put(customerLastName, new LinkedList<>());
                }

                //add the sale record to the linked list at the specified key
                hashMap.get(customerLastName).add(saleRecord);
                arrayList.add(saleRecord);

                // Assuming you have a HashMap<String, SaleRecord> customerLastNameMap
                int tableSize = hashMap.size(); // Get the current table size
                int numberOfElements = arrayList.size();

                //Loading factor L = N/T (number of entries / table size)
                double loadFactor = (double) numberOfElements / tableSize;

                if(loadFactor > 2){
                    resize();
                }

            }
            System.out.println(hashMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resizes the hash map when the loading factor exceeds 2.
     * Doubles the size of the hash map and rehashes existing elements into the new map.
     */
    private static void resize() {

        int newSize = hashMap.size() * 2;
        HashMap<String, LinkedList<SaleRecord>> newHashMap = new HashMap<>(newSize, DEFAULT_LOAD_FACTOR);
        // Rehash existing elements into the new hash map
        for (String key : hashMap.keySet()) {
            LinkedList<SaleRecord> value = hashMap.get(key);
            newHashMap.put(key, value);
        }

        // Replace the old hash map with the new one
        hashMap = newHashMap;

    }

    /**
     * Searches for each item in the array list and calculates the average number of comparisons.
     *
     * @param hashMap   the hash map containing SaleRecord data
     * @param arrayList the array list containing SaleRecord data
     * @return the average number of comparisons
     */
    public static double search(HashMap<String, LinkedList<SaleRecord>> hashMap, ArrayList<SaleRecord> arrayList) {
        int totalComparisons = 0;
        int numberOfSearches = arrayList.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (SaleRecord record : arrayList) {
            String keyToSearch = record.getCustomerLastName();
            // Search the hash map
            int comparisons = searchInHashMap(hashMap, keyToSearch);
            totalComparisons += comparisons;
        }

        //rounds the average to 2 decimal values
        return Double.parseDouble(decimalFormat.format((double) totalComparisons / numberOfSearches));
    }

    /**
     * Searches for a key in the hash map and returns the number of comparisons.
     *
     * @param hashMap     the hash map containing SaleRecord data
     * @param keyToSearch the key to search for
     * @return the number of comparisons
     */
    private static int searchInHashMap(HashMap<String, LinkedList<SaleRecord>> hashMap, String keyToSearch) {
        int comp = 0;
        // Check if the key is present in the hash map
        if (hashMap.containsKey(keyToSearch)) {
            comp++; // Checking if it is in the hash map is one comparison

            // Get the linked list matching the key
            LinkedList<SaleRecord> saleRecordList = hashMap.get(keyToSearch);
            for (SaleRecord saleRecord : saleRecordList) {
                comp++;
                // Additional logic for matching SaleRecord attributes if needed
            }
        }
        return comp;
    }

    public static void main(String[] args) {


        Main experiment3 = new Main();
        experiment3.readCSVAndBuildHashTable("src/small_sample.csv", "customerLastName", 1);




//        System.out.println("Average number of comparisons: " + comp + " table size: " + hashMap.size());
    }

}
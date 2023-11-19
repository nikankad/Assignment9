import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
    public static void insert(String csvFile) {

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip the header row
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming the order of columns in the CSV matches the constructor
                final SaleRecord saleRecord = new SaleRecord(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]));
                // Gets the customer last name from csv
                String customerLastName = data[2].substring(data[2].lastIndexOf(" ") + 1);
                //handles collisions
                if(!hashMap.containsKey(customerLastName)){
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


    public static void main(String[] args) {
       insert("src/car_sales_data.csv");
    }

}
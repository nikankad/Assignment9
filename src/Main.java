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
    // Constant variables
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 2f;

    // Customer last name will be the key

    static ArrayList<SaleRecord> arrayList = new ArrayList<>();
    static int experiment = 0;
    static HashMap<String, LinkedList<SaleRecord>> hashMap;


    static String file = "src/car_sales_data.csv";

    static long secondsTaken = 0;

    public Main(int experiment) {
        hashMap = new HashMap<>(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
        Main.experiment = experiment;
    }


    public static void addToHashMap(SaleRecord saleRecord, int experiment) {

        String mapKey = saleRecord.getCustomerLastName();
        if (experiment == 2) {
            mapKey = saleRecord.getSalePersonLastName();
        }
        if (!hashMap.containsKey(mapKey)) {
            hashMap.put(mapKey, new LinkedList<>());
        }

        // Add the sale record to the linked list at the specified key
        hashMap.get(mapKey).add(saleRecord);

        // Assuming you have a HashMap<String, SaleRecord> customerLastNameMap
        int tableSize = hashMap.size(); // Get the current table size
        int numberOfElements = arrayList.size();

        // Loading factor L = N/T (number of entries / table size)
        double loadFactor = (double) numberOfElements / tableSize;

        if (loadFactor > 2) {
            resize();
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
        for (HashMap.Entry<String, LinkedList<SaleRecord>> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            LinkedList<SaleRecord> value = entry.getValue();
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
        if (experiment == 1) {
            for (SaleRecord record : arrayList) {
                String keyToSearch = record.getCustomerLastName();
                // Search the hash map
                int comparisons = searchInHashMap(hashMap, keyToSearch);
                totalComparisons += comparisons;
            }

        } else if (experiment == 2) {
            for (SaleRecord record : arrayList) {
                String keyToSearch = record.getSalePersonLastName();
                // Search the hash map
                int comparisons = searchInHashMap(hashMap, keyToSearch);
                totalComparisons += comparisons;
            }
        }
        return (double) totalComparisons / numberOfSearches;
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
        comp++; // Checking if it is in the hash map is one comparison

        // Get the linked list matching the key
//        LinkedList<SaleRecord> saleRecordList = hashMap.get(keyToSearch);
//        for (SaleRecord saleRecord : saleRecordList) {
//            comp++;
//            // Additional logic for matching SaleRecord attributes if needed
//        }
        return comp;
    }

    public static void main(String[] args) {
        printExperiment(1);
        printExperiment(2);


//        System.out.println("Average number of compirasons: " + comp + " table size: " + hashMap.size());
    }

    public static void printExperiment(int experimentNo) {
        Main experiment = new Main(experimentNo);
        experiment.insert(file);
        double comp = search(hashMap, arrayList);

        System.out.println("Experiment: " + experimentNo);
        System.out.println(secondsTaken + " milli seconds taken to build the HashMap");
        System.out.println("Average number of comparisons: " + comp + " - table size " + hashMap.size());
    }

    /**
     * Inserts SaleRecord data from a CSV file into the hash map and the array list.
     *
     * @param csvFile the path to the CSV file containing SaleRecord data
     */
    public void insert(String csvFile) {
        long startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip the header row
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming the order of columns in the CSV matches the constructor
                final SaleRecord saleRecord = new SaleRecord(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]));

                // Gets the customer last name from CSV (used in the first experiment)
                // Handles collisions
                addToHashMap(saleRecord, experiment);
                arrayList.add(saleRecord);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();

        //this will be / 1000 for seconds
        secondsTaken = (endTime - startTime);
    }
}
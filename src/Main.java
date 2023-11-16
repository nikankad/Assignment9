import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class Main {
   static Hashtable<String, SaleRecord> hashTable = new Hashtable<>(15);
    static ArrayList<SaleRecord> arrayList = new ArrayList<SaleRecord>();

    public static void insert(String csvFile) {

        try (BufferedReader br = new BufferedReader(new FileReader("src/small_sample.csv"))) {
            br.readLine(); // Skip the header row
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming the order of columns in the CSV matches the constructor
                final SaleRecord saleRecord = new SaleRecord(data[0], data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), Double.parseDouble(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]));
                // Gets the customer last name from csv
                String customerLastName = data[2].substring(data[2].lastIndexOf(" ") + 1);
                hashTable.put(customerLastName, saleRecord);
                arrayList.add(saleRecord);
                // Insert the SaleRecord into the corresponding AVL tree based on Car Make
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    

    public static void main(String[] args) {
        insert("s");
    }
}
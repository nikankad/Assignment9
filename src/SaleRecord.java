public class SaleRecord {
    private final String date;
    private final String salesPerson;
    private final String customerName;
    private final String carMake;
    private final String carModel;

    private final int carYear;
    private final double salePrice;
    private final double comissionRate;
    private final double comissionEarned;

    private final int experimentNo;


    /**
     * Constructs a SaleRecord object with the given parameters.
     *
     * @param date            The date of the sale.
     * @param salePerson      The salesperson involved in the sale.
     * @param customerName    The name of the customer.
     * @param carMake         The make of the car.
     * @param carModel        The model of the car.
     * @param carYear         The year of the car.
     * @param salePrice       The sale price of the car.
     * @param comissionRate   The commission rate for the sale.
     * @param comissionEarned The commission earned from the sale.
     * @param experimentNo    The experiment number associated with the sale record.
     */
    public SaleRecord(String date, String salePerson, String customerName, String carMake, String carModel, int carYear,
                      double salePrice, double comissionRate, double comissionEarned, int experimentNo) {
        this.date = date;
        this.salesPerson = salePerson;
        this.customerName = customerName;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carYear = carYear;
        this.salePrice = salePrice;
        this.comissionRate = comissionRate;
        this.comissionEarned = comissionEarned;
        this.experimentNo = experimentNo;

    }

    /**
     * Computes the hash value for a given last name using the sum of ASCII values of its characters.
     *
     * @param lastName The last name for which to calculate the hash value.
     * @return The hash value based on the sum of ASCII values.
     */
    public static int hashLastName(String lastName) {
        int hashVal = 0;
        for (char ch : lastName.toCharArray()) {
            hashVal += ch;
        }
        return hashVal;
    }

    /**
     * @return String
     */
    public String getSalePerson() {
        return salesPerson;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public String getDate() {
        return date;
    }

    public int carYear() {
        return carYear;
    }

    public double getCommissionRate() {
        return comissionRate;
    }

    public String getModel() {
        return carModel;
    }

    public String getMake() {
        return carMake;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getCommissionEarned() {
        return comissionEarned;
    }

    public String getCustomerLastName() {
        return customerName.substring(customerName.lastIndexOf(" ") + 1);

    }

    public String getSalePersonLastName() {
        return salesPerson.substring(salesPerson.lastIndexOf(" ") + 1);

    }

    /**
     * Returns a string representation of the SaleRecord object.
     *
     * @return A string representation of the SaleRecord.
     */
    @Override
    public String toString() {
        return "{" + date + ", " + salesPerson + ", " + customerName + ", " + carMake + ", " + carModel + ", " + carYear +
                ", " + salePrice
                + ", " + comissionRate + ", " + comissionEarned + "}";
    }

    /**
     * Computes the hash code for the SaleRecord based on the experiment number.
     *
     * @return The hash code for the SaleRecord.
     */

    @Override
    public int hashCode() {
        if (experimentNo == 1 || experimentNo == 2) {
            return customerName.substring(customerName.lastIndexOf(" ") + 1).hashCode();
        } else if (experimentNo == 3) {
            return hashLastName(getCustomerLastName());
        } else {
            return hashLastName(getSalePersonLastName());
        }
    }


}

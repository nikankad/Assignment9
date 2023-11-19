
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


    public SaleRecord(String date, String salePerson, String customerName, String carMake, String carModel, int carYear,
                      double salePrice, double comissionRate, double comissionEarned) {
        this.date = date;
        this.salesPerson = salePerson;
        this.customerName = customerName;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carYear = carYear;
        this.salePrice = salePrice;
        this.comissionRate = comissionRate;
        this.comissionEarned = comissionEarned;

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

    @Override
    public String toString() {
        return "{"+date + ", " + salesPerson + ", " + customerName + ", " + carMake + ", " + carModel + ", " + carYear +
                ", " + salePrice
                + ", " + comissionRate + ", " + comissionEarned+"}";
    }

    public double getCommissionEarned() {
        return comissionEarned;
    }

    public String getCustomerLastName(){
        return customerName.substring(customerName.lastIndexOf(" ") + 1);

    }

    public String getSalePersonLastName(){
        return salesPerson.substring(salesPerson.lastIndexOf(" ") + 1);

    }

    @Override
    public int hashCode(){
        return customerName.substring(customerName.lastIndexOf(" ") + 1).hashCode();
    }

}

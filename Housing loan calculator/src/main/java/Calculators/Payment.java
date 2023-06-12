package Calculators;

public class Payment {

    private int month;
    private double payment;
    private double interest;
    private double credit;
    private double sumLeft;
    private int first;

    public Payment(int month, double payment, double interest, double credit, double sumLeft) {
        this.month = month;
        this.payment = payment;
        this.interest = interest;
        this.credit = credit;
        this.sumLeft = sumLeft;
    }
    public Payment() {

    }
    public int getMonth() {
        return month;
    }
    public double getPayment() {
        return payment;
    }
    public double getInterest() {
        return interest;
    }
    public double getCredit() {
        return credit;
    }
    public double getSumLeft() {
        return sumLeft;
    }
    public void setFirst(int first){
        this.first = first;
    }
    public int getFirst(){ return first;}

}

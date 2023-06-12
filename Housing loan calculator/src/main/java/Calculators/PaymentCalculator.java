package Calculators;

import java.util.ArrayList;

public class PaymentCalculator {
    private int period;
    private double sum;
    private double interest;
    private int postponeStart, postponeEnd;
    private int filterStart, filterEnd;

    public PaymentCalculator(int period, double sum, double interest, int postponeStart, int postponeEnd, int filterStart, int filterEnd) {
        this.period = period;
        this.sum = sum;
        this.interest = interest;
        this.postponeStart = postponeStart;
        this.postponeEnd = postponeEnd;
        this.filterStart = filterStart;
        this.filterEnd = filterEnd;
    }
    public PaymentCalculator(){

    }

    public ArrayList<Payment> calculate(ArrayList<Payment> payments) {
        return payments;
    }
    public int getPeriod() {
        return period;
    }
    public double getSum() {
        return sum;
    }
    public double getInterest() {
        return interest;
    }
    public int getPostponeStart() {
        return postponeStart;
    }
    public int getPostponeEnd() {
        return postponeEnd;
    }
    public int getFilterStart() {
        return filterStart;
    }
    public int getFilterEnd() {
        return filterEnd;
    }
}

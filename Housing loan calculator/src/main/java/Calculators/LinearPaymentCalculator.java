package Calculators;

import java.util.ArrayList;

public class LinearPaymentCalculator extends PaymentCalculator{

    public LinearPaymentCalculator(int period, double sum, double interest, int postponeStart, int postponeEnd, int filterStart, int filterEnd) {
        super(period, sum, interest, postponeStart, postponeEnd, filterStart, filterEnd);
    }

    @Override
    public ArrayList<Payment> calculate(ArrayList<Payment> payments) {

        double pCredit = super.getSum()/super.getPeriod();
        pCredit = Math.round(pCredit*100)/100.0;
        double sumLeft = super.getSum();
        double monthlyInterestRate = super.getInterest()/12/100;

        for(int i=1; i<=super.getPeriod() + getPostponeEnd()-getPostponeStart(); ++i){
            double interest = sumLeft * monthlyInterestRate;
            interest = Math.round(interest*100)/100.0;
            double payment = interest + pCredit;
            payment = Math.round(payment*100)/100.0;

            if (i >= super.getPostponeStart() && i < super.getPostponeEnd()) {
                if(i >= super.getFilterStart() && i <= super.getFilterEnd() || super.getFilterStart() == 0 && super.getFilterEnd() == 0) {
                    pCredit = 0;
                    payments.add(new Payment(i, interest+pCredit, interest, 0, sumLeft));
                }
            } else {
                if(i >= super.getFilterStart() && i <= super.getFilterEnd() || super.getFilterStart() == 0 && super.getFilterEnd() == 0) {
                    pCredit = super.getSum()/super.getPeriod();
                    pCredit = Math.round(pCredit*100)/100.0;
                    payments.add(new Payment(i, payment, interest, pCredit, Math.round((sumLeft-pCredit)*100)/100.0));
                }
                sumLeft -= pCredit;
                sumLeft = Math.round(sumLeft*100)/100.0;
            }

        }

        return payments;
    }
}

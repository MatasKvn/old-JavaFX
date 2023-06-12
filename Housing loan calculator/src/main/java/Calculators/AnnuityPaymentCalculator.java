package Calculators;

import java.util.ArrayList;

public class AnnuityPaymentCalculator extends PaymentCalculator{
    public AnnuityPaymentCalculator(int period, double sum, double interest, int postponeStart, int postponeEnd, int filterStart, int filterEnd) {
        super(period, sum, interest, postponeStart, postponeEnd, filterStart, filterEnd);
    }

    public ArrayList<Payment> calculate(ArrayList<Payment> payments) {
        double sumLeft = super.getSum();

        double monthlyInterestRate = super.getInterest()/12/100;
        double t1 = (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, super.getPeriod()));
        double t2 = Math.pow(1+monthlyInterestRate,super.getPeriod())-1;
        double K = t1 / t2;

        double payment = K*super.getSum();
        payment = Math.round(payment*100)/100.0;

        for(int i=1; i<=super.getPeriod() + getPostponeEnd()-getPostponeStart(); ++i){
            double interest = sumLeft*monthlyInterestRate;
            interest = Math.round(interest*100)/100.0;
            double pCredit = payment-interest;
            pCredit = Math.round(pCredit*100)/100.0;

            if (i >= super.getPostponeStart() && i <= super.getPostponeEnd()) {
                if(i >= super.getFilterStart() && i <= super.getFilterEnd() || super.getFilterStart() == 0 && super.getFilterEnd() == 0) {
                    pCredit = 0;
                    payments.add(new Payment(i, interest+pCredit, interest, 0, sumLeft));
                }
            } else {
                if(i >= super.getFilterStart() && i <= super.getFilterEnd() || super.getFilterStart() == 0 && super.getFilterEnd() == 0) {
                    payments.add(new Payment(i, payment, interest, pCredit, sumLeft - pCredit));
                }
                sumLeft -= pCredit;
                sumLeft = Math.round(sumLeft*100)/100.0;
            }

        }


        return payments;
    }
}

/*
    Q11. Create 3 sub class of bank SBI,BOI,ICICI all 4 should have method called getDetails
        which provide there specific details like rate of interest etc,print details of every banks
 */

class Bank {
    public void getDetails() {
        System.out.println("Detail of the bank: ");
    }
}


class SBI extends Bank {
    public void getDetails() {
        System.out.println("\nName: SBI");
        System.out.println("Interest Rate: 7%");
    }
}

class BOI extends Bank {
    public void getDetails() {
        System.out.println("\nName: BOI");
        System.out.println("Interest Rate: 8%");
    }
}

class ICICI extends Bank {
    public void getDetails() {
        System.out.println("\nName: ICICI");
        System.out.println("Interest Rate: 9%");
    }
}


public class Q11 {

    public static void main(String[] args) {
        Bank bank = new Bank();
        Bank sbi = new SBI();
        Bank boi = new BOI();
        Bank icici = new ICICI();
        bank.getDetails();
        sbi.getDetails();
        boi.getDetails();
        icici.getDetails();
    }

}

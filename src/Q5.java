//5. WAP to show object cloning in java using cloneable and copy constructor both.


class CloneTest implements Cloneable {
    int id;

    CloneTest(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Clone ID : " + id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();

    }

}

class CopyConstructor {
    int id;

    public CopyConstructor(int id) {
        this.id = id;
    }

    public CopyConstructor(CopyConstructor cc) {
        this.id = cc.id;

    }

    @Override
    public String toString() {
        return "CopyConstructor ID : " + id;
    }

}


public class Q5 {

    public static void main(String[] args) {

        // Object Cloning
        CloneTest ct1 = new CloneTest(1);
        CloneTest ct2;

        try {
            ct2 = (CloneTest) ct1.clone();
            System.out.println(ct1);
            System.out.println(ct2);
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed.");
        }


        //Copy Constructor
        CopyConstructor cc1 = new CopyConstructor(1);
        CopyConstructor cc2 = new CopyConstructor(cc1);
        System.out.println(cc1);
        System.out.println(cc2);
    }

}

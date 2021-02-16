//4. WAP to create singleton class.


class Singleton {
    private static Singleton singleInstance = null;

    public String desc;

    private Singleton() {
        desc = "Solo";
    }

    @Override
    public String toString() {
        return desc;
    }

    public static Singleton getInstance() {
        if (singleInstance == null)
            singleInstance = new Singleton();

        return singleInstance;
    }
}

public class Q4 {
    public static void main(String[] args) {
        Singleton first = Singleton.getInstance();
        Singleton second = Singleton.getInstance();
        System.out.println(first); // first == second
        System.out.println(second);
    }
}

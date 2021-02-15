// Q9. Write a program to display values of enums using a constructor & getPrice() method
// (Example display house & their prices)


enum House {
    SMALL(100),
    MEDIUM(200),
    LARGE(300);

    private final int price;

    House(int price) {
        this.price = price;
    }

    int getPrice() {
        return price;
    }
}


public class Q9 {
    public static void main(String[] args) {
        for (var house : House.values()) {
            System.out.println("\nHouse Size: " + house + "\n\t\tPrice: " + house.getPrice());

        }
    }
}

// Q9. Design classes having attributes for furniture where there are wooden chairs and tables,
//  metal chairs and tables. There are stress and fire tests for each products.


enum RawMaterial {
    WOOD, METAL;
}

abstract class Furniture {
    int length;
    int breadth;
    int height;
    RawMaterial type;

    public abstract void stress();

    public abstract void fire();
}


class Table extends Furniture {

    public Table(int length, int breadth, int height, RawMaterial type) {
        this.length = length;
        this.breadth = breadth;
        this.height = height;
        this.type = type;
    }

    @Override
    public void stress() {
        System.out.println("Table stress()");
    }

    @Override
    public void fire() {
        System.out.println("Table fire()");
    }

    @Override
    public String toString() {
        return "Table{" +
                "length=" + length +
                ", breadth=" + breadth +
                ", height=" + height +
                ", type=" + type +
                '}';
    }
}

class Chair extends Furniture {

    @Override
    public void stress() {
        System.out.println("Chair stress()");
    }

    @Override
    public void fire() {
        System.out.println("Chair fire()");
    }

    public Chair(int length, int breadth, int height, RawMaterial type) {
        this.length = length;
        this.breadth = breadth;
        this.height = height;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Chair{" +
                "length=" + length +
                ", breadth=" + breadth +
                ", height=" + height +
                ", type=" + type +
                '}';
    }
}


public class Q9 {
    public static void main(String[] args) {

        //Test Code
        Furniture woodTable = new Table(30, 20, 20, RawMaterial.WOOD);
        Furniture metalTable = new Table(30, 20, 20, RawMaterial.METAL);
        Furniture woodChair = new Chair(30, 20, 20, RawMaterial.WOOD);
        Furniture metalChair = new Chair(30, 20, 20, RawMaterial.METAL);

        System.out.println(woodTable);
        System.out.println(metalTable);
        System.out.println(woodChair);
        System.out.println(metalChair);

        woodTable.fire();
        woodChair.stress();
    }
}

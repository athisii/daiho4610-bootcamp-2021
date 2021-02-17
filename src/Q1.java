//Write Java code to define List . Insert 5 floating point numbers in List,
// and using an iterator,  find the sum of the numbers in List.

import java.util.ArrayList;
import java.util.List;

public class Q1 {

    public static void main(String[] args) {
        float sum = 0f;
        List<Float> list = new ArrayList<>();
        list.add(1.2f);
        list.add(2.3f);
        list.add(3.4f);
        list.add(4.5f);
        list.add(5.6f);

        var iter = list.listIterator();

        while(iter.hasNext()) {
            sum += iter.next();
        }
        System.out.println(sum);

    }
}

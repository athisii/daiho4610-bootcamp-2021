// Q6. Print the elements of an array in the decreasing frequency,
// if 2 numbers have same frequency then print the one which came first.


import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class Element implements Comparable<Element> {
    private final int index;
    private int frequency;

    public Element(int index) {
        this.index = index;
        this.frequency = 1;
    }

    public int getIndex() {
        return index;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Element other) {
        int val = other.frequency - this.frequency;
        if (val == 0) {
            return this.index - other.index;
        }
        return val;
    }

}


public class Q6 {

    private static void sortByFrequency(int[] array) {

        Map<Integer, Element> elementFrequency = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            if (elementFrequency.get(array[i]) == null)
                elementFrequency.put(array[i], new Element(i));
            else {
                Element element = elementFrequency.get(array[i]);
                element.setFrequency(element.getFrequency() + 1);
            }
        }

        TreeSet<Element> indexAndFrequency = new TreeSet<>();
        elementFrequency.forEach((key, indexFrequency) -> indexAndFrequency.add(indexFrequency));


        indexAndFrequency.forEach(indexFrequency -> {
            for (int i = 0; i < indexFrequency.getFrequency(); i++) {
                System.out.print(array[indexFrequency.getIndex()] + " ");
            }
        });
    }

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 3, 4, 4, 3, 3, 4};
        sortByFrequency(array);

    }

}


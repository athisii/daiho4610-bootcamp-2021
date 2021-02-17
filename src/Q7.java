/* Q7.  Design a Data Structure SpecialStack that supports all the stack operations like push(), pop(),
      isEmpty(), isFull() and an additional operation getMin() which should return minimum element
      from the SpecialStack. (Expected complexity O(1))
 */

import java.util.Stack;

class SpecialStack extends Stack<Integer> {
    private final int maxSize;
    Stack<Integer> minStack = new Stack<>(); //Auxiliary stack

    public SpecialStack(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    public void push(int element) {
        if (!isFull()) {
            if (isEmpty()) {
                super.push(element);
                minStack.push(element);
            } else {
                super.push(element);
                int y = minStack.pop();
                minStack.push(y);

                if (element <= y)
                    minStack.push(element);
            }
        } else System.out.println("Stack if full!");
    }

    public Integer pop() {
        if (!isEmpty()) {
            int x = super.pop();
            int y = minStack.pop();

            if (y != x)
                minStack.push(y);
            return x;
        }
        return null;
    }

    public Integer getMin() {
        if (!isEmpty()) {
            int element = minStack.pop();
            minStack.push(element);
            return element;
        }
        return null;
    }

    public boolean isFull() {
        return this.size() >= maxSize;
    }
}


public class Q7 {
    public static void main(String[] args) {
        SpecialStack specialStack = new SpecialStack(10);
        specialStack.push(6);
        specialStack.push(2);
        specialStack.push(3);
        specialStack.push(4);
        specialStack.push(1);
        System.out.println("Minimum Number: " + specialStack.getMin());
        specialStack.pop();
        System.out.println("Minimum Number: " + specialStack.getMin());

    }
}

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class can be used to store a list of values of type E.
 * 
 * @author Stuart Reges, interpreted by Iulia Bejsovec
 * @version 02/2020
 */ 
public class ArrayList<E> implements Iterable<E>{
    /** list of all values*/
    private E[] elementData;
    /** current number of elements in the list*/
    private int size;
    /** default capacity of the initial list*/
    private static final int DEFAULT_CAPACITY = 50;

    /**
     * Constructs an empty list of default capacity
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty list of a given capacity(must be bigger or equal 0)
     * @param capacity size of the list
     * @throws IllegalArgumentException if capacity is smaller than 0
     */
    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity: " + capacity);
        }
        elementData = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * Returns the current number of elements in the list
     * @return the current number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * Gets the element at the given index
     * @param index the index of the element to return
     * @return the element at the given index
     */
    public E get(int index) {
        checkIndex(index);
        return elementData[index];
    }

    /**
     * Checks the list and gets the index of the given value
     * @param value the value we are checking the list for
     * @return -1 if the list doesn't contain the value or the index
     * at which the value is
     */
    public int indexOf(E value) {
        for (int i = 0; i < size; i++) {
            if (elementData[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if the list is empty
     * @return true if the list is empty, false if not
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Checks if the list contains a value
     * @param value item that we are checking the list for 
     * @return true if the list contains the value, false otherwise
     */
    public boolean contains(E value) {
        return indexOf(value) >= 0;
    }
    
    /**
     * Adds a value to the list, updates the size of the list
     * @param value new element to be added to the list
     */
    public void add(E value) {
        ensureCapacity(size + 1);
        elementData[size] = value;
        size++;
    }
    
    /**
     * Adds a value to the list at the given index shifting subsequent values right and update the size
     * @param index the index where to add the value at, must be bigger or equal 0 and  smaller than size
     * @param value the value to add to the list
     */
    public void add(int index, E value) {
        checkIndex(index);
        ensureCapacity(size + 1);
        for (int i = size; i >= index + 1; i--) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = value;
        size++;
    }
    
    /**
     * Removes an item from the list at the given index shifting elements left and updates the size
     * @param index the index at which to remove the value, must be be bigger or equal 0 and  smaller than size
     */
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }
        elementData[size - 1] = null;
        size--;
    }
    
    /**
     * Replaces a value in the list with the given value at the given index
     * @param index the index of the element to be set to new value, must be bigger or equal 0 and  smaller than size
     * @param value the value to update the element to
     */
    public void set(int index, E value) {
        checkIndex(index);
        elementData[index] = value;
    }
    
    /**
     * Clears the list by setting every element to null and updates the size
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * Adds all elements of the given list to this list and updates the size
     * @param other the new list to be appended to this list
     */
    public void addAll(ArrayList<E> other) {
        ensureCapacity(size + other.size);
        for (E oneElement : other) {
            add(oneElement);
        }
    }

    /**
     * Creates an iterator of this list
     * @return a new ArrayListIterator
     */
    public ArrayListIterator iterator() {
        return new ArrayListIterator();
        
    }

    /**
     * Ensures that the underlying array has the given capacity; if not, the size is doubled (or more if given capacity
     * is even larger)
     * @param capacity the new capacity that we need the list to be
     */
    private void ensureCapacity(int capacity) {
        if (capacity > elementData.length) {
            int newCapacity = elementData.length + elementData.length / 2  + 1;
            if (capacity > newCapacity) {
                newCapacity = capacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    /**
     * Checks if the given index is within legal limits - be bigger or equal 0 and  smaller than size
     * @param index the index to check
     * @throws IndexOutOfBoundsException if the given index is not a legal index for the current list
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
    }
    
    /**
     * Compresses all non-null elements to the beginning of the list conserving the order of the non-null elements.
     * The new list will only have non-null values and be of the same capacity.
     */
    public void compressNulls(){

        int elementPointer = 0;
        while (elementData[elementPointer] != null && elementPointer < this.size){
            elementPointer++;
            if (elementPointer >= this.size){
                return;
            }
        }

        int counter = elementPointer;
        int counterNulls = 1;
        for (elementPointer++; elementPointer <= this.size; elementPointer++){
            if (elementData[elementPointer] != null){
                elementData[counter] = elementData[elementPointer];
                counter++;
            } else {
                counterNulls++;
            }
        }
        this.size = this.size - counterNulls+1;
    }
    
    /**
     * Creates a comma-separated bracketed version of the list
     * @return a comma-separated bracketed version of the list
     */
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        } else {
            StringBuilder result = new StringBuilder("[" + elementData[0]);
            for (int i = 1; i < size; i++) {
                result.append(", ").append(elementData[i]);
            }
            result.append("]");
            return result.toString();
        }
    }
    
    
    /**
     * Support class for ArrayList iterator implementation
     */
    private class ArrayListIterator implements Iterator<E>{
        /** current position of the iterator while going through the list */
        private int position;           
        /** whether it's okay to remove the current element */
        private boolean removeOK;      
        
        /**
         * Constructs an iterator for the given list, sets the removeOK to false
         */
        public ArrayListIterator() {
            position = 0;
            removeOK = false;
        }

        /**
         * Checks if there is an element next to the current position
         * @return true if there is an element next to the current position, false otherwise
         */
        public boolean hasNext() {
            return position < size();
        }

        /**
         * Gets the element at the current position, updates the position and sets the removeOK to true
         * @throws NoSuchElementException if there is no more element in the list
         * @return the element at the current position
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E result = elementData[position];
            position++;
            removeOK = true;
            return result;
        }

        /**
         * Removes the element at the current position, if removeOK is true, updates the position and sets removeOK
         * to false
         * @throws IllegalStateException if removeOK is false
         */
        public void remove() {
            if (!removeOK) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(position - 1);
            position--;
            removeOK = false;
        }
    }
}
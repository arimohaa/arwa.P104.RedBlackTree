import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {

    protected BinaryNode<T> root; // reference to root node of tree, null when empty

    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Checks if the tree is empty.
     * @return true if the tree contains no values, false otherwise.
     */
    public boolean isEmpty () {
        if (root == null) {
            return true; 
        }
        return false; 
    }
    
    /**
     * Removes all values and duplicates from the tree.
     */
    public void clear(){ 
        root = null; 
    }

    /**
     * Counts the number of values in the tree, including duplicates.
     * @return the number of values in the tree, including duplicates.
     */
    public int size() {
        return sizeHelper(this.root);
    }

    /**
     * Helper method that counts the number of values in the tree, including duplicates.
     * @param currentNode the current node which the count is started from. 
     * @return the number of values in the tree, including duplicates.
     */
    protected int sizeHelper(BinaryNode<T> currentNode){

        int count = 0; 
        int leftCount = 0; 
        int rightCount = 0; 

        // first base case: is the actual root not there --> if so size is zero 
        if (currentNode == null) {
            return 0; 
        }

        // second base case: if current node is a leaf node, return the size
        if (currentNode.downLeft() == null && currentNode.downRight() == null) {
            count = 1 + leftCount + rightCount; 
            return count; 
        }

        // recursive case 
        // left side 
        if (currentNode.downLeft() != null){ 
            leftCount++; 
            leftCount = sizeHelper(currentNode.downLeft());
        }

        // right side
        if (currentNode.downRight() != null) {
            rightCount++; 
            rightCount = sizeHelper(currentNode.downRight());
        }

        count = 1 + leftCount + rightCount;
        return count; 
    }

    /**
     * Check whether data is stored in the tree.
     * @param find the value to check for in the collection
     * @return true if the collection contains data one or more times, 
     *         and false otherwise
     */
    public boolean contains(Comparable<T> find){
        return containsHelper(root, find);
    }

    /**
     * Checks whether data is stored in the tree.
     * @param currentNode which acts as the start/root of the tree
     *                     and is a temp value/node. 
     * @param find the value to check for in the collection
     * @return true if the tree contains data one or more times, 
     *         and false otherwise
     */
    protected boolean containsHelper(BinaryNode<T> currentNode, Comparable<T> find){
        
        // base cases
        if (currentNode == null) {
            return false; 
        }

        if (currentNode.getEntry().equals(find)){
            return true; 
        }

         if (currentNode.downLeft() == null && currentNode.downRight() == null) {
            return false; 
        }

        // left and right check + recursive case
        BinaryNode<T> leftChild = currentNode.downLeft(); 
        BinaryNode<T> rightChild = currentNode.downRight();

        T currentValue = currentNode.getEntry(); 

        // left check 
        if (find.compareTo(currentValue) < 0) {
            return containsHelper(leftChild, find);
        } else {
            // right check
            return containsHelper(rightChild, find);
        }
        
    }

    /**
     * Inserts a new data value into the sorted collection.
     * @param data the new value being inserted
     * @throws NullPointerException if data argument is null, we do not allow
     *         null values to be stored within a SortedCollection
     */
    public void add(T data) throws NullPointerException {
        // null check
        if (data == null) {
            throw new NullPointerException("Added value can't be null!"); 
        }

        BinaryNode<T> nodeToAdd = new BinaryNode<>(data); 
        if (root == null) {
            root = nodeToAdd; 
        } else {
            addHelper(nodeToAdd, root); 
        }
    }

    /**
     * Performs the naive binary search tree insert algorithm to recursively
     * insert the provided newNode (which has already been initialized with a
     * data value) into the provided tree/subtree. When the provided subtree
     * is null, this method does nothing. 
     * @param newNode trying to add to the tree. 
     * @param subtree temporary node value representing a subtree/current node. 
     */
    protected void addHelper(BinaryNode<T> newNode, BinaryNode<T> subtree) {

        // base case
        if (subtree == null) {
            return; // quits the method, nothing gets added
        }

        T newNodeValue = newNode.getEntry(); // the new node trying to add's value
        T currentNodeValue = subtree.getEntry(); // the current node's value 

        BinaryNode<T> leftChild = subtree.downLeft(); 
        BinaryNode<T> rightChild = subtree.downRight(); 

        // left check 
        if (newNodeValue.compareTo(currentNodeValue) <= 0) {
            // check if left child of root is null
            if (subtree.downLeft() == null) {
                subtree.setLeft(newNode); // new node is now left child 
                newNode.setUp(subtree); // parent of new node is confirmed to be subtree
            } else {
                // recurse down until left child is null 
                addHelper(newNode, leftChild);
            }
        } else {
            // right check 
            if (subtree.downRight() == null) {
                subtree.setRight(newNode); // new node is now right child 
                newNode.setUp(subtree); // parent of the new node is updated to be the og subtree
            } else {
                // recurse down until right child is null
                addHelper(newNode, rightChild); 
            }
        }
    }

    // REMOVED ALL PLACEHOLDER METHODS AND ADDING ROTATE MEHTOD 

}
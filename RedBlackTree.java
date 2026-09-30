import org.junit.jupiter.api.Test; 
import static org.junit.jupiter.api.Assertions.*; 

public class RedBlackTree<T extends Comparable<T>> extends BSTRotation<T> {

    /**
     * Checks if a new red node in the RedBlackTree causes a red property violation
     * by having a red parent. If this is not the case, the method terminates without
     * making any changes to the tree. If a red property violation is detected, then
     * the method repairs this violation and any additional red property violations
     * that are generated as a result of the applied repair operation.
     * Using this method might cause nodes with a value equal to the value of one of
     * their ancestors to appear within the left and the right subtree of that ancestor,
     * even if the original insertion procedure consistently inserts such nodes into only
     * the left or the right subtree. But it will preserve the ordering of nodes within
     * the tree.
     * @param newNode a newly inserted red node, or a node turned red by previous repair
     */
    protected void ensureRedProperty(RedBlackNode<T> newNode) {
        //establishing all relationships in this tree

        RedBlackNode<T> parent = newNode.up(); 

        if (parent == null || parent.isBlackNode()) {
            return; // ideal case where the parent is a black node/null (no violations)
        }
        
        RedBlackNode<T> grandParent = parent.up(); 
        
        if (grandParent == null) {
            return; 
        }

        boolean parentIsLeft = (grandParent.downLeft() == parent); 

        RedBlackNode<T> auntie; 
        if (parentIsLeft == true) {
            auntie = grandParent.downRight(); //aunt is right child
        } else {
            auntie = grandParent.downLeft(); // aunt is left child
        }

        // case 1: red aunt, recolor operation
        if (auntie != null && auntie.isBlackNode() != true) {
            //recolor
            parent.isBlackNode = true;
            auntie.isBlackNode = true; 
            grandParent.isBlackNode = false; 

            ensureRedProperty(grandParent); // recurse up till whole tree properly recolored
            return; 
        }

        //case 2: auntie is black or null
        boolean nodeIsLeft = (parent.downLeft() == newNode); 

        if (nodeIsLeft != parentIsLeft) {
            rotate(newNode, parent); // rotate first 
            parent = newNode;
        }
        rotate(parent, grandParent); 
        parent.isBlackNode = true; // recolor operations
        grandParent.isBlackNode = false; 
    }

    /**
     * Inserts a new red node into the RedBlackTree.
     * @param data the information in the newly inserted red node.
     */
    @Override
    public void add(T data) {
        if (data == null) {
            throw new NullPointerException("Cant do that"); 
        }

        RedBlackNode<T> newNode = new RedBlackNode<>(data);
        if (this.root == null) {
            this.root = newNode; 
        } else {
            addHelper(newNode, this.root);
            ensureRedProperty(newNode);
        }
        RedBlackNode<T> rootNode = (RedBlackNode<T>) this.root; 
        rootNode.isBlackNode = true; 
    }

    //---- TESTER METHODS ---

    /**
     * Tests if the Red Aunt Recolor Operation works for a basic tree. 
     */
    @Test
    public void testRedAuntieRecolorOperation() {
        RedBlackTree<Integer> testTree = new RedBlackTree<>(); 
        testTree.add(20); 
        testTree.add(15); 
        testTree.add(25); 
        //insert 10 to essentially test the recolor operation
        testTree.add(10); 

        RedBlackNode<Integer> rootNode = (RedBlackNode<Integer>) testTree.root; 

        //shape check
        assertEquals(20, rootNode.getEntry()); 
        assertEquals(15, rootNode.downLeft().getEntry()); 
        assertEquals(25, rootNode.downRight().getEntry()); 
        assertEquals(10, rootNode.downLeft().downLeft().getEntry()); 

        //color check 
        assertTrue(rootNode.isBlackNode()); 
        assertTrue(rootNode.downLeft().isBlackNode()); 
        assertTrue(rootNode.downRight().isBlackNode()); 
        assertFalse(rootNode.downLeft().downLeft().isBlackNode()); 
    }

    /**
     * Tests if the Null Aunt Rotate and Recolor Operation works 
     * for a basic tree. 
     */
    @Test 
    public void testNullAuntieOperation(){
        //making my tree
        RedBlackTree<Integer> testTree = new RedBlackTree<>(); 
        testTree.add(20); 
        testTree.add(30); 
        testTree.add(40); //right leaning tree 

        RedBlackNode<Integer> rootNode = (RedBlackNode<Integer>) testTree.root; 
        //shape check
        assertEquals(30, rootNode.getEntry()); 
        assertEquals(20, rootNode.downLeft().getEntry()); 
        assertEquals(40, rootNode.downRight().getEntry()); 


        //color check
        assertTrue(rootNode.isBlackNode()); 
        assertFalse(rootNode.downLeft().isBlackNode()); 
        assertFalse(rootNode.downRight().isBlackNode()); 

    }

    /**
     * Tests if the Red Aunt Recolor Operation 
     * and the Black Aunt Rotate and Recolor Operation 
     * works for the tree laid out in Question #4 of RBTInsert Quiz. 
     */
    @Test
    public void testBlackAuntieOperation(){
        //case from the quiz
        //Question 4: RBT Insert
        RedBlackTree<Character> testTree = new RedBlackTree<>(); 
        //This initially tests a red aunt, but the black aunt operation is triggered after the recolor fix
        testTree.add('O');
        testTree.add('F');
        testTree.add('V');
        testTree.add('D');
        testTree.add('I');
        testTree.add('T');
        testTree.add('X');
        testTree.add('H');
        testTree.add('K');

        //insert N
        testTree.add('N'); 

        //establish root node
        RedBlackNode<Character> rootNode = (RedBlackNode<Character>) testTree.root; 

        //shape checks
        RedBlackNode<Character> f = rootNode.downLeft();
        RedBlackNode<Character> o = rootNode.downRight();
        RedBlackNode<Character> k = o.downLeft();
        RedBlackNode<Character> v = o.downRight();

        assertEquals('I', rootNode.getEntry()); 
        assertEquals('F', f.getEntry()); 
        assertEquals('O', o.getEntry()); 
        assertEquals('D', f.downLeft().getEntry()); 
        assertEquals('H', f.downRight().getEntry()); 
        assertEquals('K', k.getEntry()); 
        assertEquals('N', k.downRight().getEntry()); 
        assertEquals('V', v.getEntry()); 
        assertEquals('T', v.downLeft().getEntry()); 
        assertEquals('X', v.downRight().getEntry()); 

        //color check 
        assertTrue(rootNode.isBlackNode()); 
        assertFalse(f.isBlackNode()); 
        assertFalse(o.isBlackNode()); 
        assertTrue(f.downLeft().isBlackNode()); 
        assertTrue(f.downRight().isBlackNode()); 
        assertTrue(k.isBlackNode()); 
        assertFalse(k.downRight().isBlackNode()); 
        assertTrue(v.isBlackNode()); 
        assertFalse(v.downRight().isBlackNode()); 
        assertFalse(v.downLeft().isBlackNode());
    }

}
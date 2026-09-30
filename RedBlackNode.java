/**
 * This class represents a node in a RedBlackTree and inherits from BinaryNode.
 */
public class RedBlackNode<T> extends BinaryNode<T> {

    // store whether this is a red or black node
    protected boolean isBlackNode = false;

    /**
     * Constructor that creates a new node with the value data.
     * Both parent and child references of the new node are initialized to nul>
     * @param data the value the new node stores
     */
    public RedBlackNode(T data) { super(data); }

    /**
     * Overrides the downLeft() method from BinaryNode so that child reference>
     * as a RedBlackNode and does not need to be cast.
     */
    @Override
    public RedBlackNode<T> downLeft() {
        return (RedBlackNode<T>)this.left;
    }

    /**
     * Overrides the downRight() method from BinaryNode so that child referenc>
     * as an RedBlackNode and does not need to be cast.
     */
    @Override
    public RedBlackNode<T> downRight() {
        return (RedBlackNode<T>)this.right;
    }

    /**
     * Overrides the up() method from BinaryNode so that child reference is returned
     * as a RedBlackNode and does not need to be cast.
     */
    @Override
    public RedBlackNode<T> up() {
        return (RedBlackNode<T>)this.up;
    }

    /**
     * Returns a boolean that indicates if this is a red or black node.
     * @return true if the node is black, false if it is red
     */
    public boolean isBlackNode() {
        return this.isBlackNode;
    }

    /**
     * Inverts the color of this node, turning it either from red to black, or from
     * black to red.
     */
    public void flipColor() {
        this.isBlackNode = !this.isBlackNode;
    }

    /**
     * Returns a string representation for this node.
     * @return a string representation of the node's value and color
     */
    @Override
    public String toString() {
        return this.getEntry().toString() + ( this.isBlackNode() ? ".b" : ".r" );
    }

}

/*
 * Author: Arwa Mohamud
 * Email: mohamud4@wisc.edu
 * Course: CS400, Fall 2026
 * Assignment: P102.BSTRotation
 */

public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree_Placeholder<T> {
    
    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a right
     * child of the provided parent, this method will perform a left rotation.
     *
     * @param child is the node being rotated from child to parent position 
     * @param parent is the node being rotated from parent to child position
     */
    protected void rotate(BinaryNode<T> child, BinaryNode<T> parent) {

        // null check for parent and child
        if (child == null || parent == null) {
            throw new IllegalArgumentException("Can't have null nodes");
            // do nothing get out of this method 
        }

        // checks if child is even the is the child of parent here 
        if (parent.downRight() != child && parent.downLeft() != child) {
            return; // the right and left children must equal the child 
        }

        // right child, left child, parent check 

        BinaryNode<T> grandParent = parent.up(); 

        if (child.isRightChild()) {
            // LEFT ROTATE
            BinaryNode<T> movedSubtree = child.downLeft(); // subtree that gets moved from original spot in rotation

            // turn the moveable subtree to the right child of the parent
            parent.setRight(movedSubtree);
            if (movedSubtree != null) {
                movedSubtree.setUp(parent); 
            }

            // parent becomes left child of the child 
            child.setLeft(parent); 
            parent.setUp(child); // swap 

            // updating grandparent 
            child.setUp(grandParent); 
            if (grandParent == null) {
                this.root = child; 
            } else if (grandParent.downLeft() == parent) {
                // if parent was left child of grandparent
                grandParent.setLeft(child); 
            } else {
                // if parent was right child of grandparent 
                grandParent.setRight(child); 
            }

        } else {
            
            // RIGHT ROTATE
            BinaryNode<T> movedSubtree = child.downRight(); // subtree that gets moved from original spot in rotation

            // turn the moveable subtree to the left child of the parent
            parent.setLeft(movedSubtree);
            if (movedSubtree != null) {
                movedSubtree.setUp(parent); 
            }

            // parent becomes right  child of the child 
            child.setRight(parent); 
            parent.setUp(child); // swap 

            // update grandparent
            child.setUp(grandParent); 
            if (grandParent == null) {
                this.root = child; 
            } else if (grandParent.downLeft() == parent) {
                // if parent was left child of grandparent
                grandParent.setLeft(child); 
            } else {
                // if parent was right child of grandparent 
                grandParent.setRight(child); 
            }
        }
    }


} 
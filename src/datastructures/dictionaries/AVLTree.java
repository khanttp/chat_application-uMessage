package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will
 *    instead mask the super-class fields (i.e., the resulting node
 *    would actually have multiple copies of the node fields, with
 *    code accessing one pair or the other depending on the type of
 *    the references used to access the instance).  Such masking will
 *    lead to highly perplexing and erroneous behavior. Instead,
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {

    public class AVLNode extends BSTNode {

        private int balance;
        private int height;

        public AVLNode(K key, V value) {
            super(key, value);
            this.height = 0;

        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }

        // see if value is already stored
        V val = find(key);

        root = insert((AVLNode) root, key, value);

        // return value
        return val;
    }

    private AVLNode insert(AVLNode node, K key, V value) {

        //create a new node if the node is null
        if (node == null) {
            size++;
            return new AVLNode(key, value);
        }

        // find a place to insert new node
        if (key.compareTo(node.key) >= 0) {
            if (key.compareTo(node.key) > 0) {
                node.children[1] = insert((AVLNode) node.children[1], key, value);

            } else {
                node.value = value;
            }
        } else {
            node.children[0] = insert((AVLNode) node.children[0], key, value);
        }

        // get height and update the balance
        computeHeight(node);
        getBalance(node);

        // maintain tree balance [-1,0,1]

        // if balance factor is greater than 1
        if (node.balance > 1) {
            AVLNode curr = (AVLNode) node.children[1];

            // if the balance of right child of node is negative
            if (curr.balance < 0) {

                // rotate right child of node to the right
                node.children[1] = rightRotation((AVLNode) node.children[1]);
            }

            // rotate node to the left
            node = leftRotation(node);

            // if balance factor is less than 1
        } else if (node.balance < -1) {

            AVLNode curr = (AVLNode) node.children[0];

            // if the balance factor of left child of node is positive
            if (curr.balance > 0) {

                // rotate left child of node to the left
                node.children[0] = leftRotation((AVLNode) node.children[0]);
            }

            // rotate node to the right
            node = rightRotation(node);
        }
        return node;
    }

    // return height of the node
    public int getHeight(AVLNode node) {
        if (node == null) {
            return -1;
        }

        return node.height;
    }

    // compute the height of the node
    public void computeHeight(AVLNode node){
        // max of left children + right children
        node.height = Math.max(getHeight((AVLNode) node.children[0]), getHeight((AVLNode) node.children[1])) + 1;
    }

    // return balance of node
    public void getBalance(AVLNode node){

        // right height - left height
        node.balance = getHeight((AVLNode) node.children[1]) - getHeight((AVLNode) node.children[0]);

    }


    // perform left rotation of the tree
    public AVLNode leftRotation(AVLNode node){

        if(node == null) return null;

        // right child of node
        AVLNode temp = (AVLNode) node.children[1];

        // left child of right child of node
        AVLNode temp2 = (AVLNode) temp.children[0];

        // swap
        // set node to left child of temp
        temp.children[0] = node;
        // set right child of node to temp2
        node.children[1] = temp2;

        // compute new heights
        computeHeight(node);
        computeHeight(temp);

        return temp;
    }

    // perform left rotation of the tree
    public AVLNode rightRotation(AVLNode node){
        if (node==null) return null;

        // left child of node
        AVLNode temp = (AVLNode) node.children[0];

        // right child of left child of node
        AVLNode temp2 = (AVLNode) temp.children[1];

        // swap
        temp.children[1] = node;
        node.children[0] = temp2;

        // compute new heights
        computeHeight(node);
        computeHeight(temp);

        return temp;
    }


}


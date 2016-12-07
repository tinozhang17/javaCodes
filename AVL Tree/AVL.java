import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Collection;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Ziyu Zhang
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    // Do not make any new instance variables.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     */
    public AVL() {
        size = 0;
        root = null;
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        for (T i : data) {
            try {
                add(i);
            } catch (IllegalArgumentException e) {
                throw e;
            }
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        AVLNode<T> newNode = new AVLNode<>(data);
        root = addHelper(newNode, root);
    }

    /**
     * helper method to add a node to the tree
     *
     * @param data the data node to be added
     * @param node the current node to be examined
     * @return the added node, or the current node being examined
     */
    private AVLNode<T> addHelper(AVLNode<T> data, AVLNode<T> node) {
        if (node == null) {
            data.setHeight(0);
            data.setBalanceFactor(0);
            size++;
            return data;
        } else if (node.getData().compareTo(data.getData()) > 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (node.getData().compareTo(data.getData()) < 0) {
            node.setRight(addHelper(data, node.getRight()));
        }
        node.setHeight(getHeight(node));
        node.setBalanceFactor(getBF(node));
        if (node.getBalanceFactor() < -1
                && node.getRight().getBalanceFactor() <= 0) {
            node = rotateLeft(node);
        } else if (node.getBalanceFactor() > 1
                && node.getLeft().getBalanceFactor() >= 0) {
            node = rotateRight(node);
        } else if (node.getBalanceFactor() > 1
                && node.getLeft().getBalanceFactor() < 0) {
            node = rotateLR(node);
        } else if (node.getBalanceFactor() < -1
                && node.getRight().getBalanceFactor() > 0) {
            node = rotateRL(node);
        }
        return node;
    }

    /**
     * helper method to get the height of a node
     *
     * @param node the node being examined
     * @return the height of the node
     */
    private int getHeight(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else if (node.getLeft() == null) {
            return node.getRight().getHeight() + 1;
        } else if (node.getRight() == null) {
            return node.getLeft().getHeight() + 1;
        } else {
            return Math.max(node.getLeft().getHeight(),
                    node.getRight().getHeight()) + 1;
        }
    }

    /**
     * helper method to get the balance factor of a node
     *
     * @param node the node being examined
     * @return the balance factor of the node
     */
    private int getBF(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else if (node.getLeft() == null) {
            return -1 - node.getRight().getHeight();
        } else if (node.getRight() == null) {
            return node.getLeft().getHeight() + 1;
        } else {
            return node.getLeft().getHeight() - node.getRight().getHeight();
        }
    }

    /**
     * helper method to do left rotation
     *
     * @param node the node that needed to be rotated, AKA the "hinge"
     * @return the lead node after rotation is completed
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> right = node.getRight();
        AVLNode<T> star = node.getRight().getLeft();
        node.setRight(star);
        node.setHeight(getHeight(node));
        node.setBalanceFactor(getBF(node));
        right.setLeft(node);
        right.setHeight(getHeight(right));
        right.setBalanceFactor(getBF(right));
        return right;
    }

    /**
     * helper method to do right rotation
     *
     * @param node the node that needed to be rotated, AKA the "hinge"
     * @return the lead node after rotation is completed
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> star = node.getLeft().getRight();
        AVLNode<T> left = node.getLeft();
        node.setLeft(star);
        node.setHeight(getHeight(node));
        node.setBalanceFactor(getBF(node));
        left.setRight(node);
        left.setHeight(getHeight(left));
        left.setBalanceFactor(getBF(left));
        return left;
    }

    /**
     * helper method to do left right rotation
     *
     * @param node the node that needed to be rotated, AKA the "hinge"
     * @return the lead node after rotation is completed
     */
    private AVLNode<T> rotateLR(AVLNode<T> node) {
        AVLNode<T> leftNode = node.getLeft();
        AVLNode<T> leftRightNode = node.getLeft().getRight();
        AVLNode<T> square = leftRightNode.getLeft();
        leftNode.setRight(square);
        leftNode.setHeight(getHeight(leftNode));
        leftNode.setBalanceFactor(getBF(leftNode));
        leftRightNode.setLeft(leftNode);
        leftRightNode.setHeight(getHeight(leftRightNode));
        leftRightNode.setBalanceFactor(getBF(leftRightNode));
        node.setLeft(leftRightNode);
        node.setHeight(getHeight(node));
        node.setBalanceFactor(getBF(node));
        return rotateRight(node);
    }

    /**
     * helper method to do right left rotation
     *
     * @param node the node that needed to be rotated, AKA the "hinge"
     * @return the lead node after rotation is completed.
     */
    private AVLNode<T> rotateRL(AVLNode<T> node) {
        AVLNode<T> rightNode = node.getRight();
        AVLNode<T> rightLeftNode = node.getRight().getLeft();
        AVLNode<T> triangle = rightLeftNode.getRight();
        rightNode.setLeft(triangle);
        rightNode.setHeight(getHeight(rightNode));
        rightNode.setBalanceFactor(getBF(rightNode));
        rightLeftNode.setRight(rightNode);
        rightLeftNode.setHeight(getHeight(rightLeftNode));
        rightLeftNode.setBalanceFactor(getBF(rightLeftNode));
        node.setRight(rightLeftNode);
        node.setHeight(getHeight(node));
        node.setBalanceFactor(getBF(node));
        return rotateLeft(node);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        AVLNode<T> answer = removeHelper(root, data);
        root = answer.getRight();
        return answer.getLeft().getData();
    }

    /**
     * helper method to make inorder traversal
     *
     * @param current the node currently being examined
     * @param data the data we want to remove
     * @return a dummy node, whose right is the current node
     * after the intended node is removed, and whose left is
     * the removed node.
     */
    private AVLNode<T> removeHelper(AVLNode<T> current, T data) {
        AVLNode<T> tmp = null;
        if (current == null) {
            throw new NoSuchElementException("no such element!");
        } else if (current.getData().equals(data)) {
            size--;
            // if node.data == data
            if (current.getLeft() == null && current.getRight() == null) {
                // no child
                tmp = new AVLNode<>(current.getData());
                tmp.setLeft(current);
                tmp.setRight(null);
            } else if (current.getLeft() != null
                    && current.getRight() != null) {
                // 2 children
                // left == predecessor, right == rest of the stuff;
                tmp = findPredecessor(current.getLeft());
                AVLNode<T> replace = tmp.getLeft(); // predecessor
                replace.setLeft(tmp.getRight());
                replace.setRight(current.getRight());
                replace.setHeight(getHeight(replace));
                replace.setBalanceFactor(getBF(replace));
                if (replace.getBalanceFactor() < -1
                        && replace.getRight().getBalanceFactor() <= 0) {
                    replace = rotateLeft(replace);
                } else if (replace.getBalanceFactor() > 1
                        && replace.getLeft().getBalanceFactor() >= 0) {
                    replace = rotateRight(replace);
                } else if (replace.getBalanceFactor() > 1
                        && replace.getLeft().getBalanceFactor() < 0) {
                    replace = rotateLR(replace);
                } else if (replace.getBalanceFactor() < -1
                        && replace.getRight().getBalanceFactor() > 0) {
                    replace = rotateRL(replace);
                }
                // left == removed node, right == rest of the stuff
                tmp.setLeft(current);
                tmp.setRight(replace);
            } else if (current.getLeft() != null) {
                // right child is null
                tmp = new AVLNode<>(current.getData());
                tmp.setLeft(current);
                tmp.setRight(current.getLeft());
            } else if (current.getRight() != null) {
                // left child is left
                tmp = new AVLNode<>(current.getData());
                tmp.setLeft(current);
                tmp.setRight(current.getRight());
            }
            return tmp;
        } else if (current.getData().compareTo(data) > 0) {
            // if node.data > data, go left
            tmp = removeHelper(current.getLeft(), data);
            current.setLeft(tmp.getRight());
        } else if (current.getData().compareTo(data) < 0) {
            // if node.data < data, go right
            tmp = removeHelper(current.getRight(), data);
            current.setRight(tmp.getRight());
        }
        current.setHeight(getHeight(current));
        current.setBalanceFactor(getBF(current));
        if (current.getBalanceFactor() < -1
                && current.getRight().getBalanceFactor() <= 0) {
            current = rotateLeft(current);
        } else if (current.getBalanceFactor() > 1
                && current.getLeft().getBalanceFactor() >= 0) {
            current = rotateRight(current);
        } else if (current.getBalanceFactor() > 1
                && current.getLeft().getBalanceFactor() < 0) {
            current = rotateLR(current);
        } else if (current.getBalanceFactor() < -1
                && current.getRight().getBalanceFactor() > 0) {
            current = rotateRL(current);
        }
        tmp.setRight(current);
        return tmp;
    }

    /**
     * helper method to get the predecessor, adjusting
     * the remaining of the tree, including height and
     * balance factor
     *
     * @param current the node currently being examined
     * @return the predecessor, or the current node.
     */
    private AVLNode<T> findPredecessor(AVLNode<T> current) {
        if (current.getRight() == null) {
            // left == predecessor, right == rest of the stuff
            AVLNode<T> tmp = new AVLNode<>(current.getData());
            tmp.setLeft(current);
            tmp.setRight(current.getLeft());
            return tmp;
        } else {
            // left == predecessor, right == rest of the stuff
            AVLNode<T> tmp = findPredecessor(current.getRight());
            current.setRight(tmp.getRight());
            current.setHeight(getHeight(current));
            current.setBalanceFactor(getBF(current));
            if (current.getBalanceFactor() < -1
                    && current.getRight().getBalanceFactor() <= 0) {
                current = rotateLeft(current);
            } else if (current.getBalanceFactor() > 1
                    && current.getLeft().getBalanceFactor() >= 0) {
                current = rotateRight(current);
            } else if (current.getBalanceFactor() > 1
                    && current.getLeft().getBalanceFactor() < 0) {
                current = rotateLR(current);
            } else if (current.getBalanceFactor() < -1
                    && current.getRight().getBalanceFactor() > 0) {
                current = rotateRL(current);
            }
            tmp.setRight(current);
            return tmp;
        }
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        AVLNode<T> answer;
        try {
            answer = getHelper(root, data);
        } catch (NoSuchElementException e) {
            throw e;
        }
        // if the data is in the root
        return answer.getData();
    }

    /**
     * helper method to get a particular node matching data pssed in
     *
     * @param node the node currently being examined
     * @param data the list to add the data item to
     * @return the node that contains the data
     */
    private AVLNode<T> getHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("no such element");
        } else if (node.getData().equals(data)) {
            return node;
        } else if (node.getData().compareTo(data) > 0) {
            // node.data > data
            return getHelper(node.getLeft(), data);
        } else {
            // node.data < data
            return getHelper(node.getRight(), data);
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        return containsHelper(root, data);
    }

    /**
     * helper method to help determine if a node exists in the tree
     *
     * @param node the node currently being examined
     * @param data the data we are searching for
     * @return the boolean value of whether the node exists in the tree
     */
    private boolean containsHelper(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        } else if (node.getData().equals(data)) {
            return true;
        } else if (node.getData().compareTo(data) > 0) {
            return containsHelper(node.getLeft(), data);
        } else {
            return containsHelper(node.getRight(), data);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorderHelper(root, list);
        return list;
    }

    /**
     * helper method to make preorder traversal
     *
     * @param node the node currently being examined
     * @param list the list to add the data item to
     */
    private void preorderHelper(AVLNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorderHelper(node.getLeft(), list);
            preorderHelper(node.getRight(), list);
        }
    }

    @Override
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;
    }

    /**
     * helper method to make postorder traversal
     *
     * @param node the node currently being examined
     * @param list the list to add the data item to
     */
    private void postorderHelper(AVLNode<T> node, List<T> list) {
        if (node != null) {
            postorderHelper(node.getLeft(), list);
            postorderHelper(node.getRight(), list);
            list.add(node.getData());
        }
    }

    @Override
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    /**
     * helper method to make inorder traversal
     *
     * @param node the node currently being examined
     * @param list the list to add the data item to
     */
    private void inorderHelper(AVLNode<T> node, List<T> list) {
        if (node != null) {
            inorderHelper(node.getLeft(), list);
            list.add(node.getData());
            inorderHelper(node.getRight(), list);
        }
    }

    @Override
    public List<T> levelorder() {
        Queue<AVLNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>();
        AVLNode<T> tmp = root;
        if (root != null) {
            queue.add(root);
        }
        while (tmp != null) {
            if (tmp.getLeft() != null) {
                queue.add(tmp.getLeft());
            }
            if (tmp.getRight() != null) {
                queue.add(tmp.getRight());
            }
            AVLNode<T> poped = queue.poll();
            list.add(poped.getData());
            tmp = queue.peek();
        }
        return list;
    }


    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    @Override
    public int depth(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null!");
        }
        return depthHelper(root, data);
    }

    /**
     * Calculate the depth of the node containing the data.
     *
     * @throws java.util.NoSuchElementException if the data is not in the tree.
     * @param data data to calculate the depth of
     * @param node the node to be checked against
     * @return the depth of the node containing the data if it is in the tree
     */
    private int depthHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("no such element!");
        } else if (node.getData().equals(data)) {
            return 1;
        } else if (node.getData().compareTo(data) > 0) {
            return 1 + depthHelper(node.getLeft(), data);
        } else {
            return 1 + depthHelper(node.getRight(), data);
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        return root;
    }
}

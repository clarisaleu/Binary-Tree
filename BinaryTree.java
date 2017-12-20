import java.util.*;

/**
 * A binary search tree for Comparable objects such as Strings, Integers, etc.
 * For each node n, all nodes to the left have data which is less than n.data
 * and all nodes to the right have data which is greater than n.data. *
 * 
 * @param <T>
 */

public class BinaryTree<T extends Comparable<T>> {
	private static class Node<T extends Comparable<T>> {
		public T data;
		public Node<T> left, right;

		public void add(T d) {
			int comp = d.compareTo(data);
			if (comp == 0)
				return; // Already in tree
			if (comp < 0) {
				if (left == null) {
					left = new Node<>();
					left.data = d;
				} else {
					left.add(d);
				}
			} else {
				// Greater than
				if (right == null) {
					right = new Node<>();
					right.data = d;
				} else {
					right.add(d);
				}
			}
		}

		public boolean contains(T d) {
			int comp = d.compareTo(data);
			if (comp == 0)
				return true; // Already in tree
			if (comp < 0) {
				if (left == null) {
					return false; // Not in the tree
				} else {
					return left.contains(d);
				}
			} else {
				if (right == null) {
					return false; // Not in the tree
				} else {
					return right.contains(d);
				}
			}
		}

		public void print(int indent) {
			if (right != null)
				right.print(indent + 1);
			char[] spaces = new char[indent * 2];
			Arrays.fill(spaces, ' ');
			System.out.println(new String(spaces) + data);
			if (left != null)
				left.print(indent + 1);
		}

		/**
		 * The number of nodes of this subtree.
		 * 
		 * @return Number of nodes
		 */
		public int size() {
			// Implement the method recursively
			// We know there is a node here
			int total = 1;
			// This node may have left children
			if (left != null)
				total = total + left.size();
			// This node may have right children
			if (right != null)
				total = total + right.size();
			// The total size of the tree from this point...
			return total;
		}

		/**
		 * Delete this node.
		 * 
		 * @return The new root of this subtree (null if this node had no
		 *         children, also known as a leaf)
		 */
		public Node<T> deleteNode() {
			// Delete "this" and return the new
			// root from this subtree (which could be null
			// if this was a leaf)
			if (left == null)
				return right;
			if (right == null)
				return left;
			Node<T> successor = right;
			if (successor.left == null) {
				// Case 1: no left child of immediate successor
				right = right.right;
			} else {
				// Case 2: loop until we find leftmost child
				Node<T> successorParent = null;
				while (successor.left != null) {
					successorParent = successor;
					successor = successor.left;
				}
				successorParent.left = successor.right;
			}
			// Replace this data with successor data
			data = successor.data;
			return this;
		}

		/**
		 * Deletes the node containing d if it exists. @param d
		 * 
		 * @return A valid BinaryTree that doesn't have d in it but does have
		 *         everything else.
		 */
		public Node<T> delete(T d) {
			// This method will delete the node
			// containing d if it exists
			// It will return a valid BinaryTree
			// that doesn't have d in it but does
			// have everything else
			int comp = d.compareTo(data);
			if (comp == 0)
				return deleteNode();
			if (comp < 0) {
				// If d exists, it's to the left
				if (left != null)
					left = left.delete(d);
				return this;
			} else {
				if (right != null)
					right = right.delete(d);
				return this;
			}
		}

		public int depth() {
			// Calculate the depth of the tree
			// Recursive, of course
			// Depth of a single node: 1
			// Depth of a child of a node: one deeper than parent
			// Depth of the tree: depth of the deepest node
			int leftDepth = 0;
			if (left != null)
				leftDepth = left.depth();
			int rightDepth = 0;
			if (right != null)
				rightDepth = right.depth();
			return 1 + Math.max(leftDepth, rightDepth);
		}

		public boolean isValid(T min, T max) {
			if (min != null) {
				if (min.compareTo(data) >= 0) {
					// data is less than or equal to min: too small!
					return false;
				}
			}
			if (max != null) {
				if (max.compareTo(data) <= 0) {
					// data is greater than or equal to max: too big!
					return false;
				}
			}
			if (left != null) {
				if (!left.isValid(min, data))
					return false;
			}
			if (right != null) {
				if (!right.isValid(data, max))
					return false;
			}
			return true;
		}

		public boolean isValid() {
			// A tree is valid if everything to the
			// left of each node is less than that node
			// and everything to the right of each node
			// is greater than that node
			// HINT: Bootstrap to a method which
			// keeps track of the valid range!
			return isValid(null, null);
		}

		public static Node<String> badTree() {
			Node<String> root = new Node<>();
			root.data = "M";
			root.left = new Node<>();
			root.left.data = "D";
			root.left.right = new Node<>();
			root.left.right.data = "Z";
			root.right = new Node<>();
			root.right.data = "R";
			return root;
		}

		public void putSelfInTree(Node<T> n) {
			n.add(data);
			if (left != null)
				left.putSelfInTree(n);
			if (right != null)
				right.putSelfInTree(n);
		}
	}

	private Node<T> root, result;

	public BinaryTree() {
		root = null;
		result = null;
	}

	/**
	 * Adds data to the tree if it didn't already contain it.
	 * 
	 * @param data
	 */
	public void add(T data) {
		if (root == null) {
			root = new Node<>();
			root.data = data;
		} else {
			root.add(data);
		}
	}

	/**
	 * Returns true if the tree contains data, false otherwise
	 * 
	 * @param data
	 *            Does the tree contain this? * @return true if it does
	 */
	public boolean contains(T data) {
		if (root == null)
			return false;
		return root.contains(data);
	}

	/**
	 * Prints out a representation of the tree (rotate your head 90 degrees
	 * left)
	 */
	public void print() {
		if (root != null)
			root.print(0);
	}

	/**
	 * Gets the number of nodes of the tree in O(n) time.
	 * 
	 * @return number of nodes
	 */
	public int size() {
		if (root == null)
			return 0;
		return root.size();
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void delete(T data) {
		if (root != null)
			root = root.delete(data);
	}

	public int depth() {
		if (root == null)
			return 0;
		return root.depth();
	}

	public boolean isValid() {
		if (root == null)
			return true;
		return root.isValid();
	}

	public static BinaryTree<String> badTree() {
		BinaryTree<String> t = new BinaryTree<>();
		t.root = Node.badTree();
		return t;
	}

	public String toString() {
		return toString(root);
	}

	public String toString(Node<T> n) {
		if(n==null) return "empty";
		if(n.left==null&&n.right==null) return " "+n.data;
		else return "(" + n.data + toString(n.left) + toString(n.right) + ")"; 
	}

	public void printLevel(int n) {
		if (n < 1)
			throw new IllegalArgumentException();
		System.out.println("The data of nodes on level " + n + " ");
		printLevel(root, 1, n);
	}

	public void printLevel(Node<T> n, int level, int k) {
		if (level == k) {
			System.out.println(n.data);
			return;
		} else {
			if (n.left != null) {
				printLevel(n.left, level + 1, k);
			}
			if (n.right != null) {
				printLevel(n.right, level + 1, k);
			}
		}
	}

	/**
	 * 
	 * @param n
	 * @param k
	 * @param level
	 * @return level of node n in tree with data k (Root = index 0)
	 */
	public int getLevel(Node<T> n, T k, int level) {
		if (n == null)
			return 0;
		if (n.data == k)
			return level;
		int result = getLevel(n.left, k, level + 1);
		if (result != 0)
			return result;
		result = getLevel(n.right, k, level + 1);
		return result;
	}

	/**
	 * 
	 * @param n
	 * @param t
	 * @return true if t reaches n false if not
	 */
	public boolean isReaches(Node<T> n, T t) {
		if (n == null)
			return false;
		if (n.data.equals(t))
			return true;
		return isReaches(n.left, t) || isReaches(n.right, t);
	}

	/**
	 * Returns the data value of the node that can reach both a and b in the
	 * least number of steps. If the tree doesn't contain both a and b, return
	 * null.
	 * 
	 * @param a
	 * @param b
	 * @return data value
	 */
	@SuppressWarnings("unchecked")
	public T reachesBoth(T a, T b) {
		// Find the least common ancestor of two nodes in BST
		Node<T> n = root;
		if (root == null)
			return null;
		if (!n.contains(a) || !n.contains(b))
			return null;
		Node<T> cur = null;
		@SuppressWarnings("rawtypes")
		Queue q = new LinkedList<T>();
		q.clear();
		q.add(root);
		while (!q.isEmpty()) {
			Node<T> temp = (Node<T>) q.remove();
			if (isReaches(temp, a) && isReaches(temp, b)) {
				cur = temp;
				q.add(temp.left);
				q.add(temp.right);
			}
		}
		return cur.data;
	}

	/**
	 * Among all the nodes which are farthest from the root, find the one which
	 * is farthest to the right.
	 * 
	 * @return data value of said node
	 */

	// Helper
	public void lowestRight(Node<T> n, int level, Level m) {
		// Base Case
		if (n == null)
			return;
		if ((n.left == null) && (n.right == null) && (level > m.maxLevel)) {
			result = n;
			m.maxLevel = level;
			return;
		}
		lowestRight(n.right, level + 1, m);
		lowestRight(n.left, level + 1, m);
	}

	// Reference Class
	class Level {
		int maxLevel;
	}

	public T findRightmostLowest() {
		Level level = new Level();
		result = root;
		lowestRight(result, 0, level);
		return result.data;
	}

	/**
	 * Return the kth largest element according to the Comparable sorted order
	 * of the tree. The leftmost node has index 0 and the rightmost node has
	 * index size() - 1.
	 * 
	 * @param kindex
	 * @return element, or null if k is out of range.
	 */
	public ArrayList<T> toArrayList() {
		ArrayList<T> result = new ArrayList<T>();
		treeToArray(root, result);
		return result;
	}

	public void treeToArray(Node<T> n, ArrayList<T> result) {
		if (n == null) {
			return;
		}
		treeToArray(n.left, result);
		result.add(n.data);
		treeToArray(n.right, result);
	}

	public T findKthLargest(int k) {
		if (k < 0)
			return null;
		Node<T> temp = root;
		if (k > temp.size() - 1)
			return null;
		ArrayList<T> gets = new ArrayList<T>();
		treeToArray(root, gets);
		return gets.get(k);
	}

	public T findKthLargest(int k, Node<T> oldRoot) {
		if (k < 0)
			return null;
		Node<T> temp = oldRoot;
		if (k > temp.size() - 1)
			return null;
		ArrayList<T> gets = new ArrayList<T>();
		treeToArray(oldRoot, gets);
		return gets.get(k);
	}

	/**
	 * * EXTRA CREDIT: Balance the tree. The new root should be the *
	 * findKthLargest(size()/2) node. Recursively, the root of each subtree *
	 * should also be the size/2-largest node (indexed from 0) of that subtree.
	 * This method should not call new and should execute in O(n log n) time for
	 * full credit.
	 */

	public void balance() {
		if (root != null)
			root = balance(root);
	}

	private Node<T> balance(Node<T> oldRoot) {
		T newRoot = findKthLargest(oldRoot.size() / 2, oldRoot);
		Node<T> n = new Node<T>();
		n.data = newRoot;
		oldRoot.putSelfInTree(n);
		if (n.left != null) {
			n.left = balance(n.left);
		}
		if (n.right != null) {
			n.right = balance(n.right);
		}
		return n;
	}

	public static void main(String[] args) {
		//Random Tests
		BinaryTree<String> n = new BinaryTree<>();
		String[] x = { "M", "A", "Z", "N", "O", "Q", "X", "D" };
		for (String w : x) {
			n.add(w);
		}
		n.print();
		n.printLevel(3);
	}
}

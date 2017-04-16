
///////////////////////////////////////////////////////////////////////////////
// Semester:         CS367 Spring 2017 
// PROJECT:          p4
// FILE:             IntervalTree.java
//						
// Authors: Albert Liu, Christian Colomb, Alonso Del Rio, Kyra Dahl,
//			Weidi Dai, Tavishi Gupta
//
// Author1: Albert Liu, 		liu668@wisc.edu,	liu668, 	002
// Author2: Christian Colomb, 	ccolomb@wisc.edu, 	ccolomb, 	002
// Author3: Alonso Del Rio, 	adelrio@wisc.edu, 	adelrio, 	002
// Author4: Kyra Dahl, 			krdahl2@wisc.edu, 	krdahl2, 	002
// Author5: Weidi Dai, 			wdai38@wisc.edu, 	wdai38, 	002
// Author6: Tavishi Gupta, 		tgupta24@wisc.edu, 	tgupta24, 	002
// 
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.io.*;

public class IntervalTree<T extends Comparable<T>> implements IntervalTreeADT<T> {

	// reference to root node of tree
	private IntervalNode<T> root;

	/**
	 * Constructor to initialize tree with null root node
	 */
	public IntervalTree() {
		root = null;
	}

	/**
	 * Constructor to initialize tree with a give root node
	 * 
	 * @param root
	 *            node to be set at the root
	 */
	public IntervalTree(IntervalNode<T> root) {
		this.root = root;
	}

	@Override
	/** Returns the root node of this IntervalTree. */
	public IntervalNode<T> getRoot() {
		return root;
	}

	/**
	 * Recalculates the maximum end value of the nodes in a node's subtrees
	 * 
	 * @param nodeToRecalculate
	 *            node that is being recalculated
	 * @return new max end value of the node
	 */
	private T recalculateMaxEnd(IntervalNode<T> node) {
		node.setMaxEnd(node.getInterval().getEnd());
		// search left subtree
		if (node.getLeftNode() != null && 
				node.getMaxEnd().compareTo(node.getLeftNode().getMaxEnd()) < 0)
			node.setMaxEnd(node.getLeftNode().getMaxEnd());
		// search right subtree
		if (node.getRightNode() != null && 
				node.getMaxEnd().compareTo(node.getRightNode().getMaxEnd()) < 0)
			node.setMaxEnd(node.getRightNode().getMaxEnd());
		return node.getMaxEnd();
	}

	@Override
	/**
	 * Inserts an Interval in the tree.
	 * 
	 * 
	 * Each Interval is stored as the data item of an IntervalNode. The position
	 * of the new IntervalNode will be the position found using the binary
	 * search algorithm. This is the same algorithm presented in BST readings
	 * and lecture examples.
	 * 
	 * This method must also check and possibly update the maxEnd in the
	 * IntervalNode. Recall, that maxEnd of a node represents the maximum end of
	 * current node and all descendant nodes.
	 * 
	 * If the start and end of the given interval match an existing interval,
	 * throw an IllegalArgumentException.
	 * 
	 * @param interval
	 *            the interval (item) to insert in the tree.
	 * @throws IllegalArgumentException
	 *             if interval is null or is found to be a duplicate of an
	 *             existing interval in this tree.
	 */
	public void insert(IntervalADT<T> interval) throws IllegalArgumentException {
		if (root == null)
			root = new IntervalNode<T>(interval);
		else
			root = insertHelper(root, interval);
	}

	/**
	 * Helper method for inserting a node into a tree
	 * 
	 * @param node
	 *            node the new interval node is being compared to so it can be
	 *            determined where to place the node in the tree
	 * @param interval
	 *            interval the interval of the new node being added
	 * @return the current node as the tree is being traversed
	 * @throws IllegalArgumentException
	 *             thrown if the tree already contains the interval or the
	 *             interval is null
	 */
	private IntervalNode<T> insertHelper(IntervalNode<T> node, IntervalADT<T> 
			interval)
			throws IllegalArgumentException {
		if (contains(interval) || interval == null)
			throw new IllegalArgumentException();
		if (node == null)
			return new IntervalNode<T>(interval);
		// inserts node as leaf in left subtree
		if (interval.compareTo(node.getInterval()) < 0)
			node.setLeftNode(insertHelper(node.getLeftNode(), interval));
		// inserts node as leaf in right subtree
		else
			node.setRightNode(insertHelper(node.getRightNode(), interval));
		recalculateMaxEnd(node);
		return node;
	}

	@Override
	/**
	 * Delete the node containing the specified interval in the tree. Delete
	 * operations must also update the maxEnd of interval nodes that change as a
	 * result of deletion.
	 * 
	 * @throws IllegalArgumentException
	 *             if interval is null
	 * @throws IntervalNotFoundException
	 *             if the interval does not exist.
	 */
	public void delete(IntervalADT<T> interval) throws IntervalNotFoundException, 
			IllegalArgumentException {
		root = deleteHelper(root, interval);
	}

	@Override
	/**
	 * Recursive helper method for the delete operation. Checks if the node
	 * matches the interval being deleted. If so then it replaces the node with
	 * the node's successor and deletes the successor node. If not then it
	 * traverses the left or right subtree to look for the interval.
	 *
	 * @param node
	 *            the interval node that is currently being checked.
	 * 
	 * @param interval
	 *            the interval to delete.
	 * 
	 * @throws IllegalArgumentException
	 *             if the interval is null.
	 * 
	 * @throws IntervalNotFoundException
	 *             if the interval is not null, but is not found in the tree.
	 * 
	 * @return Root of the tree after deleting the specified interval.
	 */
	public IntervalNode<T> deleteHelper(IntervalNode<T> node, IntervalADT<T> 
			interval)
			throws IntervalNotFoundException, IllegalArgumentException {
		if (interval == null)
			throw new IllegalArgumentException();
		if (!contains(interval)) throw new IntervalNotFoundException(interval.toString());
		if (node == null)
			return null;
		// deletes node if the interval matches the one being deleted
		if (node.getInterval().getStart() == interval.getStart()) {
			if (node.getInterval().getEnd() == interval.getEnd())
				if (node.getRightNode() != null) {
					node.setInterval(node.getSuccessor().getInterval());
					node.setRightNode(deleteHelper(node.getRightNode(), 
							node.getSuccessor().getInterval()));
					node.setMaxEnd(recalculateMaxEnd(node));
					return node;
				} else if (node.getLeftNode() != null)
					return node.getLeftNode();
				else
					return null;
		} else if (interval.compareTo(node.getInterval()) < 0) // traverses left
																// subtree
			node.setLeftNode(deleteHelper(node.getLeftNode(), interval));
		else if (interval.compareTo(node.getInterval()) > 0) // traverses right
																// subtree
			node.setRightNode(deleteHelper(node.getRightNode(), interval));
		else
			throw new IntervalNotFoundException(interval.toString());
		// recalculates max end of the node
		node.setMaxEnd(recalculateMaxEnd(node));
		return node;
	}

	@Override
	/**
	 * Find and return a list of all intervals that overlap with the given
	 * interval.
	 * 
	 * @param interval
	 *            the interval to search for overlapping
	 * 
	 * @return list of intervals that overlap with the input interval.
	 */
	public ArrayList<IntervalADT<T>> findOverlapping(IntervalADT<T> interval) {
		// creates new array list to hold intervals with overlapping schedules
		ArrayList<IntervalADT<T>> myList = new ArrayList<IntervalADT<T>>();
		return findOverlappingHelper(root, interval, myList);
	}

	/**
	 * Helper method for finding overlapping schedules
	 * 
	 * @param node
	 *            current node being compared
	 * @param interval
	 *            interval that is being compared to find overlapping schedules
	 * @param myList
	 *            list of nodes of overlapping schedules
	 * @return an array list of overlapping schedules
	 */
	private ArrayList<IntervalADT<T>> findOverlappingHelper(IntervalNode<T> node, 
				IntervalADT<T> interval,
			ArrayList<IntervalADT<T>> myList) {
		if (node == null)
			return myList;
		// adds to list if the intervals overlap
		if (node.getInterval().getStart().compareTo(interval.getEnd()) <= 0
				&& node.getInterval().getEnd().compareTo(interval.getStart()) >= 0)
			myList.add(node.getInterval());
		// otherwise traverses left subtree if the interval is less than the
		// current node
		if (node.getLeftNode() != null && 
				node.getLeftNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(node.getLeftNode(), interval, myList);
		// otherwise traverses right subtree if the interval is greater than the
		// current node
		if (node.getRightNode() != null && 
				node.getRightNode().getMaxEnd().compareTo(interval.getStart()) >= 0)
			findOverlappingHelper(node.getRightNode(), interval, myList);
		return myList;
	}

	@Override
	/**
	 * Search and return a list of all intervals containing a given point. This
	 * method may return an empty list.
	 * 
	 * @throws IllegalArgumentException
	 *             if point is null
	 * 
	 * @param point
	 *            input point to search for.
	 * @return List of intervals containing the point.
	 */
	public ArrayList<IntervalADT<T>> searchPoint(T point) {
		if (point == null)
			throw new IllegalArgumentException();
		// creates new array list to hold intervals containing the point
		ArrayList<IntervalADT<T>> result = new ArrayList<IntervalADT<T>>();
		searchPointHelper(root, point, result);
		return result;
	}

	/**
	 * This method is the helper method for seachPoint
	 * 
	 * @param node
	 *            to search for
	 * @param point
	 *            to find the interval which contains this point
	 * @param result
	 *            is a list contains all intervals that contains the points
	 */
	private void searchPointHelper(IntervalNode<T> node, T point, 
			ArrayList<IntervalADT<T>> result) {
		if (node == null)
			return;
		if (node.getInterval().contains(point))
			result.add(node.getInterval());
		// traverses left and right subtree
		searchPointHelper(node.getLeftNode(), point, result);
		searchPointHelper(node.getRightNode(), point, result);
	}

	@Override
	/**
	 * Get the size of the interval tree. The size is the total number of nodes
	 * present in the tree.
	 * 
	 * @return int number of nodes in the tree.
	 */
	public int getSize() {
		return getSizeHelper(root);
	}

	/**
	 * This is the helper method to get the size
	 * 
	 * @param node
	 *            is the node to search
	 * @return the size of the tree
	 */
	private int getSizeHelper(IntervalNode<T> node) {
		if (node == null)
			return 0;
		return 1 + getSizeHelper(node.getLeftNode()) + 
				getSizeHelper(node.getRightNode());
	}

	@Override
	/**
	 * Return the height of the interval tree at the root of the tree.
	 * 
	 * @return the height of the interval tree
	 */
	public int getHeight() {
		return getHeightHelper(root, 0);
	}

	/**
	 * Recursive helper method for finding the height
	 * 
	 * @param node
	 *            being compared
	 * @param currHeight
	 *            current height of tree
	 * @return height of tree
	 */
	private int getHeightHelper(IntervalNode<T> node, int currHeight) {
		if (node == null)
			return currHeight;
		if (getHeightHelper(node.getLeftNode(), currHeight) > 
				getHeightHelper(node.getRightNode(), currHeight))
			return currHeight = getHeightHelper(node.getLeftNode(), ++currHeight);
		else
			return currHeight = getHeightHelper(node.getRightNode(), ++currHeight);
	}

	@Override
	/**
	 * Returns true if the tree contains an exact match for the start and end of
	 * the given interval. The label is not considered for this operation.
	 * 
	 * @param interval
	 *            target interval for which to search the tree for.
	 * @return boolean representing if the tree contains the interval.
	 *
	 * @throws IllegalArgumentException
	 *             if interval is null.
	 * 
	 */
	public boolean contains(IntervalADT<T> interval) throws IllegalArgumentException {
		return containsHelper(root, interval);
	}

	/**
	 * Recursive helper method for the contains method
	 * 
	 * @param root
	 *            of tree
	 * @param interval
	 *            being compared to tree
	 * @return true if the tree contains the interval. False otherwise.
	 */
	private boolean containsHelper(IntervalNode<T> node, IntervalADT<T> interval) 
			throws IllegalArgumentException {
		if (interval == null)
			throw new IllegalArgumentException();
		if (node == null)
			return false;
		if (interval.getStart() == node.getInterval().getStart()) {
			if (interval.getEnd() == node.getInterval().getEnd())
				return true;
			if (interval.getEnd().compareTo(node.getInterval().getEnd()) < 0)
				return containsHelper(node.getLeftNode(), interval);
			if (interval.getEnd().compareTo(node.getInterval().getEnd()) > 0)
				return containsHelper(node.getRightNode(), interval);
		}
		if (interval.getStart().compareTo(node.getInterval().getStart()) < 0)
			return containsHelper(node.getLeftNode(), interval);
		return containsHelper(node.getRightNode(), interval);
	}

	@Override
	/**
	 * Print the statistics of the tree in the below format
	 * 
	 * ----------------------------------------- Height: 2 Size: 3
	 * -----------------------------------------
	 * 
	 */
	public void printStats() {
		System.out.println("-----------------------------------------");
		System.out.println("Height: " + getHeight());
		System.out.println("Size: " + getSize());
		System.out.println("-----------------------------------------");
	}

}

package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author neha Implementation of the list interface based on linked nodes that
 *         store multiple items per node. Rules for adding and removing elements
 *         ensure that each node (except possibly the last one) is at least half
 *         full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
	/**
	 * Default number of elements that may be stored in each node.
	 */
	private static final int DEFAULT_NODESIZE = 4;

	/**
	 * Number of elements that can be stored in each node.
	 */
	private final int nodeSize;

	/**
	 * Dummy node for head. It should be private but set to public here only for
	 * grading purpose. In practice, you should always make the head of a linked
	 * list a private instance variable.
	 */
	public Node head;

	/**
	 * Dummy node for tail.
	 */
	private Node tail;

	/**
	 * Number of elements in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with the default node size.
	 */
	public StoutList() {
		this(DEFAULT_NODESIZE);
	}

	/**
	 * Constructs an empty list with the given node size.
	 * 
	 * @param nodeSize number of elements that may be stored in each node, must be
	 *                 an even number
	 */
	public StoutList(int nodeSize) {
		if (nodeSize <= 0 || nodeSize % 2 != 0)
			throw new IllegalArgumentException();

		// dummy nodes
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		this.nodeSize = nodeSize;
	}

	/**
	 * Constructor for grading only. Fully implemented.
	 * 
	 * @param head
	 * @param tail
	 * @param nodeSize
	 * @param size
	 */
	public StoutList(Node head, Node tail, int nodeSize, int size) {
		this.head = head;
		this.tail = tail;
		this.nodeSize = nodeSize;
		this.size = size;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean add(E item) {
		if (item == null) {
			throw new NullPointerException();
		} else if (head.next == tail) {
			Node node = new Node();
			head.next = node;
			node.previous = head;
			node.next = tail;
			tail.previous = node;
			node.addItem(item);
		} else if (tail.previous.count < nodeSize) {
			tail.previous.addItem(item);
		} else {
			Node node = new Node();
			node.previous = tail.previous;
			node.next = tail;
			node.previous.next = node;
			tail.previous = node;
			node.addItem(item);
		}
		size++;
		return true;

	}

	@Override
	public void add(int pos, E item) {
		if (pos < 0 || pos > size) {
			throw new IndexOutOfBoundsException();
		} else if (head.next == tail || size == pos) {
			add(item);
		}

		NodeInfo info = find(pos);
		Node nodeA = info.node;
		int nodeB = info.offset;

		if (nodeB == 0) {

			if (nodeA.previous.count < nodeSize && nodeA.previous != head) {
				nodeA.previous.addItem(item);
				size++;
				return;
			} else if (nodeA == tail) {
				add(item);
				size++;
				return;
			}
		} else {
			if (nodeA.count < nodeSize) {
				nodeA.addItem(nodeB, item);
			} else {
				Node newNode = new Node();
				int tempNumb = 0;

				while (tempNumb < nodeSize / 2) {
					newNode.addItem(nodeA.data[nodeSize / 2]);
					nodeA.removeItem(nodeSize / 2);
					tempNumb++;
				}

				Node previous = nodeA.next;
				nodeA.next = newNode;
				newNode.previous = nodeA;
				newNode.next = previous;
				previous.previous = newNode;
				if (nodeB > nodeSize / 2) {
					newNode.addItem(nodeB - nodeSize / 2, item);
				}
				if (nodeB <= nodeSize / 2) {
					nodeA.addItem(nodeB, item);
				}
			}
			size++;
		}
	}

	@Override
	public E remove(int pos) {
		if (pos < 0 || pos > size) {
			throw new IndexOutOfBoundsException();
		}
		NodeInfo info_node = find(pos);
		Node temp = info_node.node;
		int num = info_node.offset;
		E val = temp.data[num];

		if ((temp.next == tail) && (temp.count == 1)) {

			Node previousNode = temp.previous;
			previousNode.next = temp.next;
			temp.next.previous = previousNode;
			temp = null;
		} else if ((temp.next == tail) || (temp.count > nodeSize / 2)) {
			temp.removeItem(num);

		} else {
			temp.removeItem(num);
			Node nodeC = temp.next;

			if (nodeC.count > nodeSize / 2) {
				temp.addItem(nodeC.data[0]);
				nodeC.removeItem(0);
			}

			else if (nodeC.count <= nodeSize / 2) {
				for (int i = 0; i < nodeC.count; i++) {
					temp.addItem(nodeC.data[i]);
				}
				temp.next = nodeC.next;
				nodeC.next.previous = temp;
				nodeC = null;
			}
		}
		size = size - 1;
		return val;
	}

	/**
	 * Sort all elements in the stout list in the NON-DECREASING order. You may do
	 * the following. Traverse the list and copy its elements into an array,
	 * deleting every visited node along the way. Then, sort the array by calling
	 * the insertionSort() method. (Note that sorting efficiency is not a concern
	 * for this project.) Finally, copy all elements from the array back to the
	 * stout list, creating new nodes for storage. After sorting, all nodes but
	 * (possibly) the last one must be full of elements.
	 * 
	 * Comparator<E> must have been implemented for calling insertionSort().
	 */
	public void sort() {
		E[] data = (E[]) new Comparable[size];
		int numb = 0;
		Node temp = head.next;

		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				data[numb] = temp.data[i];
				numb++;
			}
			temp = temp.next;
		}
		head.next = tail;
		tail.previous = head;
		insertionSort(data, new ElementComparator());

		size = 0;

		for (int i = 0; i < data.length; i++) {
			add(data[i]);
		}
	}

	/**
	 * Sort all elements in the stout list in the NON-INCREASING order. Call the
	 * bubbleSort() method. After sorting, all but (possibly) the last nodes must be
	 * filled with elements.
	 * 
	 * Comparable<? super E> must be implemented for calling bubbleSort().
	 */
	public void sortReverse() {
		E[] data = (E[]) new Comparable[size];
		int numb = 0;
		Node temp = head.next;

		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				data[numb] = temp.data[i];
				numb++;
			}
			temp = temp.next;
		}

		head.next = tail;
		tail.previous = head;
		bubbleSort(data);

		size = 0;

		for (int i = 0; i < data.length; i++) {
			add(data[i]);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new StoutListIterator(index);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes and the position of the iterator.
	 *
	 * @param iter an iterator for this list
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			E data = current.data[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < nodeSize; ++i) {
				sb.append(", ");
				data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size && count == size) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Node type for this list. Each node holds a maximum of nodeSize elements in an
	 * array. Empty slots are null.
	 */
	private class Node {
		/**
		 * Array of actual data elements.
		 */

		public E[] data = (E[]) new Comparable[nodeSize];

		/**
		 * Link to next node.
		 */
		public Node next;

		/**
		 * Link to previous node;
		 */
		public Node previous;

		/**
		 * Index of the next available offset in this node, also equal to the number of
		 * elements in this node.
		 */
		public int count;

		/**
		 * Adds an item to this node at the first available offset. Precondition: count
		 * < nodeSize
		 * 
		 * @param item element to be added
		 */
		void addItem(E item) {
			if (count >= nodeSize) {
				return;
			}
			data[count++] = item;
//			 useful for debugging
//			 System.out.println("Added " + item.toString() + " at index " + count + " to node " + Arrays.toString(data));
		}

		/**
		 * Adds an item to this node at the indicated offset, shifting elements to the
		 * right as necessary.
		 * 
		 * Precondition: count < nodeSize
		 * 
		 * @param offset array index at which to put the new element
		 * @param item   element to be added
		 */
		void addItem(int offset, E item) {
			if (count >= nodeSize) {
				return;
			}
			for (int i = count - 1; i >= offset; --i) {
				data[i + 1] = data[i];
			}
			++count;
			data[offset] = item;
//			 useful for debugging
//			 System.out.println("Added " + item.toString() + " at index " + offset + " to node: " + Arrays.toString(data));
		}

		/**
		 * Deletes an element from this node at the indicated offset, shifting elements
		 * left as necessary. Precondition: 0 <= offset < count
		 * 
		 * @param offset
		 */
		void removeItem(int offset) {
			E item = data[offset];
			for (int i = offset + 1; i < nodeSize; ++i) {
				data[i - 1] = data[i];
			}
			data[count - 1] = null;
			--count;
		}
	}

	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	private NodeInfo find(int pos) {

		int numb = 0;
		Node temp = head.next;

		if (pos > size) {
			return null;
		}

		if (pos < head.next.count && head.next.count != 0) {
			NodeInfo x = new NodeInfo(temp, pos);
			return x;
		}

		while (temp != tail) {
			if (numb + temp.count > pos) {
				NodeInfo nodeInfo = new NodeInfo(temp, pos - numb);
				return nodeInfo;
			} else {
				numb += temp.count;
				temp = temp.next;
			}
		}
		return null;
	}

	class ElementComparator<E extends Comparable<E>> implements Comparator<E> {
		@Override
		public int compare(E e1, E e2) {
			return e1.compareTo(e2);
		}
	}

	private class StoutListIterator implements ListIterator<E> {

		// instance variables ...

		int cursor;
		public E[] data = (E[]) new Comparable[nodeSize];
		final static int next = 1;
		final static int previous = 0;

		private int previous_step;

		/**
		 * Default constructor
		 */
		public StoutListIterator() {
			cursor = 0;
			previous_step = -1;
			dataList();
		}

		/*
		 * helper method to push the data into list
		 */
		private void dataList() {
			data = (E[]) new Comparable[size];

			int tempNumb = 0;
			Node tempNode = head.next;

			while (tempNode != tail) {
				for (int i = 0; i < tempNode.count; i++) {
					data[tempNumb] = tempNode.data[i];
					tempNumb++;
				}
				tempNode = tempNode.next;
			}
		}

		/**
		 * Constructor finds node at a given position.
		 * 
		 * @param pos
		 */
		public StoutListIterator(int pos) {
			previous_step = -1;
			cursor = pos;
			Node temp = head.next;
			NodeInfo x = find(pos);
			dataList();
		}

		@Override
		public boolean hasNext() {
			if (cursor < size) {
				return true;
			}
			return false;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			previous_step = next;
			return data[cursor++];
		}

		@Override
		public void remove() {
			if (previous_step == next) {
				StoutList.this.remove(cursor - 1);
				dataList();
				previous_step = -1;
				cursor--;
				if (cursor < 0) {
					cursor = 0;
				}
			} else if (previous_step == previous) {
				StoutList.this.remove(cursor);
				dataList();
				previous_step = -1;
			} else {
				throw new IllegalStateException();
			}
		}

		@Override
		public boolean hasPrevious() {

			if (cursor <= 0) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public E previous() {

			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			previous_step = next;
			cursor--;
			return data[cursor];
		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			return cursor - 1;
		}

		@Override
		public void set(E e) {

			if (e == null) {
				throw new IllegalStateException();
			}

			else if (previous_step == next) {
				NodeInfo nodeInfo = find(cursor - 1);
				nodeInfo.node.data[nodeInfo.offset] = e;
				data[cursor - 1] = e;
			}

			else if (previous_step == previous) {
				NodeInfo nodeInfo = find(cursor);
				nodeInfo.node.data[nodeInfo.offset] = e;
				data[cursor] = e;
			}

		}

		@Override
		public void add(E e) {

			if (e == null) {
				throw new NullPointerException();
			} else {
				StoutList.this.add(cursor, e);
				cursor++;
				dataList();
				previous_step = -1;

			}
		}

		// Other methods you may want to add or override that could possibly facilitate
		// other operations, for instance, addition, access to the previous element,
		// etc.
		//
		// ...
		//
	}

	/**
	 * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING
	 * order.
	 * 
	 * @param arr  array storing elements from the list
	 * @param comp comparator used in sorting
	 */
	private void insertionSort(E[] arr, Comparator<? super E> comp) {
		for (int i = 1; i < arr.length; i++) {
			E value = arr[i];
			int j = i - 1;
			while (j >= 0 && comp.compare(arr[j], value) > 0) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = value;
		}
	}

	/**
	 * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
	 * description of bubble sort please refer to Section 6.1 in the project
	 * description. You must use the compareTo() method from an implementation of
	 * the Comparable interface by the class E or ? super E.
	 * 
	 * @param arr array holding elements from the list
	 */
	private void bubbleSort(E[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j].compareTo(arr[j + 1]) < 0) {
					E temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
	}
}
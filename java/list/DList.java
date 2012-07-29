/* DList.java */

package list;


import java.util.Iterator;

/**
 *  A DList is a mutable doubly-linked list ADT.  Its implementation is
 *  circularly-linked and employs a sentinel node at the head of the list.
 *
 *  DO NOT CHANGE ANY METHOD PROTOTYPES IN THIS FILE.
 **/

public class DList<T> extends List<T> implements Iterable<T> {

  /**
   *  (inherited)  size is the number of items in the list.
   *  head references the sentinel node.
   *  Note that the sentinel node does not store an item, and is not included
   *  in the count stored by the "size" field.
   *
   *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATION.
   **/

  protected DListNode<T> head;

  /* DList invariants:
   *  1)  head != null.
   *  2)  For every DListNode x in a DList, x.next != null.
   *  3)  For every DListNode x in a DList, x.prev != null.
   *  4)  For every DListNode x in a DList, if x.next == y, then y.prev == x.
   *  5)  For every DListNode x in a DList, if x.prev == y, then y.next == x.
   *  6)  For every DList l, l.head.myList = null.  (Note that l.head is the
   *      sentinel.)
   *  7)  For every DListNode x in a DList l EXCEPT l.head (the sentinel),
   *      x.myList = l.
   *  8)  size is the number of DListNodes, NOT COUNTING the sentinel,
   *      that can be accessed from the sentinel (head) by a sequence of
   *      "next" references.
   **/

  /**
   *  newNode() calls the DListNode constructor.  Use this method to allocate
   *  new DListNodes rather than calling the DListNode constructor directly.
   *  That way, only this method need be overridden if a subclass of DList
   *  wants to use a different kind of node.
   *
   *  @param item the item to store in the node.
   *  @param list the list that owns this node.  (null for sentinels.)
   *  @param prev the node previous to this node.
   *  @param next the node following this node.
   **/
  protected DListNode<T> newNode(T item, DList<T> list,
                              DListNode<T> prev, DListNode<T> next) {
    return new DListNode<T>(item, list, prev, next);
  }

  /**
   *  DList() constructs for an empty DList.
   **/
  public DList() {
	  head = newNode(null, null, null, null);
	  head.next = head;
	  head.prev = head;
  }

  /**
   *  insertFront() inserts an item at the front of this DList.
   *
   *  @param item is the item to be inserted.
   *
   *  Performance:  runs in O(1) time.
   **/
  public void insertFront(T item) {
	  head.next = newNode(item, this, head, head.next);
	  head.next.next.prev = head.next;
	  size++;
  }

  /**
   *  insertBack() inserts an item at the back of this DList.
   *
   *  @param item is the item to be inserted.
   *
   *  Performance:  runs in O(1) time.
   **/
  public void insertBack(T item) {
	    head.prev = newNode(item, this, head.prev, head);
	    head.prev.prev.next = head.prev;
	    size++;
  }
  
  /**
  *Removes the listNode that contains item from the list
  *
  */
  public void remove(T item) {
	  ListNode<T> node = front();
	  try{
	  do{
		  if (node.item().equals(item)) {
			  node.remove();
			  return;
		  }
		  node = node.next();
		  
	  }while(node.item() != item);
	  }catch (InvalidNodeException e) {
		  e.printStackTrace();
	  }
  }

  /**
   *  front() returns the node at the front of this DList.  If the DList is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.  (The sentinel is "invalid".)
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a ListNode at the front of this DList.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode<T> front() {
    return head.next;
  }

  /**
   *  back() returns the node at the back of this DList.  If the DList is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.  (The sentinel is "invalid".)
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a ListNode at the back of this DList.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode<T> back() {
    return head.prev;
  }
  
  /**
  * Finds the item at the nth node of the list
  *
  * @return the object at the nth node
  */
  public T nth(int position) 
	{
	  try {
		    ListNode<T> currentNode;
		    
		    if ((position < 0) || size == 0  || position + 1 > size)
		    {
		    	return null;
		    }
		    else {
		    	if (position <= (size-1)/2)
		    	{
		    		currentNode = front();
		    		while (position > 0) 
		    		{
		    	        if (currentNode == null) {
		    	          return null;
		    	        }
		    	        position--;
		    	        currentNode = currentNode.next();
		    	    }
		    	}
		    	else
		    	{
		    		currentNode = back();
		    		while (position < size-1) 
		    		{
		    	        if (currentNode == null) {
		    	          return null;
		    	        }
		    	        position++;
		    	        currentNode = currentNode.prev();
		    	    }
		    	}
		    	return currentNode.item();
		    }
	  } catch (InvalidNodeException e) {
		  e.printStackTrace();
	  }
	  return null;

	 }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  DO NOT CHANGE THIS METHOD.
   *
   *  @return a String representation of this DList.
   *
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    DListNode<T> current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }
  
  /**
  * Checks the list to see if it contains item
  *
  * @return true if item is in the List, false otherwise.
  */
  public boolean contains(T item) {
	  for (T nodeItem: this){
		  if (nodeItem == item) {
			  return true;
		  }
	  }
	  return false;
  }
  
  /**
  * Makes a copy of the list
  *
  * @return duplicate List that has the same item references as this list.
  */
  public List<T> clone() {
	  List<T> newList = new DList<T>();
	  ListNode<T> node = front();
	  while(node.isValidNode())
	  {
		  newList.insertBack(node.item);
		  try {
			node = node.next();
		} catch (InvalidNodeException e) {
			e.printStackTrace();
		}
	  }
	  return newList;
  }

  private static void testInvalidNode(ListNode<Object> p) {
    System.out.println("p.isValidNode() should be false: " + p.isValidNode());
    try {
      p.item();
      System.out.println("p.item() should throw an exception, but didn't.");
    } catch (InvalidNodeException lbe) {
      System.out.println("p.item() should throw an exception, and did.");
    }
    try {
      p.setItem(new Integer(0));
      System.out.println("p.setItem() should throw an exception, but didn't.");
    } catch (InvalidNodeException lbe) {
      System.out.println("p.setItem() should throw an exception, and did.");
    }
    try {
      p.next();
      System.out.println("p.next() should throw an exception, but didn't.");
    } catch (InvalidNodeException lbe) {
      System.out.println("p.next() should throw an exception, and did.");
    }
    try {
      p.prev();
      System.out.println("p.prev() should throw an exception, but didn't.");
    } catch (InvalidNodeException lbe) {
      System.out.println("p.prev() should throw an exception, and did.");
    }
    try {
      p.insertBefore(new Integer(1));
      System.out.println("p.insertBefore() should throw an exception, but " +
                         "didn't.");
    } catch (InvalidNodeException lbe) {
      System.out.println("p.insertBefore() should throw an exception, and did."
                         );
    }
    try {
      p.insertAfter(new Integer(1));
      System.out.println("p.insertAfter() should throw an exception, but " +
                         "didn't.");
    } catch (InvalidNodeException lbe) {
      System.out.println("p.insertAfter() should throw an exception, and did."
                         );
    }
    try {
      p.remove();
      System.out.println("p.remove() should throw an exception, but didn't.");
    } catch (InvalidNodeException lbe) {
      System.out.println("p.remove() should throw an exception, and did.");
    }
  }

private static void testEmpty() {
    List<Object> l = new DList<Object>();
    System.out.println("An empty list should be [  ]: " + l);
    System.out.println("l.isEmpty() should be true: " + l.isEmpty());
    System.out.println("l.length() should be 0: " + l.length());
    System.out.println("Finding front node p of l.");
    ListNode<Object> p = l.front();
    testInvalidNode(p);
    System.out.println("Finding back node p of l.");
    p = l.back();
    testInvalidNode(p);
    l.insertFront(new Integer(10));
    System.out.println("l after insertFront(10) should be [  10  ]: " + l);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
public static void main(String[] argv) {
    testEmpty();
    List l = new DList();
    l.insertFront(new Integer(3));
    l.insertFront(new Integer(2));
    l.insertFront(new Integer(1));
    System.out.println("l is a list of 3 elements: " + l);
    try {
      ListNode n;
      int i = 1;
      for (n = l.front(); n.isValidNode(); n = n.next()) {
	System.out.println("n.item() should be " + i + ": " + n.item());
        n.setItem(new Integer(((Integer) n.item()).intValue() * 2));
	System.out.println("n.item() should be " + 2 * i + ": " + n.item());
	i++;
      }
      System.out.println("After doubling all elements of l: " + l);
      testInvalidNode(n);

      i = 6;
      for (n = l.back(); n.isValidNode(); n = n.prev()) {
	System.out.println("n.item() should be " + i + ": " + n.item());
	n.setItem(new Integer(((Integer) n.item()).intValue() * 2));
	System.out.println("n.item() should be " + 2 * i + ": " + n.item());
	i = i - 2;
      }
      System.out.println("After doubling all elements of l again: " + l);
      testInvalidNode(n);

      n = l.front().next();
      System.out.println("Removing middle element (8) of l: " + n.item());
      n.remove();
      System.out.println("l is now: " + l);
      testInvalidNode(n);    
      n = l.back();
      System.out.println("Removing end element (12) of l: " + n.item());
      n.remove();
      System.out.println("l is now: " + l);
      testInvalidNode(n);    

      n = l.front();
      System.out.println("Removing first element (4) of l: " + n.item());
      n.remove();
      System.out.println("l is now: " + l);
      testInvalidNode(n);    
    } catch (InvalidNodeException lbe) {
      System.err.println ("Caught InvalidNodeException that should not happen."
                          );
      System.err.println ("Aborting the testing code.");
    }
  }


  /**
  * Returns an iterator over the DList, which allows for DList to be traversed through for loops.
  */
	@Override
	public Iterator<T> iterator() {
		return new DListIterator<T>(front());
	}
	
  /**
  * Checks for reference equality of items in each node. Shallow structural equality.
  *
  * @return returns true if their item references are equal, false otherwise.
  */
	@SuppressWarnings("unchecked")
	public boolean equals(Object item) {
		try{
			List<T> otherList = (DList<T>) item;
			if (otherList.length() != this.length()) {
				return false;
			}
			ListNode<T> node1 = this.front();
			ListNode<T> node2 = otherList.front();
			
			while(node1.isValidNode() || node2.isValidNode()) {
				if((node1.isValidNode() && !node2.isValidNode()) || !(node1.isValidNode() && node2.isValidNode())) {
					return false;
				}
				if(!node1.item.equals(node2.item)) {
					return false;
				}
				node1 = node1.next();
				node1 = node2.next();
			}
			return true;
		}catch (InvalidNodeException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ImmutableList<T> createImmutable() {
		return new ImmutableList<T>(this);
	}

}

class DListIterator<T> implements Iterator<T> {

	private ListNode<T> node;
	
	public DListIterator(ListNode<T> first) {
		node = first;
	}
	
	@Override
	public boolean hasNext() {
		return node.isValidNode();
	}

	@Override
	public T next() {
		ListNode<T> output = node;
		try {
			node = node.next();
		} catch (InvalidNodeException e) {
			e.printStackTrace();
		}
		return (T) output.item;
	}

	@Override
	public void remove() {
		
	}
	
}

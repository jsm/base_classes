/* List.java */

package list;

import java.util.Iterator;

/**
 *  A List is a mutable list ADT.  No implementation is provided.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public abstract class List<T> implements Iterable<T> {
	
  /**
   *  size is the number of items in the list.
   **/

  protected int size;
  

  /**
   *  isEmpty() returns true if this List is empty, false otherwise.
   *
   *  @return true if this List is empty, false otherwise. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public boolean isEmpty() {
    return size == 0;
  }

  /** 
   *  length() returns the length of this List. 
   *
   *  @return the length of this List.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int length() {
    return size;
  }

  /**
   *  insertFront() inserts an item at the front of this List.
   *
   *  @param item is the item to be inserted.
   **/
  public abstract void insertFront(T item);

  /**
   *  insertBack() inserts an item at the back of this List.
   *
   *  @param item is the item to be inserted.
   **/
  public abstract void insertBack(T item);
  
  public abstract void remove(T item);

  /**
   *  front() returns the node at the front of this List.  If the List is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.
   *
   *  @return a ListNode at the front of this List.
   */
  public abstract ListNode<T> front();
  

  /**
   *  back() returns the node at the back of this List.  If the List is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.
   *
   *  @return a ListNode at the back of this List.
   */
  public abstract ListNode<T> back();

  /**
   *  toString() returns a String representation of this List.
   *
   *  @return a String representation of this List.
   */
  public abstract String toString();
  
  /**
   *  contains() returns a boolean of whether or not list contains item
   *
   *  @return a boolean of whether or not list contains item.
   */
  public abstract boolean contains(T item);


  /**
  * Makes a copy of the list
  *
  * @return duplicate List that has the same item references as this list.
  */
  public abstract List<T> clone();
  
  public abstract ImmutableList<T> createImmutable();


  /**
  * Returns an iterator over the List, which allows for List to be traversed through for loops.
  */
  public abstract Iterator<T> iterator();
  
  /**
  * Finds the item at the nth node of the list
  *
  * @return the object at the nth node
  */
  public abstract T nth(int position);
  
  
  /**
  * Checks for reference equality of items in each node. Shallow structural equality.
  *
  * @return returns true if their item references are equal, false otherwise.
  */
  public abstract boolean equals(Object item);
}

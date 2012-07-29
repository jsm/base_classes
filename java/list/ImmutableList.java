/* List.java */

package list;

import java.util.Iterator;

/**
 *  An ImmutableList is a immutable list ADT.  No implementation is provided.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public  class ImmutableList<T> implements Iterable<T> {

  /**
   *  size is the number of items in the list.
   **/

  private List<T> myList;
  
  
  public ImmutableList() {
	  this(new DList<T>());
  }
  
  public ImmutableList(List<T> list) {
	  myList = list;
  }

  /**
   *  isEmpty() returns true if this List is empty, false otherwise.
   *
   *  @return true if this List is empty, false otherwise. 
   *
   *  Performance:  runs in O(1) time.
   **/
  public boolean isEmpty() {
    return myList.isEmpty();
  }

  /** 
   *  length() returns the length of this List. 
   *
   *  @return the length of this List.
   *
   *  Performance:  runs in O(1) time.
   **/
  public int length() {
    return myList.size;
  }

  /**
   *  toString() returns a String representation of this List.
   *
   *  @return a String representation of this List.
   */
  public String toString() {
	  return myList.toString();
  }
  
  /**
   *  contains() returns a boolean of whether or not list contains item
   *
   *  @return a boolean of whether or not list contains item.
   */
  public boolean contains(T item) {
	  return myList.contains(item);
  }


  /**
  * Makes a copy of the list
  *
  * @return duplicate List that has the same item references as this list.
  */
  public ImmutableList<T> clone() {
	  return new ImmutableList<T>(myList.clone());
  }


  /**
  * Returns an iterator over the List, which allows for List to be traversed through for loops.
  */
  public Iterator<T> iterator() {
	  return myList.iterator();
  }
  
  /**
  * Finds the item at the nth node of the list
  *
  * @return the object at the nth node
  */
  public T nth(int position) {
	  return myList.nth(position);
  }
  
  
  /**
  * Checks for reference equality of items in each node. Shallow structural equality.
  *
  * @return returns true if their item references are equal, false otherwise.
  */
  public boolean equals(Object item) {
	  return myList.equals(item);
  }
}

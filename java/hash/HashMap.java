/* HashTableChained.java */

package hash;
import list.*;

/**
 *  HashMap is a hash map with DList chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashMap class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 **/

public class HashMap<K,V> {

  private List<Entry<K,V>>[] buckets;
  private int size;
  private List<K> keys;
  private double loadFactor;
  private final static double defaultLoadFactor = .75;
  private final int defaultSize = 89;



  /** 
   *  Construct a new empty hash map intended to hold roughly sizeEstimate
   *  entries.
   **/
  public HashMap(int sizeEstimate) {
	  this(sizeEstimate, defaultLoadFactor);
  }
  
  /** 
   *  Construct a new empty hash map intended to hold roughly sizeEstimate
   *  entries with a load factor of loadFactor.
   **/
  @SuppressWarnings("unchecked")
public HashMap(int sizeEstimate, double loadFactor) {
	  buckets = new List[(int)(sizeEstimate/loadFactor)];
	  size = 0;
	  keys = new DList<K>();
	  this.loadFactor = loadFactor;
  }

  /** 
   *  Construct a new empty hash map with a default size and load factor.
   **/

  public HashMap() {
	  this(defaultLoadFactor);
  }
  
  /** 
   *  Construct a new empty hash map with a default size and load factor of loadFactor.
   **/
  @SuppressWarnings("unchecked")
public HashMap(double loadFactor) {
	  buckets = new List[defaultSize];
	  size = 0;
	  keys = new DList<K>();
	  this.loadFactor = loadFactor;
  }

  /**
   *  Calls compFunction with the HashMap's current buckets.
   **/

  int compFunction(int code) {
	  return compFunction(code, buckets);
  }
  
  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of buckets) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/
  int compFunction(int code, List<Entry<K,V>>[] buckets) {
	  int n = ((buckets.length + 11) * code + 7) % (buckets.length * 13 + 17) % buckets.length;
	  if(n >= 0) {
		  return n;
	  }
	  return -n;
  }
  
  /** 
   *  Returns the closest prime to n.
   *  @param int to find closest value to.
   *  @return an int that is a close prime to n
   **/
  @SuppressWarnings("unused")
  private int closestPrime(int n) {
	  int output = n;
	  while (output > 1) {
		  if (isPrime(output)) {
			  return output;
		  }
		  output--;
	  }
	  output = n;
	  while (true) {
		  if (isPrime(output)) {
			  return output;
		  }
		  output++;
	  }
  }
  
  /** 
   *  Determines whether or not a number is prime.
   *  @return true if number is prime, false otherwise.
   **/
  private boolean isPrime(int n) {
	  if (n == 1 || n == 2) {
		  return true;
	  }
	  for(int i = 2; i < n/2; i++) {
		  if (n%i == 0) {
			  return false;
		  }
	  }
	  return true;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
	  return size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    if (size < 1) {
    	return true;
    }
    return false;
  }

  /**
   *  If key is already in map, updates the value associated with the key to
   *  input parameter value. Otherwise, adds an entry to map with key and value.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return the previous value.
   **/

  public V put(K key, V value) {
	  V oldValue = null;
	  if ((size+1.0)/buckets.length > loadFactor) {
		  rehash();
	  }
	  int bucketNumber = compFunction(key.hashCode());
	  if(buckets[bucketNumber] == null) {
		  buckets[bucketNumber] = new DList<Entry<K,V>>();
	  } else {
		  for (Entry<K,V> entries: buckets[bucketNumber]) {
			  if (entries.key().equals(key)) {
				  oldValue = entries.value;
				  entries.value = value;
				  return oldValue;
			  }	
		  }
	  }
	  keys.insertBack(key);
	  Entry<K,V> entry = new Entry<K,V>(keys.back(), value);
	  buckets[bucketNumber].insertBack(entry);
	  size++;
	  return oldValue;
  }
  

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public V get(K key) {
	  return get(key, buckets);
  }
  
  /** 
   *  Search for an entry with the specified key in the specified buckets.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an value associated with key, or null if
   *          no entry contains the specified key.
   **/
  V get(K key, List<Entry<K,V>>[] buckets) {
	  int bucketNumber = compFunction(key.hashCode(), buckets);
	  if (buckets[bucketNumber] != null) {
		  for (Entry<K,V> entry: buckets[bucketNumber]) {
			  if (entry.key().equals(key)) {
				  return entry.value;
			  }	
		  }
	  }
	  return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return value associated with the specified key, or null if
   *          no entry contains the specified key.
   */

  public V remove(K key) {
	  int bucketNumber = compFunction(key.hashCode());
	  if (buckets[bucketNumber] != null) {
		  ListNode<Entry<K,V>> node = buckets[bucketNumber].front();
		  try {
			  while(node.isValidNode()){
				  if ((node.item()).key().equals(key)) {
					  Entry<K,V> output = node.item();
					  output.key.remove();
					  node.remove();
					  size--;
					  return output.value;
				  }
				  node = node.next();				  
			  }
		} catch (InvalidNodeException e) {
			e.printStackTrace();
		}
	  }
	  return null;
  }
  
  /** 
   *  Create a list of keys that cannot be modified.
   *
   *  @return ImmutableList containing this map's keys.
   */
  public ImmutableList<K> keys() {
	  return keys.createImmutable();
  }

  /**
   *  Remove all entries from the dictionary.
   */
  @SuppressWarnings("unchecked")
public void clear() {
	  buckets = new DList[this.defaultSize];
	  keys = new DList<K>();
	  size = 0;
  }
  
  /**
   *  Doubles bucket size, then rehashes all previous entries into the new buckets
   */
  @SuppressWarnings("unchecked")
private void rehash() {
	  List<Entry<K,V>>[] oldBuckets = buckets;
	  List<K> oldKeys = keys;
	  buckets = new DList[buckets.length*2+1];
	  keys = new DList<K>();
	  size = 0;
	  for (K key: oldKeys) {
		  put(key, get(key, oldBuckets));
	  }
  }
  
  
  /** 
   *  Counts total number of entries that are added to a bucket that already has
   *  at least one other entry in it.
   *
   *  @return an integer counting total number of collisions
   */
  public int countCollisions() {
	  int output = 0;
	  for (List<Entry<K,V>> bucket: buckets) {
		  if (bucket != null && bucket.length() > 1) {
			  output += bucket.length() - 1 ;
		  }
	  }
	  return output;
  }

}




/**
 *  A class for dictionary entries.
 *
 **/

class Entry<K,V> {

  protected ListNode<K> key;
  protected V value;
  
  Entry(ListNode<K> key, V value) {
	  this.key = key;
	  this.value = value;
  }

	/**
   * key() returns the entry's key
   *
   * Running time:  O(1).
   */
  K key() {
    try {
		return key.item();
	} catch (InvalidNodeException e) {
		e.printStackTrace();
	}
    return null;
  }

	/**
   * value() returns the entry's value
   *
   * Running time:  O(1).
   */
  V value() {
    return value;
  }

}
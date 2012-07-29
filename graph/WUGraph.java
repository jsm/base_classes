/* WUGraph.java */

package graph;

import hash.HashMap;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {

	private HashMap<Object,Vertex> vertices;
	private int edges;
	private int counter;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
	  vertices = new HashMap<Object,Vertex>();
	  edges = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
	  return vertices.size();
  }

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
	  return edges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
	  Object[] output = new Object[vertices.size()];
	  
	  counter = 0;
	  for (Object key: vertices.keys()) {
		  output[counter] = vertices.get(key).vertex;
		  counter++;
	  }
	  
	  return output;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
	  vertices.put(vertex, new Vertex(vertex));
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
	  Vertex theVertex = vertices.get(vertex);
	  if (theVertex != null)
	  {
		  for (Object key: theVertex.neighbors.keys()) {
			  Object neighboringVertex = theVertex.neighbors.get(key).vertex.vertex;
			  removeEdge(vertex, neighboringVertex);
		  }
		  vertices.remove(vertex);
	  }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
	  return (vertices.get(vertex) != null);
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
	  Vertex theVertex = vertices.get(vertex);
	  
	  if (theVertex != null) {
		  return theVertex.neighbors.size();
	  }
	  return 0;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
	  Vertex theVertex = vertices.get(vertex);
	  
	  if (theVertex != null && theVertex.neighbors.size() > 0) {
		  Neighbors neighbors = new Neighbors();
		  neighbors.neighborList = new Object[theVertex.neighbors.size()];
		  neighbors.weightList = new int[theVertex.neighbors.size()];
		  
		  counter = 0;
		  for (Object key: theVertex.neighbors.keys()) {
			  Neighbor neighbor = theVertex.neighbors.get(key);
			  neighbors.neighborList[counter] = neighbor.vertex.vertex;
			  neighbors.weightList[counter] = neighbor.weight;
			  counter++;
		  }
		  
		  return neighbors;
	  }
	  
	  return null;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
	  Vertex v1 = vertices.get(u);
	  Vertex v2 = vertices.get(v);
	  
	  if (v1 != null && v2 != null) {
		  if (!isEdge(u, v)) {
			  edges++;
		  }
		  v1.addNeighbor(v2, weight);
		  if (!u.equals(v)) {
			  v2.addNeighbor(v1, weight);
		  } 
	  }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
	  Vertex v1 = vertices.get(u);
	  Vertex v2 = vertices.get(v);
	  
	  if (v1 != null && v2 != null && isEdge(u, v)) {
		  v1.removeNeighbor(v2);
		  v2.removeNeighbor(v1);
		  edges--;
	  }
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
	  return vertices.get(u) != null && vertices.get(u).neighbors.get(v) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
	  Vertex vertex = vertices.get(u);
	  if (vertex != null) {
		  Neighbor neighbor = vertex.neighbors.get(v);
		  if (neighbor != null) {
			  return neighbor.weight;
		  }
	  }
	  
	  return 0;
  }
  

}

/**
 * The Vertex class represents a vertex of a weighted, undirected graph. Stores the 
 * vertex's object, as well as it's neighbors.
 */

class Vertex {
	
	static int count = 1;
	
	Object vertex;
	HashMap<Object,Neighbor> neighbors;
	
	  /**
	   * Vertex(Object vertex) constructs a vertex with no neighbors
	   *
	   * Running time:  O(1).
	   */
	Vertex(Object vertex) {
		this.vertex = vertex;
		neighbors = new HashMap<Object,Neighbor>();
	}
	
  /**
   * addNeighbor() adds a neighbor to this Vertex.
   *
   * Running time:  O(1).
   */
	void addNeighbor(Vertex vertex, int weight) {
		neighbors.put(vertex.vertex, new Neighbor(vertex, weight));
	}
	
	/**
   * removeNeighbor() removes input vertex as a neighbor of this vertex
   *
   * Running time:  O(1).
   */
	void removeNeighbor(Vertex vertex) {
		neighbors.remove(vertex.vertex);
	}
	
}

/**
 * The Neighbor class represents a neighbor of a vertex of a weighted, undirected graph.
 */
class Neighbor {
	Vertex vertex;
	int weight;
	
	  /**
	   * Vertex(Vertex vertex, int weight) constructs a Neighbor with Vertex vertex and 
	   * weight of weight
	   *
	   * Running time:  O(1).
	   */
	Neighbor(Vertex vertex, int weight) {
		this.vertex = vertex;
		this.weight = weight;
	}
}

/*
 * Uses the GraphMaker to make the graph, asks the user for the source vertex 
 * and destination vertex, and then runs Dijkstra's algorithm. The shortest 
 * path length as well as the actual path should be printed to the screen, 
 * then the program should terminate. See the examples in assignment for the 
 * appropriate formatting.
 * 
 * @author sspurlock
 * @version 2019-10-21
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Dijkstras {
	HashMap<DijkstraVertex, DijkstraVertex> parent = new HashMap<DijkstraVertex, DijkstraVertex>();
	ArrayList<DijkstraVertex> dijVert = new ArrayList<DijkstraVertex>();
	
	// Constructor: prompt user to enter file name, then
	// call runShortestPath with the file name.
	public Dijkstras() {
		System.out.println("Please enter a file name: ");
		Scanner scnr = new Scanner(System.in);
		String name = scnr.nextLine();
		runShortestPath(name);
	}

	// Make a graph and run Dijkstra's algorithm.
	public void runShortestPath(String fileName){
		// Create a new GraphMaker object and use it to make a new AdjListGraph.
		// TODO
		GraphMaker graphMaker = new GraphMaker();
		AdjListGraph graph = graphMaker.makeGraphFromFile(fileName);
		
		
		// Print the graph out and prompt the user to enter the starting 
		// and ending vertices.
		// TODO
		graph.print();
		Scanner scnr = new Scanner(System.in);
		System.out.println("Please type starting vertex: ");
		String start = scnr.nextLine();
		DijkstraVertex source = new DijkstraVertex(start);
		source.setDistance(0);
		System.out.println("Please type ending vertex: ");
		String end = scnr.nextLine();
		DijkstraVertex destination = new DijkstraVertex(end);
		
		// Call the shortestPath method with the graph and the source Vertex.
		// TODO
		shortestPath(graph, source);
		// Get the distance to the destination Vertex and print it out.
		// TODO
		Iterator iterator = dijVert.iterator();
		while(iterator.hasNext()) {
			DijkstraVertex next = (DijkstraVertex) iterator.next();
			if (end.equals(next.getName())) {
				destination = next;
				break;
			}
		}
		
		System.out.println("Distance from " + start + " to " + end + ": " + destination.getDistance());
		
		
		// Find and print the path by following back from the destination Vertex to each
		// parent. Note that, in the shortestPath method, you'll have stored 
		// the parent for each Vertex in the HashMap declared at the top of this file).
		// TODO
		
		
		ArrayList<DijkstraVertex> parents = new ArrayList<DijkstraVertex>();
		parents.add(destination);
		DijkstraVertex currDest = destination;
		while(!source.getName().equals(currDest.getName())) {
			//System.out.println(currDest.getName()); 
			
			parents.add(0, parent.get(currDest));
			currDest = parent.get(currDest);
			//System.out.println(currDest); 
		
		} 
		
		//System.out.print(source.getName());
		for( int i = 0; i < parents.size(); i++) {
			System.out.print("::" + parents.get(i).getName());
		}
	}

	// Given the graph and source vertex, run Dijkstra's algorithm.
	public void shortestPath(AdjListGraph graph, DijkstraVertex source){
		// Initialize the distance to all the vertices in the graph to infinity,
		// except the source vertex, which should be 0.
		// TODO
		ArrayList<Vertex> verticies = graph.getVertices();
		dijVert = new ArrayList<DijkstraVertex>();
		//dijVert.add(source);
		
		for(int i = 0; i < verticies.size(); i++) {
			DijkstraVertex a = (DijkstraVertex) verticies.get(i);
			if (a.getName().equals(source.getName())) {
				
				a.setDistance(0);
				dijVert.add(a);
				
			}
			else {
				a.setDistance(Double.POSITIVE_INFINITY);
				dijVert.add(a);
			}
		}
		
		
		 
		// Make a PriorityQueue (imported above in Java.Util.PriorityQueue)
		// of DijkstraVertex objects and add all the vertices.
		// TODO
		PriorityQueue<DijkstraVertex> queue = new PriorityQueue<DijkstraVertex>();
		for(int i = 0; i < dijVert.size(); i++) {
			queue.add(dijVert.get(i));
		}
		//System.out.println(queue.peek().getName());
		// Keep looping as long as the priorty queue is not empty, doing the following:
		// - get the next closest Vertex from the priority queue
		// - get its adjacent vertices
		// - for each adjacent vertex, check if the distance to get there from the 
		//   current vertex would be shorter than its current distance. If so, remove
		//   it from the priority queue, update its distance, and re-add it. Keep track
		//   of which vertex led to this vertex using the parent HashMap declared
		//   at the top of the file.
		// TODO
		double distFromSource = 0;
		while(!queue.isEmpty()) {
			DijkstraVertex currVert = queue.remove();
			distFromSource = currVert.getDistance();
			//System.out.println(currVert.getDistance());
			Collection<Vertex> adjVert = currVert.getAdjacentVertices();
			//System.out.print(currVert.getName() + ": ");
			Iterator<Vertex> iterator = adjVert.iterator();
			while(iterator.hasNext()) {
				DijkstraVertex currAdjVertex = (DijkstraVertex) iterator.next();
				//System.out.print(currAdjVertex.getName() + " ");
				//System.out.println("");
				Double edgeWeight = graph.getWeight(currVert, currAdjVertex );
				if(queue.contains(currAdjVertex)) {
					int index = dijVert.indexOf(currAdjVertex);
					if (dijVert.get(index).getDistance() > (edgeWeight + distFromSource)) {
						queue.remove(dijVert.get(index));
						dijVert.get(index).setDistance(edgeWeight + distFromSource);
						queue.add(dijVert.get(index));
						parent.put(dijVert.get(index), currVert);
						//System.out.println(dijVert.get(index).getName() + " " + (edgeWeight + distFromSource));
					}
				} 
			}
			/*for(int i = 0; i < adjVert.length; i++) {
				//System.out.println(((DijkstraVertex) adjVert[i]).getName());
				Double edgeWeight = graph.getWeight(currVert, (Vertex) adjVert[i] );
				if(queue.contains(adjVert[i])) {
					int index = dijVert.indexOf(adjVert[i]);
					if (dijVert.get(index).getDistance() > edgeWeight) {
						queue.remove(dijVert.get(index));
						dijVert.get(index).setDistance(edgeWeight + distFromSource);
						queue.add(dijVert.get(index));
						parent.put(dijVert.get(index), currVert);
						System.out.println(dijVert.get(index).getName() + " " + (edgeWeight + distFromSource));
					}
						
					
				}
			} */
			//System.out.println(queue.toString());
			//distFromSource = queue.peek().getDistance() + distFromSource;
		}
		
	}
}

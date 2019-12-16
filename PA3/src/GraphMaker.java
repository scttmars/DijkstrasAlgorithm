import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/*
 * The GraphMaker handles making a graph by reading from a file.
 * The makeGraphFromFile method takes in the name of a file. 
 * The code should then read in the input file and produce the corresponding graph.
 * The first line of the file will be the number of vertices. 
 * The next lines will give the edges as a table where table(i,j) gives the 
 * edge weight between vertices i and j. 
 * The two provided files are in this format.
 * 
 * @author sspurlock
 * @version 2019-10-21
 */
public class GraphMaker {
	public AdjListGraph makeGraphFromFile(String fileName) {
		// Create a new directed AdjListGraph and read from the file to
		// add DijkstraVertex and Edge object to the graph.
		// TODO...
		File file = new File(fileName);
		
		AdjListGraph graph = new AdjListGraph(true);
		try {
			Scanner scnr = new Scanner(file);
			
			int numVertex = scnr.nextInt();
			int count = 0;
			ArrayList<Vertex> vertecies = new ArrayList<Vertex>();
			while(count < numVertex) {
				String vertexName = scnr.next();
				Vertex a = graph.addVertex(new DijkstraVertex(vertexName));
				vertecies.add(a);
				count++;
			}
			count = 0;
			while(scnr.hasNextLine()) {
					scnr.next();
					for (int i = 0; i < numVertex; i++) {
						double distance = scnr.nextDouble();
						if (distance > 0) {
							graph.addEdge(vertecies.get(count), vertecies.get(i), distance);
						}
					}
					count++;
				}
				
			}
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Example of building a graph. Delete before turning in.
		/*AdjListGraph graph = new AdjListGraph(true);
		
		Vertex a = graph.addVertex(new DijkstraVertex("A"));
		Vertex b = graph.addVertex(new DijkstraVertex("B"));
		Vertex c = graph.addVertex(new DijkstraVertex("C"));

		graph.addEdge(a, b);
		graph.addEdge(a, c); */

		//graph.print();

		
		
		return graph;
	}
}

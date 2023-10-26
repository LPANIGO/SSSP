/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package representaciongrafo;

/**
 *
 * @author Leo
 */
import java.util.List;
import java.util.LinkedList;

//Representa un vértice del grafo
public class Vertex {
    public String name;
    public List<Edge> adj;
    public double dist;
    public Vertex prev;
    public int scratch;
    
    public Vertex(String nm){
        this.name = nm;
        this.adj = new LinkedList<Edge>();
        reset();
    }
    
    public void reset() {
        this.dist = Graph.INFINITY;
        this.prev = null;
        //this.pos 0 null;
        this.scratch = 0;
    }
}

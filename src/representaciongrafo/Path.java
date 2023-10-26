/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package representaciongrafo;

/**
 *
 * @author Leo
 */

//Representa un elemento de la cola con prioridad para el algoritmo de Dijkstra.
public class Path implements Comparable<Path> {
    
    public Vertex dest; //w
    public double cost; //D(w)
    
    public Path(Vertex d, double c){
        this.dest = d;
        this.cost = c;
    }
    
    public int compareTo(Path rhs){
        double otherCost = rhs.cost;
        
        return this.cost < otherCost ? -1 : this.cost > otherCost ? 1 : 0;
    }
}

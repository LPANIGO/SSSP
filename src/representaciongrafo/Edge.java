/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package representaciongrafo;

/**
 *
 * @author Leo
 */

//Representa una arista del grafo
public class Edge {
    public Vertex dest;
    public double cost;
    
    public Edge(Vertex d, double c){
        this.dest = d;
        this.cost = c;
    }
}

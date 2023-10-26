/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package representaciongrafo;

/**
 *
 * @author Leo
 */

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.NoSuchElementException;

//Evalúa los caminos más cortos.
public class Graph {
    
    public static final double INFINITY = Double.MAX_VALUE;
    
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>();

    public void addEdge(String sourceName, String destName, double cost){
        Vertex v = getVertex(sourceName);
        Vertex w = getVertex( destName);
        v.adj.add(new Edge(w, cost));
    }
    
    private Vertex getVertex(String vertexName){
        Vertex v = vertexMap.get(vertexName);
        if(v == null){
            v = new Vertex(vertexName);
            vertexMap.put(vertexName, v);
        }
        return v;
    }
    
    //Rutina de prep. para gestionar los vértices inalcanzables e imprimir
    //el coste total. Invoca la rutina recursiva para imprimir el camino más
    //corto a destNode despúes de ejecutar el algoritmo del camino más corto.
    public void printPath(String destName){
        Vertex w = vertexMap.get(destName);
        if(w==null){
           throw new NoSuchElementException();
        } else if(w.dist==INFINITY){
            System.out.println(destName + " is unreachable");
        } else {
            System.out.println("Cost is: " + w.dist);
            printPath(w);
            System.out.println();
        }
    }
    
    /**
     * Algoritmo del camino mas corto no ponderado con un único origen.
     */
    public void unweighted(String startName){
        clearAll();
        
        Vertex start = vertexMap.get(startName);
        if(start == null)
            throw new NoSuchElementException("Start vertex not found");
        
        Queue<Vertex> q = new LinkedList<Vertex>();
        q.add(start); 
        start.dist = 0;
        
        while( !q.isEmpty()){
            Vertex v = q.remove();
            
            for( Edge e: v.adj){
                Vertex w = e.dest;
                
                if( w.dist == INFINITY){
                    w.dist = v.dist + 1;
                    w.prev = v;
                    q.add(w);  
                }
            }
        }
    }
    
    /**
     * Algortimo del camino más corto con ponderación y un único origen.
     */
    public void dijkstra(String startName){
        PriorityQueue<Path> pq = new PriorityQueue<Path>();
        
        Vertex start = vertexMap.get(startName);
        if(start == null)
            throw new NoSuchElementException("Start vertex not found");
        
        clearAll();
        pq.add( new Path(start, 0));
        start.dist = 0;
        
        int nodesSeen = 0;
        
        while( !pq.isEmpty() && nodesSeen < vertexMap.size()){
            Path vrec = pq.remove(); //se extraen con prioridad
            Vertex v = vrec.dest;
            if( v.scratch != 0) //v ya procesado
                continue;
            
            v.scratch = 1;
            nodesSeen++;
            
            for( Edge e : v.adj){
                Vertex w = e.dest;
                double cvw = e.cost;
                
                if(cvw < 0)
                    throw new GraphException("Graph has negative edges");
                
                if(w.dist > v.dist + cvw){
                    w.dist = v.dist + cvw;
                    w.prev = v;
                    pq.add(new Path(w, w.dist));
                }
            }
        }        
    }
    
    public void negative(String startName){
    
    }
    
    /*
    Alg. camino más corto con ponderación negativa y único origen para grafos acíclicos
    */
    public void acyclic(String startName){
        Vertex start = vertexMap.get(startName);
        if(start == null)
            throw new NoSuchElementException("Start vertex not found");
        
        clearAll();
        Queue<Vertex> q = new LinkedList<Vertex>();
        //Utilizamos una cola para realizar la ordenación topológica,
        //y mantenemos la info del grado entrante en scratch.
        start.dist = 0;
        
        //Calcular los grados entrantes
        Collection<Vertex> vertexSet = vertexMap.values();
        for(Vertex v : vertexSet)
            for(Edge e : v.adj)
                e.dest.scratch++;
         
        //Poner en cola los vertices con grado entrante =
        for(Vertex v : vertexSet)
            if(v.scratch == 0)
                q.add(v);
        
        int iterations;
        for(iterations = 0; !q.isEmpty(); iterations++){
            Vertex v = q.remove();
            
            for(Edge e: v.adj){
                Vertex w = e.dest;
                double cvw = e.cost;
                
                if(--w.scratch == 0)
                    q.add(w);
                
                if(v.dist == INFINITY)
                    continue;//skip current iteration
                
                if(w.dist > v.dist + cvw){
                    w.dist = v.dist + cvw;
                    w.prev = v;
                }
            }
        }
        if(iterations != vertexMap.size())
            throw new GraphException("Graph has a cycle.");
    }
    
    //Rutina recursiva para imprimir el camino más corto hasta dest,
    //despues de ejecutar el algoritmo del camino más corto. 
    //Se sabe que ese camino existe.
    private void printPath(Vertex dest){
        if(dest.prev != null){
            printPath(dest.prev);
            System.out.println(" to ");
        }
        System.out.println(dest.name);
    }
    
    //Inicializar la información de salida de los vértices antes de
    //ejecutar ningún algortimo del camino más corto.
    private void clearAll(){
        for(Vertex v: vertexMap.values())
            v.reset();
    }  
}

//Utilizado para indicar las violaciones de las  precondiciones para los diversos
//algortimos del camino más corto.
class GraphException extends RuntimeException{
    public GraphException(String name){
        super(name);
    }
}
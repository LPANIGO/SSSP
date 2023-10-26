/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package representaciongrafo;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.util.NoSuchElementException;
/**
 *
 * @author Leo
 */
public class Test {
    /**
     *Una rutina main que:
     * 1. Lee un archivo (suministrado como un parámetro de la línea de comandos) que contiene aristas
     * 2. Construye el grafo
     * 3. Pide repetidamente dos vértices y ejecuta
     * el algoritmo de determinación del camino más corto.
     * El archivo de datos es una secuencia de líneas con el formato
     * origen destino coste
     */
    
    public static void main(String[] args){
        Graph g = new Graph();
        try{
            FileReader fin = new FileReader(args[0]);
            Scanner graphFile = new Scanner(fin); //Scanner permite leer el archivo linea por linea
            
            //Leer las aristas e insertar
            String line;
            while(graphFile.hasNextLine()){
                line = graphFile.nextLine();
                StringTokenizer st = new StringTokenizer(line);
                try{
                    if(st.countTokens() != 3){
                        System.err.println("Skipping bad line " + line);
                        continue;
                    }
                    String source = st.nextToken();
                    String dest = st.nextToken();
                    int cost = Integer.parseInt(st.nextToken());
                    g.addEdge(source, dest, cost);
                    
                }catch(NumberFormatException e){
                    System.err.println("Skipping bad line: " + line);
                }
            }
        } catch(IOException e){
            System.err.println(e);
        }
        
        System.out.println("File read...");

        Scanner in = new Scanner(System.in);
        while(processRequest(in, g)); //En este punto el grafo ya está creado e inicializado
    }
    
    /**
     * Procesar solicitud: devolver false si estamos en el final del archivo.
     */
    public static boolean processRequest(Scanner in, Graph g){
        try{
            System.out.println("Enter start node:");
            String startName = in.nextLine();
            
            System.out.println("Enter destination node:");
            String destName = in.nextLine();
            
            System.out.println("Enter algorithm (u, d, n, a): ");
            String alg = in.nextLine();
            
            if( alg.equals("u")){
                g.unweighted(startName);
            } else if( alg.equals("d")){
                g.dijkstra(startName);
            } else if( alg.equals("n")){
                g.negative(startName);
            }else if( alg.equals("a")){
                g.acyclic(startName);
            }
            g.printPath(destName);
            
        }catch(NoSuchElementException e){
            return false;
        }catch(GraphException e){
            System.err.println(e);
        }
        return true;
    }
}

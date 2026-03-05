package ejercicio4;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;

public class Solucion4 {

    public static Solucion4 create(List<Integer> ls) {
        return new Solucion4(ls);
    }

    private String camino, txt;
    private Double totalTime, totalEffort;
    private Double avgTime, avgEffort, monCons;
    
    

    public String getCamino() {
		return camino;
	}

	public String getTxt() {
		return txt;
	}

	public Double getTotalTime() {
		return totalTime;
	}

	public Double getTotalEffort() {
		return totalEffort;
	}

	public Double getAvgTime() {
		return avgTime;
	}

	public Double getAvgEffort() {
		return avgEffort;
	}

	public Double getMonCons() {
		return monCons;
	}

	private Solucion4(List<Integer> ls) {
    	txt = "";
        List<Integer> aux = List2.addLast(ls, ls.getFirst());
        
    	camino = aux.stream().map(i -> Datos4.getVertex(i)+"")
        	.collect(Collectors.joining(" ->\n\t", "Recorrido:\n\t", ""));
        
    	// Tiempo total empleado:
    	totalTime = IntStream.range(0, Datos4.N)
    		    .mapToDouble(i -> Datos4.tiempo(ls.get(i), ls.get((i + 1) % Datos4.N)))
    		    .sum();
        /// Cocinando con Miguel: el (x+1)%Datos4.N) sale de que tenemos que hacerle el módulo para tener el último elemento
        /// con el primero elemento de la lista cromosómica.
        
        avgTime = totalTime / Datos4.N;
        
        // Esfuerzo total empleado:
        totalEffort = IntStream.range(0, Datos4.N)
        	    .mapToDouble(i -> Datos4.esfuerzo(ls.get(i), ls.get((i + 1) % Datos4.N)))
        	    .sum();
        avgEffort = totalEffort / Datos4.N;
        
        // Número de lugares con monumento consecutivos a otro monumento:
        monCons = (double) IntStream.range(0, Datos4.N)
        	    .filter(i -> {
        	        int actual = ls.get(i);
        	        
        	        // Calculamos quién es el anterior (sumamos N antes del módulo para evitar negativos)
        	        int anterior = ls.get((i - 1 + Datos4.N) % Datos4.N); 
        	        // Calculamos quién es el siguiente
        	        int siguiente = ls.get((i + 1) % Datos4.N);           
        	        
        	        // El lugar cuenta si forma pareja de monumentos con el de atrás O con el de adelante
        	        return Datos4.sonMonumentos(anterior, actual) || Datos4.sonMonumentos(actual, siguiente);
        	    })
        	    .count();
        
        //monCons = (double) IntStream.range(0, Datos4.N);
     }

	@Override
    public String toString() {
    	String s1 = String.format("\nTiempos (total/medio/maximo): %.1f / %.1f / %.1f", totalTime, avgTime, Datos4.maxTime);
    	String s2 = String.format("\nEsfuerzos (total/medio): %.1f / %.1f", totalEffort, avgEffort);
    	String s3 = String.format("\nNº de lugares con monumento consecutivos a otro con monumento: %d", monCons.intValue());
        return txt+camino+s1+s2+s3;
    }

}

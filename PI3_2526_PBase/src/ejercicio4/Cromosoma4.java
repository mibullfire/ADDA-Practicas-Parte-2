package ejercicio4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.BinaryData;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma4 implements PermutationData<Solucion4> {
	public Cromosoma4(String file) {
		Datos4.iniDatos(file); // Inicializamos los datos!
	}

	@Override
	public ChromosomeType type() {
		// Cromosoma de tipo Permutacion
		return ChromosomeType.Permutation;
	}
	
	@Override
	public Integer size() {
		// Numero de vertices
		return Datos4.N;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		
		Solucion4 xd = solution(value);
		
		double error=0;
		
		double monCons = xd.getMonCons()*10000; 
		
		if (xd.getTotalTime() > Datos4.maxTime) {
		    error += 1000000; // Un castigo muy alto para que descarte esta solución
		}
			
		return -(xd.getTotalTime() + xd.getTotalEffort() + error - monCons);
	}

	@Override
	public Solucion4 solution(List<Integer> value) {
		return Solucion4.create(value);
	}

}
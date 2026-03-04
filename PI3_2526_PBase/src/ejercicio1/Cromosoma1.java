package ejercicio1;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma1 implements BinaryData<Solucion1> {
	public Cromosoma1(String file) {
		Datos1.iniDatos(file); // Inicializamos los datos!
	}

	@Override
	public ChromosomeType type() {
		// Cromosoma de tipo binario
		return ChromosomeType.Binary;
	}
	
	@Override
	public Integer size() {
		// Tantos genes como candidatos
		return Datos1.getNumCandidatos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Implementar segun el modelo
		
		double goal = 0, sum = 0, error = 0;
		
		List<String> cualidadesCromosomicas = new ArrayList<String>();
		
//		System.out.println(ls);
		
		for (int i = 0; i < size(); i++) {
			if(value.get(i)>0) {
				goal += Datos1.getValoracion(i);
				
				// Existe incompatibilidad entre candidatos
				for (int j = 0; j < size(); j++) {
					if(value.get(j)>0&&Datos1.getSonIncompatibles(i, j)) error++;
				}
				
				// Ver que candidatos cumplent todas las condiciones
				cualidadesCromosomicas.addAll(Datos1.getCualidades(i));
				
				// La suma de sueldos no supero el limite
				sum += Datos1.getSueldoMin(i);
				
			}
		}
		
		// Por cada condición incumplida sumamos al error.
		for (String i: Datos1.getCualidades()) {
			if (!cualidadesCromosomicas.contains(i)) error++;
		}
		
		if (sum > Datos1.getPresupuestoMax()) error ++;
		
		return goal -10000000*error;
		
	}

	@Override
	public Solucion1 solution(List<Integer> value) {
		return Solucion1.create(value);
	}

}
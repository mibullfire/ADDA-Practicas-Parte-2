package ejercicio2;


import java.util.List;

import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma2 implements RangeIntegerData<Solucion2> {
	public Cromosoma2(String file) {
		Datos2.iniDatos(file); // Inicializamos los datos!
	}

	@Override
	public ChromosomeType type() {
		// Cromosoma de tipo binario
		return ChromosomeType.RangeInteger;
	}
	
	@Override
	public Integer size() {
		// Tantos genes como candidatos
		return Datos2.getNumProductos();
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {

        double goal = 0, error = 0;

        double tiempoProdTotal = 0, tiempoElabTotal = 0;

        for(int i = 0; i <size(); i++) {

            if (value.get(i)>0) {
 	
                tiempoProdTotal += Datos2.getTiempoProdProd(i)*value.get(i);
                tiempoElabTotal += Datos2.getTiempoElabProd(i)*value.get(i);

            	goal += Datos2.getPrecioProd(i)*value.get(i);
                
            }
        }

        if (tiempoProdTotal > Datos2.getTiempoProdTotal()) error += 1000;
        if (tiempoElabTotal > Datos2.getTiempoElabTotal()) error += 1000;

		return goal -10000000*error;
	}

	@Override
	public Solucion2 solution(List<Integer> value) {
		return Solucion2.create(value);
	}

	@Override
	public Integer max(Integer i) {
		// Con esto ya aplicamos una restriccion. Limitamos los valores que hay.
		return Datos2.getUnidsSemanaProd(i)+1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}

}
package ejercicio3;


import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;

public class Cromosoma3 implements RangeIntegerData<Solucion3> {

    public Cromosoma3(String file) {
        Datos3.iniDatos(file);
    }

    @Override
    public ChromosomeType type() {
        return ChromosomeType.RangeInteger;
    }

    @Override
    public Integer size() {
        return Datos3.getNumElementos();
    }

    @Override
    public Double fitnessFunction(List<Integer> value) {
        int goal = goal(value);
        Double totalIncompatibilidad = totalIncompatibilidad(value);
        Double totalTamaño = totalTamaño(value);

        // Se resta la penalización por incumplimiento de restricciones al objetivo
        return goal - 10000 * (totalIncompatibilidad + totalTamaño);
    }

    private Integer goal(List<Integer> value) {
        // Cuenta el número de contenedores distintos que han sido asignados
        return new HashSet<>(value.stream()
                .filter(c -> c < Datos3.getNumContenedores())
                .toList())
                .size();
    }

    private Double totalIncompatibilidad(List<Integer> value) {
        Double cont = 0.;
        for (int i = 0; i < value.size(); i++) {
            if (value.get(i) != Datos3.getNumContenedores() &&
                Datos3.getNoPuedeUbicarse(i, value.get(i))) {
                cont++;
            }
        }
        return AuxiliaryAg.distanceToEqZero(cont);
    }

    private Double totalTamaño(List<Integer> value) {
        Map<Integer, Double> map = new HashMap<>();
        for (int i = 0; i < value.size(); i++) {
            if (!map.containsKey(value.get(i))) {
                map.put(value.get(i), 0.);
            }
            double suma = map.get(value.get(i));
            suma += Datos3.getTamElemento(i);
            map.put(value.get(i), suma);
        }

        double cont = 0;
        for (int j = 0; j < Datos3.getNumContenedores(); j++) {
            if (map.containsKey(j)) {
                cont += AuxiliaryAg.distanceToLeZero(map.get(j) - Datos3.getTamContenedor(j));
            }
        }
        return cont;
    }

    @Override
    public Integer max(Integer i) {
        // Un elemento puede asignarse a cualquier contenedor o quedar sin asignar (índice extra)
        return Datos3.getNumContenedores();
    }

    @Override
    public Integer min(Integer i) {
        return 0;
    }

    @Override
    public Solucion3 solution(List<Integer> value) {
        return Solucion3.create(value);
    }
}
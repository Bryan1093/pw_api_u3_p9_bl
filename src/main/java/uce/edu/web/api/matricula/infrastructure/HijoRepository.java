package uce.edu.web.api.matricula.infrastructure;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.web.api.matricula.domain.Hijo;

@ApplicationScoped
public class HijoRepository implements PanacheRepository<Hijo> {

    public List<Hijo> buscarPorIdEstudiante(Integer idEstudiante) {
        return find("estudiante.id", idEstudiante).list();
    }
}
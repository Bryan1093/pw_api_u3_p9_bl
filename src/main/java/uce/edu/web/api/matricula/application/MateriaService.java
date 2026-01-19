package uce.edu.web.api.matricula.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.domain.Materia;
import uce.edu.web.api.matricula.infrastructure.MateriaRepository;

@ApplicationScoped
public class MateriaService {

    @Inject
    private MateriaRepository materiaRepository;

    public List<Materia> listarTodos() {
        return this.materiaRepository.findAll().list();
    }

    public Materia consultarPorId(Integer id) {
        return this.materiaRepository.findById(id.longValue());
    }

    @Transactional
    public void crearMateria(Materia materia) {
        this.materiaRepository.persist(materia);
    }

    @Transactional
    public void actualizarMateria(Integer id, Materia materia) {
        Materia materiaActual = this.consultarPorId(id);
        materiaActual.setNombre(materia.getNombre());
        materiaActual.setCodigo(materia.getCodigo());
        materiaActual.setCreditos(materia.getCreditos());
        // se actualiza por dirty checking
    }

    @Transactional
    public void actualizacionParcial(Integer id, Materia materia) {
        Materia materiaActual = this.consultarPorId(id);
        if (materia.getNombre() != null) {
            materiaActual.setNombre(materia.getNombre());
        }
        if (materia.getCodigo() != null) {
            materiaActual.setCodigo(materia.getCodigo());
        }
        if (materia.getCreditos() != null) {
            materiaActual.setCreditos(materia.getCreditos());
        }
    }

    @Transactional
    public void eliminarMateria(Integer id) {
        this.materiaRepository.deleteById(id.longValue());
    }

    // Endpoint adicional 1: Buscar materias por código
    public Materia buscarPorCodigo(String codigo) {
        return this.materiaRepository.find("codigo", codigo).firstResult();
    }

    // Endpoint adicional 2: Listar materias por rango de créditos
    public List<Materia> listarPorCreditos(Integer creditosMin, Integer creditosMax) {
        return this.materiaRepository.find("creditos >= ?1 and creditos <= ?2", creditosMin, creditosMax).list();
    }
}

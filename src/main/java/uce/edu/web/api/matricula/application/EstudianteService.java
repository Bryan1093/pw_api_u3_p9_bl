package uce.edu.web.api.matricula.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.domain.Estudiante;
import uce.edu.web.api.matricula.infrastructure.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> listarTodos() {
        return this.estudianteRepository.findAll().list();
    }

    public Estudiante consultarPorId(Integer id) {
        return this.estudianteRepository.findById(id.longValue());
    }

    @Transactional
    public void crearEstudiante(Estudiante estudiante) {
        this.estudianteRepository.persist(estudiante);
    }

    @Transactional
    public void actualizarEstudiante(Integer id, Estudiante estudiante) {
        Estudiante estudianteActual = this.consultarPorId(id);
        estudianteActual.setNombre(estudiante.getNombre());
        estudianteActual.setApellido(estudiante.getApellido());
        estudianteActual.setFechaNacimiento(estudiante.getFechaNacimiento());
        // se actualiza por dirty checking
    }

    @Transactional
    public void actualizacionParcial(Integer id, Estudiante estudiante) {
        Estudiante estudianteActual = this.consultarPorId(id);
        if (estudiante.getNombre() != null) {
            estudianteActual.setNombre(estudiante.getNombre());
        }
        if (estudiante.getApellido() != null) {
            estudianteActual.setApellido(estudiante.getApellido());
        }
        if (estudiante.getFechaNacimiento() != null) {
            estudianteActual.setFechaNacimiento(estudiante.getFechaNacimiento());
        }
    }

    @Transactional
    public void eliminarEstudiante(Integer id) {
        this.estudianteRepository.deleteById(id.longValue());
    }

    public List<Estudiante> listarPorProvincia(String provincia, String genero) {
        return this.estudianteRepository.find("provincia = ?1 and genero = ?2", provincia, genero).list();
    }
}
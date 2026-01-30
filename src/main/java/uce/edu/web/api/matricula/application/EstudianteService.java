package uce.edu.web.api.matricula.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.application.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.application.representation.HijoRepresentation;
import uce.edu.web.api.matricula.domain.Estudiante;
import uce.edu.web.api.matricula.infrastructure.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    private EstudianteRepresentation mapperToER(Estudiante estudiante) {
        EstudianteRepresentation estudianteRepresentation = new EstudianteRepresentation();
        estudianteRepresentation.setId(estudiante.getId());
        estudianteRepresentation.setNombre(estudiante.getNombre());
        estudianteRepresentation.setApellido(estudiante.getApellido());
        estudianteRepresentation.setFechaNacimiento(estudiante.getFechaNacimiento());
        estudianteRepresentation.setProvincia(estudiante.getProvincia());
        estudianteRepresentation.setGenero(estudiante.getGenero());

        // Mapear los hijos si existen
        if (estudiante.hijos != null && !estudiante.hijos.isEmpty()) {
            List<HijoRepresentation> hijosRepresentation = estudiante.hijos.stream()
                    .map(hijo -> {
                        HijoRepresentation hr = new HijoRepresentation();
                        hr.setId(hijo.getId());
                        hr.setNombre(hijo.getNombre());
                        hr.setApellido(hijo.getApellido());
                        return hr;
                    })
                    .toList();
            estudianteRepresentation.setHijos(hijosRepresentation);
        }

        return estudianteRepresentation;
    }

    private Estudiante mapperToEstudiante(EstudianteRepresentation estudiante) {
        Estudiante estudiante1 = new Estudiante();
        estudiante1.setId(estudiante.getId());
        estudiante1.setNombre(estudiante.getNombre());
        estudiante1.setApellido(estudiante.getApellido());
        estudiante1.setFechaNacimiento(estudiante.getFechaNacimiento());
        estudiante1.setProvincia(estudiante.getProvincia());
        estudiante1.setGenero(estudiante.getGenero());
        return estudiante1;
    }

    public List<EstudianteRepresentation> listarTodos() {
        return this.estudianteRepository.findAll().list().stream()
                .map(this::mapperToER)
                .toList();
    }

    public EstudianteRepresentation consultarPorId(Integer id) {
        Estudiante e = this.estudianteRepository.findById(id.longValue());
        if (e == null) {
            return null;
        }
        return this.mapperToER(e);
    }

    @Transactional
    public void crearEstudiante(EstudianteRepresentation estudiante) {
        Estudiante e = this.mapperToEstudiante(estudiante);
        this.estudianteRepository.persist(e);
    }

    @Transactional
    public void actualizarEstudiante(Integer id, EstudianteRepresentation estudiante) {
        EstudianteRepresentation estudianteActual = this.consultarPorId(id);

        estudianteActual.setNombre(estudiante.getNombre());
        estudianteActual.setApellido(estudiante.getApellido());
        estudianteActual.setFechaNacimiento(estudiante.getFechaNacimiento());
        // se actualiza por dirty checking
    }

    @Transactional
    public void actualizacionParcial(Integer id, EstudianteRepresentation estudiante) {
        Estudiante estudianteActual = this.estudianteRepository.findById(id.longValue());

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

    public List<EstudianteRepresentation> listarPorProvincia(String provincia, String genero) {
        return this.estudianteRepository.find("provincia = ?1 and genero = ?2", provincia, genero).list().stream()
                .map(this::mapperToER).toList();
    }

}
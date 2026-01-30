package uce.edu.web.api.matricula.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.application.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.application.representation.HijoRepresentation;
import uce.edu.web.api.matricula.domain.Estudiante;
import uce.edu.web.api.matricula.domain.Hijo;
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

        // Manejar hijos si existen
        if (estudiante.getHijos() != null && !estudiante.getHijos().isEmpty()) {
            e.hijos = new ArrayList<>();
            for (HijoRepresentation hijoRep : estudiante.getHijos()) {
                Hijo hijo = new Hijo();
                hijo.setNombre(hijoRep.getNombre());
                hijo.setApellido(hijoRep.getApellido());
                hijo.estudiante = e;
                e.hijos.add(hijo);
            }
        }

        this.estudianteRepository.persist(e);
    }

    @Transactional
    public void actualizarEstudiante(Integer id, EstudianteRepresentation estudiante) {
        Estudiante estudianteActual = this.estudianteRepository.findById(id.longValue());

        estudianteActual.setNombre(estudiante.getNombre());
        estudianteActual.setApellido(estudiante.getApellido());
        estudianteActual.setFechaNacimiento(estudiante.getFechaNacimiento());
        estudianteActual.setProvincia(estudiante.getProvincia());
        estudianteActual.setGenero(estudiante.getGenero());

        // Actualizar hijos (PUT: ajusta la lista para que coincida con la enviada)
        if (estudiante.getHijos() != null) {
            if (estudianteActual.hijos == null) {
                estudianteActual.hijos = new ArrayList<>();
            }

            // 1. Identificar los IDs que vienen para mantenerlos
            List<Integer> idsEntrantes = estudiante.getHijos().stream()
                    .map(HijoRepresentation::getId)
                    .filter(idHijo -> idHijo != null)
                    .toList();

            // 2. Eliminar de la base de datos los hijos que NO vienen en la nueva lista
            // (Gracias a orphanRemoval=true, al quitarlos de la lista se borran de BD)
            estudianteActual.hijos.removeIf(h -> h.getId() != null && !idsEntrantes.contains(h.getId()));

            // 3. Actualizar existentes o agregar nuevos
            for (HijoRepresentation hijoRep : estudiante.getHijos()) {
                if (hijoRep.getId() != null) {
                    // Actualizar existente
                    estudianteActual.hijos.stream()
                            .filter(h -> h.getId().equals(hijoRep.getId()))
                            .findFirst()
                            .ifPresent(h -> {
                                h.setNombre(hijoRep.getNombre());
                                h.setApellido(hijoRep.getApellido());
                            });
                } else {
                    // Agregar nuevo
                    Hijo nuevo = new Hijo();
                    nuevo.setNombre(hijoRep.getNombre());
                    nuevo.setApellido(hijoRep.getApellido());
                    nuevo.estudiante = estudianteActual;
                    estudianteActual.hijos.add(nuevo);
                }
            }
        }
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
        if (estudiante.getProvincia() != null) {
            estudianteActual.setProvincia(estudiante.getProvincia());
        }
        if (estudiante.getGenero() != null) {
            estudianteActual.setGenero(estudiante.getGenero());
        }

        // Actualizar hijos si se envían
        if (estudiante.getHijos() != null) {
            if (estudianteActual.hijos == null) {
                estudianteActual.hijos = new ArrayList<>();
            }

            // En PATCH, si se envían hijos, asumimos que esa es la nueva lista completa de
            // hijos
            // (mismo comportamiento que PUT para la colección)

            // 1. Identificar los IDs que vienen para mantenerlos
            List<Integer> idsEntrantes = estudiante.getHijos().stream()
                    .map(HijoRepresentation::getId)
                    .filter(idHijo -> idHijo != null)
                    .toList();

            // 2. Eliminar los que ya no están
            estudianteActual.hijos.removeIf(h -> h.getId() != null && !idsEntrantes.contains(h.getId()));

            // 3. Actualizar o Insertar
            for (HijoRepresentation hijoRep : estudiante.getHijos()) {
                if (hijoRep.getId() != null) {
                    // Actualizar
                    estudianteActual.hijos.stream()
                            .filter(h -> h.getId().equals(hijoRep.getId()))
                            .findFirst()
                            .ifPresent(h -> {
                                h.setNombre(hijoRep.getNombre());
                                h.setApellido(hijoRep.getApellido());
                            });
                } else {
                    // Nuevo
                    Hijo nuevo = new Hijo();
                    nuevo.setNombre(hijoRep.getNombre());
                    nuevo.setApellido(hijoRep.getApellido());
                    nuevo.estudiante = estudianteActual;
                    estudianteActual.hijos.add(nuevo);
                }
            }
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
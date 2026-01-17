package uce.edu.web.api.matricula.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import uce.edu.web.api.matricula.application.EstudianteService;
import uce.edu.web.api.matricula.domain.Estudiante;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @GET
    @Path("/todos")
    public List<Estudiante> listarTodos() {
        return this.estudianteService.listarTodos();
    }

    @GET
    @Path("/consultarPorId/{id}")
    public Estudiante listarPorId(@PathParam("id") Integer identificador) {
        return this.estudianteService.consultarPorId(identificador);
    }

    @POST
    @Path("/guardar")
    public void guardarEstudiante(Estudiante estudiante) {
        this.estudianteService.crearEstudiante(estudiante);
    }

    @PUT
    @Path("/actualizar/{id}")
    public void actualizarEstudiante(@PathParam("id") Integer id, Estudiante estudiante) {
        this.estudianteService.actualizarEstudiante(id, estudiante);
    }

    @PATCH
    @Path("/actualizarParcial/{id}")
    public void actualizarEstudianteParcial(@PathParam("id") Integer id, Estudiante estudiante) {
        this.estudianteService.actualizacionParcial(id, estudiante);
    }

    @DELETE
    @Path("/eliminar/{id}")
    public void eliminarEstudiante(@PathParam("id") Integer id) {
        this.estudianteService.eliminarEstudiante(id);
    }
}
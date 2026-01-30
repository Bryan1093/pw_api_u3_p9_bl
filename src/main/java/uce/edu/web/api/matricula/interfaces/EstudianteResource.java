package uce.edu.web.api.matricula.interfaces;

import java.util.List;

import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.matricula.application.EstudianteService;
import uce.edu.web.api.matricula.application.HijoService;
import uce.edu.web.api.matricula.application.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.domain.Hijo;

@Path("/estudiantes")

public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @Inject
    private HijoService hijoService;

    @GET
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<EstudianteRepresentation> listarTodos() {
        System.out.println("LISTAR TODOS XXXXXXXXX");
        return this.estudianteService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public EstudianteRepresentation listarPorId(@PathParam("id") Integer identificador) {
        return this.estudianteService.consultarPorId(identificador);
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarEstudiante(EstudianteRepresentation estudiante) {
        this.estudianteService.crearEstudiante(estudiante);
        return Response.status(Response.Status.CREATED).entity(estudiante).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEstudiante(@PathParam("id") Integer id, EstudianteRepresentation estudiante) {
        this.estudianteService.actualizarEstudiante(id, estudiante);
        return Response.status(209).entity(null).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarEstudianteParcial(@PathParam("id") Integer id, EstudianteRepresentation estudiante) {
        this.estudianteService.actualizacionParcial(id, estudiante);
    }

    @DELETE
    @Path("/{id}")
    public void eliminarEstudiante(@PathParam("id") Integer id) {
        this.estudianteService.eliminarEstudiante(id);
    }

    @GET
    @Path("/provincia")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstudianteRepresentation> listarPorProvincia(@QueryParam("provincia") String provincia,
            @QueryParam("genero") String genero) {
        System.out.println("LISTAR POR PROVINCIA XXXXXXXXX");
        return this.estudianteService.listarPorProvincia(provincia, genero);
    }

    @GET
    @Path("/{id}/hijos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hijo> buscarHijosPorEstudiante(@PathParam("id") Integer id) {
        return this.hijoService.buscarPorIdEstudiante(id);
    }
}
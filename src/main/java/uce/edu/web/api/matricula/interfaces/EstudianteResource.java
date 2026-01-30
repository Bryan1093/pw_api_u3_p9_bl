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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.matricula.application.EstudianteService;
import uce.edu.web.api.matricula.application.HijoService;
import uce.edu.web.api.matricula.application.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.application.representation.HijoRepresentation;
import uce.edu.web.api.matricula.application.representation.LinkDto;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @Inject
    private HijoService hijoService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<EstudianteRepresentation> listarTodos() {
        return this.estudianteService.listarTodos().stream()
                .map(estudiante -> addLinks(estudiante))
                .toList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorId(@PathParam("id") Integer identificador) {
        EstudianteRepresentation estudiante = this.estudianteService.consultarPorId(identificador);
        if (estudiante == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(this.addLinks(estudiante)).build();
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
    public Response actualizarEstudianteParcial(@PathParam("id") Integer id, EstudianteRepresentation estudiante) {
        this.estudianteService.actualizacionParcial(id, estudiante);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarEstudiante(@PathParam("id") Integer id) {
        this.estudianteService.eliminarEstudiante(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/provincia")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstudianteRepresentation> listarPorProvincia(@QueryParam("provincia") String provincia,
            @QueryParam("genero") String genero) {
        return this.estudianteService.listarPorProvincia(provincia, genero);
    }

    @GET
    @Path("/{id}/hijos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HijoRepresentation> buscarHijosPorEstudiante(@PathParam("id") Integer id) {
        return this.hijoService.buscarPorIdEstudiante(id);
    }

    private EstudianteRepresentation addLinks(EstudianteRepresentation estudiante) {
        if (estudiante == null || estudiante.getId() == null) {
            return estudiante;
        }
        String self = this.uriInfo.getBaseUriBuilder()
                .path("estudiantes")
                .path(estudiante.getId().toString())
                .build()
                .toString();
        String hijos = this.uriInfo.getBaseUriBuilder()
                .path("estudiantes")
                .path(estudiante.getId().toString())
                .path("hijos")
                .build()
                .toString();
        estudiante.setLinks(List.of(new LinkDto(self, "self"), new LinkDto(hijos, "hijos")));
        return estudiante;
    }
}
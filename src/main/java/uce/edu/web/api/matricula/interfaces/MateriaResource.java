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
import uce.edu.web.api.matricula.application.MateriaService;
import uce.edu.web.api.matricula.domain.Materia;

@Path("/materias")

public class MateriaResource {

    @Inject
    private MateriaService materiaService;

    @GET
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Materia> listarTodos() {
        return this.materiaService.listarTodos();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Materia listarPorId(@PathParam("id") Integer identificador) {
        return this.materiaService.consultarPorId(identificador);
    }

    @POST
    @Path("")
    public Response guardarMateria(Materia materia) {
        this.materiaService.crearMateria(materia);
        return Response.status(Response.Status.CREATED).entity(materia).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizarMateria(@PathParam("id") Integer id, Materia materia) {
        this.materiaService.actualizarMateria(id, materia);
        return Response.status(209).entity(null).build();
    }

    @PATCH
    @Path("/{id}")
    public void actualizarMateriaParcial(@PathParam("id") Integer id, Materia materia) {
        this.materiaService.actualizacionParcial(id, materia);
    }

    @DELETE
    @Path("/{id}")
    public void eliminarMateria(@PathParam("id") Integer id) {
        this.materiaService.eliminarMateria(id);
    }

    // Endpoint adicional 1: Buscar materia por código
    @GET
    @Path("/codigo/{codigo}")
    public Materia buscarPorCodigo(@PathParam("codigo") String codigo) {
        return this.materiaService.buscarPorCodigo(codigo);
    }

    // Endpoint adicional 2: Listar materias por rango de créditos
    @GET
    @Path("/creditos/{min}/{max}")
    public List<Materia> listarPorCreditos(
            @PathParam("min") Integer creditosMin,
            @PathParam("max") Integer creditosMax) {
        return this.materiaService.listarPorCreditos(creditosMin, creditosMax);
    }
}

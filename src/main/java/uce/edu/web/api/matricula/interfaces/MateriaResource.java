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
import jakarta.ws.rs.QueryParam;
import uce.edu.web.api.matricula.application.MateriaService;
import uce.edu.web.api.matricula.domain.Materia;

@Path("/materias")
public class MateriaResource {

    @Inject
    private MateriaService materiaService;

    @GET
    @Path("/todos")
    public List<Materia> listarTodos() {
        return this.materiaService.listarTodos();
    }

    @GET
    @Path("/consultarPorId/{id}")
    public Materia listarPorId(@PathParam("id") Integer identificador) {
        return this.materiaService.consultarPorId(identificador);
    }

    @POST
    @Path("/guardar")
    public void guardarMateria(Materia materia) {
        this.materiaService.crearMateria(materia);
    }

    @PUT
    @Path("/actualizar/{id}")
    public void actualizarMateria(@PathParam("id") Integer id, Materia materia) {
        this.materiaService.actualizarMateria(id, materia);
    }

    @PATCH
    @Path("/actualizarParcial/{id}")
    public void actualizarMateriaParcial(@PathParam("id") Integer id, Materia materia) {
        this.materiaService.actualizacionParcial(id, materia);
    }

    @DELETE
    @Path("/eliminar/{id}")
    public void eliminarMateria(@PathParam("id") Integer id) {
        this.materiaService.eliminarMateria(id);
    }

    // Endpoint adicional 1: Buscar materia por código
    @GET
    @Path("/buscarPorCodigo/{codigo}")
    public Materia buscarPorCodigo(@PathParam("codigo") String codigo) {
        return this.materiaService.buscarPorCodigo(codigo);
    }

    // Endpoint adicional 2: Listar materias por rango de créditos
    @GET
    @Path("/buscarPorCreditos")
    public List<Materia> listarPorCreditos(
            @QueryParam("min") Integer creditosMin,
            @QueryParam("max") Integer creditosMax) {
        return this.materiaService.listarPorCreditos(creditosMin, creditosMax);
    }
}

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
    @Path("")
    public List<Materia> listarTodos() {
        return this.materiaService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Materia listarPorId(@PathParam("id") Integer identificador) {
        return this.materiaService.consultarPorId(identificador);
    }

    @POST
    @Path("")
    public void guardarMateria(Materia materia) {
        this.materiaService.crearMateria(materia);
    }

    @PUT
    @Path("/{id}")
    public void actualizarMateria(@PathParam("id") Integer id, Materia materia) {
        this.materiaService.actualizarMateria(id, materia);
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

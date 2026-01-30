package uce.edu.web.api.matricula.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uce.edu.web.api.matricula.domain.Hijo;
import uce.edu.web.api.matricula.infrastructure.HijoRepository;

@ApplicationScoped
public class HijoService {

    @Inject
    private HijoRepository hijoRepository;

    public List<Hijo> buscarPorIdEstudiante(Integer idEstudiante) {
        return this.hijoRepository.buscarPorIdEstudiante(idEstudiante);
    }
}
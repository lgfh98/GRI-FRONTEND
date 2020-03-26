package co.edu.uniquindio.gri.dao;

import co.edu.uniquindio.gri.model.RecononocimientosInvestigador;
import co.edu.uniquindio.gri.repository.ReconocimientosRepository;
import co.edu.uniquindio.gri.utilities.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class TipoDAO.
 */
@Service
public class ReconocimientosDAO {

    /**
     * Repository para tipos.
     */
    @Autowired
    ReconocimientosRepository reconocimientosRepository;

    /**
     * Obtiene todas las lineas de investigación de una facultad
     *
     * @param grupoId el id del grupo
     * @return las lineas de investigación de una facultad
     */
    public List<RecononocimientosInvestigador> getReconocimientos(Long grupoId, String searchCriteria) {
        switch (searchCriteria) {
            case Util.UNIVERSITY_PARAM_ID:
                return reconocimientosRepository.findAllReconocimientos();
            case Util.FACULTY_PARAM_ID:
                return reconocimientosRepository.getReconocimientosFacultad(grupoId);
            case Util.CENTER_PARAM_ID:
                return reconocimientosRepository.getReconocimientosCentros(grupoId);
            case Util.PROGRAM_PARAM_ID:
                return reconocimientosRepository.getReconocimientosProgramas(grupoId);
            case Util.GROUP_PARAM_ID:
                return reconocimientosRepository.getReconocimientosGrupos(grupoId);
            case Util.RESEARCHER_PARAM_ID:
                return reconocimientosRepository.getReconocimientosInvestigadores(grupoId);
            default:
                return reconocimientosRepository.getReconocimientos();
        }
    }

    /**
     * Obtiene todas las lineas de investigación de una facultad
     *
     * @return las lineas de investigación de una facultad
     */
    public List<RecononocimientosInvestigador> findAll() {
        return reconocimientosRepository.findAllReconocimientos();
    }
}

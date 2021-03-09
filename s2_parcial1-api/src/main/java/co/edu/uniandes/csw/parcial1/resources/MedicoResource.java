/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.parcial1.resources;

import co.edu.uniandes.csw.parcial1.dtos.MedicoDTO;
import co.edu.uniandes.csw.parcial1.ejb.MedicoLogic;
import co.edu.uniandes.csw.parcial1.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @medico josejbocanegra
 */
@Path("/medicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class MedicoResource {
    
    @Inject
    private MedicoLogic medicoLogic;
    
    @POST
    public MedicoDTO createMedico(MedicoDTO medico) throws BusinessLogicException{
         MedicoDTO medicoDTO = new MedicoDTO(medicoLogic.createMedico(medico.toEntity()));
        return medicoDTO;
    }
  
}

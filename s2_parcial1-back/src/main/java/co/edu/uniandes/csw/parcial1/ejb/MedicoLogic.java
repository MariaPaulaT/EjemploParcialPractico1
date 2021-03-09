/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.parcial1.ejb;

import co.edu.uniandes.csw.parcial1.entities.MedicoEntity;
import co.edu.uniandes.csw.parcial1.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.parcial1.persistence.MedicoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author MariaPaulaT <mp.tellez@uniandes.edu.co>
 */
@Stateless
public class MedicoLogic {
     @Inject
    private MedicoPersistence persistence;
    
     /**
     * Se encarga de crear un Medico en la base de datos.
     *
     * @param medicoEntity Objeto de MedicoEntity con los datos nuevos
     * @return Objeto de MedicoEntity con los datos nuevos y su ID.
     * @throws co.edu.uniandes.csw.discomovil.exceptions.BusinessLogicException
     */
    public MedicoEntity createMedico( MedicoEntity medicoEntity )  throws BusinessLogicException  {
        
        if (medicoEntity.getName() == null ||medicoEntity.getName().equals(" "))  {
            throw new BusinessLogicException("La descripcion del medico esta vacia");
        }
        if (medicoEntity.getSecondName() == null ||medicoEntity.getSecondName().equals(" "))  {
            throw new BusinessLogicException("La descripcion del medico esta vacia");
        }
        if (persistence.findByRecord(medicoEntity.getName()) != null) {
            throw new BusinessLogicException("El nombre ya existe");
        }
        if (medicoEntity.getSpecialty().length() < 4 ) {
            throw new BusinessLogicException("El nombre del medico esta vacio");
        }
        medicoEntity = persistence.create(medicoEntity);
        return medicoEntity;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.parcial1.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.uniandes.csw.parcial1.entities.MedicoEntity;
import java.util.List;
import javax.persistence.TypedQuery;
/**
 *
 * @author MariaPaulaT <mp.tellez@uniandes.edu.co>
 */
@Stateless
public class MedicoPersistence {
    
     @PersistenceContext(unitName = "parcial1PU")
    protected EntityManager em;
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param medico
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public MedicoEntity create ( MedicoEntity medico )
    {
        em.persist(medico);
        return medico;
    }
     public MedicoEntity findByRecord(String name) {
        // Se crea un query para buscar libros con el isbn que recibe el método como argumento. ":isbn" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From MedicoEntity e where e.name = :name", MedicoEntity.class);
        // Se remplaza el placeholder ":isbn" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<MedicoEntity> sameName = query.getResultList();
        MedicoEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        return result;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.parcial1.test.logic;

import co.edu.uniandes.csw.parcial1.ejb.MedicoLogic;
import co.edu.uniandes.csw.parcial1.entities.MedicoEntity;
import co.edu.uniandes.csw.parcial1.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.parcial1.persistence.MedicoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author MariaPaulaT <mp.tellez@uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class MedicoLogicTest {
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private MedicoLogic medicoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<MedicoEntity> data = new ArrayList<>();
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedicoEntity.class.getPackage())
                .addPackage(MedicoLogic.class.getPackage())
                .addPackage(MedicoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
     /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

   
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from MedicoEntity").executeUpdate();
       // em.createQuery("delete from ServiceEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MedicoEntity entity = factory.manufacturePojo(MedicoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

     /**
     * Prueba para crear un Medico
     *
     * @throws co.edu.uniandes.csw.discomovil.exceptions.BusinessLogicException
     */
    @Test
    public void createMedicoTest() throws BusinessLogicException {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        // newEntity.setEditorial(editorialData.get(0));
        MedicoEntity result = medicoLogic.createMedico(newEntity);
        Assert.assertNotNull(result);
        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getName(), entity.getName());
        Assert.assertEquals(newEntity.getSecondName(), entity.getSecondName());
       Assert.assertEquals(newEntity.getRecord(), entity.getRecord());
        Assert.assertEquals(newEntity.getSpecialty(), entity.getSpecialty());
    }

    @Test ( expected = BusinessLogicException.class )
    public void createMedicoNameNullTest() throws BusinessLogicException {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class );
        newEntity.setName(null);
        MedicoEntity result = medicoLogic.createMedico( newEntity );
    }
    
    @Test ( expected = BusinessLogicException.class )
    public void createMedicoDescriptionNullTest() throws BusinessLogicException {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class );
        newEntity.setSecondName(null);
        MedicoEntity result = medicoLogic.createMedico( newEntity );
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createMedicoTestConNameExistente() throws BusinessLogicException {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setName(data.get(0).getName());
        medicoLogic.createMedico(newEntity);
    }
}

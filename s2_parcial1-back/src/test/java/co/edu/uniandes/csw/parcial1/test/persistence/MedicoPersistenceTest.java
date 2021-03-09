/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.parcial1.test.persistence;

import co.edu.uniandes.csw.parcial1.entities.MedicoEntity;
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
public class MedicoPersistenceTest {
         @Inject
    private MedicoPersistence medicoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private final List<MedicoEntity> data = new ArrayList<MedicoEntity>();
      
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MedicoEntity.class.getPackage())
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
            em.joinTransaction();
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
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            MedicoEntity entity = factory.manufacturePojo(MedicoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    /**
     * Prueba para crear un Medico.
     */
     @Test
    public void createMedicoTest() {
         PodamFactory factory = new PodamFactoryImpl();
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        MedicoEntity result = medicoPersistence.create(newEntity);
        Assert.assertNotNull(result);

        MedicoEntity entity = em.find(MedicoEntity.class, result.getId());

        Assert.assertNotNull(entity);
        Assert.assertEquals(newEntity.getName(), entity.getName());    
        Assert.assertEquals(newEntity.getRecord(), entity.getRecord());
        Assert.assertEquals(newEntity.getSecondName(), entity.getSecondName());
        Assert.assertEquals(newEntity.getSpecialty(), entity.getSpecialty());
    }
    
    @Test
    public void findMedicoByRecordTest() {
        MedicoEntity entity = data.get(0);
        MedicoEntity newEntity = medicoPersistence.findByRecord(entity.getName());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getRecord(), newEntity.getRecord());

        newEntity = medicoPersistence.findByRecord(null);
        Assert.assertNull(newEntity);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.parcial1.dtos;

import co.edu.uniandes.csw.parcial1.ejb.MedicoLogic;
import co.edu.uniandes.csw.parcial1.entities.MedicoEntity;
import co.edu.uniandes.csw.parcial1.persistence.MedicoPersistence;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @medico josejbocanegra
 */
public class MedicoDTO implements Serializable {

    private Long id;
    private String name;
    private String secondName;
    private String record;
    private String especiality;
   

    /**
     * Constructor vacio
     */
    public MedicoDTO() {
    }

    /**
     * Crea un objeto MedicoDTO a partir de un objeto MedicoEntity.
     *
     * @param medicoEntity Entidad MedicoEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public MedicoDTO(MedicoEntity medicoEntity) {
        if (medicoEntity != null) {
            this.id = medicoEntity.getId();
            this.name = medicoEntity.getName();
            this.secondName = medicoEntity.getSecondName();
            this.record = medicoEntity.getRecord();
            this.especiality = medicoEntity.getSpecialty();
             
        }
    }

    /**
     * Convierte un objeto MedicoDTO a MedicoEntity.
     *
     * @return Nueva objeto MedicoEntity.
     *
     */
    public MedicoEntity toEntity() {
        MedicoEntity medicoEntity = new MedicoEntity();
        medicoEntity.setId(this.getId());
        medicoEntity.setName(this.getName());
        medicoEntity.setSecondName(this.getSecondName());
        medicoEntity.setRecord(this.getRecord());
        medicoEntity.setSpecialty(this.getEspeciality());
        return medicoEntity;
    }

    /**
     * Obtiene el atributo id.
     *
     * @return atributo id.
     *
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor del atributo id.
     *
     * @param id nuevo valor del atributo
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el atributo name.
     *
     * @return atributo name.
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el valor del atributo name.
     *
     * @param name nuevo valor del atributo
     *
     */
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the secondName
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * @param secondName the secondName to set
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * @return the record
     */
    public String getRecord() {
        return record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(String record) {
        this.record = record;
    }

    /**
     * @return the especiality
     */
    public String getEspeciality() {
        return especiality;
    }

    /**
     * @param especiality the especiality to set
     */
    public void setEspeciality(String especiality) {
        this.especiality = especiality;
    }
}

 

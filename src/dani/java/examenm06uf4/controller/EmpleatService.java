/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dani.java.examenm06uf4.controller;

import dani.java.examenm06uf4.model.Empleat;
import java.util.List;

/**
 * Servei per cridar als mètodes d'administració de la base de dades
 * @author dani
 */
public class EmpleatService implements DAO<Empleat> {
    
    private EmpleatDAO empleatDao;

    public EmpleatService(EmpleatDAO empleatDao) {
        this.empleatDao = empleatDao;
    }
    
    public Empleat get(String t) {
        return empleatDao.get(t);
    }

    @Override
    public List<Empleat> getAll() {
        return empleatDao.getAll();
    }

    @Override
    public void insert(Empleat t) {
        empleatDao.insert(t);
    }

    @Override
    public void update(Empleat t) {
        empleatDao.update(t);
    }

    @Override
    public void delete(Empleat t) {
        empleatDao.delete(t);
    }
    
    public void closeService() {
        empleatDao.closeRegistry();
    }
    
}

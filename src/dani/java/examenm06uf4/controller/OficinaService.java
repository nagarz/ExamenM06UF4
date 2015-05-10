/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dani.java.examenm06uf4.controller;

import dani.java.examenm06uf4.model.Oficina;
import java.util.List;

/**
 * Servei per cridar als mètodes d'administració de la base de dades
 * @author dani
 */
public class OficinaService implements DAO<Oficina> {
    
    private OficinaDAO oficinaDAO;

    public OficinaService(OficinaDAO oficinaDAO) {
        this.oficinaDAO = oficinaDAO;
    }

    @Override
    public List<Oficina> getAll() {
        return oficinaDAO.getAll();
    }

    @Override
    public void insert(Oficina t) {
        oficinaDAO.insert(t);
    }

    @Override
    public void update(Oficina t) {
        oficinaDAO.update(t);
    }

    @Override
    public void delete(Oficina t) {
        oficinaDAO.delete(t);
    }
    
    public void closeService() {
        oficinaDAO.closeRegistry();
    }
    
}

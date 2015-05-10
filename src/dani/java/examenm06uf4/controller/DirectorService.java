/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dani.java.examenm06uf4.controller;

import dani.java.examenm06uf4.model.Director;
import java.util.List;

/**
 * Servei per cridar als mètodes d'administració de la base de dades
 * @author dani
 */
public class DirectorService implements DAO<Director> {
    
    private DirectorDAO directorDAO;

    public DirectorService(DirectorDAO directorDAO) {
        this.directorDAO = directorDAO;
    }

    @Override
    public List<Director> getAll() {
        return directorDAO.getAll();
    }

    @Override
    public void insert(Director t) {
        directorDAO.insert(t);
    }

    @Override
    public void update(Director t) {
        directorDAO.update(t);
    }

    @Override
    public void delete(Director t) {
        directorDAO.delete(t);
    }
    
    public void closeService() {
        directorDAO.closeRegistry();
    }
    
}

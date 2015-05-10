/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dani.java.examenm06uf4.controller;

import dani.java.examenm06uf4.model.Empresa;
import java.util.List;

/**
 * Servei per cridar als mètodes d'administració de la base de dades
 * @author dani
 */
public class EmpresaService implements DAO<Empresa> {
    
    private EmpresaDAO empresaDAO;

    public EmpresaService(EmpresaDAO empresaDAO) {
        this.empresaDAO = empresaDAO;
    }

    @Override
    public List<Empresa> getAll() {
        return empresaDAO.getAll();
    }

    @Override
    public void insert(Empresa t) {
        empresaDAO.insert(t);
    }

    @Override
    public void update(Empresa t) {
        empresaDAO.update(t);
    }

    @Override
    public void delete(Empresa t) {
        empresaDAO.delete(t);
    }
    
    public void closeService() {
        empresaDAO.closeRegistry();
    }
    
}

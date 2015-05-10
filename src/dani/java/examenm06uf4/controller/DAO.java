/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dani.java.examenm06uf4.controller;

import java.util.List;

/**
 *  Interficié DAO amb els mètodes per administrar la base de dades
 * @author dani
 */
public interface DAO<T> {
    
    List<T> getAll();
    void insert(T t);
    void update(T t);
    void delete(T t);
    
}

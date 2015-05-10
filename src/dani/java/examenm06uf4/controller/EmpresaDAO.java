/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dani.java.examenm06uf4.controller;

import dani.java.examenm06uf4.model.Empresa;
import dani.java.examenm06uf4.view.AppViews;
import dani.java.examenm06uf4.view.Main;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author dani
 */
public class EmpresaDAO implements DAO<Empresa> {
    
    private final SessionFactory sessionFactory;
    
    private ServiceRegistry serviceRegistry;
    
    private final Session session;

    public static final Logger logger = Logger.getLogger(Main.class.getName());

    private FileHandler fh;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private Date date = new Date();
    
    public EmpresaDAO (SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            //loads default hibernate.cfg.xml from classpath
            Configuration configuration = new Configuration().configure();
            serviceRegistry =
                    new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            //builds a session factory from the service registry
            this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } else {
            this.sessionFactory = sessionFactory;
        }
        session = this.sessionFactory.openSession();
        try {
            fh = new FileHandler("log.txt", true);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException | SecurityException ex) {
            EmpresaDAO.logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public List<Empresa> getAll() {
        Transaction tx = session.beginTransaction();
        List<Empresa> empreses = session.createQuery("from Empresa").list();
        logger.log(Level.INFO, "in getAll() method{}", empreses.size());
        tx.commit();
        return empreses;
    }

    @Override
    public void insert(Empresa t) {
        logger.log(Level.INFO, "in insert(Empresa) method{}", t.toString());
        AppViews.area.appendText(sdf.format(date) + " " +Empresa.class.getName() + "\n"+ Level.INFO + ": in insert(Empresa) method{}" + t.toString() + "\n");
        Transaction tx = session.beginTransaction();
        session.save(t);
        tx.commit();
    }

    @Override
    public void update(Empresa t) {
        logger.log(Level.INFO, "in update(Empresa) method{}", t.toString());
        AppViews.area.appendText(sdf.format(date) + " " + Empresa.class.getName() + "\n"+ Level.INFO + ": in update(Empresa) method{}" + t.toString() + "\n");
        Transaction tx = session.beginTransaction();
        session.update(t);
        tx.commit();
    }

    @Override
    public void delete(Empresa t) {
        logger.log(Level.INFO, "in delete(Empresa) method{}", t.toString());
        AppViews.area.appendText(sdf.format(date) + " " + Empresa.class.getName() + "\n"+ Level.INFO + ": in delete(Empresa) method{}" + t.toString() + "\n");
        Transaction tx = session.beginTransaction();
        session.delete(t);
        tx.commit();
    }
    
    void closeRegistry() {
        if (serviceRegistry != null ) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }
    
    public Session getSession() {
        return this.session;
    }
    
}

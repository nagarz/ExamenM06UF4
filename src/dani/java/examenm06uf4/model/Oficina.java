package dani.java.examenm06uf4.model;
// Generated May 10, 2015 1:47:47 PM by Hibernate Tools 4.3.1



/**
 * Oficina generated by hbm2java
 */
public class Oficina  implements java.io.Serializable {


     private Integer id;
     private Empleat empleat;
     private Empresa empresa;
     private String direccio;
     private String ciutat;
     private String provincia;

    public Oficina() {
    }

    public Oficina(Empleat empleat, Empresa empresa, String direccio, String ciutat, String provincia) {
       this.empleat = empleat;
       this.empresa = empresa;
       this.direccio = direccio;
       this.ciutat = ciutat;
       this.provincia = provincia;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Empleat getEmpleat() {
        return this.empleat;
    }
    
    public void setEmpleat(Empleat empleat) {
        this.empleat = empleat;
    }
    public Empresa getEmpresa() {
        return this.empresa;
    }
    
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    public String getDireccio() {
        return this.direccio;
    }
    
    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }
    public String getCiutat() {
        return this.ciutat;
    }
    
    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }
    public String getProvincia() {
        return this.provincia;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }




}



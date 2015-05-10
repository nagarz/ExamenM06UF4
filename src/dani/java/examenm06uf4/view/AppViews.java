/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dani.java.examenm06uf4.view;

import dani.java.examenm06uf4.controller.DirectorDAO;
import dani.java.examenm06uf4.controller.DirectorService;
import dani.java.examenm06uf4.controller.EmpleatDAO;
import dani.java.examenm06uf4.controller.EmpleatService;
import dani.java.examenm06uf4.controller.EmpresaDAO;
import dani.java.examenm06uf4.controller.EmpresaService;
import dani.java.examenm06uf4.controller.OficinaDAO;
import dani.java.examenm06uf4.controller.OficinaService;
import dani.java.examenm06uf4.model.Director;
import dani.java.examenm06uf4.model.Empleat;
import dani.java.examenm06uf4.model.Empresa;
import dani.java.examenm06uf4.model.Oficina;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author dani
 */
public class AppViews {
    
    private static ObservableList<Empresa> empresaObList;
    private static ObservableList<Empleat> empleatObList;
    private static ObservableList<Director> directorObList;
    private static ObservableList<Oficina> oficinaObList;
    private static List<Empresa> empresaList;
    private static List<Empleat> empleatList;
    private static List<Oficina> oficinaList;
    private static Director director;
    private static Oficina oficina;
    private static TableView tableView;
    private static TableView dirTable;
    private static TableView oficinaTable;
    public static TextArea area;
    private static Empresa empresa;
    private static Empleat empleat;
    private static EmpresaService empresaDao;
    private static EmpleatService empleatDao;
    private static OficinaService oficinaDao;
    private static DirectorService directorDao;
    private static Boolean showLog = false;
    
    public static Scene mainScene() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(25);
	gridPane.setHgap(25);
        
        VBox vbox = new VBox(20);
        gridPane.add(vbox, 2, 2);
        
        empleatObList = FXCollections.observableArrayList();
        empresaObList = FXCollections.observableArrayList();
        
        empresaTable();
        gridPane.add(tableView, 0, 2);
        tableView.setMinSize(450, 200);
        tableView.setMaxSize(450, 200);
        
        Button consultaButton = new Button("Consultar");
        consultaButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (empresa != null) {
                    consultaEmpresa();
                }
            }
        });
       
        Button empresaButton = new Button("Empreses");
        empresaButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                empresaTable();
                gridPane.add(tableView, 0, 2);
                consultaButton.setVisible(true);
            }
        });
        gridPane.add(empresaButton, 0, 1);
        
        Button empleatButton = new Button("Empleats");
        empleatButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    empleatTable();
                    gridPane.add(tableView, 0, 2);
                    consultaButton.setVisible(false);
                } catch (Exception e) {
                    errorDialog("No hi ha empleats a la base de dades");
                }
            }
        });
        gridPane.add(empleatButton, 1, 1);
        
        Button eliminarButton = new Button("Eliminar");
        eliminarButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (empleat != null) {
                    try {
                        empleatDao.delete(empleat);
                        empleatList.remove(empleat);
                        empleatObList.remove(empleat);
                    } catch (Exception e) {
                        errorDialog("No pots eliminar un empleat\nresponsable d'una oficina.");
                    }
                }
                if (empresa != null) {
                    try {
                        empresaDao.delete(empresa);
                        empresaList.remove(empresa);
                        empresaObList.remove(empresa);
                    } catch (Exception e) {
                        errorDialog("No pots eliminar una empresa\namb empleats a la plantilla.");
                    }
                }
            }
        });
        
        Button sortirButton = new Button("Sortir");
        sortirButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (empleatDao != null) {
                    empleatDao.closeService();
                }
                if (empresaDao != null) {
                    empresaDao.closeService();
                }
                Main.stage.close();
            }
        });
        gridPane.add(sortirButton, 2, 3);
        
        vbox.getChildren().addAll(consultaButton, eliminarButton);
        
        area = new TextArea("");
        area.setMinHeight(200);
        area.setMaxHeight(200);
        area.setVisible(false);
        area.setEditable(false);
        GridPane.setColumnSpan(area, 2);
        gridPane.add(area, 0, 4);        
        
        Button logButton = new Button("Log");
        logButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (showLog == false) {
                    Main.stage.setMinHeight(700);
                    Main.stage.setMaxHeight(700);
                    area.setVisible(true);
                    showLog = true;
                } else {
                    Main.stage.setMinHeight(450);
                    Main.stage.setMaxHeight(450);
                    area.setVisible(false);
                    showLog = false;
                }
            }
        });
        logButton.setMinWidth(80);
        gridPane.add(logButton, 1, 3);
        

        Scene scene = new Scene(gridPane, 600, 450);
        return scene;
    }
    
    private static void consultaEmpresa() {
        final Stage consultaStage = new Stage();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(25);
	gridPane.setHgap(25);
        
        directorDao = new DirectorService(new DirectorDAO(null));
        oficinaDao = new OficinaService(new OficinaDAO(null));
        
        Label dirLabel = new Label("Director");
        gridPane.add(dirLabel, 0, 0);
        
        directorObList = FXCollections.observableArrayList();
        directorObList.clear();
        directorObList.add(director);
        dirTable = new TableView(directorObList);
        TableColumn<Director, String> dniColumn = new TableColumn<>("DNI");
        TableColumn<Director, String> nomColumn = new TableColumn<>("Nom");
        TableColumn<Director, String> cognomColumn = new TableColumn<>("Cognoms");
        TableColumn<Director, String> telColumn = new TableColumn<>("Telefon");
        TableColumn<Director, String> emailColumn = new TableColumn<>("Email");
        TableColumn<Director, Double> salariColumn = new TableColumn<>("Salari");
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("Dni"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        cognomColumn.setCellValueFactory(new PropertyValueFactory<>("Cognoms"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("Telefon"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        salariColumn.setCellValueFactory(new PropertyValueFactory<>("Salari"));
        dirTable.getColumns().addAll(dniColumn, nomColumn, cognomColumn, telColumn, emailColumn, salariColumn);
        dirTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent x) {
                if (x.isPrimaryButtonDown()) {
                    director = (Director) dirTable.getSelectionModel().getSelectedItem();
                }
            }
        });
        dirTable.setMinSize(450, 75);
        dirTable.setMaxSize(450, 75);
        gridPane.add(dirTable, 0, 1);
        
        Button editDirButton = new Button("Modificar");
        editDirButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (director != null) {
                    modificarDirector();
                }
            }
        });
        gridPane.add(editDirButton, 1, 1);

        Label ofiLabel = new Label("Oficines");
        gridPane.add(ofiLabel, 0, 2);
        
        oficinaObList = FXCollections.observableArrayList();
        oficinaObList.clear();
        oficinaList = oficinaDao.getAll();
        for (Oficina o:oficinaList) {
            if (o.getEmpresa().getCif().equals(empresa.getCif())) {
                oficinaObList.add(o);
            }
            System.out.println(o);
        }
        oficinaTable = new TableView(oficinaObList);
        TableColumn<Oficina, String> direccioColumn = new TableColumn<>("Direcció");
        TableColumn<Oficina, String> ciutatColumn = new TableColumn<>("Ciutat");
        TableColumn<Oficina, String> provinciaColumn = new TableColumn<>("Provincia");
        TableColumn<Oficina, String> dniResponsableColumn = new TableColumn<>("DNI Responsable");
        direccioColumn.setCellValueFactory(new PropertyValueFactory<>("Direccio"));
        ciutatColumn.setCellValueFactory(new PropertyValueFactory<>("Ciutat"));
        provinciaColumn.setCellValueFactory(new PropertyValueFactory<>("Provincia"));
        dniResponsableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Oficina, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Oficina, String> p) {
                if (p.getValue().getEmpleat()!= null) {
                    return new SimpleObjectProperty(p.getValue().getEmpleat().getDni());
                } else {
                    return new SimpleObjectProperty("Sense Objecte");
                }
            }
        });
        oficinaTable.getColumns().addAll(direccioColumn, ciutatColumn, provinciaColumn, dniResponsableColumn);
        oficinaTable.setMinSize(450, 150);
        oficinaTable.setMaxSize(450, 150);
        oficinaTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent x) {
                if (x.isPrimaryButtonDown()) {
                    oficina = (Oficina) oficinaTable.getSelectionModel().getSelectedItem();
                }
            }
        });
        gridPane.add(oficinaTable, 0, 3);
        
        VBox vBox = new VBox(20);
        gridPane.add(vBox, 1, 3);
        
        Button addButton = new Button("Afegir");
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                afegirOficina();
            }
        });
        Button editButton = new Button("Modificar");
        editButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    modificarOficina();
                } catch (Exception e) {
                    errorDialog("Selecciona una oficina.");
                }
            }
        });
        Button delButton = new Button("Esborrar");
        delButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    oficinaDao.delete(oficina);
                    oficinaObList.remove(oficina);
                } catch (Exception e) {
                    errorDialog("Selecciona una oficina.");
                }
            }
        });
        
        vBox.getChildren().addAll(addButton, editButton, delButton);
        
        Button exitButton = new Button("Sortir");
        exitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (oficinaDao != null) {
                    oficinaDao.closeService();
                }
                if (directorDao != null) {
                    directorDao.closeService();
                }
                consultaStage.close();
            }
        });
        gridPane.add(exitButton, 1, 4);
        
        
        Scene scene = new Scene (gridPane, 600, 450);
        consultaStage.initModality(Modality.WINDOW_MODAL);
        consultaStage.initOwner(Main.stage);
        consultaStage.setScene(scene);
        consultaStage.show();
    }
    
    public static void errorDialog(String msg) {
        final Stage dialogStage = new Stage();
        Text text = new Text(msg);
        text.setLineSpacing(5);
        VBox vbox = new VBox(20);
        Button acceptButton = new Button("Acceptar");
        acceptButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                dialogStage.close();
            }
        });
        vbox.getChildren().addAll(text, acceptButton);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 270, 125, Color.web("eee"));
        dialogStage.initOwner(Main.stage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
    
    private static void empleatTable() {
        empleatDao = new EmpleatService(new EmpleatDAO(null));
        empleatList = empleatDao.getAll();
        System.out.println(empleatList.get(1));
        empleatObList.clear();
        for (Empleat e : empleatList) {
            empleatObList.add(e);
            tableView = new TableView<>(empleatObList);

            GridPane.setColumnSpan(tableView, 2);
        }
        TableColumn<?, String> dniColumn = new TableColumn<>("DNI");
        TableColumn<?, String> nomColumn = new TableColumn<>("Nom");
        TableColumn<?, String> cognomColumn = new TableColumn<>("Cognoms");
        TableColumn<?, String> telColumn = new TableColumn<>("Telefon");
        TableColumn<?, String> emailColumn = new TableColumn<>("Email");

        dniColumn.setCellValueFactory(new PropertyValueFactory<>("Dni"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        cognomColumn.setCellValueFactory(new PropertyValueFactory<>("Cognoms"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("Telefon"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));

        tableView.getColumns().addAll(dniColumn, nomColumn, cognomColumn, telColumn, emailColumn);

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent x) {
                if (x.isPrimaryButtonDown()) {
                    empresa = null;
                    director = null;
                    empleat = (Empleat) tableView.getSelectionModel().getSelectedItem();
                }
            }
        });
    }
    
    private static void empresaTable() {
        empresaDao = new EmpresaService(new EmpresaDAO(null));
        empresaList = empresaDao.getAll();
        empresaObList.clear();
        for (Empresa e : empresaList) {
            empresaObList.add(e);
            tableView = new TableView<>(empresaObList);
            
            GridPane.setColumnSpan(tableView, 2);
        }
        TableColumn<?, String> cifColumn = new TableColumn<>("CIF");
        TableColumn<?, String> nomColumn = new TableColumn<>("Nom");
        TableColumn<?, String> ongColumn = new TableColumn<>("ONG");
        TableColumn<?, String> telColumn = new TableColumn<>("Telefon");
        TableColumn<?, String> dataColumn = new TableColumn<>("Establiment");

        cifColumn.setCellValueFactory(new PropertyValueFactory<>("Cif"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        ongColumn.setCellValueFactory(new PropertyValueFactory<>("Ong"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("Telefon"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("Establiment"));

        tableView.getColumns().addAll(cifColumn, nomColumn, ongColumn, telColumn, dataColumn);

        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent x) {
                if (x.isPrimaryButtonDown()) {
                    empleat = null;
                    empresa = (Empresa) tableView.getSelectionModel().getSelectedItem();
                    director = empresa.getDirector();
                }
            }
        });
    }
    
    private static void modificarDirector() {
        Stage modStage = new Stage();
        VBox vbox = new VBox(5);
        Label dniLabel = new Label("DNI");
        final TextField dniField = new TextField();
        dniField.setMaxWidth(200);
        Label nomLabel = new Label("Nom");
        final TextField nomField = new TextField();
        nomField.setMaxWidth(200);
        Label cognomsLabel = new Label("Cognoms");
        final TextField cognomsField = new TextField();
        cognomsField.setMaxWidth(200);
        Label telLabel = new Label("Telefon");
        final TextField telField = new TextField();
        telField.setMaxWidth(200);
        Label mailLabel = new Label("Email");
        final TextField mailField = new TextField();
        mailField.setMaxWidth(200);
        Label salariLabel = new Label("Salari");
        final TextField salariField = new TextField();
        salariField.setMaxWidth(200);
        
        vbox.setAlignment(Pos.CENTER);
        HBox hbox = new HBox(15);
        Button okButton = new Button("Acceptar");
        okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (dniField.getText().length()>0 & nomField.getText().length()>0 & cognomsField.getText().length()>0 & telField.getText().length()>0 & mailField.getText().length()>0 & salariField.getText().length()>0) {
                    director.setEmpresa(empresa);
                    director.setDni(dniField.getText());
                    director.setNom(nomField.getText());
                    director.setCognoms(cognomsField.getText());
                    director.setTelefon(telField.getText());
                    director.setEmail(mailField.getText());
                    director.setSalari(Double.valueOf(salariField.getText()));
                    directorDao.update(director);
                    directorObList.clear();
                    directorObList.add(director);
                    modStage.close();
                }
            }
        });
        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                modStage.close();
            }
        });
        hbox.getChildren().addAll(okButton, cancelButton);
        hbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(dniLabel, dniField, nomLabel, nomField, cognomsLabel, cognomsField, telLabel, telField, mailLabel, mailField, salariLabel, salariField, hbox);
        
        Scene scene = new Scene(vbox, 250, 400);
        modStage.setScene(scene);
        modStage.initModality(Modality.WINDOW_MODAL);
        modStage.initOwner(Main.stage);
        modStage.show();
    }
    
    private static void modificarOficina() {
        Stage modStage = new Stage();
        VBox vbox = new VBox(5);
        Label dniLabel = new Label("DNI Responsable");
        final TextField dniField = new TextField();
        dniField.setMaxWidth(200);
        Label direccioLabel = new Label("Direcció");
        final TextField direccioField = new TextField();
        direccioField.setMaxWidth(200);
        Label ciutatLabel = new Label("Ciutat");
        final TextField ciutatField = new TextField();
        ciutatField.setMaxWidth(200);
        Label provinciaLabel = new Label("Provincia");
        final TextField provinciaField = new TextField();
        provinciaField.setMaxWidth(200);
        vbox.setAlignment(Pos.CENTER);
        
        HBox hbox = new HBox(15);
        Button okButton = new Button("Acceptar");
        okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (dniField.getText().length()>0 & direccioField.getText().length()>0 & ciutatField.getText().length()>0 & provinciaField.getText().length()>0) {
                    oficina.setCiutat(ciutatField.getText());
                    oficina.setProvincia(provinciaField.getText());
                    oficina.setDireccio(direccioField.getText());
                    try {
                        oficina.setEmpleat(empleatDao.get(dniField.getText()));
                    } catch (Exception e) {
                        errorDialog("Aquest DNI no correspon a cap\n empleat a la base de dades.");
                        modStage.close();
                        return;
                    }
                    oficinaDao.update(oficina);
                    oficinaObList.clear();
                    oficinaObList.add(oficina);
                    modStage.close();
                }
            }
        });
        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                modStage.close();
            }
        });
        hbox.getChildren().addAll(okButton, cancelButton);
        hbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(dniLabel, dniField, direccioLabel, direccioField, ciutatLabel, ciutatField, provinciaLabel, provinciaField, hbox);
        
        Scene scene = new Scene(vbox, 250, 400);
        modStage.setScene(scene);
        modStage.initModality(Modality.WINDOW_MODAL);
        modStage.initOwner(Main.stage);
        modStage.show();
    }
    
    private static void afegirOficina() {
        Stage modStage = new Stage();
        VBox vbox = new VBox(20);
        Label dniLabel = new Label("DNI Responsable");
        final TextField dniField = new TextField();
        dniField.setMaxWidth(200);
        Label direccioLabel = new Label("Direcció");
        final TextField direccioField = new TextField();
        direccioField.setMaxWidth(200);
        Label ciutatLabel = new Label("Ciutat");
        final TextField ciutatField = new TextField();
        ciutatField.setMaxWidth(200);
        Label provinciaLabel = new Label("Provincia");
        final TextField provinciaField = new TextField();
        provinciaField.setMaxWidth(200);
        vbox.setAlignment(Pos.CENTER);
        
        HBox hbox = new HBox(15);
        Button okButton = new Button("Acceptar");
        okButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (dniField.getText().length()>0 & direccioField.getText().length()>0 & ciutatField.getText().length()>0 & provinciaField.getText().length()>0) {
                    Oficina newOficina = new Oficina(empleatDao.get(dniField.getText()), empresa, direccioField.getText(), ciutatField.getText(), provinciaField.getText());
                    oficinaDao.insert(newOficina);
                    oficinaObList.add(oficina);
                    modStage.close();
                }
            }
        });
        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                modStage.close();
            }
        });
        hbox.getChildren().addAll(okButton, cancelButton);
        hbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(dniLabel, dniField, direccioLabel, direccioField, ciutatLabel, ciutatField, provinciaLabel, provinciaField, hbox);
        
        Scene scene = new Scene(vbox, 250, 400);
        modStage.setScene(scene);
        modStage.initModality(Modality.WINDOW_MODAL);
        modStage.initOwner(Main.stage);
        modStage.show();
    }
    
}

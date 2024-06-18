package com.example.practicafrancisco;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TablacesurController implements Initializable {


//en esta parte de aqui se van a porner las cosas que se an metido al fxml y se tine que porner que tipo de variable es
// para que funcione


    @FXML
    private Label textocambia;
    @FXML
    private TableColumn<Coche, String> columnaentrega;
    @FXML
    private TableColumn<Coche, String> columnamatricula;
    @FXML
    private TableColumn<Coche, String> columnacoste;
    @FXML
    private TableView<Coche> tablaid;
    @FXML
    private TableColumn<Coche, String> columnasalida;
    @FXML
    private TableColumn<Coche, String> columnatarifa;
    @FXML
    private TableColumn<Coche, String> columnacliente;
    @FXML
    private TableColumn<Coche, String> columnamodelo;
    @FXML
    private RadioButton botonstandard;
    @FXML
    private RadioButton botonoferta;
    @FXML
    private RadioButton botonlargaduracion;
    @FXML
    private Button botonañadirtabla;
    @FXML
    private Button botonsalir;
    @FXML
    private DatePicker salida;
    @FXML
    private ChoiceBox<Cliente> cliente;
    @FXML
    private DatePicker entrada;
    @FXML
    private TextField matricula;
    @FXML
    private Label coste;
    @FXML
    private ComboBox<String> modelo;
    @FXML
    private ToggleGroup tarifa;


    private ArrayList<Coche> coches;
    @FXML
    private Button actualizar;
    @FXML
    private Button VerReporte;
    @FXML
    private Button DescargarReprote;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //aqui estamos creados el cliente y la plantilla en un arraylist y seguido estamos cleando los datos del cliente

        var clientes = new ArrayList<Cliente>();
        Cliente santiago = new Cliente();
        santiago.setCorreo("correo@correo.com");
        santiago.setNombre("Santiago");
        clientes.add(santiago);

        Cliente jorge = new Cliente();
        jorge.setCorreo("correo@correo.com");
        jorge.setNombre("Jorge");
        clientes.add(jorge);

        //aqui estamos creando os tres ejemplo que saldran en la tabla y todos los datos que necesita (hay que ponerlos en orden)


        Coche coche1 = new Coche(1, new Date(), "ABC123", new Date(), 100, "standard", "citroen", santiago);
        Coche coche2 = new Coche(2, new Date(), "DEF456", new Date(), 150, "oferta", "porch", jorge);
        Coche coche3 = new Coche(3, new Date(), "GHI789", new Date(), 200, "largaduracion", "ferrari", santiago);


        coches = new ArrayList<Coche>();
        coches.add(coche1);
        coches.add(coche2);
        coches.add(coche3);

        tablaid.getItems().addAll(coches);


        modelo.getItems().addAll("citroen", "porch", "ferrari");


        //convierte el cliente en string para que se pueda ver y no salga un churro

        cliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                if (cliente != null) return cliente.getNombre().toUpperCase();
                return null;
            }

            @Override
            public Cliente fromString(String s) {
                return null;
            }
        });

        //los modelos que llaman a los respectivos tablas hay que poner esta parte incluida la de salida para que lo que va
        //bajo funcione

        cliente.getItems().addAll(santiago, jorge);
        cliente.setValue(clientes.get(0));
        modelo.setValue("citroen");
        entrada.setValue(LocalDate.now());
        salida.setValue(LocalDate.now());


        //lo que nos calcula el tiempo que ha estado el coche y te da la diferencia

        salida.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                System.out.println("ahora: " + t1);
                System.out.println("antes: " + localDate);
                Period dif = Period.between(entrada.getValue(), t1);
                System.out.println(dif.getDays());
                                               }
                                           }
        );



        //esta parte es para que se rellene la tabla (te da todos los valores de las propiedades)



        columnaentrega.setCellValueFactory((fila) -> {
            Date entrada = fila.getValue().getEntrega();
            return new SimpleStringProperty(entrada.toString());
        });

        columnasalida.setCellValueFactory((fila) -> {
            Date salida = fila.getValue().getSalida();
            return new SimpleStringProperty(salida.toString());
        });

        columnamodelo.setCellValueFactory((fila) -> {
            String modelo = fila.getValue().getModelo();
            return new SimpleStringProperty(modelo);
        });
        columnamatricula.setCellValueFactory((fila) -> {
            String matricula = fila.getValue().getMatricula();
            return new SimpleStringProperty(matricula);
        });

        columnacoste.setCellValueFactory((fila) -> {
            Integer coste = fila.getValue().getCoste();
            return new SimpleStringProperty(coste.toString());
        });

        columnatarifa.setCellValueFactory((fila) -> {
            String tarifa = fila.getValue().getTarifa();
            return new SimpleStringProperty(tarifa);
        });

        columnacliente.setCellValueFactory((fila) -> {
            Cliente cliente = fila.getValue().getCliente();
            return new SimpleStringProperty(cliente.getNombre());
        });




     //que al pulsar en un ejmlo de la tabla se enseñe en la parte de la izquierda y no dejam modificarlas

        tablaid.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Coche>() {
            @Override
            public void changed(ObservableValue<? extends Coche> observableValue, Coche coche, Coche t1) {
                if (t1 != null) {
                    // System.out.println(t1);

                    matricula.setText(t1.getMatricula());
                    modelo.setValue(t1.getModelo());
                    cliente.setValue(t1.getCliente());
                    entrada.setValue(t1.getEntrega().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    salida.setValue(t1.getSalida().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                    switch (t1.getTarifa()) {
                        case "standard":
                            botonstandard.setSelected(true);
                            break;
                        case "oferta":
                            botonoferta.setSelected(true);
                            break;
                        case "largaduracion":
                            botonlargaduracion.setSelected(true);
                            break;
                    }


                    Integer pos = tablaid.getSelectionModel().getSelectedIndex();
                    System.out.println(coches.get(pos));
                }
            }
        });


        System.out.println(tablaid.getSelectionModel().getSelectedIndex());

    }


    //esta funcion te añade un nuevo ejemplo en la tabla metiendo los datos en la izquierda

    @FXML
    public void añadir(ActionEvent actionEvent) {
        String matriculaText = matricula.getText();
        String modeloSeleccionado = modelo.getValue();
        Cliente clienteSeleccionado = cliente.getValue();
        LocalDate fechaEntrada = entrada.getValue();
        LocalDate fechaSalida = salida.getValue();

        if (matriculaText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos.");
            alert.showAndWait();
            return;
        }

        String tarifaSeleccionada = "";
        if (botonstandard.isSelected()) {
            tarifaSeleccionada = "Standard";
        } else if (botonoferta.isSelected()) {
            tarifaSeleccionada = "Oferta";
        } else if (botonlargaduracion.isSelected()) {
            tarifaSeleccionada = "Larga duración";
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione una tarifa.");
            alert.showAndWait();
            return;
        }

        Integer costeCalculado = calcularCoste(fechaEntrada, fechaSalida, tarifaSeleccionada);

        Coche nuevoCoche = new Coche();
        nuevoCoche.setMatricula(matriculaText);
        nuevoCoche.setModelo(modeloSeleccionado);
        nuevoCoche.setCliente(clienteSeleccionado);
        nuevoCoche.setEntrega(Date.from(fechaEntrada.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        nuevoCoche.setSalida(Date.from(fechaSalida.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        nuevoCoche.setCoste(costeCalculado);
        nuevoCoche.setTarifa(tarifaSeleccionada);

        coches.add(nuevoCoche);
        tablaid.getItems().clear();
        tablaid.getItems().addAll(coches);

    }



    //esta funcion es simple SALES
    @FXML
    public void salir(ActionEvent actionEvent) {
        Stage stage = (Stage) botonsalir.getScene().getWindow();
        stage.close();
    }


    //esta funcion te hace el calculo de la del tiempo que se a quedado el coche

    private Integer calcularCoste(LocalDate fechaEntrada, LocalDate fechaSalida, String tarifa) {
        long dias = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
        int tarifaDiaria = 0;
        switch (tarifa) {
            case "Standard":
                tarifaDiaria = 8;
                break;
            case "Oferta":
                tarifaDiaria = 6;
                break;
            case "Larga duración":
                tarifaDiaria = 2;
                break;

        }

        return (int) dias * tarifaDiaria;
    }



    //esta funcion hace que al darle al boton actualizar se cambien los datos

    @FXML
    public void actualizar(ActionEvent actionEvent) {

        if (tablaid.getSelectionModel().getSelectedIndex() < 0) {
            return;
        } else {
            Integer pos = tablaid.getSelectionModel().getSelectedIndex();
            coches.get(pos).setMatricula(matricula.getText());
            coches.get(pos).setModelo(modelo.getValue());
            coches.get(pos).setCliente(cliente.getValue());


            coches.get(pos).setTarifa(tarifa.selectedToggleProperty().getName());


            coches.get(pos).setEntrega(java.util.Date.from(entrada.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            coches.get(pos).setSalida(java.util.Date.from(salida.getValue().atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));

            tablaid.getItems().clear();
            tablaid.getItems().addAll(coches);
        }

    }



    //enseña el reporte

    @FXML
    public void enseñar(ActionEvent actionEvent) {
        try {
            (new Report()).generateReport();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }


    //descarga el reporte


    @FXML
    public void descargar(ActionEvent actionEvent) {
        try {
            (new Report()).descargarreporte();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }





}










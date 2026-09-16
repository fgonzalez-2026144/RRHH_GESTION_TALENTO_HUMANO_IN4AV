package org.gestiontalentoshumanos.system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.gestiontalentoshumanos.system.ClasePrincipal;

public class NominaController {

    @FXML
    private TableView<Colaborador> tablaColaboradores;

    @FXML
    private TableColumn<Colaborador, String> colId;

    @FXML
    private TableColumn<Colaborador, String> colNombre;

    @FXML
    private TableColumn<Colaborador, String> colPuesto;

    @FXML
    private TableColumn<Colaborador, String> colDepartamento;

    @FXML
    private TableColumn<Colaborador, String> colFecha;

    @FXML
    private TableColumn<Colaborador, Double> colSalario;

    @FXML
    private TableColumn<Colaborador, Void> colAccion;

    @FXML
    private Label lblTotalColaboradores;

    @FXML
    private Label lblTotalSalarios;

    @FXML
    private Label lblEstado;

    @FXML
    private TextField txtBuscar;


    private final ObservableList<Colaborador> lista =
            FXCollections.observableArrayList();


    @FXML
    public void initialize() {

        configurarColumnas();

        cargarDatos();

        configurarBotonEditar();

        actualizarTotales();
    }


    private void configurarColumnas() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colPuesto.setCellValueFactory(
                new PropertyValueFactory<>("puesto")
        );

        colDepartamento.setCellValueFactory(
                new PropertyValueFactory<>("departamento")
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fecha")
        );

        colSalario.setCellValueFactory(
                new PropertyValueFactory<>("salario")
        );
    }


    private void cargarDatos() {

        lista.add(new Colaborador(
                "1001",
                "Ana López García",
                "Analista",
                "Finanzas",
                "12/01/2022",
                8000
        ));

        lista.add(new Colaborador(
                "1002",
                "Carlos Pérez",
                "Desarrollador",
                "TI",
                "15/03/2022",
                10000
        ));

        lista.add(new Colaborador(
                "1003",
                "Sofía Ramírez",
                "Diseñadora",
                "Marketing",
                "20/06/2022",
                9000
        ));

        lista.add(new Colaborador(
                "1004",
                "Luis Martínez",
                "Asistente",
                "RRHH",
                "10/09/2022",
                7500
        ));

        tablaColaboradores.setItems(lista);
    }


    private void configurarBotonEditar() {

        colAccion.setCellFactory(param -> new TableCell<>() {

            private final Button btnEditar =
                    new Button("Editar salario");

            {
                btnEditar.setStyle(
                        "-fx-background-color: #2878d4;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;"
                );

                btnEditar.setOnAction(event -> {

                    Colaborador colaborador =
                            getTableView()
                                    .getItems()
                                    .get(getIndex());

                    editarSalario(colaborador);
                });
            }


            @Override
            protected void updateItem(
                    Void item,
                    boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEditar);
                }
            }
        });
    }


    private void editarSalario(
            Colaborador colaborador) {

        TextInputDialog dialog =
                new TextInputDialog(
                        String.valueOf(
                                colaborador.getSalario()
                        )
                );

        dialog.setTitle("Editar salario");

        dialog.setHeaderText(
                "Modificar salario de "
                        + colaborador.getNombre()
        );

        dialog.setContentText(
                "Nuevo salario:"
        );


        dialog.showAndWait().ifPresent(
                resultado -> {

                    try {

                        double nuevoSalario =
                                Double.parseDouble(resultado);

                        if (nuevoSalario <= 0) {

                            mostrarMensaje(
                                    "El salario debe ser mayor que 0."
                            );

                            return;
                        }

                        colaborador.setSalario(
                                nuevoSalario
                        );

                        tablaColaboradores.refresh();

                        actualizarTotales();

                    } catch (NumberFormatException e) {

                        mostrarMensaje(
                                "Ingrese solamente números."
                        );
                    }
                }
        );
    }


    private void actualizarTotales() {

        double total = 0;

        for (Colaborador c : lista) {
            total += c.getSalario();
        }

        lblTotalColaboradores.setText(
                String.valueOf(lista.size())
        );

        lblTotalSalarios.setText(
                String.format("Q %.2f", total)
        );

        lblEstado.setText("Pendiente");
    }


    @FXML
    private void actualizarTabla() {

        tablaColaboradores.refresh();

        actualizarTotales();
    }


    @FXML
    private void buscarColaborador() {

        String texto =
                txtBuscar.getText().toLowerCase();

        if (texto.isEmpty()) {

            tablaColaboradores.setItems(lista);

            return;
        }


        ObservableList<Colaborador> resultados =
                FXCollections.observableArrayList();


        for (Colaborador c : lista) {

            if (
                    c.getId().toLowerCase().contains(texto)
                    ||
                    c.getNombre().toLowerCase().contains(texto)
                    ||
                    c.getPuesto().toLowerCase().contains(texto)
                    ||
                    c.getDepartamento().toLowerCase().contains(texto)
            ) {

                resultados.add(c);
            }
        }

        tablaColaboradores.setItems(resultados);
    }


    private void mostrarMensaje(String mensaje) {

        Alert alerta =
                new Alert(Alert.AlertType.WARNING);

        alerta.setTitle("Aviso");

        alerta.setHeaderText(null);

        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }


    @FXML
    private void irInicio() {

        ClasePrincipal.cambiarVista("ColaboradoresView.fxml");
    }


    @FXML
    private void irColaboradores() {

        ClasePrincipal.cambiarVista("ColaboradoresView.fxml");
    }


    @FXML
    private void irReportes() {

        Alert aviso = new Alert(Alert.AlertType.INFORMATION);

        aviso.setTitle("Reportes");
        aviso.setHeaderText(null);
        aviso.setContentText("La vista de Reportes aun no esta creada.");

        aviso.showAndWait();
    }


    @FXML
    private void cerrarSesion() {

        Stage stage =
                (Stage) tablaColaboradores
                        .getScene()
                        .getWindow();

        stage.close();
    }
}

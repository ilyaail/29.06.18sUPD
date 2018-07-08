package sample;


import javafx.event.EventHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable{
    @FXML
    TableView<ModelTable> table;
    @FXML
    TableColumn<ModelTable, String> col_id;
    @FXML
    TableColumn<ModelTable, String> col_pile;
    @FXML
    TableColumn<ModelTable, String>  col_amount;
    @FXML
    RadioButton rb1;
    @FXML
    RadioButton rb2;
    @FXML
    Pane overlay = new Pane();
    @FXML
    Button buttonAdd;
    @FXML
    Button buttonAdd1;
    @FXML
    Button buttonAdd2;
    @FXML
    Button buttonAdd3;
    @FXML
    Button buttonAdd4;




    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //DB

            try {
                Connection con = DBConn.getConnection();
                ResultSet rs = con.createStatement().executeQuery("select * from piles");
                while (rs.next()) {
                    oblist.add(new ModelTable(rs.getString("num_pile"), rs.getString("gruz"), rs.getInt("amount")));
                }
            }catch (SQLException ex){
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_pile.setCellValueFactory(new PropertyValueFactory<>("pile"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        table.setItems(oblist);

        draw();
        rbuttons();

        images();

    }


    // creating images


    private void images() {
        Image ImageOk = new Image(getClass().getResourceAsStream("css/img/tree.png"));
        Image ImageOk1 = new Image(getClass().getResourceAsStream("css/img/buildings.png"));
        Image ImageOk2 = new Image(getClass().getResourceAsStream("css/img/elevator.png"));
        Image ImageOk3 = new Image(getClass().getResourceAsStream("css/img/e1.png"));

        buttonAdd1.graphicProperty().setValue(new ImageView(ImageOk));
        buttonAdd2.graphicProperty().setValue(new ImageView(ImageOk1));
        buttonAdd3.graphicProperty().setValue(new ImageView(ImageOk2));
        buttonAdd4.graphicProperty().setValue(new ImageView(ImageOk3));
    }


    private void rbuttons() {
        final ToggleGroup group = new ToggleGroup();

        rb1.setText("Норма");
        rb1.setToggleGroup(group);
        rb2.setText("Брак");
        rb2.setToggleGroup(group);
    }


    //drawing a stack


    private void draw() {


    //button actions


        buttonAdd.setOnAction(e -> {

            ModelTable selectedItem = table.getSelectionModel().getSelectedItem();

            if(selectedItem.amount>0) {
                Line line = new Line(40, 40, 155, 40);
                if (rb1.isSelected()) {
                    line.setStroke(Color.rgb(0, 128, 0));
                } else {
                    line.setStroke(Color.rgb(173, 50, 39));
                }
                line.setStrokeWidth(55);

                overlay.getChildren().addAll(line);
                makedragable(line);

                Tooltip tooltip = new Tooltip(String.valueOf("ID " + selectedItem.id + ": " + selectedItem.pile));
                Tooltip.install(line, tooltip);

                selectedItem.amount=selectedItem.amount-1;
                table.refresh();
            }
            else {
                ModalWindow.newWindow("Stack");
            }
        });
        buttonAdd1.setOnAction(e-> {
            Image imageTree = new Image(getClass().getResourceAsStream("css/img/treesmall.png"));
            ImageView iv3 = new ImageView();
            iv3.setImage(imageTree);
            Rectangle2D viewportRect = new Rectangle2D(0,0,110,170);
            iv3.setViewport(viewportRect);

            overlay.getChildren().addAll(iv3);
            makedragable(iv3);
        });
        buttonAdd2.setOnAction(e-> {
            Image imageTree = new Image(getClass().getResourceAsStream("css/img/buildingssmall.png"));
            ImageView iv3 = new ImageView();
            iv3.setImage(imageTree);
            Rectangle2D viewportRect = new Rectangle2D(0,0,110,170);
            iv3.setViewport(viewportRect);

            overlay.getChildren().addAll(iv3);
            makedragable(iv3);
        });
        buttonAdd3.setOnAction(e-> {
            Image imageTree = new Image(getClass().getResourceAsStream("css/img/elevatorsmall.png"));
            ImageView iv3 = new ImageView();
            iv3.setImage(imageTree);
            Rectangle2D viewportRect = new Rectangle2D(0,0,110,170);
            iv3.setViewport(viewportRect);

            overlay.getChildren().addAll(iv3);
            makedragable(iv3);
        });
        buttonAdd4.setOnAction(e-> {
            Image imageTree = new Image(getClass().getResourceAsStream("css/img/elsmall.png"));
            ImageView iv3 = new ImageView();
            iv3.setImage(imageTree);
            Rectangle2D viewportRect = new Rectangle2D(0,0,110,170);
            iv3.setViewport(viewportRect);

            overlay.getChildren().addAll(iv3);
            makedragable(iv3);
        });

    }



    //making shape draggable



    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    private void makedragable(Node node) {

        node.setOnMousePressed(lineOnMousePressedEventHandler);

        node.setOnMouseDragged(lineOnMouseDraggedEventHandler);
    }
    EventHandler<MouseEvent> lineOnMousePressedEventHandler = new EventHandler<MouseEvent>()
         {

            @Override

            public void handle(MouseEvent event) {
                orgSceneX = event.getSceneX();
                orgSceneY = event.getSceneY();

                    Node l = ((Node) (event.getSource()));

                    orgTranslateX = l.getTranslateX();
                    orgTranslateY = l.getTranslateY();


            }
        };
    EventHandler<MouseEvent> lineOnMouseDraggedEventHandler = new EventHandler <MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;

            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            Node l = ((Node)(event.getSource()));
            l.setTranslateX(newTranslateX);
            l.setTranslateY(newTranslateY);
        }
    };

    }






package TP3D;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.PickResult;

public class Interface extends Application {
    private Earth earth;
    private World world;
    private Camera camera;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Catch me if you can!");

        world = new World("./data/airport-codes_no_comma.csv");
        earth = new Earth();

        Pane pane = new Pane(earth);
        Scene theScene = new Scene(pane, 1200, 800, true);

        // Caméra
        camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);
        theScene.setCamera(camera);

        // Event handlers
        theScene.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    System.out.println("Clicked on (" + event.getSceneX() + "," + event.getSceneY() + ")");
                }

                if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    camera.setTranslateZ(camera.getTranslateZ() + event.getSceneY() - 400);
                }

                // Clique droit pour aéroport
                if (event.getButton() == MouseButton.SECONDARY &&
                        event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                    PickResult pickResult = event.getPickResult();
                    if (pickResult.getIntersectedNode() != null) {
                        Point2D pickTexCoords = pickResult.getIntersectedTextureCoordinate();
                        if (pickTexCoords != null) {
                            double X = pickTexCoords.getX();
                            double Y = pickTexCoords.getY();

                            // Conversion en lat/lon
                            double theta = 180 * (0.5 - Y);  // latitude
                            double phi = 360 * (X - 0.5);    // longitude

                            Aeroport nearest = world.findNearestAirport(phi, theta);
                            System.out.println("Aéroport le plus proche: " + nearest);

                            // Boule rouge
                            earth.getChildren().clear();
                            earth.getChildren().add(((Sphere)earth.getChildren().get(0)));
                            earth.displayRedSphere(nearest);
                        }
                    }
                }
            }
        });

        primaryStage.setScene(theScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
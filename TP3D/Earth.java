package TP3D;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Earth extends Group {
    private Sphere sph;
    private Rotate ry;
    private final double R = 300;

    public Earth() {
        // Création de la sphère Terre
        sph = new Sphere(R);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new javafx.scene.image.Image("earth_from_space.png"));
        sph.setMaterial(material);

        // Rotation Y
        ry = new Rotate(0, Rotate.Y_AXIS);
        sph.getTransforms().add(ry);

        this.getChildren().add(sph);

        // Animation de rotation
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                ry.setAngle((time / 1e9) * 360 / 15); // 1 tour en 15s
            }
        };
        animationTimer.start();
    }

    public Sphere createSphere(Aeroport a, Color color) {
        double theta = Math.toRadians(a.getLatitude());
        double phi = Math.toRadians(a.getLongitude());

        double x = R * Math.cos(theta) * Math.sin(phi);
        double y = -R * Math.sin(theta);
        double z = -R * Math.cos(theta) * Math.cos(phi);

        Sphere sphere = new Sphere(2);
        sphere.setTranslateX(x * 1.3); // Facteur 13/10
        sphere.setTranslateY(y * 1.3);
        sphere.setTranslateZ(z * 1.3);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        sphere.setMaterial(material);

        return sphere;
    }

    public void displayRedSphere(Aeroport a) {
        Sphere redSphere = createSphere(a, Color.RED);
        this.getChildren().add(redSphere);
    }

    public void displayYellowSphere(Aeroport a) {
        Sphere yellowSphere = createSphere(a, Color.YELLOW);
        this.getChildren().add(yellowSphere);
    }
}
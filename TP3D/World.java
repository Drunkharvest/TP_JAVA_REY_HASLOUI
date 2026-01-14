package TP3D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class World {
    private List<Aeroport> list = new ArrayList<>();

    public World(String fileName) {
        try (BufferedReader buf = new BufferedReader(new FileReader(fileName))) {
            String s = buf.readLine(); // Skip header
            while ((s = buf.readLine()) != null) {
                s = s.replaceAll("\"", "");
                String[] fields = s.split(",");
                if (fields.length >= 12 && fields[1].equals("large_airport")) {
                    String iataCode = fields[fields.length - 3];
                    String name = fields[fields.length - 1];
                    String city = fields[fields.length - 5];
                    String country = fields[fields.length - 4];
                    double latitude = Double.parseDouble(fields[fields.length - 2]);
                    double longitude = Double.parseDouble(fields[0]);

                    list.add(new Aeroport(iataCode, name, city, country, latitude, longitude));
                }
            }
        } catch (Exception e) {
            System.out.println("Maybe the file isn't there?");
            if (!list.isEmpty()) {
                System.out.println(list.get(list.size() - 1));
            }
            e.printStackTrace();
        }
    }

    public Aeroport findNearestAirport(double lon, double lat) {
        Aeroport nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Aeroport a : list) {
            double distance = distance(lat, lon, a.getLatitude(), a.getLongitude());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = a;
            }
        }
        return nearest;
    }

    public Aeroport findByCode(String code) {
        for (Aeroport a : list) {
            if (a.getIataCode().equals(code)) {
                return a;
            }
        }
        return null;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        Aeroport a1 = new Aeroport("TMP", "", "", "", lat1, lon1);
        Aeroport a2 = new Aeroport("TMP", "", "", "", lat2, lon2);
        return a1.calculDistance(a2);
    }

    public List<Aeroport> getList() {
        return list;
    }
}
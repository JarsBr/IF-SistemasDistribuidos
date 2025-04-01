package br.edu.ifogoiano.WebSocketTemperatura;

public class TemperatureData {
    private String location;
    private double temperature;

    public TemperatureData(String location, double temperature) {
        this.location = location;
        this.temperature = temperature;
    }

    public String getLocation() {
        return location;
    }

    public double getTemperature() {
        return temperature;
    }
}
package com.example.unitconverter;

public class UnitConverter {

    // Base unit is meters
    private static final double FEET_TO_METERS = 0.3048;
    private static final double INCHES_TO_METERS = 0.0254;
    private static final double CENTIMETERS_TO_METERS = 0.01;
    private static final double YARDS_TO_METERS = 0.9144;

    /**
     * Converts from one length unit to another.
     *
     * @param value The value to convert
     * @param fromUnit The unit to convert from (Feet, Inches, Centimeters, Meters, Yards)
     * @param toUnit The unit to convert to (Feet, Inches, Centimeters, Meters, Yards)
     * @return The converted value
     * @throws IllegalArgumentException if units are not supported
     */
    public double convert(double value, String fromUnit, String toUnit) {
        // Convert from source unit to meters (base unit)
        double meters = toMeters(value, fromUnit);

        // Convert from meters to target unit
        return fromMeters(meters, toUnit);
    }

    /**
     * Converts a value from any unit to meters.
     *
     * @param value The value to convert
     * @param unit The unit to convert from
     * @return The value in meters
     * @throws IllegalArgumentException if unit is not supported
     */
    private double toMeters(double value, String unit) {
        switch (unit) {
            case "Feet":
                return value * FEET_TO_METERS;
            case "Inches":
                return value * INCHES_TO_METERS;
            case "Centimeters":
                return value * CENTIMETERS_TO_METERS;
            case "Meters":
                return value; // Already in meters
            case "Yards":
                return value * YARDS_TO_METERS;
            default:
                throw new IllegalArgumentException("Unsupported unit: " + unit);
        }
    }

    /**
     * Converts a value in meters to any other unit.
     *
     * @param meters The value in meters
     * @param unit The unit to convert to
     * @return The converted value
     * @throws IllegalArgumentException if unit is not supported
     */
    private double fromMeters(double meters, String unit) {
        switch (unit) {
            case "Feet":
                return meters / FEET_TO_METERS;
            case "Inches":
                return meters / INCHES_TO_METERS;
            case "Centimeters":
                return meters / CENTIMETERS_TO_METERS;
            case "Meters":
                return meters; // Already in meters
            case "Yards":
                return meters / YARDS_TO_METERS;
            default:
                throw new IllegalArgumentException("Unsupported unit: " + unit);
        }
    }
}
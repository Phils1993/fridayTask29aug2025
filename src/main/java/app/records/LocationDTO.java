package app.records;

public record LocationDTO(
        int id,
        double latitude,
        double longitude,
        String address) {
}

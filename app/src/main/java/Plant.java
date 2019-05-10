import java.time.LocalDate;

import javax.xml.datatype.Duration;

public class Plant {
    private String nickname;
    private String species;
    private LocalDate plantDate;
    private Duration wateringTime;
    private int lightIntensity;

    public Plant (String nickname, String species, LocalDate plantDate, Duration wateringTime, int lightIntensity){
        this.nickname = nickname;
        this.species = species;
        this.plantDate = plantDate;
        this.wateringTime = wateringTime;
        this.lightIntensity = lightIntensity;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public LocalDate getPlantDate() {
        return plantDate;
    }

    public void setPlantDate(LocalDate plantDate) {
        this.plantDate = plantDate;
    }

    public Duration getWateringTime() {
        return wateringTime;
    }

    public void setWateringTime(Duration wateringTime) {
        this.wateringTime = wateringTime;
    }

    public int getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(int lightIntensity) {
        this.lightIntensity = lightIntensity;
    }
}

import java.time.LocalDate;
import java.util.Calendar;

import javax.xml.datatype.Duration;

public class Plant {
    private String nickname;
    private String species;
    private Calendar plantDate;
    private int wateringTime;
    private int lightIntensity;

    public Plant (String nickname, String species, Calendar plantDate, int wateringTime, int lightIntensity){
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

    public Calendar getPlantDate() {
        return plantDate;
    }

    public void setPlantDate(Calendar plantDate) {
        this.plantDate = plantDate;
    }

    public int getWateringTime() {
        return wateringTime;
    }

    public void setWateringTime(int wateringTime) {
        this.wateringTime = wateringTime;
    }

    public int getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(int lightIntensity) {
        this.lightIntensity = lightIntensity;
    }
}

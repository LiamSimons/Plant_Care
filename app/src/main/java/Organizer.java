import java.util.ArrayList;

public class Organizer {
    private ArrayList<Plant> myPlants;

    public Organizer(){
        myPlants = new ArrayList<>();
    }

    private void addPlant(Plant plant){
        myPlants.add(plant);
    }
}

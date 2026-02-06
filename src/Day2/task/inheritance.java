package Day2.task;
class vehicle {
    String modeOfTransportation;
    int numberOfPassengers;
    String color;
    boolean hasWheels = true;

    vehicle(String modeOfTransportation, int numberOfPassengers, String color) {
        this.modeOfTransportation = modeOfTransportation;
        this.numberOfPassengers = numberOfPassengers;
        this.color = color;
    }


    void displayVehicleDetails() {
        System.out.println("Mode of Transportation: " + modeOfTransportation);
        System.out.println("Number of Passengers: " + numberOfPassengers);
        System.out.println("Color: " + color);
        System.out.println("Has Wheels: " + hasWheels);
    }
}

class Car extends vehicle {
    String modelName;

    Car(String modelName, int numberOfPassengers, String color) {
        super("Road", numberOfPassengers, color);
        this.modelName = modelName;
    }

    void displayCarDetails() {
        System.out.println("Car Model: " + modelName);
        displayVehicleDetails();



    }
}

class Boat extends vehicle {
    String name;

    Boat(String name, int numberOfPassengers, String color) {
        super("Water", numberOfPassengers, color);
        this.name = name;
        this.hasWheels = false;
    }



    void displayBoatDetails() {
        System.out.println("Boat Name: " + name);
        displayVehicleDetails();


    }
}
public class inheritance {
    public static void main(String[] args) {
        Car car = new Car("Swift", 5, "Red");
        Boat boat = new Boat("Boat ABC", 10, "White");

        System.out.println("Car Details:");
        car.displayCarDetails();

        System.out.println("\nBoat Details:");
        boat.displayBoatDetails();



    }
}


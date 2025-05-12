import java.util.Scanner;

public class House {
    private String address;
    private int numberOfRooms;
    private double area; // in square meters

    // Constructor
    public House(String address, int numberOfRooms, double area) {
        this.address = address;
        setNumberOfRooms(numberOfRooms); // use setter for validation
        setArea(area); // use setter for validation
    }

    // Getter and Setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter and Setter for numberOfRooms
    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        if (numberOfRooms > 0) {
            this.numberOfRooms = numberOfRooms;
        } else {
            System.out.println("❌ Number of rooms must be positive. Keeping previous value.");
        }
    }

    // Getter and Setter for area
    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        if (area > 0) {
            this.area = area;
        } else {
            System.out.println("❌ Area must be greater than zero. Keeping previous value.");
        }
    }

    // Method to calculate price
    public double calculatePrice(double pricePerSquareMeter) {
        return area * pricePerSquareMeter;
    }

    // Method to display house details
    public void displayInfo(double pricePerSquareMeter) {
        System.out.println("\n🏡 House Information:");
        System.out.println("Address: " + address);
        System.out.println("Number of Rooms: " + numberOfRooms);
        System.out.println("Area: " + area + " sq.m");
        System.out.printf("Total Price: %.2f ETB\n", calculatePrice(pricePerSquareMeter));
    }

    // Main method with user input and modification
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🏠 House Registration 🏠");

        // 1. Initial input
        System.out.print("Enter house address: ");
        String address = scanner.nextLine();

        System.out.print("Enter number of rooms: ");
        int rooms = scanner.nextInt();

        System.out.print("Enter area in square meters: ");
        double area = scanner.nextDouble();

        System.out.print("Enter price per square meter (ETB): ");
        double pricePerSqm = scanner.nextDouble();

        // 2. Create House object
        House myHouse = new House(address, rooms, area);

        // 3. Show original data
        myHouse.displayInfo(pricePerSqm);

        // 4. Ask user if they want to update anything
        scanner.nextLine(); // consume newline
        System.out.print("\nDo you want to update any house details? (yes/no): ");
        String updateChoice = scanner.nextLine().trim().toLowerCase();

        if (updateChoice.equals("yes")) {
            System.out.print("Enter new address (or press Enter to keep current): ");
            String newAddress = scanner.nextLine();
            if (!newAddress.isEmpty()) {
                myHouse.setAddress(newAddress);
            }

            System.out.print("Enter new number of rooms (or 0 to keep current): ");
            int newRooms = scanner.nextInt();
            if (newRooms != 0) {
                myHouse.setNumberOfRooms(newRooms);
            }

            System.out.print("Enter new area in square meters (or 0 to keep current): ");
            double newArea = scanner.nextDouble();
            if (newArea != 0) {
                myHouse.setArea(newArea);
            }
        }

        // 5. Final updated display
        System.out.println("\n✅ Final Updated House Details:");
        myHouse.displayInfo(pricePerSqm);
    }
}

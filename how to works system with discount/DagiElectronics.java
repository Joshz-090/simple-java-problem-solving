import java.util.*;

class ElectronicsProduct {
    protected int productId;
    protected String name;
    protected double price;
    protected double originalPrice;

    public ElectronicsProduct(String name, double price) {
        this.productId = generateId();
        this.name = name;
        this.price = price;
        this.originalPrice = price;
    }

    private int generateId() {
        Random rand = new Random();
        return rand.nextInt(10000); // 0000 - 9999
    }

    public void applyDiscount(double discountPercent) {
        price = originalPrice - (originalPrice * discountPercent / 100);
    }

    public double getFinalPrice() {
        return price;
    }

    public void showPriceDetails() {
        System.out.printf("| %-15s| %10.2f  | %10.2f    |  %04d  |  ",
                name, originalPrice, price, productId);
    }
}

class WashingMachine extends ElectronicsProduct {
    private int warrantyPeriod; // in months

    public WashingMachine(String name, double price, int warrantyPeriod) {
        super(name, price);
        this.warrantyPeriod = warrantyPeriod;
    }

    public void extendWarranty(int months) {
        warrantyPeriod += months;
    }

    @Override
    public void showPriceDetails() {
        super.showPriceDetails();
        System.out.printf(" %8d months     |\n", warrantyPeriod);
    }
}

class Phone extends ElectronicsProduct {
    public Phone(String name, double price) {
        super(name, price);
    }

    @Override
    public void showPriceDetails() {
        super.showPriceDetails();
        System.out.println("       1 months      |");
    }
}

class Laptop extends ElectronicsProduct {
    public Laptop(String name, double price) {
        super(name, price);
    }

    @Override
    public void showPriceDetails() {
        super.showPriceDetails();
        System.out.println("       3 months      |");
    }
}

class Headset extends ElectronicsProduct {
    public Headset(String name, double price) {
        super(name, price);
    }

    @Override
    public void showPriceDetails() {
        super.showPriceDetails();
        System.out.println("       0 months      |");
    }
}

public class DagiElectronics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double newYearDiscount = 15.0;
        List<ElectronicsProduct> cart = new ArrayList<>();

        System.out.println("\n       |=============================================================================| ");
        System.out.println("       |                                                                             |");
        System.out.println("       |                    Happy New Year from Dagi Electronics!                    |");
        System.out.println("       |       USE special discount for new year - 15% discount on each item!        |");
        System.out.println("       |                                                                             |");
        System.out.println("       |=============================================================================| \n");

        while (true) {
            System.out.print(
                    "What do you want to buy? (Phone/Laptop/Headset/WashingMachine) or type 'checkout' to finish: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("checkout")) {
                break;
            }

            ElectronicsProduct product = null;

            switch (choice) {
                case "phone":
                    product = new Phone("iPhone 15", 95000);
                    break;
                case "laptop":
                    product = new Laptop("MacBook Pro", 180000);
                    break;
                case "headset":
                    product = new Headset("Sony WH-1000XM5", 18000);
                    break;
                case "washingmachine":
                    product = new WashingMachine("LG TurboWash", 45000, 24);
                    break;
                default:
                    System.out.println("Sorry, we don't have that product.");
                    continue;
            }

            product.applyDiscount(newYearDiscount);
            cart.add(product);
            System.out.println("Added to cart: " + product.name);
        }

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Thank you for visiting!");
            return;
        }

        // Display receipt
        System.out.println("\n\n================================= DAGI ELECTRONICS =================================");
        System.out.println("\n" + "                      NEW YEAR SALE - 15% OFF ON ALL ITEMS!");
        System.out.println("|-------------------------------------------------------------------------------|");
        System.out.println("| Product        | Original Rs | Discounted Rs | ID     | Warranty (if any)     |");
        System.out.println("|-------------------------------------------------------------------------------|");

        double totalOriginal = 0;
        double totalDiscounted = 0;

        for (ElectronicsProduct product : cart) {
            product.showPriceDetails();
            totalOriginal += product.originalPrice;
            totalDiscounted += product.getFinalPrice();
        }

        System.out.println("|-------------------------------------------------------------------------------|");
        System.out.printf("| TOTAL          | %10.2f  | %12.2f  |              ...               |\n",
                totalOriginal, totalDiscounted);
        System.out.println("|-------------------------------------------------------------------------------|");
        System.out.printf("| YOU SAVED      | Rs.%-9.2f                                                 |\n",
                (totalOriginal - totalDiscounted));
        System.out.println("|-------------------------------------------------------------------------------|");
        System.out.println("|                     THANK YOU FOR SHOPPING WITH US!                           |");
        System.out.println("==================================================================================");
    }
}
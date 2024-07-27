import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

class Car
{
    private String carId;
    private String model;
    private String brand;
    private double basePricePerDay;
    private boolean isAvailable;

    Car(String carId,String model, String brand, double basePricePerDay) {
        this.carId = carId;
        this.model = model;
        this.brand = brand;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;

    }
        public String getCarId () {
            return carId;
        }

        public String getModel () {
            return model;
        }

        public String getBrand () {
            return brand;
        }

        public double calculation(int rentaldays)
        {
            return basePricePerDay*rentaldays;
        }
        public double getBasePricePerDay () {
            return basePricePerDay;
        }

        public boolean isAvailable () {
            return isAvailable;
        }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String cid;
    private String cname;
    private String number;

    public Customer(String cid, String cname, String number) {
        this.cid = cid;
        this.cname = cname;
        this.number = number;
    }

    public String getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public String getNumber() {
        return number;
    }

}
class Rental{

    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days)
    {
        this.car = car;
        this.days= days;
        this.customer = customer;
    }
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}
 class CarRentalSystem {

    private List<Customer> customers;
    private List<Car> cars;
    private List<Rental> rentals;

    public CarRentalSystem()
    {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

     public void addCustomer(Customer customer) {
         customers.add(customer);
     }

     public void rentCar(Car car, Customer customer, int days)
     {
         if(car.isAvailable())
         {
             car.rent();
             rentals.add(new Rental(car,customer,days));
         }
         else
         {
             System.out.println("Car is Not Available for Rent this time");
         }
     }

     public void returnCar(Car car)
     {
         car.returnCar();
         Rental rentalToRemove = null;
         for(Rental rental : rentals)
         {
             if(rental.getCar() == car) {
                 rentalToRemove = rental;
                 break;
             }
         }
         if(rentalToRemove != null)
         {
             rentals.remove(rentalToRemove);
         }
         else
         {
             System.out.println("Car was not rented");
         }
     }

     public void menu()
     {
         Scanner scan = new Scanner(System.in);
         while(true) {
             System.out.println("-----------Welcome to Car Rental System---------- --");
             System.out.println("1.Rent a Car");
             System.out.println("2.Return a Car");
             System.out.println("3.Exit");
             System.out.println("Enter the choice");
             int choice = scan.nextInt();
             scan.nextLine(); //for consume new line;

             if(choice == 1)
             {
                 System.out.println("Rent a Car");
                 System.out.println("Enter your Name");
                 String name = scan.nextLine();

                 System.out.println("Enter your Mobile Number");
                 String number = scan.nextLine();

                 System.out.println("\nAvailable Cars:: ");
                 for(Car car: cars)
                 {
                     if(car.isAvailable())
                     {
                         System.out.println(car.getCarId()+" - "+car.getBrand()+"  "+car.getModel());
                     }
                 }

                 System.out.println("Enter the CarId do you want to rent:");
                 String carId = scan.nextLine();

                 System.out.println("Enter the days do want to rent:");
                 int rentalDays = scan.nextInt();
                 scan.nextLine(); // Consume newline

                 Customer newCustomer = new Customer("CUS"+(customers.size()  +1), name, number);
                 addCustomer(newCustomer);

                 Car selectedCar = null;
                 for(Car car : cars)
                 {
                     if(car.isAvailable() && car.getCarId().equals(carId))
                     {
                         selectedCar = car;
                         break;
                     }
                 }

                 if(selectedCar != null)
                 {
                     double totalPrice = selectedCar.calculation(rentalDays);
                     System.out.println("== Rental Information ==");
                     System.out.println("Customer Id :- "+newCustomer.getCid());
                     System.out.println("Customer Name :- "+newCustomer.getCname());
                     System.out.println("Mobile Number :- "+newCustomer.getNumber());
                     System.out.println("Car :- "+ selectedCar.getBrand()+" "+selectedCar.getModel());
                     System.out.println("Rental Days :- "+ rentalDays);
                     System.out.println("Total Price: $"+totalPrice);

                     System.out.println("Are You Confirm the Car (Y/N)");
                     String confirm = scan.nextLine();

                     if(confirm.equals("Y"))
                     {
                         rentCar(selectedCar,newCustomer,rentalDays);
                         System.out.println("Car is Successfylly rented to "+ newCustomer.getCname());
                     }
                     else {
                         System.out.println("\nInvalid car selection or car not available for rent.");
                     }
                 }
             }

             else if(choice == 2)
             {
                 System.out.println("== Return a Car ==");
                 System.out.println("Enter the car Id you want to return ");
                 String carId = scan.nextLine();
                 Car carToReturn = null;
                 for(Car car: cars)
                 {
                     if(car.getCarId().equals(carId) && !car.isAvailable())
                     {
                         carToReturn = car;
                         break;
                     }
                 }

                 if(carToReturn != null)
                 {
                        Customer customer = null;
                        for(Rental rental : rentals)
                        {
                            if(rental.getCar() == carToReturn)
                            {
                                customer = rental.getCustomer();
                                break;
                            }
                        }
                        if(customer != null)
                        {
                            returnCar(carToReturn);
                            System.out.println("Car Return successfully by "+ customer.getCname());
                        }
                        else
                        {
                            System.out.println("Car was not rented or rental information is missing.");
                        }
                 }
                 else
                 {
                     System.out.println("Invalid car Id or Car is not Rented");
                 }
             }
             else if(choice == 3)
             {
                 break;
             }
             else
             {
                 System.out.println("Invalid choice !! Please select Correct Option");
             }
         }

         System.out.println("Thank You For Using Car Rental System");

     }
}

class Rental_Car_System {
    public  static void main(String[] args) {
        CarRentalSystem carRental = new CarRentalSystem();
        Car car1 = new Car("C001", "Toyota", "Fortuner", 76.0);
        Car car2 = new Car("C002", "Audi", "A6", 120.0);
        Car car3 = new Car("C003", "Hyundai", "Verna", 50.0);
        Car car4 = new Car("C004", "Mahindra", "Scorpio", 66.0);
        Car car5 = new Car("C005", "Mercedes", "A class", 100.0);

        carRental.addCar(car1);
        carRental.addCar(car2);
        carRental.addCar(car3);
        carRental.addCar(car4);
        carRental.addCar(car5);

        carRental.menu();
    }
}

package main;

import model.Product;
import model.Sale;
import model.Employee;
import model.Client;
import model.Amount;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private ArrayList<Sale> sales;

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        shop.loadInventory();
        shop.iniciarSesion();
        shop.Menu();
    }

    public void iniciarSesion() {
        Scanner sc = new Scanner(System.in);
        Employee employee = new Employee("test", 123, "test");
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Introduzca numero de empleado: ");
            int employeeId = sc.nextInt();
            sc.nextLine(); 
            System.out.print("Introduzca contraseña: ");
            String password = sc.nextLine();

            if (employee.login(employeeId, password)) {
                System.out.println("Login correcto");
                loggedIn = true;
            } else {
                System.out.println("Usuario o contraseña incorrecta!");
            }
        }
    }

    public void Menu() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) A\u00f1adir producto");
            System.out.println("3) A\u00f1adir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver el total de ventas");
            System.out.println("9) Eliminar un producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    showCash();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    addStock();
                    break;
                case 4:
                    setExpired();
                    break;
                case 5:
                    showInventory();
                    break;
                case 6:
                    sale();
                    break;
                case 7:
                    showSales();
                    break;
                case 8:
                    showTotalSales();
                    break;
                case 9:
                    deleteProduct();
                    break;
                case 10:
                    System.out.println("Nos vemos!!");
                    return;
                default:
                    System.out.println("Opcion incorrecta");
            }
        } while (true);
    }

    public void loadInventory() {
        inventory.add(new Product("Manzana", 10.00, true, 10));
        inventory.add(new Product("Pera", 20.00, true, 20));
        inventory.add(new Product("Hamburguesa", 30.00, true, 30));
        inventory.add(new Product("Fresa", 5.00, true, 20));
    }

    private void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        if (findProduct(name) != null) {
            System.out.println("Error: producto ya existente en el inventario.");
            return;
        }
        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();
        inventory.add(new Product(name, wholesalerPrice, true, stock));
        System.out.println("Producto agregado con exito.");
    }

    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            System.out.print("Seleccione la cantidad a a\u00f1adir: ");
            int stock = scanner.nextInt();
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());
        } else {
            System.out.println("No se ha encontrado el producto con nombre: " + name);
        }
    }

    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        } else {
            System.out.println("Producto no encontrado");
        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            System.out.println(product);
        }
    }

    public void sale() {
        Scanner sc = new Scanner(System.in);
        Client client = new Client("Cliente", 456, 50.00);

        Amount totalAmount = new Amount(0);
        while (true) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar: ");
            String name = sc.nextLine();

            if (name.equals("0")) {
                break;
            }

            Product product = findProduct(name);
            if (product != null && product.isAvailable()) {
                System.out.print("Cantidad a vender: ");
                int cantidad = sc.nextInt();
                sc.nextLine();

                if (product.getStock() >= cantidad) {
                    totalAmount.add(product.getPublicPrice().getValue() * cantidad);
                    product.setStock(product.getStock() - cantidad);

                    if (product.getStock() == 0) {
                        product.setAvailable(false);
                    }
                } else {
                    System.out.println("Stock insuficiente.");
                }
            } else {
                System.out.println("Producto no encontrado o sin stock.");
            }
        }

        if (client.pay(totalAmount)) {
            System.out.println("Venta realizada con exito, total: " + totalAmount);
        } else {
            System.out.println("El cliente tiene un saldo insuficiente. Deuda: " + totalAmount);
        }

        totalAmount.setValue(totalAmount.getValue() * TAX_RATE);
        cash.add(totalAmount);
        sales.add(new Sale(client, inventory, totalAmount.getValue()));
    }

    private void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            System.out.println(sale);
        }
    }

    private void showTotalSales() {
        Amount totalSales = new Amount(0);
        for (Sale sale : sales) {
            totalSales.add(sale.getAmount());
        }
        System.out.println("Total de ventas: " + totalSales);
    }

    private void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del producto a eliminar: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            inventory.remove(product);
            System.out.println("Producto eliminado con exito.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }
    /**
     * find product by name
     * 
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
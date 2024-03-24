import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class GenerateInfoFiles {
   
    public static void main(String[] args) {
        try {
            createSalesMenFile(5); // Generar archivos de ventas de 5 vendedores
            createProductsFile(10); // Generar archivo de productos con 10 productos
            createSalesManInfoFile(5); // Generar archivo de información de 5 vendedores
            System.out.println("Archivos generados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al generar archivos: " + e.getMessage());
        }
    }
public static void createSalesMenFile(int randomSalesCount) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salesmen.txt"))) {
            Random random = new Random();
            for (int i = 0; i < randomSalesCount; i++) {
                int id = i + 1;
                String name = "Vendedor" + id;
                long documentNumber = 1000000000 + random.nextInt(900000000);
                writer.write("CC;" + documentNumber + "\n");
                for (int j = 0; j < random.nextInt(5) + 1; j++) {
                    int productId = random.nextInt(10) + 1; // Assuming 10 products available
                    int quantity = random.nextInt(10) + 1; // Random quantity
                    writer.write("IDProducto" + productId + ";" + quantity + "\n");
                }
                writer.write("\n");
            }
        }
    }

    public static void createProductsFile(int productsCount) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("products.txt"))) {
            for (int i = 0; i < productsCount; i++) {
                int id = i + 1;
                String name = "Producto" + id;
                double price = Math.round(Math.random() * 1000 * 100.0) / 100.0; // Random price
                writer.write("IDProducto" + id + ";" + name + ";" + price + "\n");
            }
        }
    }
    
public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salesman_info.txt"))) {
            Random random = new Random();
            for (int i = 0; i < salesmanCount; i++) {
                int id = i + 1;
                String name = "Vendedor" + id;
                long documentNumber = 1000000000 + random.nextInt(900000000);
                writer.write("CC;" + documentNumber + ";" + name + "\n");
            }
        }
    }
}


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateInfoFiles {

    public static void main(String[] args) {
        String outputFolder = "generated_files/";

        File folder = new File(outputFolder);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Carpeta '" + outputFolder + "' creada exitosamente.");
            } else {
                System.err.println("Error al crear la carpeta '" + outputFolder + "'.");
                return;
            }
        }

        createSalesMenFiles(10, outputFolder);

        createProductsFile(20, outputFolder);

        createSalesManInfoFile(10, outputFolder);

        System.out.println("Archivos generados exitosamente en la carpeta '" + outputFolder + "'.");
    }

    public static void createSalesMenFiles(int salesmenCount, String outputFolder) {
        for (int i = 1; i <= salesmenCount; i++) {
            String fileName = outputFolder + "salesman_" + i + ".txt";
            try (FileWriter writer = new FileWriter(fileName)) {
                Random random = new Random();
                for (int j = 1; j <= random.nextInt(10) + 1; j++) {
                    String productId = "IDProducto" + j;
                    int quantitySold = random.nextInt(10) + 1;
                    writer.write(productId + ";" + quantitySold + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createProductsFile(int productsCount, String outputFolder) {
        String fileName = outputFolder + "products_info.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();
            for (int i = 1; i <= productsCount; i++) {
                String productId = "IDProducto" + i;
                String productName = "NombreProducto" + i;
                double price = 10 + random.nextDouble() * 100;
                writer.write(productId + ";" + productName + ";" + price + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createSalesManInfoFile(int salesmanCount, String outputFolder) {
        String fileName = outputFolder + "salesmen_info.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();
        	for (int i = 1; i <= salesmanCount; i++) {
                String tipoDocumento = "CC";
                long numeroDocumento = random.nextLong(100000000,199999999) + i;
                String nombres = GetNameOrLastname("RealNames.txt",random);
                String apellidos = GetNameOrLastname("LastName.txt",random);
                writer.write(tipoDocumento + ";" + numeroDocumento + ";" + nombres + ";" + apellidos + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String GetNameOrLastname(String fileName,Random random) {
    InputStream inputStream = GenerateInfoFiles.class.getResourceAsStream(fileName);
    if (inputStream != null) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            List<String> linelist = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
              linelist.add(line);	
            }
            int index = (int)(Math.random() * linelist.size());
            return linelist.get(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.err.println("File not found: " + fileName);
    }
    return null;
    }
}

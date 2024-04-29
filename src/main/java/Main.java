import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String outputFolder = "generated_files/";

        if (createSalesmenReport(outputFolder)) {
            System.out.println("Archivo de reporte de ventas de los vendedores creado exitosamente.");
        } else {
            System.err.println("Error al crear archivo de reporte de ventas de los vendedores.");
        }

        if (createProductsReport(outputFolder)) {
            System.out.println("Archivo de reporte de productos vendidos por cantidad creado exitosamente.");
        } else {
            System.err.println("Error al crear archivo de reporte de productos vendidos por cantidad.");
        }
    }

    public static boolean createSalesmenReport(String outputFolder) {
        try {
            List<File> salesmanFiles = getSalesmanFiles(outputFolder);
            Map<String, Integer> salesTotalBySalesman = new HashMap<>();

            for (File salesmanFile : salesmanFiles) {
                int totalSales = getTotalSales(salesmanFile);
                String salesmanName = salesmanFile.getName().replace(".txt", "");
                salesTotalBySalesman.put(salesmanName, totalSales);
            }

            List<Map.Entry<String, Integer>> sortedSales = new ArrayList<>(salesTotalBySalesman.entrySet());
            Collections.sort(sortedSales, (a, b) -> b.getValue().compareTo(a.getValue()));

            String salesmenReportFile = outputFolder + "salesmen_report.csv";
            try (FileWriter writer = new FileWriter(salesmenReportFile)) {
                for (Map.Entry<String, Integer> entry : sortedSales) {
                    writer.write(entry.getKey() + ";" + entry.getValue() + "\n");
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static List<File> getSalesmanFiles(String outputFolder) {
        List<File> salesmanFiles = new ArrayList<>();
        File folder = new File(outputFolder);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile() && file.getName().startsWith("salesman_") && file.getName().endsWith(".txt")) {
                    salesmanFiles.add(file);
                }
            }
        }
        return salesmanFiles;
    }

    private static int getTotalSales(File salesmanFile) throws IOException {
        int totalSales = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(salesmanFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    totalSales += Integer.parseInt(parts[1]);
                }
            }
        }
        return totalSales;
    }


    public static boolean createProductsReport(String outputFolder) {
        try {
            File productsFile = new File(outputFolder + "products_info.txt");
            if (!productsFile.exists()) {
                System.err.println("Error: El archivo de información de productos no existe.");
                return false;
            }

            Map<String, Integer> productsSold = new HashMap<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(productsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length == 3) {
                        String productName = parts[1];
                        int quantitySold = 0;
                        productsSold.put(productName, quantitySold);
                    }
                }
            }

            List<File> salesmanFiles = getSalesmanFiles(outputFolder);

            for (File salesmanFile : salesmanFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(salesmanFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (parts.length == 2) {
                            String productId = parts[0];
                            int quantitySold = Integer.parseInt(parts[1]);
                            if (productsSold.containsKey(productId)) {
                                productsSold.put(productId, productsSold.get(productId) + quantitySold);
                            }
                        }
                    }
                }
            }

            List<Map.Entry<String, Integer>> sortedProducts = new ArrayList<>(productsSold.entrySet());
            Collections.sort(sortedProducts, (a, b) -> b.getValue().compareTo(a.getValue()));

            String productsReportFile = outputFolder + "products_report.csv";
            try (FileWriter writer = new FileWriter(productsReportFile)) {
                for (Map.Entry<String, Integer> entry : sortedProducts) {
                    writer.write(entry.getKey() + ";" + entry.getValue() + "\n");
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

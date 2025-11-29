package com.conversor;

import java.util.Scanner;

public class Conversor {

    public void iniciar() {
        Scanner sc = new Scanner(System.in);
        ClienteAPI api = new ClienteAPI();

        // El bucle principal permite múltiples conversiones hasta que el usuario decida salir.
        while (true) {
            try {
                System.out.println("\n==============================================");
                System.out.println("=== Conversor de Moneda - Elige una opción ===");
                System.out.println("1. USD (Dólar) -> ARS (Peso Argentino)");
                System.out.println("2. ARS (Peso Argentino) -> USD (Dólar)");
                System.out.println("3. USD (Dólar) -> BRL (Real Brasileño)");
                System.out.println("4. BRL (Real Brasileño) -> USD (Dólar)");
                System.out.println("5. USD (Dólar) -> COP (Peso Colombiano)");
                System.out.println("6. COP (Peso Colombiano) -> USD (Dólar)");
                System.out.println("7. Salir");
                System.out.println("==============================================");
                System.out.print("Opción: ");

                // Leer la opción del menú
                int opcion = sc.nextInt();

                if (opcion == 7) {
                    System.out.println("¡Gracias por usar el conversor! Saliendo...");
                    break;
                }

                String base, destino;

                // Definir las monedas base y destino según la opción elegida
                switch (opcion) {
                    case 1: base = "USD"; destino = "ARS"; break;
                    case 2: base = "ARS"; destino = "USD"; break;
                    case 3: base = "USD"; destino = "BRL"; break;
                    case 4: base = "BRL"; destino = "USD"; break;
                    case 5: base = "USD"; destino = "COP"; break;
                    case 6: base = "COP"; destino = "USD"; break;
                    default:
                        System.out.println("Opción no válida. Por favor, elige un número del 1 al 7.");
                        continue; // Vuelve al inicio del bucle
                }

                System.out.print("Monto a convertir: ");
                double monto = sc.nextDouble();

                // Obtener las tasas de conversión de la API
                var tasas = api.obtenerTasas(base);

                // Realizar la conversión
                double tasa = tasas.getConversionRates().get(destino);
                double convertido = monto * tasa;

                // Mostrar el resultado con formato
                System.out.printf("Resultado: %.2f %s = %.2f %s\n", monto, base, convertido, destino);

            } catch (java.util.InputMismatchException e) {
                // Captura si el usuario ingresa texto en lugar de un número para la opción o el monto
                System.out.println("Error de entrada: Por favor, ingresa un valor numérico válido.");
                sc.next(); // Limpiar el buffer del scanner para evitar bucles infinitos
            } catch (RuntimeException e) {
                // Captura errores de la API o de la lógica de conversión
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Cerrar el scanner al finalizar la aplicación
        sc.close();
    }
}
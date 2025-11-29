package com.conversor;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class ClienteAPI {

    // Reutilizar el cliente HTTP para mejor rendimiento
    private static final HttpClient CLIENTE_HTTP = HttpClient.newBuilder()
            .connectTimeout(java.time.Duration.ofSeconds(10))
            .build();

    private static final String API_KEY = "76b9cb23ce26f87983042f68";

    public ExchangeRates obtenerTasas(String baseCurrency) {

        String direccion = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + baseCurrency;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .GET()
                .build();

        try {
            HttpResponse<String> response = CLIENTE_HTTP.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                // Manejo de error si la API responde con un código distinto a 200 (OK)
                throw new RuntimeException("Error en la respuesta de la API. Código: " + response.statusCode() + ". Cuerpo: " + response.body());
            }

            return new Gson().fromJson(response.body(), ExchangeRates.class);

        } catch (java.io.IOException | InterruptedException e) {
            // Excepción general de I/O o interrupción del hilo
            throw new RuntimeException("Error al consultar la API para la moneda: " + baseCurrency, e);
        } catch (com.google.gson.JsonSyntaxException e) {
            // Excepción si el JSON no es válido, indicando una posible moneda base incorrecta.
            throw new RuntimeException("Moneda base '" + baseCurrency + "' no válida o error en el formato de respuesta.", e);
        }
    }
}
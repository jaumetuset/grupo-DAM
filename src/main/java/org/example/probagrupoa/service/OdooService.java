package org.example.probagrupoa.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OdooService {

    private final String ODOO_URL = "http://40.89.147.152:8069/jsonrpc";
    private final String DB_NAME = "simarrodb";
    private final String USERNAME = "admin";
    private final String PASSWORD = "admin";

    private RestTemplate restTemplate = new RestTemplate();


    public String authenticate() {

        Map<String, Object> payload = new HashMap<>();
        payload.put("jsonrpc", "2.0");
        payload.put("method", "call");

        Map<String, Object> params = new HashMap<>();
        params.put("service", "common");
        params.put("method", "login");
        params.put("args", new Object[]{DB_NAME, USERNAME, PASSWORD});

        payload.put("params", params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {

            ResponseEntity<Map> response = restTemplate.postForEntity(ODOO_URL, request, Map.class);

            if (response.getBody() == null) {
                throw new RuntimeException("La respuesta de Odoo es nula");
            }

            Object result = response.getBody().get("result");

            if (result == null) {
                throw new RuntimeException("Autenticación fallida: respuesta inesperada de Odoo - " + response.getBody());
            }

            return result.toString();

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return null;
        }

    }


    public String createPartner(String name, String email) {

        String authResult = authenticate();
        if (authResult == null) {
            throw new RuntimeException("No se pudo autenticar en Odoo");
        }

        int uid = Integer.parseInt(authResult);

        Map<String, Object> payload = new HashMap<>();
        payload.put("jsonrpc", "2.0");
        payload.put("method", "call");

        Map<String, Object> params = new HashMap<>();
        params.put("service", "object");
        params.put("method", "execute");

        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("email", email);
        values.put("is_premium", true);

        Object[] args = new Object[]{
                DB_NAME,
                uid,
                PASSWORD,
                "res.partner",
                "create",
                values
        };

        params.put("args", args);
        payload.put("params", params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {

            ResponseEntity<Map> response = restTemplate.postForEntity(ODOO_URL, request, Map.class);

            if (response.getBody() == null) {
                throw new RuntimeException("Respuesta nula de Odoo al crear el partner");
            }

            Object result = response.getBody().get("result");

            if (result == null) {
                throw new RuntimeException("Error al crear el partner: " + response.getBody());
            }

            return result.toString();

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return null;
        }

    }
}
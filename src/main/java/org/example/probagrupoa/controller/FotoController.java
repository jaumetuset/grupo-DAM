package org.example.probagrupoa.controller;

import org.example.probagrupoa.entity.Foto;
import org.example.probagrupoa.entity.Producto;
import org.example.probagrupoa.repository.IFotoRepository;
import org.example.probagrupoa.repository.IProductoRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/uploads")
public class FotoController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private IProductoRepository articuloRepository;

    @Autowired
    private IFotoRepository fotoRepository;

    @PostMapping("/imagen/{articuloId}")
    public ResponseEntity<Map<String, String>> uploadFile(@PathVariable Integer articuloId, @RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // Verifica que el artículo exista
            Producto articulo = articuloRepository.findById(articuloId)
                    .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

            // Verifica que el directorio exista
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Guarda el archivo en el servidor con un nombre único
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            // Guarda la URL en la base de datos
            Foto foto = new Foto();
            String fileUrl = "http://api.grupoa.com:8080/MyApp/uploads/" + fileName;
            foto.setUrl(fileUrl);
            foto.setProducto(articulo);
            fotoRepository.save(foto);

            // Respuesta en formato JSON
            response.put("message", "Imagen guardada correctamente");
            response.put("imageUrl", fileUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Error al subir la imagen");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/{articuloId}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer articuloId) throws IOException {

        Optional<Producto> articulo = articuloRepository.findById(articuloId);

        if (articulo.isPresent() && articulo.get().getFoto() != null) {
            // Extraer el nombre del archivo desde la URL
            String fullUrl = articulo.get().getFoto().getUrl();
            String filename = fullUrl.substring(fullUrl.lastIndexOf("/") + 1); // Extrae solo el nombre del archivo

            File file = new File(UPLOAD_DIR + filename);
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Detecta el tipo de contenido de la imagen
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

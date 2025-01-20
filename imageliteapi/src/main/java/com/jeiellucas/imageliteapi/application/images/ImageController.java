package com.jeiellucas.imageliteapi.application.images;

import com.jeiellucas.imageliteapi.domain.entity.Image;
import com.jeiellucas.imageliteapi.domain.enums.ImageExtension;
import com.jeiellucas.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;

    @PostMapping()
    public ResponseEntity save(
            @RequestParam("file") MultipartFile file, //Recebendo o arquivo
            @RequestParam(value = "name", required = true) String name, // Como fazer o parametro ser opcional ou não
            @RequestParam("tags")List<String> tags
    ) throws IOException {

        log.info("Imagem Recebida: name: {}, size: {}", file.getOriginalFilename(), file.getSize());

        Image image = imageMapper.mapToImage(file, name, tags);
        Image savedImage = imageService.save(image);
        URI imageUri = buildImageURL(savedImage);

        return ResponseEntity.created(imageUri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id){
        var possibleImage = imageService.getById(id);
        if(possibleImage.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var image = possibleImage.get();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());

        headers.setContentDispositionFormData("inline;filename=\"" + image.getFileName() + "\"", image.getFileName());


        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ImageDTO>> search(@RequestParam(value = "extension", required = false, defaultValue = "") String extension,
                                                 @RequestParam(value = "query", required = false) String query){

        var result = imageService.search(ImageExtension.ofName(extension), query);

        var images = result.stream().map(image -> {
            var url = buildImageURL(image);
            return imageMapper.imageToDTO(image, url.toString()); // Especificação de retorno para lambda de multiplas linhas, mostra o que deve ser coletado
        }).collect(Collectors.toList());

        return ResponseEntity.ok(images);
    }

    private URI buildImageURL(Image image){
        String imagePath = "/" + image.getId();

        //Caminho total = http://localhost:8080/v1/images/id_image
        return ServletUriComponentsBuilder
                //CurrentRequestUri = http://localhost:8080/v1/images
                .fromCurrentRequestUri()
                //Path = /id_image
                .path(imagePath)
                .build().toUri();
    }
}
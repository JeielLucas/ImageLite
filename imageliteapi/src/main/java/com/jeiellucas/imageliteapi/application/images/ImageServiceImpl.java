package com.jeiellucas.imageliteapi.application.images;

import com.jeiellucas.imageliteapi.domain.entity.Image;
import com.jeiellucas.imageliteapi.domain.service.ImageService;
import com.jeiellucas.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor //Cria construtor com argumentos obrigatorios
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    @Transactional //Operações de escrita
    public Image save(Image image){
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> getById(String id){
        return imageRepository.findById(id);
    }
}

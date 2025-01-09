package com.jeiellucas.imageliteapi.application.images;

import com.jeiellucas.imageliteapi.domain.entity.Image;
import com.jeiellucas.imageliteapi.domain.service.ImageService;
import com.jeiellucas.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor //Cria construtor com argumentos obrigatorios
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public Image save(Image image){
        return imageRepository.save(image);
    }
}

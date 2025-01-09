package com.jeiellucas.imageliteapi.infra.repository;

import com.jeiellucas.imageliteapi.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}

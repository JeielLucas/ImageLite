package com.jeiellucas.imageliteapi.infra.repository;

import com.jeiellucas.imageliteapi.domain.entity.Image;
import com.jeiellucas.imageliteapi.domain.enums.ImageExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.jeiellucas.imageliteapi.infra.repository.specs.GenericSpecs.conjunction;
import static com.jeiellucas.imageliteapi.infra.repository.specs.ImageSpecs.*;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
    /**
     * SELECT * FROM IMAGE WHERE 1 = 1 AND EXTENSION = 'PNG' AND (NAME LIKE 'NAME' OR TAGS LIKE 'NAME')
     **/
    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){
        // SELECT * FROM IMAGE WHERE 1 = 1, conjunction retorna sempre true
        // Exemplo de uso -> Filtros: Utilizo para retornar todas as imagens, sem filtros
        Specification<Image> spec = where(conjunction());

        if(extension != null){
            // AND EXTENSION = 'PNG'
            spec = spec.and(extensionEquals(extension));
        }

        if(StringUtils.hasText(query)){
            // AND (NAME LIKE 'query' OR TAGS LIKE 'query')
            // anyOf -> OR -> Precisa de apenas 1 sendo atendido || allOf -> AND -> Os dois sendo atendido
            spec = spec.and(anyOf(nameLike(query), tagsLike(query)));
        }

        return findAll(spec);
    }
}

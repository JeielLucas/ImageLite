package com.jeiellucas.imageliteapi.domain.entity;

import com.jeiellucas.imageliteapi.domain.enums.ImageExtension;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table // Posso definir o nome com a propriedade name = "tb_name"
@EntityListeners(AuditingEntityListener.class) //Auditoria, escutando e mapeando para o createdDate
@Data //Cria getters, setters, equals and hash code, to string, construtor para campos obrigatorios
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column // Posso usar o argumento do table para definir nome da coluna
    private String name;
    @Column
    private Long size;
    @Column
    @Enumerated(EnumType.STRING)
    private ImageExtension extension;
    @Column
    @CreatedDate
    private LocalDateTime uploadDate;
    @Column
    private String tags;
    @Column
    @Lob // Mostrar que é um arquivo
    private byte[] file;
}

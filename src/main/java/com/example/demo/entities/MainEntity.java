package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "main_entity")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode // <- add lazyEntity to equals & hashCode
public class MainEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plain_field")
    private String plainFiled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lazy_id")
    private LazyEntity lazyEntity;


//    with spring boot 2.1.3 this method won't be invoked if repository method return Collection<>
//    wii be invoked with version 2.1.4+
//    @Override
//    public int hashCode() {
//        throw new RuntimeException("should not be invoked after migration");
//    }
}

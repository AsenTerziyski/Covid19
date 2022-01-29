package com.covid.countries.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    private Long IdDb;
//    private LocalDateTime created;
//    private LocalDateTime modified;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getIdDb() {
        return IdDb;
    }

    public void setIdDb(Long id) {
        this.IdDb = id;
    }

//    @NotNull
//    public LocalDateTime getCreated() {
//        return created;
//    }
//
//    public BaseEntity setCreated(LocalDateTime created) {
//        this.created = created;
//        return this;
//    }
//
//    public LocalDateTime getModified() {
//        return modified;
//    }
//
//    public BaseEntity setModified(LocalDateTime modified) {
//        this.modified = modified;
//        return this;
//    }

//    @PrePersist
//    public void beforeCreate() {
//        this.created = LocalDateTime.now();
//    }
//
//    @PostPersist
//    public void OnUpdate() {
//        this.modified = LocalDateTime.now();
//    }

}

package com.origamigt.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Pagina.
 */
@Entity
@Table(name = "pagina")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Pagina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "sub_titulo")
    private String subTitulo;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "orden", nullable = false)
    private Integer orden;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "fecha_expiracion")
    private LocalDate fechaExpiracion;

    @NotNull
    @Column(name = "activa", nullable = false)
    private Boolean activa;

    @NotNull
    @Column(name = "etiqueta", nullable = false)
    private String etiqueta;

    @Column(name = "images")
    private String images;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Pagina titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public Pagina subTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
        return this;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Pagina descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public Pagina orden(Integer orden) {
        this.orden = orden;
        return this;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Pagina fechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
        return this;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public Pagina fechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
        return this;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Boolean isActiva() {
        return activa;
    }

    public Pagina activa(Boolean activa) {
        this.activa = activa;
        return this;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public Pagina etiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
        return this;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getImages() {
        return images;
    }

    public Pagina images(String images) {
        this.images = images;
        return this;
    }

    public void setImages(String images) {
        this.images = images;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pagina)) {
            return false;
        }
        return id != null && id.equals(((Pagina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pagina{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", subTitulo='" + getSubTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", orden=" + getOrden() +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", fechaExpiracion='" + getFechaExpiracion() + "'" +
            ", activa='" + isActiva() + "'" +
            ", etiqueta='" + getEtiqueta() + "'" +
            ", images='" + getImages() + "'" +
            "}";
    }
}

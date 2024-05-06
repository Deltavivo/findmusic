package com.deltavivo.findmusic.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="musicas")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;

    public Musica(){}

    public Musica(String nomeMusica) {
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @ManyToOne
    private Artista artista;

    @Override
    public String toString() {
        return  "Musicas = " + titulo;
    }
}

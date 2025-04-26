package com.jeroka.album.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String name;
    private String userId;
    private String backgroundImage;
    private String titleColor;
    private String songColor;
    private Integer titleBackgroundOpacity;
    private Integer songsBackgroundOpacity;
    private Integer backgroundImageOpacity;
    private Integer overlayOpacity;
    private String overlayColor;
    private String titleAlignment;
    private String songsAlignment;
    
    @ElementCollection
    @CollectionTable(name = "album_songs", joinColumns = @JoinColumn(name = "album_id"))
    @Column(name = "song_name")
    private List<String> songs;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Album() {
    }

    public Album(UUID id, String name, String userId, String backgroundImage, String titleColor, String songColor,
                Integer titleBackgroundOpacity, Integer songsBackgroundOpacity, Integer backgroundImageOpacity,
                Integer overlayOpacity, String overlayColor, String titleAlignment, String songsAlignment,
                List<String> songs, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.backgroundImage = backgroundImage;
        this.titleColor = titleColor;
        this.songColor = songColor;
        this.titleBackgroundOpacity = titleBackgroundOpacity;
        this.songsBackgroundOpacity = songsBackgroundOpacity;
        this.backgroundImageOpacity = backgroundImageOpacity;
        this.overlayOpacity = overlayOpacity;
        this.overlayColor = overlayColor;
        this.titleAlignment = titleAlignment;
        this.songsAlignment = songsAlignment;
        this.songs = songs;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getUserId() { return userId; }
    public String getBackgroundImage() { return backgroundImage; }
    public String getTitleColor() { return titleColor; }
    public String getSongColor() { return songColor; }
    public Integer getTitleBackgroundOpacity() { return titleBackgroundOpacity; }
    public Integer getSongsBackgroundOpacity() { return songsBackgroundOpacity; }
    public Integer getBackgroundImageOpacity() { return backgroundImageOpacity; }
    public Integer getOverlayOpacity() { return overlayOpacity; }
    public String getOverlayColor() { return overlayColor; }
    public String getTitleAlignment() { return titleAlignment; }
    public String getSongsAlignment() { return songsAlignment; }
    public List<String> getSongs() { return songs; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setBackgroundImage(String backgroundImage) { this.backgroundImage = backgroundImage; }
    public void setTitleColor(String titleColor) { this.titleColor = titleColor; }
    public void setSongColor(String songColor) { this.songColor = songColor; }
    public void setTitleBackgroundOpacity(Integer titleBackgroundOpacity) { this.titleBackgroundOpacity = titleBackgroundOpacity; }
    public void setSongsBackgroundOpacity(Integer songsBackgroundOpacity) { this.songsBackgroundOpacity = songsBackgroundOpacity; }
    public void setBackgroundImageOpacity(Integer backgroundImageOpacity) { this.backgroundImageOpacity = backgroundImageOpacity; }
    public void setOverlayOpacity(Integer overlayOpacity) { this.overlayOpacity = overlayOpacity; }
    public void setOverlayColor(String overlayColor) { this.overlayColor = overlayColor; }
    public void setTitleAlignment(String titleAlignment) { this.titleAlignment = titleAlignment; }
    public void setSongsAlignment(String songsAlignment) { this.songsAlignment = songsAlignment; }
    public void setSongs(List<String> songs) { this.songs = songs; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", titleColor='" + titleColor + '\'' +
                ", songColor='" + songColor + '\'' +
                ", titleBackgroundOpacity=" + titleBackgroundOpacity +
                ", songsBackgroundOpacity=" + songsBackgroundOpacity +
                ", backgroundImageOpacity=" + backgroundImageOpacity +
                ", overlayOpacity=" + overlayOpacity +
                ", overlayColor='" + overlayColor + '\'' +
                ", titleAlignment='" + titleAlignment + '\'' +
                ", songsAlignment='" + songsAlignment + '\'' +
                ", songs=" + songs +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 
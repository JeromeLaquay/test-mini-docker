package com.jeroka.album.controller;

import com.jeroka.album.dto.AlbumDto;
import com.jeroka.album.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        List<AlbumDto> albums = albumService.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable UUID id) {
        AlbumDto album = albumService.getAlbumById(id);
        return ResponseEntity.ok(album);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AlbumDto>> getAlbumsByUserId(@PathVariable String userId) {
        List<AlbumDto> albums = albumService.getAlbumsByUserId(userId);
        return ResponseEntity.ok(albums);
    }

    @PostMapping
    public ResponseEntity<AlbumDto> createAlbum(@Valid @RequestBody AlbumDto albumDto) {
        AlbumDto createdAlbum = albumService.createAlbum(albumDto);
        return ResponseEntity.ok(createdAlbum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable UUID id, @Valid @RequestBody AlbumDto albumDto) {
        AlbumDto updatedAlbum = albumService.updateAlbum(id, albumDto);
        return ResponseEntity.ok(updatedAlbum);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable UUID id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.ok().build();
    }
} 
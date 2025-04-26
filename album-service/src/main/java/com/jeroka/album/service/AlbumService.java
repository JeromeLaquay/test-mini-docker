package com.jeroka.album.service;

import com.jeroka.album.dto.AlbumDto;
import com.jeroka.album.mapper.AlbumMapper;
import com.jeroka.album.model.Album;
import com.jeroka.album.repository.AlbumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlbumService {
    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);
    
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    public AlbumService(AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    @Transactional(readOnly = true)
    public List<AlbumDto> getAllAlbums() {
        try {
            logger.debug("Fetching all albums");
            List<Album> albums = albumRepository.findAll();
            logger.debug("Found {} albums", albums.size());
            return albums.stream()
                    .map(albumMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching all albums", e);
            throw new RuntimeException("Failed to fetch albums: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public AlbumDto getAlbumById(UUID id) {
        try {
            logger.debug("Fetching album with id: {}", id);
            Album album = albumRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Album not found with id: " + id));
            return albumMapper.toDto(album);
        } catch (Exception e) {
            logger.error("Error fetching album with id: {}", id, e);
            throw new RuntimeException("Failed to fetch album: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<AlbumDto> getAlbumsByUserId(String userId) {
        try {
            logger.debug("Fetching albums for user: {}", userId);
            List<Album> albums = albumRepository.findByUserId(userId);
            logger.debug("Found {} albums for user {}", albums.size(), userId);
            return albums.stream()
                    .map(albumMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching albums for user: {}", userId, e);
            throw new RuntimeException("Failed to fetch user albums: " + e.getMessage());
        }
    }

    @Transactional
    public AlbumDto createAlbum(AlbumDto albumDto) {
        try {
            logger.debug("Creating new album: {}", albumDto);
            Album album = albumMapper.toEntity(albumDto);
            Album savedAlbum = albumRepository.save(album);
            logger.debug("Created album with id: {}", savedAlbum.getId());
            return albumMapper.toDto(savedAlbum);
        } catch (Exception e) {
            logger.error("Error creating album: {}", albumDto, e);
            throw new RuntimeException("Failed to create album: " + e.getMessage());
        }
    }

    @Transactional
    public AlbumDto updateAlbum(UUID id, AlbumDto albumDto) {
        try {
            logger.debug("Updating album with id: {}", id);
            Album existingAlbum = albumRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Album not found with id: " + id));
            
            Album albumToUpdate = albumMapper.toEntity(albumDto);
            albumToUpdate.setId(existingAlbum.getId());
            albumToUpdate.setCreatedAt(existingAlbum.getCreatedAt());
            
            Album updatedAlbum = albumRepository.save(albumToUpdate);
            logger.debug("Updated album with id: {}", updatedAlbum.getId());
            return albumMapper.toDto(updatedAlbum);
        } catch (Exception e) {
            logger.error("Error updating album with id: {}", id, e);
            throw new RuntimeException("Failed to update album: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteAlbum(UUID id) {
        try {
            logger.debug("Deleting album with id: {}", id);
            Album album = albumRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Album not found with id: " + id));
            albumRepository.delete(album);
            logger.debug("Deleted album with id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting album with id: {}", id, e);
            throw new RuntimeException("Failed to delete album: " + e.getMessage());
        }
    }
} 
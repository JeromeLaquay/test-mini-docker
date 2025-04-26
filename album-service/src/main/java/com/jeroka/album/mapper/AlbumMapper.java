package com.jeroka.album.mapper;

import com.jeroka.album.dto.AlbumDto;
import com.jeroka.album.model.Album;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlbumMapper {
    
    public AlbumDto toDto(Album album) {
        if (album == null) {
            return null;
        }
        
        AlbumDto dto = new AlbumDto();
        dto.setId(album.getId());
        dto.setName(album.getName());
        dto.setUserId(album.getUserId());
        dto.setBackgroundImage(album.getBackgroundImage());
        dto.setTitleColor(album.getTitleColor());
        dto.setSongColor(album.getSongColor());
        dto.setTitleBackgroundOpacity(album.getTitleBackgroundOpacity());
        dto.setSongsBackgroundOpacity(album.getSongsBackgroundOpacity());
        dto.setBackgroundImageOpacity(album.getBackgroundImageOpacity());
        dto.setOverlayOpacity(album.getOverlayOpacity());
        dto.setOverlayColor(album.getOverlayColor());
        dto.setTitleAlignment(album.getTitleAlignment());
        dto.setSongsAlignment(album.getSongsAlignment());
        dto.setSongs(album.getSongs() != null ? new ArrayList<>(album.getSongs()) : null);
        dto.setCreatedAt(album.getCreatedAt());
        dto.setUpdatedAt(album.getUpdatedAt());
        
        return dto;
    }
    
    public Album toEntity(AlbumDto dto) {
        if (dto == null) {
            return null;
        }
        
        Album album = new Album();
        album.setId(dto.getId());
        album.setName(dto.getName());
        album.setUserId(dto.getUserId());
        album.setBackgroundImage(dto.getBackgroundImage());
        album.setTitleColor(dto.getTitleColor());
        album.setSongColor(dto.getSongColor());
        album.setTitleBackgroundOpacity(dto.getTitleBackgroundOpacity());
        album.setSongsBackgroundOpacity(dto.getSongsBackgroundOpacity());
        album.setBackgroundImageOpacity(dto.getBackgroundImageOpacity());
        album.setOverlayOpacity(dto.getOverlayOpacity());
        album.setOverlayColor(dto.getOverlayColor());
        album.setTitleAlignment(dto.getTitleAlignment());
        album.setSongsAlignment(dto.getSongsAlignment());
        album.setSongs(dto.getSongs() != null ? new ArrayList<>(dto.getSongs()) : null);
        album.setCreatedAt(dto.getCreatedAt());
        album.setUpdatedAt(dto.getUpdatedAt());
        
        return album;
    }
    
    public List<AlbumDto> toDtoList(List<Album> albums) {
        if (albums == null) {
            return null;
        }
        
        List<AlbumDto> dtoList = new ArrayList<>(albums.size());
        for (Album album : albums) {
            dtoList.add(toDto(album));
        }
        
        return dtoList;
    }
} 
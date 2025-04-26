package com.jeroka.subscription.service.impl;

import com.jeroka.subscription.dto.SubscriptionPlanDto;
import com.jeroka.subscription.model.SubscriptionPlan;
import com.jeroka.subscription.model.SubscriptionType;
import com.jeroka.subscription.repository.SubscriptionPlanRepository;
import com.jeroka.subscription.service.SubscriptionPlanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des plans d'abonnement.
 */
@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    public SubscriptionPlanServiceImpl(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @Override
    @Transactional
    public SubscriptionPlanDto createSubscriptionPlan(SubscriptionPlanDto subscriptionPlanDto) {
        SubscriptionPlan plan = convertToEntity(subscriptionPlanDto);
        plan.setCreatedAt(LocalDateTime.now());
        plan.setActive(true);
        SubscriptionPlan savedPlan = subscriptionPlanRepository.save(plan);
        return convertToDto(savedPlan);
    }

    @Override
    public SubscriptionPlanDto getSubscriptionPlanById(Long id) {
        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan d'abonnement non trouvé avec l'ID: " + id));
        return convertToDto(plan);
    }

    @Override
    public SubscriptionPlanDto getSubscriptionPlanByName(String name) {
        SubscriptionPlan plan = subscriptionPlanRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Plan d'abonnement non trouvé avec le nom: " + name));
        return convertToDto(plan);
    }

    @Override
    public List<SubscriptionPlanDto> getAllSubscriptionPlans() {
        return subscriptionPlanRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionPlanDto> getActiveSubscriptionPlans() {
        return subscriptionPlanRepository.findByIsActiveTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionPlanDto> getSubscriptionPlansByType(SubscriptionType type) {
        return subscriptionPlanRepository.findByType(type).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionPlanDto> getActiveSubscriptionPlansByType(SubscriptionType type) {
        return subscriptionPlanRepository.findByTypeAndIsActiveTrue(type).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubscriptionPlanDto updateSubscriptionPlan(Long id, SubscriptionPlanDto subscriptionPlanDto) {
        SubscriptionPlan existingPlan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan d'abonnement non trouvé avec l'ID: " + id));
        
        updateSubscriptionPlanFromDto(existingPlan, subscriptionPlanDto);
        SubscriptionPlan updatedPlan = subscriptionPlanRepository.save(existingPlan);
        return convertToDto(updatedPlan);
    }

    @Override
    @Transactional
    public SubscriptionPlanDto setSubscriptionPlanActive(Long id, boolean active) {
        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan d'abonnement non trouvé avec l'ID: " + id));
        
        plan.setActive(active);
        SubscriptionPlan updatedPlan = subscriptionPlanRepository.save(plan);
        return convertToDto(updatedPlan);
    }

    @Override
    @Transactional
    public boolean deleteSubscriptionPlan(Long id) {
        if (subscriptionPlanRepository.existsById(id)) {
            subscriptionPlanRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<SubscriptionPlanDto> getSubscriptionPlansByMaxPrice(Double maxPrice) {
        return subscriptionPlanRepository.findByPriceLessThanEqual(maxPrice).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Méthodes de conversion entre entité et DTO
    private SubscriptionPlan convertToEntity(SubscriptionPlanDto dto) {
        SubscriptionPlan entity = new SubscriptionPlan();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setPrice(dto.getPrice());
        entity.setDurationMonths(dto.getDurationMonths());
        entity.setFeatures(dto.getFeatures());
        entity.setActive(dto.isActive());
        return entity;
    }

    private SubscriptionPlanDto convertToDto(SubscriptionPlan entity) {
        return new SubscriptionPlanDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getType(),
                entity.getPrice(),
                entity.getDurationMonths(),
                entity.getFeatures(),
                entity.isActive()
        );
    }

    private void updateSubscriptionPlanFromDto(SubscriptionPlan entity, SubscriptionPlanDto dto) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getType() != null) {
            entity.setType(dto.getType());
        }
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }
        if (dto.getDurationMonths() != null) {
            entity.setDurationMonths(dto.getDurationMonths());
        }
        if (dto.getFeatures() != null) {
            entity.setFeatures(dto.getFeatures());
        }
        entity.setActive(dto.isActive());
    }
} 
package com.jeroka.subscription.service.impl;

import com.jeroka.subscription.dto.SubscriptionDto;
import com.jeroka.subscription.model.Subscription;
import com.jeroka.subscription.model.SubscriptionType;
import com.jeroka.subscription.repository.SubscriptionRepository;
import com.jeroka.subscription.service.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des abonnements.
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Transactional
    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = convertToEntity(subscriptionDto);
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setActive(true);
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return convertToDto(savedSubscription);
    }

    @Override
    public SubscriptionDto getSubscriptionById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Abonnement non trouvé avec l'ID: " + id));
        return convertToDto(subscription);
    }

    @Override
    public List<SubscriptionDto> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionDto> getSubscriptionsByUserId(String userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDto getActiveSubscriptionByUserId(String userId) {
        return subscriptionRepository.findByUserIdAndIsActive(userId, true)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public SubscriptionDto updateSubscription(Long id, SubscriptionDto subscriptionDto) {
        Subscription existingSubscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Abonnement non trouvé avec l'ID: " + id));
        
        updateSubscriptionFromDto(existingSubscription, subscriptionDto);
        Subscription updatedSubscription = subscriptionRepository.save(existingSubscription);
        return convertToDto(updatedSubscription);
    }

    @Override
    @Transactional
    public SubscriptionDto setSubscriptionActive(Long id, boolean active) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Abonnement non trouvé avec l'ID: " + id));
        
        subscription.setActive(active);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return convertToDto(updatedSubscription);
    }

    @Override
    @Transactional
    public SubscriptionDto setAutoRenew(Long id, boolean autoRenew) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Abonnement non trouvé avec l'ID: " + id));
        
        subscription.setAutoRenew(autoRenew);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return convertToDto(updatedSubscription);
    }

    @Override
    @Transactional
    public boolean deleteSubscription(Long id) {
        if (subscriptionRepository.existsById(id)) {
            subscriptionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<SubscriptionDto> getSubscriptionsByType(SubscriptionType type) {
        return subscriptionRepository.findByType(type).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionDto> getSubscriptionsExpiringBefore(LocalDateTime date) {
        return subscriptionRepository.findByEndDateBeforeAndIsActive(date, true).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int renewSubscriptions() {
        LocalDateTime now = LocalDateTime.now();
        List<Subscription> subscriptionsToRenew = subscriptionRepository.findSubscriptionsToRenew(now);
        
        for (Subscription subscription : subscriptionsToRenew) {
            // Logique de renouvellement: calculer la nouvelle date de fin en fonction du type
            LocalDateTime newEndDate;
            if (subscription.getType() == SubscriptionType.MONTHLY) {
                newEndDate = subscription.getEndDate().plusMonths(1);
            } else if (subscription.getType() == SubscriptionType.YEARLY) {
                newEndDate = subscription.getEndDate().plusYears(1);
            } else {
                continue; // Ne pas renouveler les abonnements gratuits
            }
            
            subscription.setEndDate(newEndDate);
            subscriptionRepository.save(subscription);
        }
        
        return subscriptionsToRenew.size();
    }

    // Méthodes de conversion entre entité et DTO
    private Subscription convertToEntity(SubscriptionDto dto) {
        Subscription entity = new Subscription();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setType(dto.getType());
        entity.setStartDate(dto.getStartDate() != null ? dto.getStartDate() : LocalDateTime.now());
        entity.setEndDate(dto.getEndDate());
        entity.setActive(dto.isActive());
        entity.setAutoRenew(dto.isAutoRenew());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    private SubscriptionDto convertToDto(Subscription entity) {
        return new SubscriptionDto(
                entity.getId(),
                entity.getUserId(),
                entity.getType(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.isActive(),
                entity.isAutoRenew(),
                entity.getPrice()
        );
    }

    private void updateSubscriptionFromDto(Subscription entity, SubscriptionDto dto) {
        if (dto.getUserId() != null) {
            entity.setUserId(dto.getUserId());
        }
        if (dto.getType() != null) {
            entity.setType(dto.getType());
        }
        if (dto.getStartDate() != null) {
            entity.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            entity.setEndDate(dto.getEndDate());
        }
        entity.setActive(dto.isActive());
        entity.setAutoRenew(dto.isAutoRenew());
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }
    }
} 
package com.accessed.miniproject.services;

import com.accessed.miniproject.dto.response.PageResponse;
import com.accessed.miniproject.model.Sector;
import com.accessed.miniproject.repositories.SectorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SectorService {
    SectorRepository sectorRepository;
    public PageResponse<Sector> getAllSectors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Sector> sectors = sectorRepository.findAll(pageable);
        return PageResponse.<Sector>builder()
                .content(sectors.getContent())
                .totalElements(sectors.getTotalElements())
                .totalPages(sectors.getTotalPages())
                .pageNumber(page)
                .pageSize(size)
                .last(sectors.isLast())
                .build();
    }
}

package com.accessed.miniproject.services;

import com.accessed.miniproject.dto.request.ProvincesCreationRequest;
import com.accessed.miniproject.dto.response.ProvincesResponse;
import com.accessed.miniproject.enums.EErrorCode;
import com.accessed.miniproject.exception.AppException;
import com.accessed.miniproject.mapper.ProvincesMapper;
import com.accessed.miniproject.model.Provinces;
import com.accessed.miniproject.repositories.ProvincesRepository;
import com.accessed.miniproject.utils.SlugUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProvincesService {

    ProvincesRepository provincesRepository;
    ProvincesMapper provincesMapper;

    public String findImageByCity(String city) {

        String imageUrl = provincesRepository.findImageByCity(SlugUtil.toSlug(city))
                .orElseThrow(() -> new AppException(EErrorCode.NOT_FOUND));

        return imageUrl;
    }

    public ProvincesResponse createProvinces(ProvincesCreationRequest request) {

        String cityCode = SlugUtil.toSlug(request.getCity());
        boolean existed = provincesRepository.existsByCityCode(cityCode);
        if (existed) {
            throw new AppException(EErrorCode.RESOURCE_EXISTED);
        }

        Provinces newProvinces = provincesMapper.toProvinces(request);
        newProvinces.setCityCode(cityCode);
        provincesRepository.save(newProvinces);

        return provincesMapper.toProvincesResponse(newProvinces);
    }
}

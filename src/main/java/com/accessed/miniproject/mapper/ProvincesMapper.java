package com.accessed.miniproject.mapper;

import com.accessed.miniproject.dto.request.ProvincesCreationRequest;
import com.accessed.miniproject.dto.response.ProvincesResponse;
import com.accessed.miniproject.model.Provinces;
import org.mapstruct.Mapper;

@Mapper
public interface ProvincesMapper {

    Provinces toProvinces(ProvincesCreationRequest request);

    ProvincesResponse toProvincesResponse(Provinces provinces);
}

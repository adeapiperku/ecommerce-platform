package com.ecommerce_platform.service.interf;

import com.ecommerce_platform.dto.AddressDto;
import com.ecommerce_platform.dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto addressDto);
}

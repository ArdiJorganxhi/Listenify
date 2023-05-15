package dev.ardijorganxhi.listenify.utils;

import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PaginationUtilsTest {

    @Test
    public void it_should_return_pageable() {
        PaginationRequest request = new PaginationRequest();

        Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());

        assertEquals(request.getPage() - 1, pageable.getPageNumber());
        assertEquals(request.getSize(), pageable.getPageSize());
        assertEquals(request.getDirection(), pageable.getSort().getOrderFor(request.getSortField()).getDirection());
    }
}

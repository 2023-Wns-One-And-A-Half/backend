package com.oneandahalf.backend.product.presentation;


import static org.springframework.http.HttpStatus.CREATED;

import com.oneandahalf.backend.member.presentation.support.Auth;
import com.oneandahalf.backend.product.application.InterestProductService;
import com.oneandahalf.backend.product.presentation.request.InterestProductRequest;
import com.oneandahalf.backend.product.presentation.request.UnInterestProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/interest-products")
@RestController
public class InterestProductController {

    private final InterestProductService interestProductService;

    @PostMapping
    public ResponseEntity<Void> interest(
            @Auth Long memberId,
            @RequestBody InterestProductRequest request
    ) {
        interestProductService.interest(request.toCommand(memberId));
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unInterest(
            @Auth Long memberId,
            @RequestBody UnInterestProductRequest request
    ) {
        interestProductService.unInterest(request.toCommand(memberId));
        return ResponseEntity.noContent().build();
    }
}

package com.shinsunsu.anofspring.controller;

import com.shinsunsu.anofspring.dto.response.ProductResponse;
import com.shinsunsu.anofspring.service.DislikeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dislikeproduct")
public class DislikeProductController {

    private final DislikeProductService dislikeProductService;

    @PutMapping("/{productId}")
    public ResponseEntity saveDislikeProduct(@PathVariable Long productId, Principal principal){
        return new ResponseEntity(dislikeProductService.dislikeProduct(productId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse.productResponse>> listDislikeProduct(Principal principal) {
        return new ResponseEntity<>(dislikeProductService.listDislikeProduct(principal.getName()), HttpStatus.OK);
    }
}

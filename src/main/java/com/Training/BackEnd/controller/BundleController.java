package com.Training.BackEnd.controller;

import com.Training.BackEnd.dto.BundleRequestDto;
import com.Training.BackEnd.dto.BundleResponseDto;
import com.Training.BackEnd.service.BundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bundles")
public class BundleController {

    @Autowired
    BundleService bundleService;

    @GetMapping
    public List<BundleResponseDto> getBundles() {
        return bundleService.getBundles();
    }

    @GetMapping("/{id}")
    public BundleResponseDto getBundle(@PathVariable("id") int id) {
        return bundleService.getBundle(id);
    }

    @PostMapping
    public ResponseEntity addBundle(@RequestBody final BundleRequestDto bundleDto) {
        bundleService.addBundle(bundleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBundle(@PathVariable("id") int id) {
        bundleService.deleteBundle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity deleteBundles() {
        bundleService.deleteBundles();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/provision")
    public ResponseEntity<Object> bundleProvision(@PathVariable("id") int id) {
        bundleService.provisionBundle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

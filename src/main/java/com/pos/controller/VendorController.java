package com.pos.controller;

import com.pos.dto.VendorDto;
import com.pos.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class VendorController {
    
    private final VendorService vendorService;
    
    @GetMapping
    public ResponseEntity<List<VendorDto>> getAllVendors(@RequestParam(required = false) String search) {
        List<VendorDto> vendors = vendorService.searchVendors(search);
        return ResponseEntity.ok(vendors);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VendorDto> getVendorById(@PathVariable Long id) {
        VendorDto vendor = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendor);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VendorDto> createVendor(@Valid @RequestBody VendorDto vendorDto) {
        VendorDto createdVendor = vendorService.createVendor(vendorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVendor);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VendorDto> updateVendor(@PathVariable Long id, 
                                                @Valid @RequestBody VendorDto vendorDto) {
        VendorDto updatedVendor = vendorService.updateVendor(id, vendorDto);
        return ResponseEntity.ok(updatedVendor);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/stats/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalVendorCount() {
        Long count = vendorService.getTotalVendorCount();
        return ResponseEntity.ok(count);
    }
}
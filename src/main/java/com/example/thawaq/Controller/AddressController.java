package com.example.thawaq.Controller;

import com.example.thawaq.Api.ApiResponse;
import com.example.thawaq.DTO.AddressDTO;
import com.example.thawaq.Model.User;
import com.example.thawaq.Service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;


    @GetMapping("/get")
    public ResponseEntity getAddresses()
    {
        return ResponseEntity.status(200).body(addressService.getAddresses());
    }
    @PostMapping("/add") //v2
    public ResponseEntity addAddress(@Valid @RequestBody AddressDTO addressDTO, @AuthenticationPrincipal User user)
    { //v2
        addressService.addAddress(user.getId(), addressDTO);
        return ResponseEntity.status(200).body(new ApiResponse("address added"));
    }
    @PutMapping("/update") //v2
    public ResponseEntity updateAddress(@Valid@RequestBody AddressDTO addressDTO, @AuthenticationPrincipal User user)
    { //v2
        addressService.updateAddress(user.getId(), addressDTO);
        return ResponseEntity.status(200).body(new ApiResponse("address updated"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAddress(@PathVariable Integer id, @AuthenticationPrincipal User user)
    {
        addressService.deleteAddress(user.getId(), id);
        return ResponseEntity.status(200).body(new ApiResponse("address deleted"));
    }
}

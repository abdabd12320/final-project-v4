package com.example.thawaq.Controller;

import com.example.thawaq.Api.ApiResponse;
import com.example.thawaq.Model.Branch;
import com.example.thawaq.Model.User;
import com.example.thawaq.Service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/branch")
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/get")
    public ResponseEntity getBranches()
    {
        return ResponseEntity.status(200).body(branchService.getBranches());
    }

    @GetMapping("/get-my-branches")
    public ResponseEntity getMyBranches(@AuthenticationPrincipal User user)
    {
        return ResponseEntity.status(200).body(branchService.getMyBranches(user.getId()));
    }

    @PostMapping("/add/{storeID}") //v2
    public ResponseEntity addBranch(@PathVariable Integer storeID, @Valid@RequestBody Branch branch,@AuthenticationPrincipal User user)
    { //v2
        branchService.addBranch(user.getId(), storeID, branch);
        return ResponseEntity.status(200).body(new ApiResponse("Branch added"));
    }
    @PutMapping("/update/{branchID}") //v2
    public ResponseEntity updateBranch(@PathVariable Integer branchID,@Valid@RequestBody Branch branch,@AuthenticationPrincipal User user)
    { //v2
        branchService.updateBranch(user.getId(),branchID,branch);
        return ResponseEntity.status(200).body(new ApiResponse("Branch updated"));
    }
    @DeleteMapping("/delete/{branchID}")
    public ResponseEntity deleteBranch(@PathVariable Integer branchID,@AuthenticationPrincipal User user)
    {
        branchService.deleteBranch(user.getId(), branchID);
        return ResponseEntity.status(200).body(new ApiResponse("Branch deleted"));
    }

    @PutMapping("/open/{storeAdminId}/{branchId}")
    public ResponseEntity openBranch(@PathVariable Integer storeAdminId,@PathVariable Integer branchId){
        branchService.openBranch(storeAdminId, branchId);
        return ResponseEntity.status(200).body("Branch opened");
    }

    @PutMapping("/close/{storeAdminId}/{branchId}")
    public ResponseEntity closeBranch(@PathVariable Integer storeAdminId,@PathVariable Integer branchId){
        branchService.closeBranch(storeAdminId, branchId);
        return ResponseEntity.status(200).body("Branch closed");
    }
}

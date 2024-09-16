package com.example.thawaq.Service;

import com.example.thawaq.Api.ApiException;
import com.example.thawaq.Model.*;
import com.example.thawaq.Repository.BranchRepository;
import com.example.thawaq.Repository.MenuRepository;
import com.example.thawaq.Repository.StoreAdminRepository;
import com.example.thawaq.Repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final StoreRepository storeRepository;
    private final StoreAdminRepository storeAdminRepository;
    private final MenuRepository menuRepository;

    // All CRUD Abdulrahman
    public List<Branch> getBranches()
    {
        return branchRepository.findAll();
    }

    public List<Branch> getMyBranches(Integer saID)
    {
        if(storeAdminRepository.findStoreAdminById(saID) == null)
        {
            throw new ApiException("Store admin not found");
        }
        if(storeAdminRepository.findStoreAdminById(saID).getStore() == null)
        {
            throw new ApiException("Store is empty");
        }
        return branchRepository.findAllByStore(storeAdminRepository.findStoreAdminById(saID).getStore());
    }

    public void addBranch(Integer storeAdminID,Integer storeID, Branch branch) //v2
    {
        Store s = storeRepository.findStoreById(storeID);
        StoreAdmin sa = storeAdminRepository.findStoreAdminById(storeAdminID);
        if(s == null)
        {
            throw new ApiException("Store not found");
        }
        if(sa == null)
        {
            throw new ApiException("Store admin not found");
        }
        if(!sa.getStore().getId().equals(s.getId()))
        {
            throw new ApiException("Not match");
        }
        branch.setStore(s);
        branchRepository.save(branch);
    }
    public void updateBranch(Integer storeAdminID,Integer branchID,Branch branch) //v2
    {
        Branch b = branchRepository.findBranchById(branchID);
        if(b == null)
        {
            throw new ApiException("Branch not found");
        }
        if(storeAdminRepository.findStoreAdminById(storeAdminID) == null)
        {
            throw new ApiException("Store admin not found");
        }
        if(storeAdminRepository.findStoreAdminById(storeAdminID).getStore() == null)
        {
            throw new ApiException("Store is empty");
        }
//        if(storeAdminRepository.findStoreAdminById(storeAdminID).getStore().getId() != storeRepository.findStoreById(storeID).getId())
//        {
//            throw new ApiException("Not match between store admin and store");
//        }
        if(b.getStore().getId() != storeAdminRepository.findStoreAdminById(storeAdminID).getStore().getId())
        {
            throw new ApiException("Not match between branch and store");
        }
        b.setName(branch.getName());
        b.setOpen(branch.isOpen());
        branchRepository.save(b);
    }
    public void deleteBranch(Integer storeAdminID,Integer bID)
    {
        if(storeAdminRepository.findStoreAdminById(storeAdminID) == null)
        {
            throw new ApiException("Store admin not found");
        }
        if(storeAdminRepository.findStoreAdminById(storeAdminID).getStore() == null)
        {
            throw new ApiException("Store is empty");
        }
        if(branchRepository.findBranchById(bID) == null)
        {
            throw new ApiException("Branch not found");
        }
        if(branchRepository.findBranchById(bID).getStore().getId() != storeAdminRepository.findStoreAdminById(storeAdminID).getStore().getId())
        {
            throw new ApiException("Not match between branch and store");
        }
        branchRepository.deleteById(bID);
    }

    public void openBranch(Integer storeAdminId , Integer branchID){
        StoreAdmin s = storeAdminRepository.findStoreAdminById(storeAdminId);
        if (s == null){
            throw new ApiException("Store not found");
        }
        Branch b = branchRepository.findBranchById(branchID);
        if (b == null){
            throw new ApiException("Branch not found");
        }
        if (!b.getStore().equals(s.getStore())){
            throw new ApiException("Store not match");
        }
        b.setOpen(true);
        branchRepository.save(b);
    }
    public void closeBranch(Integer storeAdminId , Integer branchID){
        StoreAdmin s = storeAdminRepository.findStoreAdminById(storeAdminId);
        if (s == null){
            throw new ApiException("Store not found");
        }
        Branch b = branchRepository.findBranchById(branchID);
        if (b == null){
            throw new ApiException("Branch not found");
        }
        if (!b.getStore().equals(s.getStore())){
            throw new ApiException("Store not match");
        }
        b.setOpen(false);
        branchRepository.save(b);
    }

}

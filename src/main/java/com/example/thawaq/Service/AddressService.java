package com.example.thawaq.Service;

import com.example.thawaq.Api.ApiException;
import com.example.thawaq.DTO.AddressDTO;
import com.example.thawaq.Model.Address;
import com.example.thawaq.Model.Branch;
import com.example.thawaq.Repository.AddressRepository;
import com.example.thawaq.Repository.BranchRepository;
import com.example.thawaq.Repository.StoreAdminRepository;
import com.example.thawaq.Repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final BranchRepository branchRepository;
    private final StoreAdminRepository storeAdminRepository;
    private final StoreRepository storeRepository;

    // All CRUD Abdulrahman
    public List<Address> getAddresses()
    {
        return addressRepository.findAll();
    }

    public void addAddress(Integer storeAdminID,AddressDTO addressDTO) //v2
    {
        Branch b = branchRepository.findBranchById(addressDTO.getBranch_id());

        if(storeAdminRepository.findStoreAdminById(storeAdminID) == null)
        {
            throw new ApiException("Store admin not found");
        }
        if(storeAdminRepository.findStoreAdminById(storeAdminID).getStore() == null)
        {
            throw new ApiException("Store is empty");
        }
        if(b == null)
        {
            throw new ApiException("Branch not found");
        }
        if(storeAdminRepository.findStoreAdminById(storeAdminID).getStore().getId() != b.getStore().getId())
        {
            throw new ApiException("Not match");
        }

        Address a = new Address(null,addressDTO.getCity(), addressDTO.getStreet(), b);

        addressRepository.save(a);
    }

    public void updateAddress(Integer storeAdminID,AddressDTO addressDTO) //v2
    {
        Address a = addressRepository.findAddressById(addressDTO.getBranch_id());

        if(storeAdminRepository.findStoreAdminById(storeAdminID) == null)
        {
            throw new ApiException("Store admin not found");
        }
        if(storeAdminRepository.findStoreAdminById(storeAdminID).getStore() == null)
        {
            throw new ApiException("Store is empty");
        }
        if(a == null)
        {
            throw new ApiException("Address not found");
        }
        if(storeAdminRepository.findStoreAdminById(storeAdminID).getStore().getId() != a.getBranch().getStore().getId())
        {
            throw new ApiException("Not match");
        }

        a.setCity(addressDTO.getCity());
        a.setStreet(addressDTO.getStreet());
        addressRepository.save(a);
    }
    public void deleteAddress(Integer storeAdminID,Integer id)
    {
        if(storeAdminRepository.findStoreAdminById(storeAdminID) == null)
        {
            throw new ApiException("Store admin not found");
        }
        if(addressRepository.findAddressById(id) == null)
        {
            throw new ApiException("Address not found");
        }
        if(storeAdminRepository.findStoreAdminById(storeAdminID).getStore().getId() != addressRepository.findAddressById(id).getBranch().getStore().getId())
        {
            throw new ApiException("Not match");
        }
        addressRepository.deleteById(id);
    }
}

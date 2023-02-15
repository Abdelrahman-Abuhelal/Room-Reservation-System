package com.example.roomreservation.service;

import com.example.roomreservation.model.branch.Branch;
import com.example.roomreservation.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch addBranch(Branch branch){
       return branchRepository.save(branch);
    }

    public List<Branch> getAllBranches(){
        return branchRepository.findAll();
    }

    public Branch getBranchById(Long id){
        return branchRepository.findById(id).orElseThrow();
    }

}

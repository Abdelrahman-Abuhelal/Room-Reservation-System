package com.example.roomreservation.service;

import com.example.roomreservation.exception.branch.BranchNotFoundException;
import com.example.roomreservation.exception.branch.NoSuchBranchException;
import com.example.roomreservation.model.branch.Branch;
import com.example.roomreservation.model.branch.BranchName;
import com.example.roomreservation.repository.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch addBranch(Branch branch){

        if (branch.getName()== BranchName.BETHLEHEM || branch.getName()==BranchName.NABLUS || branch.getName()==BranchName.RAMALLAH){
            return branchRepository.save(branch);
        }
        String message="You Should Enter RAMALLAH ,BETHLEHEM or NABLUS";
        throw new NoSuchBranchException(message);
    }

    public List<Branch> getAllBranches(){
        return branchRepository.findAll();
    }

    public Branch getBranchByName(BranchName name){
    return  branchRepository.findByName(name).orElseThrow(()->new BranchNotFoundException(String.format("Branch with the name %s isn't found",name)));
    }

    public Branch getBranchById(Long id){
        return branchRepository.findById(id).orElseThrow(()->new BranchNotFoundException(String.format("Branch with the id %s isn't found",id)));
    }

    public String deleteBranchById(Long id){
        String message=String.format("the branch with the id %s is deleted: ",id);
        branchRepository.deleteById(id);
        return message;
    }


}

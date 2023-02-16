package com.example.roomreservation.controller;

import com.example.roomreservation.model.branch.Branch;
import com.example.roomreservation.model.room.Room;
import com.example.roomreservation.service.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public ResponseEntity<Branch> addBranch(@RequestBody @Valid Branch branch){
        Branch addedBranch = branchService.addBranch(branch);
        return new ResponseEntity<>(addedBranch, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches(){
       List<Branch> allBranches= branchService.getAllBranches();
        return new ResponseEntity<>(allBranches, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id){
        Branch selectedBranch= branchService.getBranchById(id);
        return new ResponseEntity<>(selectedBranch, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBranchById(@PathVariable Long id){
        String message= branchService.deleteBranchById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}

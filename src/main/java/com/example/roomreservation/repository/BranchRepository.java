package com.example.roomreservation.repository;

import com.example.roomreservation.model.branch.Branch;
import com.example.roomreservation.model.branch.BranchName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BranchRepository extends JpaRepository<Branch,Long> {

    Optional<Branch>findByName(BranchName name);
}

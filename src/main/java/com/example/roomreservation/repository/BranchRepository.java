package com.example.roomreservation.repository;

import com.example.roomreservation.model.branch.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch,Long> {
}

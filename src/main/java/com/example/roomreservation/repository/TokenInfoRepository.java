package com.example.roomreservation.repository;

import com.example.roomreservation.model.token.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TokenInfoRepository extends JpaRepository<TokenInfo,Long> {
    Optional<TokenInfo> findByRefreshToken(String refreshToken);

}

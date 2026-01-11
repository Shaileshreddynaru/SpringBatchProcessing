package com.Shaileshreddy.SpringBatchProcessing.repository;


import com.Shaileshreddy.SpringBatchProcessing.Entity.BitCoinData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitCoinDataRepo extends JpaRepository<BitCoinData, Integer> {

}

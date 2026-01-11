package com.Shaileshreddy.SpringBatchProcessing.Config;


import com.Shaileshreddy.SpringBatchProcessing.Entity.BitCoinData;
import org.springframework.batch.item.ItemProcessor;

public class BitCoinDataProcessor implements ItemProcessor<BitCoinData,BitCoinData> {

    @Override
    public BitCoinData process(BitCoinData item) throws Exception {
        return item;
    }
}

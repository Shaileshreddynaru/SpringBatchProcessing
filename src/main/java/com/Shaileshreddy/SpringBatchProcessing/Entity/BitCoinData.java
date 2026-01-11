package com.Shaileshreddy.SpringBatchProcessing.Entity;


import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="BitCoinData")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BitCoinData {

    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String date;
    private double close;
    private double high;

    private double low;
    private double open;
    private long Volume;
}

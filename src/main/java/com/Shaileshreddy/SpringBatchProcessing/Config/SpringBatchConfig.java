package com.Shaileshreddy.SpringBatchProcessing.Config;


import com.Shaileshreddy.SpringBatchProcessing.Entity.BitCoinData;
import com.Shaileshreddy.SpringBatchProcessing.repository.BitCoinDataRepo;
import lombok.AllArgsConstructor;
import org.hibernate.engine.internal.Collections;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;


import static org.hibernate.engine.internal.Collections.*;

@Configuration
@AllArgsConstructor
public class SpringBatchConfig {



    private BitCoinDataRepo bitCoinDataRepo;

    @Bean
    public FlatFileItemReader<BitCoinData> reader(){

        FlatFileItemReader<BitCoinData> itemReader = new FlatFileItemReader<BitCoinData>();
         itemReader.setResource(new ClassPathResource("bitcoin.csv"));

        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }


    private LineMapper<BitCoinData> lineMapper() {
        DefaultLineMapper<BitCoinData> lineMapper=new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer=new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("Date","Close","High","Low","Open","Volume");

        BeanWrapperFieldSetMapper<BitCoinData> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BitCoinData.class);
        fieldSetMapper.setCustomEditors(
                java.util.Collections.singletonMap(
                        Date.class,
                        new CustomDateEditor(
                                new SimpleDateFormat("yyyy-MM-dd"),false)));
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
   public BitCoinDataProcessor processor(){

        return new BitCoinDataProcessor();
   }


   @Bean
   public RepositoryItemWriter<BitCoinData> writer(){
        RepositoryItemWriter<BitCoinData> itemWriter=new RepositoryItemWriter<>();
        itemWriter.setRepository(bitCoinDataRepo);
        itemWriter.setMethodName("save");
        return itemWriter;
   }

   @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("csv-step", jobRepository)
                .<BitCoinData,BitCoinData>chunk(100,transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();

   }

   @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("csv-job", jobRepository).flow(step(jobRepository,transactionManager)).build().build();
   }


}

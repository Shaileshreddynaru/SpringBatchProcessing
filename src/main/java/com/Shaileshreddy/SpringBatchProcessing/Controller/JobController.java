package com.Shaileshreddy.SpringBatchProcessing.Controller;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
   public JobLauncher jobLauncherApplicationRunner;

    @Autowired
   private Job job;

    @PostMapping("/importToDatabase")
    public void importCSVToDatabase(){
        JobParameters jobParameter=new JobParametersBuilder()
                .addLong("StartAt" , System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncherApplicationRunner.run(job, jobParameter);
        } catch (Exception ex)  {
            ex.printStackTrace();
        }

    }

}

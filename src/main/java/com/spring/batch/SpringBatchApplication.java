package com.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * packageName    : com.spring.batch
 * fileName       : SpringBatchApplication
 * author         : Jae-Yoon Lee
 * date           : 2022/05/23
 * description    :
 * The @EnableBatchProcessing annotation adds autoconfiguration for Spring Batch to an application and automatically creates beans for a JobRepository, JobLauncher, JobRegistry, PlatformTransactionManager, JobBuilderFactory and StepBuilderFactory.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/23        jaeyoonlee       최초 생성
 */
@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchApplication {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job delivePackageJob(){
        return this.jobBuilderFactory.get("delivePackageJob").start(packageItemStep()).build();
    }

    @Bean
    public Step packageItemStep() {
        return this.stepBuilderFactory.get("packgeItemStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("the item has been packaged");
            return RepeatStatus.FINISHED;
        }).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchApplication.class, args);
    }
}

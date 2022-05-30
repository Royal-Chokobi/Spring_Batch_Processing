package com.spring.batch.buildingFlows;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.spring.batch.buildingFlows
 * fileName       : ConditionalFlows
 * author         : Jae-Yoon Lee
 * date           : 2022/05/31
 * description    : Spring Batch Building Conditional flows
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/05/31        Jae-Yoon Lee       최초 생성
 */

@Configuration
@EnableBatchProcessing
public class ConditionalFlows {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowsJob(){
        return jobBuilderFactory.get("flowsJob")
                .start(stepBase())
                .next(stepOne())
                .on("FAILED").to(stepTwo())
                .from(stepOne())
                .on("*").to(stepThree()).end()
                .build();
    }

    @Bean
    public Step stepBase(){
        return stepBuilderFactory.get("Step-Base").tasklet((stepContribution, chunkContext) -> {
            System.out.println(">>>>>>>>>>>> Step Start >>>>>>>>>>>>");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step stepOne(){
        return stepBuilderFactory.get("Step-1").tasklet((stepContribution, chunkContext) -> {
            System.out.println(">>>>>> 1. >>>>>> Step-1");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step stepTwo(){
        boolean exp = true;
        return stepBuilderFactory.get("Step-2").tasklet((stepContribution, chunkContext) -> {
            if(exp) {
                throw new RuntimeException();
            }
            System.out.println(">>>>>> 2. >>>>>> Step-2");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step stepThree(){
        return stepBuilderFactory.get("Step-3").tasklet((stepContribution, chunkContext) -> {
            System.out.println(">>>>>> 3. >>>>>> Step-3");
            return RepeatStatus.FINISHED;
        }).build();
    }

}

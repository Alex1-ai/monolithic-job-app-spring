package com.chidi.firstjobapp.job;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobService {
    ResponseEntity<List<Job>> getAllJobs();


    ResponseEntity<Job> createJob(Job job);

    ResponseEntity<Job> getJobById(Long id);

    boolean deleteJobById(Long id);

    ResponseEntity<String> updateJob(Long id, Job updatedJob);
}

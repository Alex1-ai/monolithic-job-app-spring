package com.chidi.firstjobapp.job.impl;

import com.chidi.firstjobapp.job.Job;
import com.chidi.firstjobapp.job.JobRepository;
import com.chidi.firstjobapp.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepo;

    @Autowired
    public JobServiceImpl(JobRepository jobRepo) {
        this.jobRepo = jobRepo;
    }

    @Override
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobRepo.findAll();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Job> createJob(Job job) {
        Job savedJob = jobRepo.save(job);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Job> getJobById(Long id) {
        Optional<Job> jobOpt = jobRepo.findById(id);
        if (jobOpt.isPresent()) {
            return new ResponseEntity<>(jobOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public boolean deleteJobById(Long id) {
        if (jobRepo.existsById(id)) {
            try {
                jobRepo.deleteById(id);
                return true;
            } catch (Exception e) {
                // Optionally log the exception
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public ResponseEntity<String> updateJob(Long id, Job updatedJob) {
        Optional<Job> existingJobOpt = jobRepo.findById(id);
        if (existingJobOpt.isPresent()) {
            Job existingJob = existingJobOpt.get();
            existingJob.setTitle(updatedJob.getTitle());
            System.out.println(existingJob);
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setMinSalary(updatedJob.getMinSalary());
            existingJob.setMaxSalary(updatedJob.getMaxSalary());
            existingJob.setLocation(updatedJob.getLocation());
            jobRepo.save(existingJob);
            return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
        }
    }
}

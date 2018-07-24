package by.bogdan.springbatch.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/load")
class LoadController(val jobLauncher: JobLauncher,
                     val job: Job) {

    private val log: Logger = LoggerFactory.getLogger(LoadController::class.java)

    @GetMapping
    fun load(): BatchStatus {
        val params = HashMap<String, JobParameter>()
        params["time"] = JobParameter(System.currentTimeMillis())
        val jobParams = JobParameters(params)
        val jobExecution = jobLauncher.run(job, jobParams)

        log.info("JobExecution: $jobExecution")
        log.info("Batch is Running...")
        while (jobExecution.isRunning) {
            Thread.sleep(10)
        }
        log.info("Batch ended")
        return jobExecution.status
    }
}
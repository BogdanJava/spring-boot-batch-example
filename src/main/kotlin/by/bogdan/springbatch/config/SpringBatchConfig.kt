package by.bogdan.springbatch.config

import by.bogdan.springbatch.model.User
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.LineMapper
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@EnableBatchProcessing
@Configuration
class SpringBatchConfig {

    @Bean
    fun job(jobBuilderFactory: JobBuilderFactory,
            stepBuilderFactory: StepBuilderFactory,
            itemReader: ItemReader<User>,
            itemProcessor: ItemProcessor<User, User>,
            itemWriter: ItemWriter<User>): Job {
        val step = stepBuilderFactory.get("ETL-file-load")
                .chunk<User, User>(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build()

        return jobBuilderFactory.get("ETL-Load")
                .incrementer(RunIdIncrementer())
                .start(step)
                .build()
    }

    @Bean
    fun itemReader(@Value("\${input}") resource: Resource): FlatFileItemReader<User> {
        val itemReader = FlatFileItemReader<User>()
        itemReader.setResource(resource)
        itemReader.setName("CSV-Reader")
        itemReader.setLinesToSkip(1)
        itemReader.setLineMapper(lineMapper())
        return itemReader
    }

    @Bean
    fun lineMapper(): LineMapper<User>? {
        val lineTokenizer = DelimitedLineTokenizer()
        lineTokenizer.setDelimiter(",")
        lineTokenizer.setStrict(false)
        lineTokenizer.setNames(arrayOf("id", "name", "department", "salary"))
        val fieldSetMapper = BeanWrapperFieldSetMapper<User>()
        fieldSetMapper.setTargetType(User::class.java)
        val lineMapper = DefaultLineMapper<User>()
        lineMapper.setLineTokenizer(lineTokenizer)
        lineMapper.setFieldSetMapper(fieldSetMapper)
        return lineMapper
    }
}
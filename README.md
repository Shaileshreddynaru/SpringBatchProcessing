# Spring Batch Processing — Bitcoin Records Import

This Spring Boot + Spring Batch project imports ~4,000 Bitcoin records into a database using a batch job.

## Overview

The application reads a data file containing Bitcoin records, processes each record (validations / transformations), and writes them in bulk to a relational database using Spring Batch for efficient, restartable processing.

## Features

- Batch import of ~4k Bitcoin records
- Chunked processing for memory efficiency
- Configurable reader/writer and transaction/chunk size
- Restartability and fault tolerance via Spring Batch

## Prerequisites

- Java 17
- Maven 3.6+
- A relational database (MySQL). The project is configured to use the datasource in `src/main/resources/application.properties`.

## Configuration

Update the datasource and job properties in `src/main/resources/application.properties:

- `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password` — database connection
- `batch.input-file` — path to the input file (e.g. `classpath:bitcoin.csv` or an absolute path)
- `spring.batch.job.enabled` — set to `false` to disable auto-start if you prefer manual triggering

package com.kainos.ea.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.JobRoles;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.core.JobRequestValidator;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobRoleDao;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JobRequestValidatorTest {
  private final JobRequestValidator jobRequestValidator = new JobRequestValidator();
  DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
  JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
  Connection conn;


  @Test
  void checkJobRoleIdExists_shouldNotThrowException_whenValidIdIsQueried() throws
          ActionFailedException, ValidationException {

    List<JobRoles> jobRoles = new ArrayList<>();
    int idToCheck = 1;

    JobRoles jobRoles1 = new JobRoles(1,"Testing Engineer", "Engineering", "Manager");
    JobRoles jobRoles2 = new JobRoles(2,"Testing2 Engineer", "Engineering", "Manager");
    JobRoles jobRoles3 = new JobRoles(3,"Testing3 Engineer", "Engineering", "Manager");

    jobRoles.add(jobRoles1);
    jobRoles.add(jobRoles2);
    jobRoles.add(jobRoles3);

    Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
    Mockito.when(jobRoleDao.getJobRoles(conn)).thenReturn(jobRoles);

    try {
      jobRequestValidator.checkJobRoleIdExists(databaseConnector, jobRoleDao, idToCheck);
    } catch (Exception e) {
      Assertions.fail("Test Failed -> Exception Thrown");
    }
  }

  @Test
  void checkJobRoleIdExists_shouldNotThrowException_whenInValidIdIsQueried() throws
          ActionFailedException {

    int idToCheck = -1;
    List<JobRoles> jobRoles = new ArrayList<>();

    Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
    Mockito.when(jobRoleDao.getJobRoles(conn)).thenReturn(jobRoles);

    assertThrows(ValidationException.class,
            () -> jobRequestValidator.checkJobRoleIdExists(databaseConnector, jobRoleDao, idToCheck));
  }
}

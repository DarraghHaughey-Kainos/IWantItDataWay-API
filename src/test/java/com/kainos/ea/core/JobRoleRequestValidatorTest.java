package com.kainos.ea.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.cli.Credential;
import org.kainos.ea.cli.JobRoleRequest;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.client.DoesNotExistException;
import org.kainos.ea.client.ValidationException;
import org.kainos.ea.core.CredentialValidator;
import org.kainos.ea.core.JobRoleRequestValidator;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JobRoleRequestValidatorTest {

    JobRoleRequestValidator jobRoleRequestValidator = new JobRoleRequestValidator();

    JobRoleRequest jobRoleRequestValid =
            new JobRoleRequest("Engineer", 1, 2, "link@sharepoint.com");

    JobRoleRequest jobRoleRequestInvalidTitleShort =
            new JobRoleRequest("", 1, 2, "link@sharepoint.com");

    JobRoleRequest jobRoleRequestInvalidTitleLong =
            new JobRoleRequest(
                    "hiufehwishiufhiufhshuifashuihfuheiushuifhiughiuhsdiufhuighuhsiuadhiuhgiudhaiuhuihgduihiughiusdghauihgiuhiuahguis", 1, 2, "link@sharepoint.com");
    JobRoleRequest jobRoleRequestInvalidBand =
            new JobRoleRequest("Engineer", 0, 2, "link@sharepoint.com");

    JobRoleRequest jobRoleRequestInvalidCapability =
            new JobRoleRequest("Engineer", 2, 0, "link@sharepoint.com");

    JobRoleRequest jobRoleRequestInvalidLinkShort =
            new JobRoleRequest("Engineer", 2, 2, "");

    JobRoleRequest jobRoleRequestInvalidLinkLong =
            new JobRoleRequest("Engineer", 2, 2,
                    "EngineerEngineerEngineerE" +
                            "ngineerEngineerEngineerEngineerEngineer" +
                            "EngineerEngineerEngineerEngineerEngineerEng" +
                            "ineerEngineerEngineerEngineerEngineerEngineerEngine" +
                            "erEngineerEngineerEngineerEngineerEngineerEngineerEngine" +
                            "erEngineerEngineerEngineerEngineerEngineerEngineerEng" +
                            "ineerEngineerEngineerEngineerEngineerEngineerEngineerEnginee" +
                            "rEngineerEngineerEngineerEngineerEngineerEngineerEngineerEngi" +
                            "neerEngineerEnginEngineerEngineerEngineerEngineerEngineerEngin" +
                            "eerEngineerEngineerEngineerEngineerEngineerEngineereerEngineerEng" +
                            "ineerEngineerEngineerEngineerEngineerEngineer");

    @Test
    void isValidJobRole_shouldReturnNull_whenValidJobRoleProvided()
            throws DoesNotExistException, ActionFailedException {

        String result = jobRoleRequestValidator.isValidJobRole(jobRoleRequestValid);

        assertNull(result);

    }

    @Test
    void isValidJobRole_shouldReturnString_whenShortJobTitleProvided() throws DoesNotExistException, ActionFailedException {

        String result = jobRoleRequestValidator.isValidJobRole(jobRoleRequestInvalidTitleShort);

        assertEquals(result, "The job role title must be at least 5 characters");

    }

    @Test
    void isValidJobRole_shouldReturnString_whenLongJobTitleProvided() throws DoesNotExistException, ActionFailedException {

        String result = jobRoleRequestValidator.isValidJobRole(jobRoleRequestInvalidTitleLong);

        assertEquals(result, "The job role title must be 100 characters or less");

    }

    @Test
    void isValidJobRole_shouldThrowException_whenInvalidBandProvided() {

        assertThrows(DoesNotExistException.class,
                () -> jobRoleRequestValidator.isValidJobRole(jobRoleRequestInvalidBand));

    }

    @Test
    void isValidJobRole_shouldReturnString_whenInvalidCapabilityProvided() {


        assertThrows(DoesNotExistException.class,
                () -> jobRoleRequestValidator.isValidJobRole(jobRoleRequestInvalidCapability));

    }

    @Test
    void isValidJobRole_shouldReturnString_whenShortSharepointLinkProvided() throws DoesNotExistException, ActionFailedException {

        String result = jobRoleRequestValidator.isValidJobRole(jobRoleRequestInvalidLinkShort);

        assertEquals(result, "Sharepoint Link must be at least 15 characters to be a valid link");

    }

    @Test
    void isValidJobRole_shouldReturnString_whenLongSharepointLinkProvided() throws DoesNotExistException, ActionFailedException {

        String result = jobRoleRequestValidator.isValidJobRole(jobRoleRequestInvalidLinkLong);

        assertEquals(result, "Sharepoint Links must be 500 characters or less to be a valid link");

    }
}

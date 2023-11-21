package com.kainos.ea.resources;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.api.BandService;
import org.kainos.ea.cli.Band;
import org.kainos.ea.client.ActionFailedException;
import org.kainos.ea.resources.BandController;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BandControllerTest {
    BandService bandService = Mockito.mock(BandService.class);
    BandController bandController = new BandController(bandService);

    @Test
    void bandController_shouldReturn500Response_whenBandRoleServiceThrowsActionFailedException() throws ActionFailedException {
        int expectedStatusCode = 500;

        Mockito.doThrow(ActionFailedException.class).when(bandService).getBands();

        Response response = bandController.getBands();

        assertEquals(response.getStatus(), expectedStatusCode);
    }

    @Test
    void bandController_shouldReturn200Response_whenBandServiceDoesNotThrowException() throws ActionFailedException {
        int expectedStatusCode = 200;

        Band band1 = new Band(1,"Manager");
        Band band2 = new Band(2,"Manager");
        Band band3 = new Band(3,"Manager");

        List<Band> bands = new ArrayList<>();
        bands.add(band1);
        bands.add(band2);
        bands.add(band3);

        Mockito.when(bandService.getBands()).thenReturn(bands);

        Response response = bandController.getBands();

        assertEquals(response.getStatus(), expectedStatusCode);
        assertEquals(response.getEntity(), bands);
    }
}

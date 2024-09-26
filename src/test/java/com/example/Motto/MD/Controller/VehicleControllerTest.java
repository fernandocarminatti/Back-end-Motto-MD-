package com.example.Motto.MD.Controller;

import com.example.Motto.MD.Dto.CreateVehicleDto;
import com.example.Motto.MD.Entity.Vehicle;
import com.example.Motto.MD.Entity.VehicleFactory;
import com.example.Motto.MD.Service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-tests.yml"})
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    VehicleService vehicleService;

    @DisplayName("Return 201 on Successful Vehicle Creation")
    @ParameterizedTest
    @CsvSource({"1, 2000, Just a bike, ABC1D23", "2, 2000, Just a car, ABC1D24", "3, 2000, Just a transportation, ABC1D25"})
    void testCreateVehicle(int vehicleType, String manufactureYear, String modelName, String plateNumber) throws Exception {
        CreateVehicleDto createVehicleDto = new CreateVehicleDto(vehicleType, manufactureYear, modelName, plateNumber);
        Vehicle testVehicle = VehicleFactory.createVehicle(createVehicleDto);
        testVehicle.setId(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        when(this.vehicleService.createVehicle(createVehicleDto)).thenReturn(Optional.of(testVehicle));

        MvcResult requestResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/vehicles")
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(createVehicleDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(requestResult.getResponse().getHeader("Location"), "/api/v1/vehicles/" + plateNumber);
    }

    @DisplayName("Return 400 on Wrong Vehicle Type")
    @ParameterizedTest
    @CsvSource({"7, 2000, Just a bike, ABC1D23", "8, 2000, Just a car, ABC1D24", "9, 2000, Just a transportation, ABC1D25"})
    void testCreateVehicleWithWrongVehicleType(int vehicleType, String manufactureYear, String modelName, String plateNumber) throws Exception {
        CreateVehicleDto createVehicleDto = new CreateVehicleDto(vehicleType, manufactureYear, modelName, plateNumber);
        ObjectMapper objectMapper = new ObjectMapper();
        when(this.vehicleService.createVehicle(createVehicleDto)).thenReturn(Optional.empty());

        MvcResult requestResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/vehicles")
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(createVehicleDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(requestResult.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("Return 400 on bad JSON Format")
    @ParameterizedTest
    @CsvSource({"1, 2000, Just a bike, ABC1D23", "2, 2000, Just a car, ABC1D24", "3, 2000, Just a transportation, ABC1D25"})
    void testCreateVehicleWithBadJSONFormat(int vehicleType, String manufactureYear, String modelName, String plateNumber) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("Type", vehicleType);
        objectNode.put("manufactureYear", manufactureYear);
        objectNode.put("model", modelName);
        objectNode.put("plate", plateNumber);

        when(this.vehicleService.createVehicle(new CreateVehicleDto(vehicleType, manufactureYear, modelName, plateNumber))).thenReturn(Optional.empty());

        MvcResult requestResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/vehicles")
                        .characterEncoding("UTF-8")
                        .content(objectNode.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(requestResult.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        assertEquals(requestResult.getResolvedException().getClass(), HttpMessageNotReadableException.class);
    }

    @DisplayName("Return existing Vehicle from Plate Number")
    @ParameterizedTest
    @CsvSource({"1, 2000, Just a bike, ABC1D23", "2, 2000, Just a car, ABC1D24", "3, 2000, Just a transportation, ABC1D25"})
    void testGetVehicleByPlateNumber(int vehicleType, String manufactureYear, String modelName, String plateNumber) throws Exception {
        Vehicle testVehicle = VehicleFactory.createVehicle(new CreateVehicleDto(vehicleType, manufactureYear, modelName, plateNumber));
        testVehicle.setId(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        when(this.vehicleService.getVehicleByPlateNumber(plateNumber)).thenReturn(Optional.of(testVehicle));

        MvcResult requestResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/vehicles/" + plateNumber)
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }
}

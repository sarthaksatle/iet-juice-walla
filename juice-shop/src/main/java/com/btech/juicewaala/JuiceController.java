package com.btech.juicewaala;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class JuiceController {

    // Juice model class to store multiple parameters for each juice
    static class Juice {
        private String description;
        private double price;
        private String size;

        public Juice(String description, double price, String size) {
            this.description = description;
            this.price = price;
            this.size = size;
        }

        // Getters and Setters
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        public String getSize() { return size; }
        public void setSize(String size) { this.size = size; }
    }

    private Map<String, Juice> juices = new HashMap<>();

    @Operation(summary = "Get List of All Available Juices", description = "Fetches a list of all the juices available in the shop.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of juices")
    })
    @GetMapping("/juices")
    public Map<String, Juice> getJuices() {
        if (juices.isEmpty()) {
            juices.put("Orange", new Juice("Fresh Orange Juice", 5.0, "Medium"));
            juices.put("Apple", new Juice("Fresh Apple Juice", 4.5, "Small"));
            juices.put("Mango", new Juice("Mango Juice", 6.0, "Large"));
        }
        return juices;
    }

    @Operation(summary = "Get Juice by Name", description = "Fetches the details of a specific juice by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the juice details"),
            @ApiResponse(responseCode = "404", description = "Juice not found")
    })
    @GetMapping("/juices/{name}")
    public Juice getJuice(@PathVariable String name) {
        return juices.getOrDefault(name, null);
    }

    @Operation(summary = "Add New Juice", description = "Adds a new juice with details to the shop's menu.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Juice added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/juices")
    public String addJuice(@RequestBody Juice newJuice,
                           @RequestParam String name) {
        juices.put(name, newJuice);
        return "Juice added successfully";
    }

    @Operation(summary = "Update Juice Information", description = "Updates the details of an existing juice by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juice updated successfully"),
            @ApiResponse(responseCode = "404", description = "Juice not found")
    })
    @PutMapping("/juices/{name}")
    public String updateJuice(
            @PathVariable String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = true) Double price,
            @RequestParam(required = false) String size) {
        Juice juice = juices.get(name);
        if (juice != null) {
            if (description != null) juice.setDescription(description);
            if (price != null) juice.setPrice(price);
            if (size != null) juice.setSize(size);
            return "Juice updated successfully";
        } else {
            return "Juice not found";
        }
    }

    @Operation(summary = "Delete Juice by Name", description = "Deletes a juice from the menu by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Juice deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Juice not found")
    })
    @DeleteMapping("/juices/{name}")
    public String deleteJuice(@PathVariable String name) {
        if (juices.containsKey(name)) {
            juices.remove(name);
            return "Juice deleted successfully";
        } else {
            return "Juice not found";
        }
    }

    @GetMapping("/greeting")
    @Operation(summary = "Get a greeting message", description = "Returns a personalized greeting message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved greeting",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public String getGreeting(
            @Parameter(description = "First name of the person to greet", example = "John")
            @RequestParam(value = "firstName", defaultValue = "World", required = true) String firstName,
            @Parameter(description = "Last name of the person to greet", example = "Doe")
            @RequestParam(value = "lastName", required = false) String lastName) {
        return "Hello, " + firstName  + " " + lastName + "!";
    }
}

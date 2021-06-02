package com.cursor.homework19.controller;

import com.cursor.homework19.entity.Laptop;
import com.cursor.homework19.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LaptopController {

    private final LaptopService laptopService;

    @Autowired
    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @GetMapping("/")
    public ResponseEntity<String> rootPage() {
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(startPageMessage());
    }

    @GetMapping("/laptop")
    public ResponseEntity<List<Laptop>> getAllLaptops() {
        var allLaptops = laptopService.findAllLaptops();
        return ResponseEntity.ok(allLaptops);
    }

    @GetMapping(value = "/laptop", params = {"sorted"})
    public ResponseEntity<List<Laptop>> getAllLaptopsSortedDesc(
            @RequestParam(value = "sorted", defaultValue = "desc") String sortingMethod
    ) {
        Sort.Direction direction;
        if (sortingMethod.equals("asc")) {
            direction = Sort.Direction.ASC;
        } else if (sortingMethod.equals("desc")) {
            direction = Sort.Direction.DESC;
        } else {
            return ResponseEntity.badRequest().build();
        }
        var allLaptopsSortedDesk = laptopService.findAllLaptopsSortedByProducer(direction);
        return ResponseEntity.ok(allLaptopsSortedDesk);
    }

    @GetMapping(value = "/laptop", params = {"used"})
    public ResponseEntity<List<Laptop>> getAllUsedLaptops(
            @RequestParam(value = "used", defaultValue = "true") boolean isUsed
    ) {
        var allUsedLaptops = laptopService.findAllLaptopsByUsed(isUsed);
        return ResponseEntity.ok(allUsedLaptops);
    }

    @GetMapping(value = "/laptop", params = {"minMemorySize"})
    public ResponseEntity<List<Laptop>> getAllByMinMemorySize(
            @RequestParam(value = "minMemorySize", defaultValue = "16") int memorySize
    ) {
        var allLaptopsByMemorySizeAfter = laptopService.findAllLaptopsByMemorySizeAfter(memorySize);
        return ResponseEntity.ok(allLaptopsByMemorySizeAfter);
    }

    private String startPageMessage() {
        return "Hello stranger. Here you can see all available requests:\n\n" +
                "/laptop - returns all laptops\n" +
                "/laptop?sorted=[asc or desc] - returns all laptops sorted ascending or " +
                        "descending by producer(default desc)\n" +
                "/laptop?used=[true or false] - returns all laptops by [used] status(default true)\n" +
                "/laptop?minMemorySize=[number] - returns all laptops with memory size " +
                        "higher than [number](default 16)\n";
    }
}

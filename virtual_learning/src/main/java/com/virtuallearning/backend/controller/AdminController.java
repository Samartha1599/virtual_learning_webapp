package com.virtuallearning.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtuallearning.backend.exception.ResourceNotFoundException;
import com.virtuallearning.backend.model.Admin;
import com.virtuallearning.backend.repository.AdminRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@GetMapping("/admins")
	public List<Admin> getAllAdmins(){
		return adminRepository.findAll();
	}
	
	@PostMapping("/admins")
	public Admin createAdmin(@RequestBody Admin admin) {
		return adminRepository.save(admin);
	}
	
	@PutMapping("/admins/{id}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin adminDetails){
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin not exist with id: "+id));
		admin.setName(adminDetails.getName());
		admin.setEmail(adminDetails.getEmail());
		admin.setPassword(adminDetails.getPassword());
		Admin updatedAdmin = adminRepository.save(admin);
		return ResponseEntity.ok(updatedAdmin); 
	}
	
	@DeleteMapping("/admins/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteAdmin(@PathVariable Long id){
		Admin admin = adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin not exist with id: "+id));
		adminRepository.delete(admin);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response); 
	}

}

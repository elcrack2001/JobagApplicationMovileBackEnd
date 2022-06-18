package com.example.jobagapi.controller;

import com.example.jobagapi.domain.model.Postulant;
import com.example.jobagapi.resource.PostulantResource;
import com.example.jobagapi.resource.SavePostulantResource;
import com.example.jobagapi.domain.service.PostulantService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api")
public class PostulantController {
    @Autowired
    private PostulantService postulantService;

    @Autowired
    private ModelMapper mapper;



    @Operation(summary="Get Postulants", description="Get All Postulants", tags={"postulants"})
    @GetMapping("/postulants")
    public Page<PostulantResource> getAllPostulants(Pageable pageable){
        Page<Postulant> postulantPage = postulantService.getAllPostulants(pageable);
        List<PostulantResource> resources = postulantPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }
    @Operation(summary="Post Postulants", description="Create Postulants", tags={"postulants"})
    @PostMapping("/postulants")
    public PostulantResource createPostulant(@Valid @RequestBody SavePostulantResource resource) {
        Postulant postulant = convertToEntity(resource);
        return convertToResource(postulantService.createPostulant(postulant));
    }

    @Operation(summary="Get PostulantsById", description="Get PostulantsById", tags={"postulants"})
    @GetMapping("/postulants/{id}")
    public PostulantResource getPostulantById(@PathVariable(name = "id") Long postulantId) {
        return convertToResource(postulantService.getPostulantById(postulantId));
    }

    @Operation(summary="Delete Postulant By Id", description="DeletePostulantById", tags={"postulants"})
    @DeleteMapping("/postulants/{postulantId}}")

    public ResponseEntity<?> deletePostulant(@PathVariable Long postulantId) {
        return postulantService.deletePostulant(postulantId);
    }





    private Postulant convertToEntity(SavePostulantResource resource) {
        return mapper.map(resource, Postulant.class);
    }
    private PostulantResource convertToResource(Postulant entity)
    {
        return mapper.map(entity, PostulantResource.class);
    }









}

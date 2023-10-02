package com.a2.newsbytes.tag;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<Map<String, List<Tag>>> getAllTags() {

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("tags", tagService.getAllTags()));

    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Tag>> getTagById(@PathVariable("id") Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("tag", tagService.getTagById(id)));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PostMapping
    public ResponseEntity<Map<String, Tag>> createTag(@RequestBody Tag tag)
    {
        Tag createdTag = tagService.createTag(tag);
        if (createdTag == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("content", createdTag));
    }


    @PreAuthorize("hasAuthority('EDITOR')")
    @PostMapping("/{id}")
    public ResponseEntity<Map<String, Tag>> updateTagById(@PathVariable("id") Long id, @RequestBody Tag updatedTagData)
    {
        Tag updatedTag = tagService.updateTagById(id, updatedTagData);
        if (updatedTag != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("tag", updatedTag));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTagById(@PathVariable("id") Long id)
    {
        tagService.deleteTagById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

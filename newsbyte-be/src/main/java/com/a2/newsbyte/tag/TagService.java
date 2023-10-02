package com.a2.newsbyte.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags()
    {
        List<Tag> tags = new ArrayList<Tag>();
        tagRepository.findAllByOrderById().forEach(Tag -> tags.add(Tag));
        return tags;
    }

    public Tag getTagById(Long id)
    {
        return tagRepository.findById(id).orElse(null);
    }

    public Tag createTag(Tag tag)
    {
        if (tagRepository.existsById(tag.getId())) {
            return null;
        }
        return tagRepository.save(tag);    //create tag
    }

    public Tag updateTagById(Long id, Tag updatedTagData){
        Optional<Tag> oldTagData = tagRepository.findById(id);
        if(oldTagData.isPresent()){
            Tag updatedTag = oldTagData.get();
            updatedTag.setName(updatedTagData.getName());
            updatedTag.setDescription(updatedTagData.getDescription());
            return tagRepository.save(updatedTag);
        }
        return null;
    }
    public void deleteTagById(Long id)
    {
        if(!tagRepository.existsById(id)){
            throw new NoSuchElementException();             //handling this in global handler class
        }
        tagRepository.deleteById(id);
    }
    public Tag getTagByName(String name){
        return tagRepository.findByName(name);
    }
}

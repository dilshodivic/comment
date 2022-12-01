package com.example.service;

import com.example.dto.ContactDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ContactEntity;
import com.example.entity.ProfileEntity;
import com.example.exceptions.ItemNotFoundException;
import com.example.repository.ContactRepository;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public ContactDTO createContact(ContactDTO contactDTO){
        ContactEntity contactEntity = toEntity(contactDTO);
        contactRepository.save(contactEntity);

        contactDTO.setId(contactEntity.getId());
        return contactDTO;
    }
    public List<ContactDTO> getContactList() {
        List<ContactEntity> contactEntities = contactRepository.findAll();

        List<ContactDTO> dtoList = new ArrayList<>();

        for (ContactEntity contactEntity : contactEntities) {
            ContactDTO dto = toDTO(contactEntity);
            dtoList.add(dto);
        }

        return dtoList;
    }
    public ContactDTO getContactById(Integer id) {
        ContactEntity contactEntity = contactRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
        ContactDTO contactDTO = toDTO(contactEntity);
        return contactDTO;
    }
    public Boolean deleteById(Integer id){
        getContactById(id);
        contactRepository.deleteById(id);
        return true;
    }
    public ContactDTO updateContact(ContactDTO contactDTO, Integer id) {
        ContactEntity contactEntity = get(id);
        contactEntity.setName(contactDTO.getName());
        ContactEntity entity = contactRepository.save(contactEntity);
        return toDTO(entity);
    }
    public ContactDTO getContactByProfileId(Integer profileId) {
        profileRepository.findById(profileId).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
        ContactEntity contact = contactRepository.findByProfile_Id(profileId);
        return toDTO(contact);
    }
    public ContactDTO getContactByProfileEntity(Integer profileId){
        ProfileEntity profileEntity = profileRepository.findById(profileId).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
        ContactEntity contact = contactRepository.findByProfile(profileEntity);
        return toDTO(contact);
    }


    public ContactDTO toDTO(ContactEntity contactEntity) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contactEntity.getId());
        contactDTO.setName(contactEntity.getName());
        return contactDTO;
    }
    public ContactEntity toEntity(ContactDTO contactDTO){
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setName(contactDTO.getName());
        return contactEntity;
    }

    public ContactEntity get(Integer id){
        return contactRepository.findById(id).orElseThrow(()->{
            throw new ItemNotFoundException("Item not found");
        });
    }
}

package com.example.dto;

import com.example.entity.ContactEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;

    private Integer contactId;
    private ContactDTO contact;

    public ProfileDTO(Integer id, String name, String surname, Integer contactId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.contactId = contactId;
    }
}

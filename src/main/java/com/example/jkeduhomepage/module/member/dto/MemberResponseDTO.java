package com.example.jkeduhomepage.module.member.dto;

import com.example.jkeduhomepage.module.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponseDTO {
    private Long id;

    private String loginId;

    private String email;

    private String role;

    private String name;

    private String phone;

    private String status;

    private String createdDate;

    private String updatedDate;

    public static MemberResponseDTO saveMember(Member member){
        return MemberResponseDTO.builder()
                .loginId(member.getLoginId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .build();
    }

    public static MemberResponseDTO choiceMember(Member member){
        return MemberResponseDTO.builder()
                .loginId(member.getLoginId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .status(String.valueOf(member.getStatus()))
                .role(String.valueOf(member.getRole()))
                .createdDate(String.valueOf(member.getCreateDate()))
                .updatedDate(String.valueOf(member.getUpdateDate()))
                .build();
    }

    public static List<MemberResponseDTO> listMember(List<Member> memberList){
        List<MemberResponseDTO> memberResponseDTOList=new ArrayList<>();
        memberList.forEach(
                ml->{
                    MemberResponseDTO memberResponseDTO=new MemberResponseDTO();
                    memberResponseDTO.setId(ml.getId());
                    memberResponseDTO.setLoginId(ml.getLoginId());
                    memberResponseDTO.setName(ml.getName());
                    memberResponseDTO.setPhone(ml.getPhone());
                    memberResponseDTO.setEmail(ml.getEmail());
                    memberResponseDTO.setStatus(String.valueOf(ml.getStatus()));
                    memberResponseDTO.setCreatedDate(String.valueOf(ml.getCreateDate()));
                    memberResponseDTO.setUpdatedDate(String.valueOf(ml.getUpdateDate()));
                    memberResponseDTOList.add(memberResponseDTO);
                });
        return memberResponseDTOList;
    }

}

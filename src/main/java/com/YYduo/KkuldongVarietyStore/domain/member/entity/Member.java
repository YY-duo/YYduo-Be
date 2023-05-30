package com.YYduo.KkuldongVarietyStore.domain.member.entity;

import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.entity.Guestbook;
import com.YYduo.KkuldongVarietyStore.domain.member.dto.MemberPatchDto;
import com.YYduo.KkuldongVarietyStore.global.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String motto;

    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    private LocalDate birthday;

    private int avatar = 1;

    private String instagram;

    private String github;

    private String twitter;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Guestbook> ownedGuestbooks = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    @JsonManagedReference
    private List<Guestbook> writtenGuestbooks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonIgnore
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private Refresh refreshToken;




    public enum BloodType {
        A형,
        B형,
        O형,
        AB형
    }


}
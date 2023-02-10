package com.example.jkeduhomepage.module.article.entity;

import com.example.jkeduhomepage.module.common.utility.Basetime;
import com.example.jkeduhomepage.module.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Article extends Basetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "url")
    private String url;

    @Column(name = "thumbnail_image_url")
    private String thumbnail_image_url;

    @ManyToOne
    @JoinColumn(name="member_id", referencedColumnName = "id")
    private Member member;

}

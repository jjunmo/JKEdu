package com.example.jkeduhomepage.module.article.entity;

import com.example.jkeduhomepage.module.common.enums.Category;
import com.example.jkeduhomepage.module.common.utility.Basetime;
import com.example.jkeduhomepage.module.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name="member_id", referencedColumnName = "id")
    private Member member;
//
//    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    @JoinColumn(name="uploadFile_id",referencedColumnName = "id")
//    private List<UploadFile> uploadFileList = new ArrayList<>();


}

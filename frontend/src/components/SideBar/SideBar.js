import styled from "styled-components";
import { useLocation } from "react-router-dom";

const SideDiv = styled.div`
  position: fixed;
  right: 5vw;
  top: 250px;
  z-index: 2;

  @media screen and (max-width: 768px) {
    top: 125px;
    right: 2.5vw;
  }
`;

const Menu = styled.div`
  width: 70px;
  height: 290px;
  border-radius: 10px;
  background-color: #f4eeff;

  @media screen and (max-width: 768px) {
    width: 35px;
    height: 145px;
  }
`;

const ImgDiv = styled.div`
  padding-top: 20px;

  @media screen and (max-width: 768px) {
    padding-top: 10px;
  }
`;

const GoKakao = styled.a.attrs(() => ({
  href: "https://www.naver.com",
  target: "_blank",
  rel: "noopener noreferrer",
}))`
  display: block;
  height: 70px;
  background-image: url("/img/aside-logo/kakao-icon.png");
  background-size: 70px;

  @media screen and (max-width: 768px) {
    height: 35px;
    background-size: 35px;
  }
`;

const GoBlog = styled(GoKakao).attrs(() => ({
  href: "https://m.blog.naver.com/PostList.naver?blogId=jkoreaeie88",
}))`
  background-image: url("/img/aside-logo/blog-icon.png");
`;

const GoInsta = styled(GoKakao).attrs(() => ({
  href: "https://www.instagram.com/jk_haeundae_campus",
}))`
  background-image: url("/img/aside-logo/insta-icon.png");
`;

const SideBar = () => {
  const location = useLocation();

  // 주소가 아래와 같을 때만 SiderBar 표시
  if (
    location.pathname === "/" ||
    location.pathname === "/login" ||
    location.pathname === "/sign-up" ||
    location.pathname === "/main" ||
    location.pathname === "/payment"
  ) {
    return (
      <SideDiv>
        <Menu>
          <ImgDiv>
            <GoKakao></GoKakao>
          </ImgDiv>
          <ImgDiv>
            <GoBlog></GoBlog>
          </ImgDiv>
          <ImgDiv>
            <GoInsta></GoInsta>
          </ImgDiv>
        </Menu>
      </SideDiv>
    );
  }
  // 그 외에는 SideBar 미표시
  else {
    return null;
  }
};

export default SideBar;

import styled from "styled-components";
import { Link, useLocation } from "react-router-dom";

import "../../App.css";
// *** 출력용 더미데이터 생성
import dummys from "db/header.json";

const HeaderBar = styled.header`
  display: flex;
  width: 100%;
  height: 80px;
  align-items: center;
  justify-content: center;

  background-color: #1e1d4d;
  text-align: center;
  font-size: 24px;
  @media screen and (max-width: 768px) {
    width: 100%;
    height: 40px;
  }
`;

// 로그인 전에 로고 눌렀을 때
const ImgLink = styled.a.attrs(() => ({
  href: "/",
}))`
  height: 60px;

  > img {
    height: 60px;
    width: 300px;
  }

  @media screen and (max-width: 768px) {
    height: 30px;
    > img {
      height: 30px;
      width: 150px;
    }
  }
`;
// 로그인 후에 로고 클릭 시 메인으로
const ImgLink2 = styled(ImgLink).attrs(() => ({
  href: "/main",
}))``;

const DropDownDiv = styled.div`
  width: 140px;
  text-align: right;
  color: white;
  position: absolute;
  right: 15px;

  &:hover {
    > div {
      display: block;
    }
  }
  @media screen and (max-width: 768px) {
    width: 70px;
    right: 12px;
    font-size: 12px;
  }
`;
const DropDownContent = styled.div`
  display: none;
  width: inherit;
  position: absolute;
  z-index: 1;
  text-align: right;
  font-size: 16px;

  @media screen and (max-width: 768px) {
    font-size: 10px;
  }
`;

const DropDownMenu = styled(Link)`
  height: 40px;
  display: block;
  padding: 10px 5px;
  color: black;
  background-color: #f5f5f5;
  text-decoration: none;
  &:hover {
    background-color: #dbe2ef;
  }

  @media screen and (max-width: 768px) {
    height: 20px;
    padding: 5px 2.5px;
  }
`;

const AccountDiv = styled.div`
  height: 80px;
  display: flex;
  align-items: center;
  position: absolute;
  right: 15px;
  line-height: 80px;
  @media screen and (max-width: 768px) {
    height: 40px;
    line-height: 40px;
    right: 7px;
  }
`;

const LoginDiv = styled.div``;
const SingUpDiv = styled.div`
  ::before {
    content: "|";
    margin: 0 10px;
    color: white;
  }

  @media screen and (max-width: 768px) {
    ::before {
      content: "|";
      color: white;
      margin: 0 5px;
      font-size: 12px;
    }
  }
`;

const LoginLink = styled(Link)`
  color: white;
  text-decoration: none;

  &:hover {
    text-decoration: underline;
  }

  @media screen and (max-width: 768px) {
    font-size: 12px;
  }
`;
const SignUpLink = styled(Link)`
  color: white;
  text-decoration: none;
  &:hover {
    text-decoration: underline;
  }

  @media screen and (max-width: 768px) {
    font-size: 12px;
  }
`;

const Header = () => {
  const location = useLocation();

  const name = dummys.name;

  if (
    location.pathname === "/" ||
    location.pathname === "/login" ||
    location.pathname === "/sign-up"
  ) {
    return (
      <HeaderBar>
        <ImgLink>
          <img src="/img/jk-logo/jk-logo2.png" alt="jk-logo" />
        </ImgLink>
        <AccountDiv>
          <LoginDiv>
            <LoginLink to="/login">로그인</LoginLink>
          </LoginDiv>
          <SingUpDiv>
            <SignUpLink to="/sign-up">회원가입</SignUpLink>
          </SingUpDiv>
        </AccountDiv>
      </HeaderBar>
    );
  }

  // 현재 이 부분이 문제가 있다.
  // 아마 href="/main"이 되면서 main으로 get 요청을 보내면서 발생한 문제이다.
  // 추후에 Link로 고치거나 Server측에서 해당 /main으로 요청하면 index.html을 보여주게 해야한다.
  else {
    return (
      <HeaderBar>
        <ImgLink2>
          <img src="/img/jk-logo/jk-logo2.png" alt="jk-logo" />
        </ImgLink2>
        <DropDownDiv>
          <span>{name}님</span>
          <DropDownContent>
            <DropDownMenu to="/mypage">마이페이지</DropDownMenu>
            <DropDownMenu to="/">로그아웃</DropDownMenu>
          </DropDownContent>
        </DropDownDiv>
      </HeaderBar>
    );
  }
};

export default Header;

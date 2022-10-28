import { useNavigate } from "react-router-dom";
import { useState } from "react";
import styled from "styled-components";

import "App.css";

// 회원정보 db 더미데이터
import userList from "db/login.json";

const Main = styled.div`
  width: 100%;
  // header 만큼
  height: calc(100% - 80px);
  display: flex;
  align-items: center;
  justify-content: center;

  font-size: 1.6rem;

  @media screen and (max-width: 768px) {
    height: calc(100% - 40px);
  }
`;

const Content = styled.div`
  max-width: 40rem;
  border-radius: 2rem;
  font-family: SCDream5;
  font-size: 1.8rem;
  margin: 0 auto;
  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;

const LoginForm = styled.form`
  margin-top: 3rem;

  @media screen and (max-width: 768px) {
    /* margin-top: 1.5rem; */
    width: 90%;
    margin: 0 auto;
  }
`;

const Title = styled.div`
  font-family: SCDream9;
  font-size: 3rem;
  margin: 3rem 0;
  @media screen and (max-width: 768px) {
    font-size: 2rem;
    margin: 1.5rem 0;
  }
`;

const Part = styled.div``;

const Input = styled.input.attrs({
  required: true,
})`
  padding-left: 1.5rem;
  width: 40rem;
  max-width: 40rem;
  height: 4.5rem;
  margin: 1rem auto;

  font-size: 1.8rem;
  border: none;
  border-radius: 1.5rem;
  background-color: #f0f5f9;

  :focus {
    outline: 0.2rem solid black;
  }

  @media screen and (max-width: 768px) {
    width: 100%;
    padding-left: 0.75rem;
  }
`;

const BtnList = styled.div`
  display: flex;
  margin-top: 2rem;
  justify-content: space-between;
  max-width: 40rem;
  @media screen and (max-width: 768px) {
    margin-top: 1rem;
    max-width: 36rem;
  }
`;

const SignInBtn = styled.button.attrs({
  type: "submit",
})`
  width: 19rem;
  max-width: 19rem;
  height: 5.5rem;
  border: none;

  border-radius: 5rem;
  font-size: 2rem;
  background-color: #1e1d4d;
  text-decoration: none;
  color: white;
  cursor: pointer;

  @media screen and (max-width: 768px) {
    max-width: 17rem;
    width: 50%;
    border-radius: 2.5rem;
    font-size: 1.7rem;
  }
`;
const SignUpBtn = styled(SignInBtn)``;

const Login = () => {
  const navigate = useNavigate();

  const [loginInfo, setLoginInfo] = useState({
    id: "",
    pw: "",
  });

  // id, pw 입력을 받는 함수
  const changeInfo = (e) => {
    const { name, value } = e.target;
    setLoginInfo({ ...loginInfo, [name]: value });
  };

  // 로그인 시도 함수
  const trySignIn = (e) => {
    e.preventDefault();

    for (let i = 0; i < userList.length; i++) {
      if (loginInfo.id === userList[i].id && loginInfo.pw === userList[i].pw) {
        navigate("/main");
        break;
      } else if (i === userList.length - 1) {
        alert("로그인 실패");
      }
    }

    // 서버(apache)측에서 rewrite 해줘야함

    // document.location.href = "/main";
  };

  // 회원 가입 페이지로 이동
  const moveSignUp = () => {
    navigate("/sign-up");
  };

  return (
    <Main>
      <Content>
        <LoginForm onSubmit={trySignIn}>
          <Title>로그인</Title>
          <Part>ID</Part>
          <Input onChange={changeInfo} name="id" value={loginInfo.id} />
          <Part>비밀번호</Part>
          <Input
            onChange={changeInfo}
            name="pw"
            type="password"
            value={loginInfo.pw}
          />
          <BtnList>
            <SignInBtn type="submit">Login</SignInBtn>
            <SignUpBtn onClick={moveSignUp}>Join</SignUpBtn>
          </BtnList>
        </LoginForm>
      </Content>
    </Main>
  );
};

export default Login;

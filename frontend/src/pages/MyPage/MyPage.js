import { useNavigate } from "react-router-dom";
import { useState } from "react";
import styled from "styled-components";

import "App.css";

const Main = styled.div`
  width: 60rem;
  max-width: 60rem;

  margin: 3rem auto;

  font-size: 1.6rem;
  font-family: SCDream5;

  @media screen and (max-width: 768px) {
    width: 90%;
  }
`;

const Content = styled.div`
  width: 40rem;
  max-width: 40rem;
  margin: 0 auto;
  box-sizing: border-box;

  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;

const Form = styled.form``;

const Title = styled.div`
  font-family: SCDream5;
  text-align: center;
  font-size: 3rem;

  @media screen and (max-width: 768px) {
    font-size: 2rem;
  }
`;

const Infomation = styled.div``;

const Id = styled.div`
  display: flex;
  height: 4.5rem;
  width: 40rem;
  max-width: 40rem;
  margin: 0.5rem auto 0 auto;
  box-sizing: border-box;
  align-items: center;
  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;
const IdSpan = styled.span`
  width: 15rem;
  max-width: 15rem;

  @media screen and (max-width: 768px) {
  }
`;
const IdDiv = styled.div`
  width: 25rem;
  max-width: 25rem;
  @media screen and (max-width: 768px) {
    width: 40%;
  }
`;

const PwInput = styled.input.attrs(() => ({
  type: "password",
}))`
  box-sizing: border-box;
  padding-left: 1.5rem;
  width: 25rem;
  max-width: 25rem;
  height: 3.5rem;
  font-size: 1.8rem;
  border: 0;
  border-radius: 1.5rem;
  background-color: #f0f5f9;

  :focus {
    outline: none;
    border: 2px solid #35a753;
  }
  @media screen and (max-width: 768px) {
    width: 40%;
  }
`;

const ButtonDiv = styled.div`
  display: flex;
  justify-content: space-between;
`;

const GoBackBtn = styled.button`
  box-sizing: border-box;
  width: 180px;
  height: 40px;
  text-align: center;

  border: none;
  border-radius: 15px;
  font-size: 15px;
  font-family: SCDream5;
  color: white;
  background-color: #1e1d4d;
  text-decoration: none;
  &:hover {
    cursor: pointer;
  }
`;
const ChangeBtn = styled(GoBackBtn)``;
const DeleteAccountBtn = styled(GoBackBtn)`
  background-color: red;
`;

const Delete = styled.div`
  width: 550px;
  max-width: 550px;
  display: flex;
  align-items: center;
  margin: 20px auto 0 auto;

  @media screen and (max-width: 768px) {
    flex-direction: column;
    width: 100%;
  }
`;

const DeleteExplain = styled.div`
  @media screen and (max-width: 768px) {
    margin: 20px auto;
  }
`;

const MyPage = () => {
  const navigate = useNavigate();

  const [pw, setPw] = useState({
    currentPw: "",
    newPw: "",
    newPwConfirm: "",
  });

  // 비밀번호 변경을 클릭했을 때
  const isClickChangePw = (e) => {
    e.preventDefault();
    // 비밀번호 일치하는지 확인하고
    if (pw.newPw === pw.newPwConfirm) {
      window.alert("비밀번호를 성공적으로 변경하셨습니다.");
      navigate("/main");
    } else {
      window.alert("비밀번호가 일치하지 않습니다.\n다시확인해주세요.");
    }
  };

  // 비밀변호 input 변경 시
  const isChangePw = (e) => {
    const { name, value } = e.target;
    setPw({ ...pw, [name]: value });
  };

  // 계정을 삭제하는 함수
  const isDeleteAccount = (e) => {
    e.preventDefault();
    const inputName = window.prompt(
      "정말로 계정을 삭제하시겠습니까?\n삭제하시려면 아이디를 입력해주세요."
    );

    if (inputName === "aa") {
      window.alert("삭제가 완료되었습니다.");
    } else {
      window.alert("아이디를 다시 한번 확인해주세요");
    }
  };

  // 메인 Page로 돌아가는 함수
  const goBackPage = () => {
    navigate("/main");
  };

  return (
    <Main>
      <Content>
        <Form>
          <Title>마이 페이지</Title>
          <Infomation>
            <Id>
              <IdSpan>아이디</IdSpan>
              <IdDiv>adasdasdasdas</IdDiv>
            </Id>
            <Id>
              <IdSpan>현재 비밀번호</IdSpan>
              <PwInput
                onChange={isChangePw}
                name="currentPw"
                value={pw.currentPw}
              ></PwInput>
            </Id>
            <Id>
              <IdSpan>새 비밀번호</IdSpan>
              <PwInput
                onChange={isChangePw}
                name="newPw"
                value={pw.newPw}
              ></PwInput>
            </Id>
            <Id>
              <IdSpan>새 비밀번호 확인 </IdSpan>
              <PwInput
                onChange={isChangePw}
                name="newPwConfirm"
                value={pw.newPwConfirm}
              ></PwInput>
            </Id>
            <Id>
              <IdSpan>전화번호</IdSpan>
              <IdDiv>010-1234-5678</IdDiv>
            </Id>
            <Id>
              <IdSpan>이메일</IdSpan>
              <IdDiv>xxxxxx@naver.com</IdDiv>
            </Id>
            <Id>
              <IdSpan>남은 테스트 횟수</IdSpan>
              <IdDiv>1 회</IdDiv>
            </Id>
          </Infomation>
          <ButtonDiv>
            <GoBackBtn onClick={goBackPage}>Back</GoBackBtn>
            <ChangeBtn onClick={isClickChangePw}>Save</ChangeBtn>
          </ButtonDiv>
        </Form>
      </Content>
      <Delete>
        <DeleteAccountBtn onClick={isDeleteAccount}>계정 삭제</DeleteAccountBtn>
        <DeleteExplain>
          계정 삭제시 잔여 테스트 횟수가 삭제가 삭제됩니다.
        </DeleteExplain>
      </Delete>
    </Main>
  );
};

export default MyPage;

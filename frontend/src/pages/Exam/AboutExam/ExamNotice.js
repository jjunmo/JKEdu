//테스트 유의사항 확인 페이지

import styled from "styled-components";
import { useNavigate } from "react-router-dom";

import "App.css";

const Main = styled.div`
  box-sizing: border-box;
  display: flex;
  width: 1000px;
  margin: 0 auto;
  align-items: center;
  height: calc(100% - 80px);
  font-family: SCDream5;
`;
const Wrapper = styled.div`
  margin: 0 auto;
`;

const Title = styled.div`
  font-size: 30px;
  text-align: center;
`;
const Content = styled.div`
  display: flex;
  font-size: 18px;
  margin: 20px 0;
`;

const BtnDiv = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const NextBtn = styled.button`
  box-sizing: border-box;
  border: none;
  border-radius: 50px;
  width: 180px;
  height: 50px;
  font-family: Jua;
  font-size: 20px;
  background-color: #1e1d4d;
  color: white;
  text-decoration: none;
  cursor: pointer;
`;

const ExamNotice = () => {
  const navigate = useNavigate();

  return (
    <Main>
      <Wrapper>
        <Title>Test Infomation</Title>
        <Content>
          1. 시험은 총 5가지 영역으로 구성됩니다.(Speaking, Reading, Listening,
          Grammar, Writing)
        </Content>
        <Content>2. 총 시험 시간은 약 90분입니다.</Content>
        <Content>
          3. Internet Explorer에서는 제대로 동작하지 않을 수도 있으므로 Chrome
          브라우저를 권장합니다.
        </Content>
        <Content>4. Speaking 시험을 위해 마이크를 준비해주세요.</Content>
        <Content>5. Listening 시험을 위해 오디오 장치를 준비해주세요.</Content>
        <BtnDiv>
          <NextBtn onClick={() => navigate("/select-exam")}>시험 시작</NextBtn>
        </BtnDiv>
      </Wrapper>
    </Main>
  );
};

export default ExamNotice;
